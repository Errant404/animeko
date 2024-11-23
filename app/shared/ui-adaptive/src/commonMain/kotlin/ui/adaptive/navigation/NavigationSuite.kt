/*
 * Copyright (C) 2024 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.ui.adaptive.navigation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.him188.ani.app.ui.foundation.layout.AniWindowInsets
import me.him188.ani.app.ui.foundation.layout.currentWindowAdaptiveInfo1

/**
 * @see NavigationSuite with Ani modifications:
 * - Added `windowInsets` parameter
 * - Added `navigationRailHeader` parameter
 * - Added `navigationRailItemSpacing` parameter, to better comply M3 design
 */
@Composable
fun AniNavigationSuite(
    modifier: Modifier = Modifier,
    layoutType: NavigationSuiteType =
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo1()),
    colors: NavigationSuiteColors = NavigationSuiteDefaults.colors(),
    navigationRailHeader: @Composable (NavigationRailItemScope.() -> Unit)? = null, // Ani added
    navigationRailFooter: @Composable (NavigationRailItemScope.() -> Unit)? = null, // Ani added
    navigationRailItemSpacing: Dp = 0.dp, // Ani added
    content: NavigationSuiteScope.() -> Unit
) {
    val scope by rememberStateOfItems(content)
    // Define defaultItemColors here since we can't set NavigationSuiteDefaults.itemColors() as a
    // default for the colors param of the NavigationSuiteScope.item non-composable function.
    val defaultItemColors by rememberUpdatedState(NavigationSuiteDefaults.itemColors())

    when (layoutType) {
        NavigationSuiteType.NavigationBar -> {
            NavigationBar(
                modifier = modifier,
                containerColor = colors.navigationBarContainerColor,
                contentColor = colors.navigationBarContentColor,
                windowInsets = AniWindowInsets.forNavigationBar(), // Ani added
            ) {
                scope.itemList.forEach {
                    NavigationBarItem(
                        modifier = it.modifier,
                        selected = it.selected,
                        onClick = it.onClick,
                        icon = { NavigationItemIcon(icon = it.icon, badge = it.badge) },
                        enabled = it.enabled,
                        label = it.label,
                        alwaysShowLabel = it.alwaysShowLabel,
                        colors = it.colors?.navigationBarItemColors
                            ?: defaultItemColors.navigationBarItemColors,
                        interactionSource = it.interactionSource,
                    )
                }
            }
        }

        NavigationSuiteType.NavigationRail -> {
            NavigationRail(
                modifier = modifier,
                containerColor = colors.navigationRailContainerColor,
                contentColor = colors.navigationRailContentColor,
                windowInsets = AniWindowInsets.forNavigationRail(), // Ani added
                header = {
                    navigationRailHeader?.let { lambda ->
                        val itemScope = remember(this) {
                            NavigationRailItemScopeImpl(
                                this,
                                { defaultItemColors }, { navigationRailItemSpacing },
                            )
                        }
                        lambda(itemScope)
                    }
                },
            ) {
                scope.itemList.forEach {
                    NavigationRailItem(
                        modifier = it.modifier.then(Modifier.padding(bottom = navigationRailItemSpacing)),
                        selected = it.selected,
                        onClick = it.onClick,
                        icon = { NavigationItemIcon(icon = it.icon, badge = it.badge) },
                        enabled = it.enabled,
                        label = it.label,
                        alwaysShowLabel = it.alwaysShowLabel,
                        colors = it.colors?.navigationRailItemColors
                            ?: defaultItemColors.navigationRailItemColors,
                        interactionSource = it.interactionSource,
                    )
                }

                Spacer(Modifier.weight(1f))

                if (navigationRailFooter != null) {
                    val itemScope = remember(this) {
                        NavigationRailItemScopeImpl(
                            this,
                            { defaultItemColors }, { navigationRailItemSpacing },
                        )
                    }
                    navigationRailFooter(itemScope)
                }
            }
        }

        NavigationSuiteType.NavigationDrawer -> {
            PermanentDrawerSheet(
                modifier = modifier,
                drawerContainerColor = colors.navigationDrawerContainerColor,
                drawerContentColor = colors.navigationDrawerContentColor,
                windowInsets = AniWindowInsets.forNavigationDrawer(), // Ani added
            ) {
                scope.itemList.forEach {
                    NavigationDrawerItem(
                        modifier = it.modifier,
                        selected = it.selected,
                        onClick = it.onClick,
                        icon = it.icon,
                        badge = it.badge,
                        label = { it.label?.invoke() ?: Text("") },
                        colors = it.colors?.navigationDrawerItemColors
                            ?: defaultItemColors.navigationDrawerItemColors,
                        interactionSource = it.interactionSource,
                    )
                }
            }
        }

        NavigationSuiteType.None -> { /* Do nothing. */
        }
    }
}

private class NavigationRailItemScopeImpl(
    columnScope: ColumnScope,
    private val defaultItemColors: () -> NavigationSuiteItemColors,
    private val navigationRailItemSpacing: () -> Dp,
) : NavigationRailItemScope, ColumnScope by columnScope {
    override val itemColors: NavigationRailItemColors
        get() = defaultItemColors().navigationRailItemColors
    override val itemSpacing: Dp
        get() = navigationRailItemSpacing()
}

@Stable
interface NavigationRailItemScope : ColumnScope {
    val itemColors: NavigationRailItemColors
    val itemSpacing: Dp
}

@Composable
private fun NavigationItemIcon(
    icon: @Composable () -> Unit,
    badge: (@Composable () -> Unit)? = null,
) {
    if (badge != null) {
        BadgedBox(badge = { badge.invoke() }) {
            icon()
        }
    } else {
        icon()
    }
}
