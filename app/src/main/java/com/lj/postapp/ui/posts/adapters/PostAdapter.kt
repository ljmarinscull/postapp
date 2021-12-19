package com.lj.postapp.ui.posts.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lj.postapp.R
import com.lj.postapp.data.model.PostObject
import com.lj.postapp.databinding.PostItemLayoutBinding

class PostAdapter(
    private val listener: (PostObject) -> Unit ) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var mDataSet: List<PostObject> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item_layout, parent, false)

        mContext = parent.context
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val recipe = mDataSet[position]
        holder.itemView.setOnClickListener {
            listener(recipe)
        }
        holder.bind(recipe)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mDataSet.size

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val binding = PostItemLayoutBinding.bind(view)

        fun bind(value: PostObject) = with(binding){
            title.text = value.title
            body.text = value.body
        }
    }
}
