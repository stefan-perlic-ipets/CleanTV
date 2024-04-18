package com.perla.cleantv.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.perla.cleantv.R
import com.perla.cleantv.ui.theme.CleanTVTheme
import com.perla.cleantv.ui.view_model.TvViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: TvViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanTVTheme {
                viewModel.initialize()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val initialized = viewModel.initialized.observeAsState()
                    if (initialized.value!!) {
                        val channelNumberState = viewModel.channelNumber.observeAsState()
                        val channelInfo = viewModel.channelInfos[channelNumberState.value!!]
                        TVView(channelInfo.channelUrl)
                        TVControlLayout(viewModel)
                        ChannelInfoLayout(viewModel)
//                        EpgLayout(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun EpgLayout(viewModel: TvViewModel) {
    val scrollState = rememberScrollState()
    var rowHeight by remember { mutableStateOf(100.dp) }
    val animatedHeight by animateDpAsState(targetValue = rowHeight)

    LaunchedEffect(key1 = true) {
        delay(13000L) // Delay for 3 seconds
        rowHeight = 0.dp // Collapse the row
        viewModel.collapseInfo()
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .height(animatedHeight)
                .animateContentSize()
        ) {
            repeat(13) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(100.dp)
                        .background(Color.Gray)
                        .width(150.dp)
                ) {
                    Column {
                        Text(text = "Start wars - Poslednji dzedaji", fontSize = 10.sp)
                        Text(text = "19.00 - 21.00", fontSize = 8.sp)
                    }
                }
            }

        }
    }
}

@Composable
fun ChannelInfoLayout(viewModel: TvViewModel) {
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        Column {
            Box(
                modifier = Modifier.background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Channel number: ${viewModel.channelNumber.value}")
            }
            Text(text = "Info", Modifier.clickable {
                viewModel.expandInfo()
            })
        }
    }
}


@Composable
fun TVControlLayout(viewModel: TvViewModel) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        // Row for control buttons
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            // Previous Button
            IconButton(onClick = { viewModel.previousVideoPressed() }) {
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.cloud), "cloud")
            }
            // Play/Pause Button
            IconButton(onClick = { viewModel.playPressed() }) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Play")
            }

            // Next Button
            IconButton(onClick = { viewModel.nextPressed() }) {
                Icon(Icons.Filled.ArrowForward, contentDescription = "Next")
            }
        }
    }
}

@Composable
fun TVView(uri: String) {
    val localContext = LocalContext.current
    Log.e("TAG", "Recomposing")

    // Initialize the player instance
    val player = remember {
        Log.e("TAG", "Initializing exo player")
        ExoPlayer.Builder(localContext).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            play()
        }
    }

    Log.e("TAG", "Stopping and playing $uri")
    val newMediaItem = MediaItem.fromUri(uri)
    player.stop()
    player.setMediaItem(newMediaItem)
    player.prepare()
    player.play()

    AndroidView(
        modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
        factory = { ctx ->
            PlayerView(ctx).apply {
                this.player = player
            }
        },
        update = { player ->
            player.controllerAutoShow = false
        }
    )

    // Remember to release the player when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }
}
