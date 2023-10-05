package dev.haqim.recipehub.ui.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.haqim.recipehub.R
import dev.haqim.recipehub.databinding.FragmentRecipesBinding
import dev.haqim.recipehub.domain.model.CategoryItem
import dev.haqim.recipehub.domain.model.RecipeItem
import dev.haqim.recipehub.ui.categories.CategoriesFragmentDirections
import dev.haqim.recipehub.ui.categories.CategoryListAdapter
import dev.haqim.recipehub.ui.categories.CategoryListListener
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    
    private lateinit var binding: FragmentRecipesBinding
    private val viewModel: RecipesVM by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecipesBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.show()
        
        val args: RecipesFragmentArgs by navArgs()
        val categoryName = args.categoryName
        
        binding.tvRecipesTitle.text = getString(R.string.recipes_format, categoryName)
        viewModel.setCategory(categoryName)
        val adapter = RecipeListAdapter(listener = object: RecipeListListener(){
            override fun onClick(recipe: RecipeItem) {
                findNavController().navigate(
                    RecipesFragmentDirections.actionRecipesFragmentToRecipeFragment(recipe.id)
                )
            }
        })
        
        binding.rvRecipes.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingFlow?.collect(adapter::submitData)
        }
        
        // Inflate the layout for this fragment
        return binding.root
    }
}