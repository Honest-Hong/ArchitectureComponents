package hong.mason.architecturecomponents.provider

import com.google.firebase.firestore.FirebaseFirestore
import hong.mason.architecturecomponents.data.Post
import io.reactivex.Observable

/**
 * Created by kakao on 2017. 11. 21..
 */
class PostsProvider {
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getPosts(): Observable<List<Post>> {
        return Observable.defer {
            Observable.create<List<Post>> { observer ->
                database.collection(COLLECTION)
                        .get()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val list: MutableList<Post> = ArrayList()
                                it.result.documents.forEach {
                                    val post = Post(
                                            it.getLong("id"),
                                            it.getString("writer"),
                                            it.getString("title"),
                                            it.getString("content"),
                                            it.getLong("writeDate")
                                    )
                                    list.add(post)
                                }
                                observer.onNext(list)
                            } else {
                                observer.onError(Exception("not successful"))
                            }
                        }
                        .addOnFailureListener {
                            observer.onError(it)
                        }
            }

        }
    }

    fun addPost(post: Post) {
        database.collection(COLLECTION)
                .add(post)
    }

    companion object {
        const val COLLECTION = "Posts"
    }
}