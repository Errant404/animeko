/*
 * Ani
 * Copyright (C) 2022-2024 Him188
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.him188.ani.app.ui.foundation

import androidx.annotation.CallSuper
import androidx.compose.runtime.RememberObserver
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import me.him188.ani.utils.logging.error
import me.him188.ani.utils.logging.logger
import me.him188.ani.utils.logging.trace

/**
 * 带有 [backgroundScope], 当 [AbstractViewModel] 被 forget 时自动 close scope 以防资源泄露.
 *
 * 因此 [AbstractViewModel] 需要与 compose remember 一起使用, 否则需手动管理生命周期.
 * 在构造 [AbstractViewModel] 时需要考虑其声明周期问题.
 */ // We can't use Android's Viewmodel because it's not available in Desktop platforms. 
abstract class AbstractViewModel : RememberObserver, ViewModel(), HasBackgroundScope {
    val logger by lazy { logger(this::class) }

    private var _backgroundScope = createBackgroundScope()
    override val backgroundScope: CoroutineScope
        get() {
            return _backgroundScope
        }


    private var referenceCount = 0

    @CallSuper
    override fun onAbandoned() {
        referenceCount--
    }

    @CallSuper
    override fun onForgotten() {
        referenceCount--
    }

    @CallSuper
    override fun onRemembered() {
        referenceCount++
        logger.trace { "${this::class.simpleName} onRemembered, refCount=$referenceCount" }
        if (referenceCount == 1) {
            this.init() // first remember
        }
    }

    private fun createBackgroundScope(): CoroutineScope {
        return CoroutineScope(
            CoroutineExceptionHandler { coroutineContext, throwable ->
                logger.error(throwable) { "Unhandled exception in background scope, coroutineContext: $coroutineContext" }
            } + SupervisorJob(),
        )
    }

    /**
     * Called when the view model is remembered the first time.
     */
    protected open fun init() {
    }

    override fun onCleared() {
        backgroundScope.cancel()
        super.onCleared()
    }
}