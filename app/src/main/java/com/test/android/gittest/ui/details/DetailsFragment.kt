package com.test.android.gittest.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.test.android.gittest.MainActivity
import com.test.android.gittest.R
import com.test.android.gittest.databinding.DetailsFragmentBinding
import com.test.android.gittest.domain.model.User
import com.test.android.gittest.domain.model.UserDetails
import com.test.android.gittest.util.interfaces.IUpdateTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var binding: DetailsFragmentBinding

    private lateinit var updateTitle: IUpdateTitle

    override fun onAttach(context: Context) {
        super.onAttach(context)
        updateTitle = activity as IUpdateTitle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        val user = arguments?.getParcelable<User>("user") ?: return
        viewModel.user = user

        updateTitle.updateTitle(user.name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(layoutInflater)
        val view = binding.root

        loadingState(true)

        Glide.with(this)
            .load(viewModel.user.urlAvatar)
            .placeholder(R.drawable.ic_baseline_image_48)
            .transform(CircleCrop())
            .into(binding.ivAvatar2)

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.userDetails.collectLatest {
                loadUserDetails(it.data ?: return@collectLatest)
            }
        }

        binding.tvLink.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.user.url))
            startActivity(myIntent)
        }

        return view
    }


    @SuppressLint("SetTextI18n")
    private fun loadUserDetails(user: UserDetails) {
        binding.tvName.text = user.name
        binding.tvLink.text = user.url
        binding.tvRepo.text = "Repos: ${user.repos}"
        binding.tvGists.text = "Gists: ${user.gists}"
        binding.tvFollowers.text = "Followers: ${user.followers}"

        loadingState(false)
    }

    private fun loadingState(state: Boolean){
        binding.progressBar.isVisible = state

        binding.tvName.isVisible = !state
        binding.tvLink.isVisible = !state
        binding.tvRepo.isVisible = !state
        binding.tvGists.isVisible = !state
        binding.tvFollowers.isVisible = !state
    }
}