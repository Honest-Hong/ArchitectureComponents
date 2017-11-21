package hong.mason.architecturecomponents.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import hong.mason.architecturecomponents.adapter.PostsAdapter
import hong.mason.architecturecomponents.R
import hong.mason.architecturecomponents.data.Post
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddFragment.EventListener {
    private lateinit var viewModel : PostsViewModel
    private val adapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(PostsViewModel::class.java)
        viewModel.onCreate()
        viewModel.getPosts()
                .observe(this, Observer {
                    adapter.addAll(it ?: emptyList())
                })
        viewModel.isError()
                .observe(this, Observer {
                    Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT).show()
                })
        viewModel.isLoading()
                .observe(this, Observer { loading ->
                    progressBar.visibility = if (loading == true) View.VISIBLE else View.GONE
                    recyclerView.visibility = if (loading == false) View.VISIBLE else View.GONE
                })
        viewModel.isShowAddView()
                .observe(this, Observer { show ->
                    Log.d("MainActivity", "isShowAddView::$show")
                    if (show == true) {
                        supportFragmentManager.beginTransaction()
                                .add(R.id.frameAdd, AddFragment(), AddFragment::class.java.name)
                                .commit()
                        buttonAdd.visibility = View.GONE
                    } else {
                        val fragment = supportFragmentManager.findFragmentByTag(AddFragment::class.java.name)
                        if (fragment != null) {
                            supportFragmentManager.beginTransaction()
                                    .remove(fragment)
                                    .commit()
                        }
                        buttonAdd.visibility = View.VISIBLE
                    }
                })

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
        recyclerView.adapter = adapter

        buttonAdd.setOnClickListener {
            viewModel.onClickAddButton()
        }
    }

    override fun onCancel() {
        viewModel.onCancel()
    }

    override fun onSubmit(post: Post) {
        viewModel.onSubmit(post)
    }
}
