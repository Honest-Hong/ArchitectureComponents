package hong.mason.architecturecomponents.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import hong.mason.architecturecomponents.data.Post
import hong.mason.architecturecomponents.base.BaseViewModel
import hong.mason.architecturecomponents.provider.PostsProvider
import io.reactivex.schedulers.Schedulers

/**
 * Created by kakao on 2017. 11. 21..
 */
class PostListViewModel : ViewModel(), BaseViewModel {
    enum class State {
        LOADING, LOADED, ERROR
    }

    private val posts : MutableLiveData<List<Post>> = MutableLiveData()
    private val state : MutableLiveData<State> = MutableLiveData()
    private val clickedPost : MutableLiveData<Post> = MutableLiveData()
    private lateinit var provider : PostsProvider

    fun getPosts() : LiveData<List<Post>> = posts
    fun getState() : LiveData<State> = state
    fun getClickedPost() : LiveData<Post> = clickedPost

    override fun onCreate() {
        provider = PostsProvider()
        state.value = State.LOADING
        provider.getPosts()
                .subscribeOn(Schedulers.newThread())
                .subscribe ({
                    Log.d("PostListViewModel", "onCreate::$it")
                    state.value = State.LOADED
                    posts.value = it
                }, {
                    state.value = State.ERROR
                })
    }

    override fun onDestroy() {
    }

    fun onSubmit(post: Post) {
        provider.addPost(post)
    }

    fun onClickPost(post: Post) {
        clickedPost.value = post
    }
}