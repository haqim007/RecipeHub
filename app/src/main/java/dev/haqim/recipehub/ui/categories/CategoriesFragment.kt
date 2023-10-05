package dev.haqim.recipehub.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.haqim.recipehub.databinding.FragmentCategoriesBinding
import dev.haqim.recipehub.domain.model.CategoryItem
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val viewModel: CategoriesVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        
        val adapter = CategoryListAdapter(listener = object: CategoryListListener(){
            override fun onClick(category: CategoryItem) {
                findNavController().navigate(
                    CategoriesFragmentDirections.actionCategoriesFragmentToRecipesFragment(category.name)
                )
            }
        })
        
        binding.rvCategories.adapter = adapter
        
        viewLifecycleOwner.lifecycleScope.launch { 
            viewModel.pagingFlow.collect{
                adapter.submitData(it)
            }
        }
        
        
        return binding.root
    }

}