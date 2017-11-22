package hong.mason.architecturecomponents.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import hong.mason.architecturecomponents.R
import hong.mason.architecturecomponents.data.Post
import kotlinx.android.synthetic.main.viewholder_post.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by kakao on 2017. 11. 21..
 */
class PostViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun bindView(data : Post) {
        itemView.textWriter.text = data.writer
        itemView.textTitle.text = data.title
        val date = SimpleDateFormat("MM/dd", Locale.getDefault()).format(Date(data.writeDate))
        itemView.textWriteDate.text = date
        when(data.degree) {
            0 -> itemView.imageDegree.setImageResource(R.drawable.shape_degree0)
            1 -> itemView.imageDegree.setImageResource(R.drawable.shape_degree1)
            2 -> itemView.imageDegree.setImageResource(R.drawable.shape_degree2)
            3 -> itemView.imageDegree.setImageResource(R.drawable.shape_degree3)
            4 -> itemView.imageDegree.setImageResource(R.drawable.shape_degree4)
        }
    }
}