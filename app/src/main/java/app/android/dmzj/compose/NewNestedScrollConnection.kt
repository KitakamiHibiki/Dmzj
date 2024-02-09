package app.android.dmzj.compose

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RefreshState(
    //协程作用域
    val scope: CoroutineScope,
    //顶部是否可用
    val topEnable: Boolean = true,
    //底部是否可用
    val bottomEnable: Boolean = true,
    //顶部最大高度
    val topHeightMax: Float = 200f,
    //底部最大高度
    val bottomHeightMax: Float = 200f,
    //正在下拉刷新状态
    var isRefresh: Boolean = false,
    //正在上拉加载状态
    var isLoadMore: Boolean = false,
    //下拉刷新运行函数
    val refreshFunction: () -> Unit = {},
    //上拉加载运行函数
    val loadMoreFunction: () -> Unit = {},

    ) {
    //初始化当前顶部高度
    var topHeight = Animatable(0f)

    //初始化当前底部高度
    var bottomHeight = Animatable(0f)

    //运行函数是否运行
    var isRunning: Boolean by mutableStateOf(false)

    //动画变动顶部高度
    suspend fun topAnimeChangeHeight(AddOffSet: Float) {
        if (!topEnable) return
        if (topHeight.value + AddOffSet >= topHeightMax) {
            topHeight.animateTo(200f)
            return
        }
        if (topHeight.value + AddOffSet <= 0) {
            topHeight.animateTo(0f)
            return
        }
        topHeight.animateTo(topHeight.value + AddOffSet)
    }

    //动画变动底部高度
    suspend fun bottomAnimeChangeHeight(AddOffSet: Float) {
        if (!bottomEnable) return
        if (bottomHeight.value + AddOffSet >= bottomHeightMax) {
            bottomHeight.animateTo(200f)
            return
        }
        if (bottomHeight.value + AddOffSet <= 0) {
            bottomHeight.animateTo(0f)
            return
        }
        bottomHeight.animateTo(bottomHeight.value + AddOffSet)
        Log.i("bottomHeight", "${bottomHeight.value}")
    }

    //松手后的运行内容
    suspend fun Running() {
        isRunning = true
        if (isRefresh) {
            if (topHeight.value >= 0.5 * topHeightMax) {
                topHeight.animateTo(topHeightMax / 2)
                Thread {
                    refreshFunction()
                    scope.launch {
                        topHeight.animateTo(0f)
                    }
                    isRefresh = false
                    isRunning = false
                }.start()
            } else {
                topHeight.animateTo(0f)
                isRefresh = false
                isRunning = false
            }
            return
        }
        if (isLoadMore) {
            if (bottomHeight.value >= 0.5 * bottomHeightMax) {
                bottomHeight.animateTo(bottomHeightMax / 2)
                Thread {
                    try {
                        loadMoreFunction()
                    }catch (ex:Exception){
                        ex.printStackTrace()
                    }
                    scope.launch {
                        bottomHeight.animateTo(0f)
                    }
                    isLoadMore = false
                    isRunning = false
                }.start()
            } else {
                bottomHeight.animateTo(0f)
                isLoadMore = false
                isRunning = false
            }
            return
        }
        isLoadMore = false
        isRefresh = false
        isRunning = false
    }
}

//上下拉刷新框架布局
@Composable
fun Refresh(
    state: RefreshState,
    nestedScrollConnection: RefreshNestedScrollConnection,
    modifier: Modifier = Modifier,
    topBox: @Composable (state: RefreshState, text: String, modifier: Modifier) -> Unit = { a, b, c ->
        TopBox(
            a,
            b,
            c
        )
    },
    bottomBox: @Composable (state: RefreshState, text: String, modifier: Modifier) -> Unit = { a, b, c ->
        BottomBox(
            a,
            b,
            c
        )
    },
    content: @Composable (modifier: Modifier) -> Unit
) {
    Column(
        modifier = modifier
            .nestedScroll(nestedScrollConnection)
    ) {
        Box {
            content(
                modifier = Modifier
            )
            Column {
                topBox(
                    state,
                    if (state.topHeight.value < state.topHeightMax * 0.5) "下拉以刷新" else "松开以刷新",
                    Modifier.height(state.topHeight.value.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                bottomBox(
                    state,
                    if (state.bottomHeight.value < state.bottomHeightMax * 0.5) "上拉以加载" else "松开以加载",
                    Modifier.height(state.bottomHeight.value.dp)
                )
            }
        }
    }
}

//顶部下拉栏
@Composable
fun TopBox(state: RefreshState, text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Yellow)
    ) {
        Column {
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                if (!state.isRunning) {
                    Text(text = text)
                } else {
                    //加载动画
                    AnimatedVisibility(
                        state.isRunning && (state.topHeight.value >= state.topHeightMax / 2),
                        modifier = Modifier.size(50.dp)
                    ) {
                        CircularProgressIndicator()
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

//底部上拉栏
@Composable
fun BottomBox(state: RefreshState, text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Yellow)
    ) {
        Column {
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                if (!state.isRunning) {
                    Text(text = text)
                } else {
                    //加载动画
                    AnimatedVisibility(
                        state.isRunning && (state.bottomHeight.value >= state.bottomHeightMax / 2),
                        modifier = Modifier.size(50.dp)
                    ) {
                        CircularProgressIndicator()
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }

}

//继承NestedScrollConnection，滚动控制
class RefreshNestedScrollConnection(
    private val scrollState: ScrollState,
    private val state: RefreshState
) : NestedScrollConnection {
    //从屏幕中松手后的数据处理
    override suspend fun onPreFling(available: Velocity): Velocity {
        if (state.topHeight.value != 0f) {
            state.isRefresh = true
            state.scope.launch {
                state.Running()
            }
            return available
        }
        if (state.bottomHeight.value != 0f) {
            state.isLoadMore = true
            state.scope.launch {
                state.Running()
            }
            return available
        }
        if (state.isRunning) return available
        if (state.isRefresh || state.isLoadMore) {
            state.scope.launch {
                state.Running()
            }
        }
        if (scrollState.value == 0)
            return available
        return super.onPreFling(available)
    }

    //手仍在屏幕上滑动时的数据处理
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        if (state.isRunning) return available
        state.isRefresh =
            scrollState.value == 0 && source == NestedScrollSource.Drag && available.y > 0

        state.isLoadMore =
            scrollState.value == scrollState.maxValue && source == NestedScrollSource.Drag && available.y < 0

        if (state.topHeight.value != 0f) {
            state.scope.launch {
                state.topAnimeChangeHeight(available.y)
            }
            return available
        }
        if (state.bottomHeight.value != 0f) {
            state.scope.launch {
                state.bottomAnimeChangeHeight(-available.y)
            }
            return available
        }
        if (!state.isRefresh && !state.isLoadMore) return Offset.Zero
        if (state.isRefresh) {
            state.scope.launch {
                state.topAnimeChangeHeight(available.y)
            }
            return available
        }

        if (state.isLoadMore) {
            state.scope.launch {
                state.bottomAnimeChangeHeight(-available.y)
            }

            return available
        }
        return Offset.Zero
    }
}