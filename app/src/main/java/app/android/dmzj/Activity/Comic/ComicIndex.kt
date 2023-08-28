package app.android.dmzj.Activity.Comic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.android.dmzj.Compose.Refresh
import app.android.dmzj.Compose.RefreshNestedScrollConnection
import app.android.dmzj.Compose.RefreshState


class ComicIndex {
    @Composable
    fun MainCompose() {
        var list by remember { mutableStateOf(List(40) { "This is $it" }) }
        val scope = rememberCoroutineScope()
        val state = RefreshState(scope,
            refreshFunction = {
                list = List(10) { "This is $it" }
            },
            loadMoreFunction = {
                list = list + List(10) { "This is ${it + list.size}" }
            })
        val scrollState = rememberScrollState()
        val nestedScrollConnection = RefreshNestedScrollConnection(scrollState, state)

        Refresh(state, nestedScrollConnection) {
            Column(
                modifier = it
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
            ) {
                repeat(list.size) { count ->
                    Text(
                        text = list[count], modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(if (count % 2 == 0) Color.Cyan else Color.Yellow)
                    )
                }
            }

        }
    }

    @Composable
    fun TopNavBar() {
        Row(
            modifier = Modifier
                .height(50.dp)
                .width(1000.dp)
                .background(Color.White)
        ) {

        }
    }
}