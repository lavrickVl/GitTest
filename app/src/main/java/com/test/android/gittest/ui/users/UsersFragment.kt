package com.test.android.gittest.ui.users

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.test.android.gittest.R
import com.test.android.gittest.data.local.cash.UsersCash
import com.test.android.gittest.databinding.UsersFragmentBinding
import com.test.android.gittest.domain.model.User
import com.test.android.gittest.ui.users.adapter.UsersAdapter
import com.test.android.gittest.util.interfaces.INavigationListener
import com.test.android.gittest.util.interfaces.IUpdateTitle
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UsersFragment : Fragment(), INavigationListener {

    private val viewModel: UsersViewModel by viewModels()
    private lateinit var binding: UsersFragmentBinding

    private var dialog: Dialog? = null

    private val adapter = UsersAdapter(listOf(), this)
    private lateinit var updateTitle: IUpdateTitle

    override fun onAttach(context: Context) {
        super.onAttach(context)
        updateTitle = activity as IUpdateTitle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateTitle.updateTitle(getString(R.string.users))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UsersFragmentBinding.inflate(layoutInflater)
        val view = binding.root

        binding.recyclerUsers.adapter = adapter

        if (UsersCash.isNotEmpty()) {
            adapter.updateUsersList(UsersCash.list)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.users.collect {
                adapter.updateUsersList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.eventFlow.collectLatest {

                dialog = AlertDialog.Builder(requireContext())
                    .setTitle(R.string.error)
                    .setMessage(it)
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        dialog!!.cancel()
                        dialog = null
                    }
                    .create()

                dialog!!.show()
            }
        }

        binding.swipeContainer.setOnRefreshListener {
            viewModel.refreshUsers()
            binding.swipeContainer.setRefreshing(false)
        }

        return view
    }


    override fun navigateToDetails(user: User, imageView: ImageView) {
        val bundle = bundleOf("user" to user)
            val extras = FragmentNavigatorExtras(
                imageView to "imageView"
            )
        findNavController().navigate(
            R.id.action_usersFragment_to_detailsFragment,
            bundle,
            null,
            extras
        )
    }


}