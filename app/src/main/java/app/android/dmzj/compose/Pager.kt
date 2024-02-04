package app.android.dmzj.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class Pager(private val list: List<ImageBitmap>) {
    private var position = mutableStateOf(0)
    private var screenWidth = 0
    private var offsetX = Animatable(0f)

    @Composable
    fun PagerView(modifier: Modifier = Modifier) {
        screenWidth = LocalConfiguration.current.screenWidthDp
        val scope = rememberCoroutineScope()
        Box(modifier = Modifier.pointerInput(Unit) {
            detectDragGestures(
                onDrag = { change, dragAmount ->
                    change.consumeAllChanges()
                    System.out.println(dragAmount.x)
                    scope.launch {
                        var x = offsetX.value + dragAmount.x / 2
                        if (x > screenWidth)
                            x = screenWidth.toFloat()
                        else if (x < -screenWidth)
                            x = -screenWidth.toFloat()
                        offsetX.animateTo(x, tween(0))
                    }
                },
                onDragEnd = {
                    scope.launch {
                        if (offsetX.value > screenWidth / 2) {
                            subView()
                        } else if (offsetX.value < -screenWidth / 2)
                            addView()
                        else
                            back()
                    }
                }
            )
        }) {
            Surface(modifier = modifier.offset(x = (offsetX.value - screenWidth).dp)) {
                Image(
                    bitmap = list[if (position.value == 0) list.size - 1 else position.value - 1],
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Surface(modifier = modifier.offset(x = offsetX.value.dp)) {
                Image(
                    bitmap = list[position.value],
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Surface(modifier = modifier.offset(x = (offsetX.value + screenWidth).dp)) {
                Image(
                    bitmap = list[(position.value + 1) % list.size],
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }

    }

    suspend fun addView() {
        offsetX.stop()
        offsetX.animateTo(-screenWidth.toFloat(), tween(500))
        position.value = (position.value + 1) % list.size
        offsetX.animateTo(0f, tween(0))
    }

    suspend fun subView() {
        offsetX.stop()
        offsetX.animateTo(screenWidth.toFloat(), tween(500))
        position.value = if (position.value == 0) list.size - 1 else position.value - 1
        offsetX.animateTo(0f, tween(0))
    }

    suspend fun back() {
        offsetX.stop()
        offsetX.animateTo(0f, tween(200))
    }

}