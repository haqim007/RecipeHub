package dev.haqim.recipehub.ui.categories


import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.haqim.recipehub.R
import dev.haqim.recipehub.databinding.ItemCategoryBinding
import dev.haqim.recipehub.domain.model.CategoryItem

class CategoryListAdapter(
    private val listener: CategoryListListener
) : PagingDataAdapter<CategoryItem, RecyclerView.ViewHolder>(DIFF_CALLBACK){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(getItem(position), listener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.onCreate(parent)
    }

    class ViewHolder private constructor(
        private val binding: ItemCategoryBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun onBind(category: CategoryItem?, listener: CategoryListListener){
            category?.let {
                    
                binding.tvCategory.text = it.name
                Glide.with(itemView.context).load(it.image)
                    .placeholder(R.drawable.baseline_downloading_24)
                    .transform(CenterInside(), RoundedCorners(24))
                    .into(binding.ivCategory)

            

                binding.root.setOnClickListener {
                    listener.onClick(category)
                }
            }
        }

        companion object{
            fun onCreate(parent: ViewGroup): ViewHolder{
                val itemView = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(itemView)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoryItem>(){
            override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}

abstract class CategoryListListener{
    abstract fun onClick(category: CategoryItem)
}