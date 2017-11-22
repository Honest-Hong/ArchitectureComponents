package hong.mason.architecturecomponents.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hong.mason.architecturecomponents.R
import hong.mason.architecturecomponents.data.Post
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * Created by kakao on 2017. 11. 22..
 */
class PostDetailFragment : Fragment() {
    private lateinit var viewModel : PostDetailViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater?.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PostDetailViewModel::class.java)
        viewModel.getPost().observe(this, Observer {
            textWriter.text = it?.writer
            textTitle.text = it?.title
            textContent.text = it?.content
            when(it?.degree ?: 0) {
                0 -> imageDegree.setImageResource(R.drawable.shape_degree0)
                1 -> imageDegree.setImageResource(R.drawable.shape_degree1)
                2 -> imageDegree.setImageResource(R.drawable.shape_degree2)
                3 -> imageDegree.setImageResource(R.drawable.shape_degree3)
                4 -> imageDegree.setImageResource(R.drawable.shape_degree4)
            }
        })
        viewModel.post.value = arguments.getParcelable(Post::class.java.name)
    }

    companion object {
        fun newInstance(post : Post) : PostDetailFragment {
            val args = Bundle()
            args.putParcelable(Post::class.java.name, post)
            val fragment = PostDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}