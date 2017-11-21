package hong.mason.architecturecomponents.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import hong.mason.architecturecomponents.data.Post
import kotlinx.android.synthetic.main.viewholder_post.view.*

/**
 * Created by kakao on 2017. 11. 21..
 */
class PostViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun bindView(data : Post) {
        itemView.textWriter.text = data.writer
        itemView.textTitle.text = data.title
        itemView.textWriteDate.text = data.writeDate.toString()
    }
}