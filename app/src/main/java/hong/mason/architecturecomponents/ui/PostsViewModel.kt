package hong.mason.architecturecomponents.ui

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
class PostsViewModel : ViewModel(), BaseViewModel {
    private val posts : MutableLiveData<List<Post>> = MutableLiveData()
    private val loading : MutableLiveData<Boolean> = MutableLiveData()
    private val error : MutableLiveData<Boolean> = MutableLiveData()
    private val showAddView : MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var provider : PostsProvider

    fun getPosts() : MutableLiveData<List<Post>> = posts
    fun isError() : MutableLiveData<Boolean> = error
    fun isLoading() : MutableLiveData<Boolean> = loading
    fun isShowAddView() : MutableLiveData<Boolean> = showAddView

    override fun onCreate() {
        provider = PostsProvider()
        loading.value = true
        provider.getPosts()
                .subscribeOn(Schedulers.newThread())
                .subscribe ({
                    loading.value = false
                    posts.value = it
                }, {
                    loading.value = false
                    error.value = true
                })
    }

    override fun onDestroy() {
    }

    fun onClickAddButton() {
        Log.d("PostsViewModel", "onClickAddButton")
        showAddView.value = true
    }

    fun onCancel() {
        showAddView.value = false
    }

    fun onSubmit(post: Post) {
        provider.addPost(post)
        onCancel()
    }
}