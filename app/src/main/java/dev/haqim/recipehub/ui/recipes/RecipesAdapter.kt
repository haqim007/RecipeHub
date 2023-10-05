package dev.haqim.recipehub.ui.recipes


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
import dev.haqim.recipehub.databinding.ItemRecipeBinding
import dev.haqim.recipehub.domain.model.RecipeItem

class RecipeListAdapter(
    private val listener: RecipeListListener
) : PagingDataAdapter<RecipeItem, RecyclerView.ViewHolder>(DIFF_CALLBACK){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(getItem(position), listener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.onCreate(parent)
    }

    class ViewHolder private constructor(
        private val binding: ItemRecipeBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun onBind(recipe: RecipeItem?, listener: RecipeListListener){
            recipe?.let {
                    
                binding.tvRecipe.text = it.name
                Glide.with(itemView.context).load(it.image)
                    .placeholder(R.drawable.baseline_downloading_24)
                    .transform(CenterInside(), RoundedCorners(24))
                    .into(binding.ivRecipe)

                binding.root.setOnClickListener {
                    listener.onClick(recipe)
                }
            }
        }

        companion object{
            fun onCreate(parent: ViewGroup): ViewHolder{
                val itemView = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(itemView)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecipeItem>(){
            override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

abstract class RecipeListListener{
    abstract fun onClick(recipe: RecipeItem)
}