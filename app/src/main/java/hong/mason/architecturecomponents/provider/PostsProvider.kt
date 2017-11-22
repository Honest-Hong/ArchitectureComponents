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
                        .addSnapshotListener { snapshot, exception ->
                            if(exception != null) {
                                exception.printStackTrace()
                                observer.onError(Exception("not successful"))
                            }
                            val list: MutableList<Post> = ArrayList()
                            snapshot.documents.forEach {
                                val post = Post(
                                        it.getLong("id"),
                                        it.getString("writer"),
                                        it.getLong("date"),
                                        it.getLong("writeDate"),
                                        it.getString("title"),
                                        it.getString("content"),
                                        it.getLong("degree").toInt()
                                )
                                list.add(post)
                            }
                            observer.onNext(list)
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