package hong.mason.architecturecomponents.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import hong.mason.architecturecomponents.base.BaseViewModel
import hong.mason.architecturecomponents.data.Post

/**
 * Created by kakao on 2017. 11. 22..
 */
class PostDetailViewModel : ViewModel(), BaseViewModel {
    val post : MutableLiveData<Post> = MutableLiveData()

    fun getPost() : LiveData<Post> = post

    override fun onCreate() {
    }

    override fun onDestroy() {
    }
}