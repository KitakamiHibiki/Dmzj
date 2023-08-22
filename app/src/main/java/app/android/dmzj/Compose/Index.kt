package app.android.dmzj.Compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class Index {
    @Preview
    @Composable
    fun MainCompose() {
        Column {
            TopNavBar()
            Text(text = "Manga")
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