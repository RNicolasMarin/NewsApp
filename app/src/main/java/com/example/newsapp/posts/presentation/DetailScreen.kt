package com.example.newsapp.posts.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.newsapp.R

@Composable
fun DetailScreenRoot(
    id: Int,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(id) {
        viewModel.getPostDetails(id)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailScreen(
        state = state
    )
}

@Composable
fun DetailScreen(
    state: DetailState,
    modifier: Modifier = Modifier,
) {
    val post = state.post ?: return
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(5.dp)
    ) {
        Text(
            text = post.title,
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onSurface,
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(5.dp))

        AsyncImage(
            model = post.image,
            contentDescription = "Post Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            SimpleSection(R.string.detail_status, post.status)
            SimpleSection(R.string.detail_category, post.category)
        }
        Spacer(modifier = Modifier.height(5.dp))
        SimpleSection(null, post.content)
        Spacer(modifier = Modifier.height(5.dp))
        SimpleSection(R.string.detail_url, post.url)
        Spacer(modifier = Modifier.height(5.dp))
        SimpleSection(R.string.detail_published_at, post.publishedAt)
        Spacer(modifier = Modifier.height(5.dp))
        SimpleSection(R.string.detail_updated_at, post.updatedAt)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun SimpleSection(
    @StringRes labelRes: Int?,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        labelRes?.let {
            Text(
                text = stringResource(id = it),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = TextStyle(
                fontWeight = FontWeight.Normal
            )
        )
    }
}
