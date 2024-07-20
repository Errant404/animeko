package me.him188.ani.app.ui.subject.episode.video.sidesheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import me.him188.ani.app.ui.foundation.BackgroundScope
import me.him188.ani.app.ui.foundation.HasBackgroundScope
import me.him188.ani.app.ui.foundation.icons.PlayingIcon
import me.him188.ani.app.ui.subject.cache.contentColorForWatchStatus
import me.him188.ani.app.ui.subject.episode.EpisodePresentation
import me.him188.ani.app.ui.subject.episode.video.settings.EpisodeVideoSettingsSideSheet
import kotlin.coroutines.CoroutineContext

private typealias Item = EpisodePresentation

/**
 * 播放页面内选集
 */
@Stable
class EpisodeSelectorState(
    itemsFlow: Flow<List<Item>>,
    currentEpisodeId: Flow<Int>,
    private val onSelect: (Item) -> Unit,
    parentCoroutineContext: CoroutineContext,
) : HasBackgroundScope by BackgroundScope(parentCoroutineContext) {
    /**
     * 可切换的剧集列表
     */
    val items: List<Item> by itemsFlow.produceState(emptyList())

    private val currentEpisodeId by currentEpisodeId.produceState(-1)

    /**
     * 当前选中剧集在 [items] 中的 index. -1 代表未选中
     */
    val currentIndex by derivedStateOf {
        if (this.currentEpisodeId == -1) {
            -1
        } else {
            items.indexOfFirst { it.episodeId == this.currentEpisodeId }
        }
    }

    /**
     * 当前选中的剧集
     */
    val current: Item? by derivedStateOf {
        items.find { it.episodeId == this.currentEpisodeId }
    }

    val hasNextEpisode by derivedStateOf {
        val currentIndex = currentIndex
        currentIndex != -1 && currentIndex < items.lastIndex
                && items[currentIndex + 1].isKnownBroadcast // 仅限下一集开播了
    }

    fun select(item: Item) {
        onSelect(item)
    }

    fun selectNext() {
        val currentIndex = currentIndex
        if (currentIndex != -1 && currentIndex < items.lastIndex) {
            onSelect(items[currentIndex + 1])
        }
    }
}

@Composable
fun EpisodeSelectorSideSheet(
    state: EpisodeSelectorState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    EpisodeVideoSettingsSideSheet(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "选择剧集") },
        closeButton = {
            IconButton(onClick = onDismissRequest) {
                Icon(Icons.Rounded.Close, contentDescription = "关闭")
            }
        },
        modifier = modifier,
    ) {
        val lazyListState = rememberLazyListState()
        // 自动滚动到当前选中的剧集
        LaunchedEffect(true) {
            val currentIndex = snapshotFlow { state.currentIndex }
                .filter { it != -1 }
                .first()
            if (currentIndex != -1) {
                lazyListState.scrollToItem(
                    currentIndex,
                    // 显示半个上个元素
                    scrollOffset = -(lazyListState.layoutInfo.visibleItemsInfo.getOrNull(0)?.size?.div(2) ?: 0),
                )
            }
        }
        LazyColumn(state = lazyListState) {
            itemsIndexed(state.items, key = { _, item -> item.episodeId }) { index, item ->
                val selected = index == state.currentIndex
                val color = contentColorForWatchStatus(item.collectionType, item.isKnownBroadcast)
                ListItem(
                    headlineContent = { Text(item.title, color = color) },
                    Modifier.clickable {
                        state.select(item)
                        onDismissRequest()
                    },
                    leadingContent = {
                        ProvideTextStyle(MaterialTheme.typography.bodyLarge) {
                            Text(item.sort, fontFamily = FontFamily.Monospace, color = color)
                        }
                    },
                    trailingContent = {
                        if (selected) {
                            PlayingIcon(contentDescription = "正在播放")
                        }
                    },
                    colors =
                    if (selected) ListItemDefaults.colors(
                        headlineColor = MaterialTheme.colorScheme.primary,
                        leadingIconColor = MaterialTheme.colorScheme.primary,
                        trailingIconColor = MaterialTheme.colorScheme.primary,
                    )
                    else ListItemDefaults.colors(),
                )

                if (index != state.items.lastIndex) {
                    HorizontalDivider(Modifier.padding(horizontal = 4.dp))
                }
            }
        }
    }
}

