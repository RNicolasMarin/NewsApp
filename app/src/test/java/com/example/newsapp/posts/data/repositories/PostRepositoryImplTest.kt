package com.example.newsapp.posts.data.repositories

import com.example.newsapp.core.domain.Result.Error
import com.example.newsapp.core.domain.Result.Success
import com.example.newsapp.posts.data.remote.dtos.PostDetailResponse
import com.example.newsapp.posts.data.remote.dtos.PostResponse
import com.example.newsapp.posts.data.remote.services.PostService
import com.example.newsapp.posts.domain.models.PostComplete
import com.example.newsapp.posts.domain.models.PostSimple
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class PostRepositoryImplTest {

    private lateinit var repository: PostRepositoryImpl
    private val service: PostService = mockk()

    @Before
    fun setUp() {
        repository = PostRepositoryImpl(service)
    }

    @Test
    fun `getPosts returns Success when response is successful and body is not null`() = runBlocking {
        val postList = listOf(PostSimple(1, "Title", "Body"))
        val postListResponse = listOf(PostResponse(1, "Title", "Body"))
        val response: Response<List<PostResponse?>?> = Response.success(postListResponse)

        coEvery { service.getPosts() } answers {
            response
        }

        val result = repository.getPosts()

        assertThat(result is Success).isTrue()
        assertThat(postList).isEqualTo((result as Success).data)
    }

    @Test
    fun `getPosts returns Error when response is not successful`() = runBlocking {
        val response: Response<List<PostResponse?>?> = Response.error(404, "".toResponseBody())

        coEvery { service.getPosts() } answers {
            response
        }

        val result = repository.getPosts()

        assertThat(result is Error).isTrue()
    }

    @Test
    fun `getPosts returns Error when exception is thrown`() = runBlocking {
        coEvery { service.getPosts() } throws RuntimeException("Network error")

        val result = repository.getPosts()

        assertThat(result is Error).isTrue()
    }

    @Test
    fun `getPostDetail returns Success when response is successful and body is not null`() = runBlocking {
        val postDetail = PostComplete(1, "Title", "Url to image", "Published", "History", "Body", "This is a Url", "04/02/2023 13:25:21", "14/03/2023 17:22:20")
        val postDetailResponse = PostDetailResponse(1, "Title", "Url to image", "Published", "History", "Body", "This is a Url", "04/02/2023 13:25:21", "14/03/2023 17:22:20")
        val response: Response<PostDetailResponse?> = Response.success(postDetailResponse)

        coEvery { service.getPostDetail(1) } answers { response }

        val result = repository.getPostDetail(1)

        assertThat(result is Success).isTrue()
        assertThat(postDetail).isEqualTo((result as Success).data)
    }

    @Test
    fun `getPostDetail returns Error when response is not successful`() = runBlocking {
        val response: Response<PostDetailResponse?> = Response.error(404, "".toResponseBody())

        coEvery { service.getPostDetail(1) } answers { response }

        val result = repository.getPostDetail(1)

        assertThat(result is Error).isTrue()
    }

    @Test
    fun `getPostDetail returns Error when exception is thrown`() = runBlocking {
        coEvery { service.getPostDetail(1) } throws RuntimeException("Network error")

        val result = repository.getPostDetail(1)

        assertThat(result is Error).isTrue()
    }
}