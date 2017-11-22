package hong.mason.architecturecomponents.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hong.mason.architecturecomponents.R
import hong.mason.architecturecomponents.adapter.PostsAdapter
import kotlinx.android.synthetic.main.fragment_post_list.*

/**
 * Created by kakao on 2017. 11. 22..
 */
class PostListFragment : Fragment() {
    private lateinit var viewModel : PostListViewModel
    private val adapter = PostsAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_post_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity).get(PostListViewModel::class.java)
        viewModel.getPosts()
                .observe(this, Observer {
                    Log.d("PostListFragment", "onViewCreated::getPosts::$it")
                    adapter.setList(it ?: emptyList())
                })
        viewModel.getState()
                .observe(this, Observer { state ->
                    when(state) {
                        PostListViewModel.State.LOADING -> {
                            progressBar.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        }
                        PostListViewModel.State.ERROR ->
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        PostListViewModel.State.LOADED -> {
                            progressBar.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                        }
                    }

                })

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter.setOnPostClick {
            viewModel.onClickPost(it)
        }
    }

    companion object {
        fun newInstance() : PostListFragment = PostListFragment()
    }
}