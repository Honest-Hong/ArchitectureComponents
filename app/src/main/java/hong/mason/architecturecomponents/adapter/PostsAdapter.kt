package hong.mason.architecturecomponents.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import hong.mason.architecturecomponents.data.Post
import hong.mason.architecturecomponents.R

/**
 * Created by kakao on 2017. 11. 21..
 */
class PostsAdapter : RecyclerView.Adapter<PostViewHolder>() {

    private var onPostClick : (Post) -> Unit = { }

    private var list : List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PostViewHolder =
            PostViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.viewholder_post, parent, false))

    override fun onBindViewHolder(holder: PostViewHolder?, position: Int) {
        holder?.bindView(list[position])
        holder?.itemView?.setOnClickListener {
            onPostClick.invoke(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<Post>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setOnPostClick(listener : (Post) -> Unit) {
        onPostClick = listener
    }
}