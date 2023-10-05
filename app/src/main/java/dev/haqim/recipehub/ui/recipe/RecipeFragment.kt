package dev.haqim.recipehub.ui.recipe

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.haqim.recipehub.R
import dev.haqim.recipehub.data.mechanism.ResourceHandler
import dev.haqim.recipehub.data.mechanism.handle
import dev.haqim.recipehub.databinding.FragmentRecipeBinding
import dev.haqim.recipehub.domain.model.Recipe
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    
    private lateinit var binding: FragmentRecipeBinding
    private val viewModel: RecipeVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        
        val args: RecipeFragmentArgs by navArgs()
        val id = args.id

        (activity as AppCompatActivity).supportActionBar?.hide()
        
        binding.btnBack.setOnClickListener { 
            findNavController().popBackStack()
        }
        
        
        viewModel.getDetail(id)
        viewLifecycleOwner.lifecycleScope.launch { 
            val recipeFlow = viewModel.state.map { it.recipe }
            recipeFlow.collectLatest { 
                it.handle(object: ResourceHandler<Recipe>{
                    override fun onSuccess(data: Recipe?) {
                        binding.pbLoader.isVisible = false
                        showData(data, id)
                    }

                    override fun onError() {
                        binding.pbLoader.isVisible = false
                        showError(id)
                    }

                    override fun onLoading() {
                        binding.pbLoader.isVisible = true
                    }

                    override fun onIdle() {
                        binding.pbLoader.isVisible = false
                    }
                })
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun showError(id: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error))
            .setMessage(getString(R.string.failed_to_fetch_data))
            .setNeutralButton(getString(R.string.close)) { dialog, which ->
                dialog.dismiss()
                findNavController().popBackStack()
            }
            .setPositiveButton(getString(R.string.try_again)) { dialog, which ->
                viewModel.getDetail(id)
            }
            .show()
    }

    private fun showData(data: Recipe?, id: String) {
        data?.let {
            with(binding) {
                Glide.with(requireContext()).load(data.image)
                    .placeholder(R.drawable.baseline_downloading_24)
                    .into(ivRecipe)
                chipCategory.text = data.category
                val builder = SpannableStringBuilder()
                data.ingredients.forEach { item ->
                    builder.append(
                        "$item\n", // Add some linebreaks
                        BulletSpan(), // Add the bullet point span
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                tvIngredients.text = builder
                tvRecipeInstruction.text = data.instructions
                tvRecipeTitle.text = data.name
                btnYoutube.setOnClickListener {
                    watchYoutube(data.youtubeKey)
                }
            }


        } ?: run {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.error))
                .setMessage(getString(R.string.data_empty))
                .setNeutralButton(getString(R.string.close)) { dialog, which ->
                    dialog.dismiss()
                    findNavController().popBackStack()
                }
                .setPositiveButton(getString(R.string.try_again)) { dialog, which ->
                    viewModel.getDetail(id)
                }
                .show()
        }
    }

    private fun watchYoutube(key: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$key")
        )
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }
}