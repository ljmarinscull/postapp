package com.lj.postapp.ui.postcomments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lj.postapp.databinding.FragmentPostCommentsBinding
import com.lj.postapp.ui.postcomments.adapters.CommentAdapter
import com.lj.postapp.utils.visible

const val ARG_POSTID = "postId"

class PostCommentsFragment : Fragment() {

    private lateinit var mAdapter: CommentAdapter
    private val viewModel: PostCommentsViewModel by activityViewModels()

    private var _binding: FragmentPostCommentsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var postId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postId = it.getInt(ARG_POSTID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostCommentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        viewModel.getCommentsByPostId(postId)
    }

    private fun setupUI() = with(binding){

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = CommentAdapter()
        recyclerView.adapter = mAdapter

        viewModel.progressBarVisible.observe(viewLifecycleOwner, {
            progressBar.visible = it
        })

        viewModel.comments.observe(viewLifecycleOwner, { result ->
            result ?: return@observe

            if (result.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
                recyclerViewEmptyView.visibility = View.GONE
                mAdapter.mDataSet = result
            } else {
                recyclerView.visibility = View.GONE
                recyclerViewEmptyView.visibility = View.VISIBLE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}