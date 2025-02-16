package com.example.newsapp.posts.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.R
import com.example.newsapp.core.presentation.ShimmerPlaceholder
import com.example.newsapp.posts.domain.models.Post
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        },
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (state.status) {
            HomeStatus.ERROR -> {
                HomeScreenError(onAction)
            }
            else -> { //HomeStatus.LOADING, HomeStatus.SUCCESS
                HomeScreenLoadingAndSuccess(state)
            }
        }
    }
}

@Composable
fun HomeScreenLoadingAndSuccess(
    state: HomeState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        itemsIndexed(
            items = state.posts,
            key = { _, item -> item.id }
        ) { index, post ->
            PostItem(
                post = post,
                isLoading = state.status == HomeStatus.LOADING,
                modifier = Modifier.fillMaxWidth()
            )
            if (index < state.posts.size - 1) {
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
private fun HomeScreenError(onAction: (HomeAction) -> Unit) {
    Text(
        text = stringResource(id = R.string.news_error),
        fontSize = 25.sp,
        color = MaterialTheme.colorScheme.onSurface,
        style = TextStyle(
            fontWeight = FontWeight.Bold
        )
    )
    Spacer(modifier = Modifier.height(5.dp))
    Button(
        onClick = {
            onAction(HomeAction.RetryInitialPosts)
        }
    ) {
        Text(
            text = stringResource(id = R.string.news_retry),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun PostItem(
    post: Post,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(5.dp)
    ShimmerPlaceholder(
        shape = shape,
        modifier = modifier,
        isLoading = isLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = shape,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = shape
                )
                .padding(5.dp)
        ) {
            Text(
                text = post.title,
                maxLines = 2,
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = post.content,
                maxLines = 5,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = TextStyle(
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
@Preview
fun HomeScreenPreviewLoading() {
    NewsAppTheme {
        HomeScreen(
            state = HomeState(
                posts = (1..10).map {
                    Post(
                        id = it,
                        title = "This is the title $it",
                        content = "This is the content $it"
                    )
                }
            ),
            onAction = {}
        )
    }
}

@Composable
@Preview
fun HomeScreenPreviewError() {
    NewsAppTheme {
        HomeScreen(
            state = HomeState(
                posts = (1..10).map {
                    Post(
                        id = it,
                        title = "This is the title $it",
                        content = "This is the content $it"
                    )
                },
                status = HomeStatus.ERROR
            ),
            onAction = {}
        )
    }
}