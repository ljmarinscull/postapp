package com.lj.postapp.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lj.postapp.data.model.PostObject
import com.lj.postapp.databinding.FragmentPostsBinding
import com.lj.postapp.ui.posts.adapters.PostAdapter
import com.lj.postapp.utils.visible

class PostsFragment : Fragment() {

    private var _binding: FragmentPostsBinding? = null
    private val viewModel: PostsViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        viewModel.getPosts()
    }

    private fun setupUI() = with(binding){

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = PostAdapter(::postSelected)
        recyclerView.adapter = mAdapter

        viewModel.progressBarVisible.observe(viewLifecycleOwner, {
            progressBar.visible = it
        })

        viewModel.posts.observe(viewLifecycleOwner, { result ->
            result ?: return@observe

            if (result.isNotEmpty()) {
                recyclerView.visibility = VISIBLE
                recyclerViewEmptyView.visibility = GONE
                mAdapter.mDataSet = result
            } else {
                recyclerView.visibility = GONE
                recyclerViewEmptyView.visibility = VISIBLE
            }
        })
    }

    private fun postSelected(post: PostObject) {
        val action = PostsFragmentDirections.actionPostsToPostComments()
        action.postId = post.id!!
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}