package com.example.newsapp.users.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.R
import com.example.newsapp.core.presentation.ShimmerPlaceholder
import com.example.newsapp.users.domain.models.User

@Composable
fun UsersScreenRoot(
    viewModel: UsersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    UsersScreen(
        state = state,
        onAction = { action ->
            /*when (action) {
                is UsersAction.RetryInitialPosts -> goToDetail(action.id)
                else -> Unit
            }*/
            viewModel.onAction(action)
        },
    )
}

@Composable
fun UsersScreen(
    state: UsersState,
    onAction: (UsersAction) -> Unit,
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
            UsersStatus.ERROR -> {
                UsersScreenError(onAction)
            }
            else -> { //HomeStatus.LOADING, HomeStatus.SUCCESS
                UsersScreenLoadingAndSuccess(
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun UsersScreenLoadingAndSuccess(
    state: UsersState,
    onAction: (UsersAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        itemsIndexed(
            items = state.users,
            key = { _, item -> item.id }
        ) { index, user ->
            UserItem(
                user = user,
                isLoading = state.status == UsersStatus.LOADING,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
                    /*.clickable {
                        if (state.status == HomeStatus.SUCCESS) {
                            onAction(HomeAction.GoToDetail(post.id))
                        }
                    }*/
            )
            if (index < state.users.size - 1) {
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    isLoading: Boolean,
    modifier: Modifier
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
                text = "${user.firstName} ${user.lastName}",
                maxLines = 2,
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            SimpleSection(labelRes = R.string.users_email, value = user.email)
            Spacer(modifier = Modifier.height(5.dp))
            SimpleSection(labelRes = R.string.users_birth_date, value = user.birthDate)
            Spacer(modifier = Modifier.height(5.dp))
            SimpleSection(labelRes = R.string.users_phone, value = user.phone)
            Spacer(modifier = Modifier.height(5.dp))
            SimpleSection(labelRes = R.string.users_website, value = user.website)
        }
    }
}

@Composable
fun SimpleSection(
    @StringRes labelRes: Int,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = stringResource(id = labelRes),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = TextStyle(
                fontWeight = FontWeight.Medium
            )
        )
    }
}


@Composable
fun UsersScreenError(onAction: (UsersAction) -> Unit) {
    Text(
        text = stringResource(id = R.string.users_error),
        fontSize = 25.sp,
        color = MaterialTheme.colorScheme.onSurface,
        style = TextStyle(
            fontWeight = FontWeight.Bold
        )
    )
    Spacer(modifier = Modifier.height(5.dp))
    Button(
        onClick = {
            onAction(UsersAction.RetryUsers)
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