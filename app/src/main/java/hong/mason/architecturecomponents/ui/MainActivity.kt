package hong.mason.architecturecomponents.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import hong.mason.architecturecomponents.R
import hong.mason.architecturecomponents.data.Post
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddFragment.EventListener {
    private lateinit var viewModel: PostListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Posts"
        // 뷰 모델 생성
        viewModel = ViewModelProviders.of(this).get(PostListViewModel::class.java)
        viewModel.onCreate()
        viewModel.getClickedPost()
                .observe(this, Observer {
                    if (it != null) {
                        showPostDetailFragment(it)
                    }
                })
        // 추가 버튼 이벤트
        buttonAdd.setOnClickListener {
            showAddFragment()
        }
        // 목록 화면 보여주기
        showListFragment()
    }

    /**
     * 추가 화면에서 취소
     */
    override fun onCancel() {
        removeFragment(findAddFragment())
        buttonAdd.visibility = View.VISIBLE
    }

    /**
     * 추가 화면에서 확정
     */
    override fun onSubmit(post: Post) {
        viewModel.onSubmit(post)
        onCancel()
    }

    override fun onBackPressed() {
        val detailFragment = findDetailFragment()
        val addFragment = findAddFragment()
        when {
            addFragment != null -> onCancel()
            detailFragment != null -> showListFragment()
            else -> super.onBackPressed()
        }
    }

    /**
     * 목록 화면 띄우기
     */
    private fun showListFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameMain, PostListFragment.newInstance(), PostListFragment::class.java.name)
                .commit()
        buttonAdd.visibility = View.VISIBLE
    }

    /**
     * 추가 화면 띄우기
     */
    private fun showAddFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.frameAdd, AddFragment.newInstance(), AddFragment::class.java.name)
                .commit()
        buttonAdd.visibility = View.GONE
    }

    /**
     * 자세히 보기 화면 띄우기
     */
    private fun showPostDetailFragment(post: Post) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameMain, PostDetailFragment.newInstance(post), PostDetailFragment::class.java.name)
                .commit()
        buttonAdd.visibility = View.GONE
        removeFragment(findAddFragment())
    }

    /**
     * 추가 화면 찾기
     */
    private fun findAddFragment(): AddFragment? =
            supportFragmentManager.findFragmentByTag(AddFragment::class.java.name) as AddFragment?

    private fun findDetailFragment(): PostDetailFragment? =
            supportFragmentManager.findFragmentByTag(PostDetailFragment::class.java.name) as PostDetailFragment?

    /**
     * 화면 제거하기
     */
    private fun removeFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit()
        }
    }
}
