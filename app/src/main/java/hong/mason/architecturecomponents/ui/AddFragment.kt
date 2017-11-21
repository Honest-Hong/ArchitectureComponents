package hong.mason.architecturecomponents.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hong.mason.architecturecomponents.R
import hong.mason.architecturecomponents.data.Post
import kotlinx.android.synthetic.main.fragment_add.*

/**
 * Created by kakao on 2017. 11. 21..
 */
class AddFragment : Fragment() {
    interface EventListener {
        fun onCancel()
        fun onSubmit(post: Post)
    }

    private var eventListener : EventListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is EventListener) {
            eventListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_add, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSubmit.setOnClickListener {
            eventListener?.onSubmit(Post(
                    -1,
                    editWriter.text.toString(),
                    editTitle.text.toString(),
                    editContent.text.toString(),
                    System.currentTimeMillis()
            ))
        }
        buttonCancel.setOnClickListener {
            eventListener?.onCancel()
        }
    }
}