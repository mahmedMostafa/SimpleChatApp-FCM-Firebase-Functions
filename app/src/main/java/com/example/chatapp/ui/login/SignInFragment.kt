package com.example.chatapp.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.ui.login.status.*
import kotlinx.android.synthetic.main.sign_in_fragment.*


class SignInFragment : Fragment(R.layout.sign_in_fragment), View.OnClickListener {


    private val viewModel: SignInViewModel by lazy {
        ViewModelProvider(this).get(SignInViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //i don't think this is the best solution but it works fine
        // a splash would have covered that nicely
        if (viewModel.isLoggedInAlready()) {
            this.findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToChatRoomFragment()
            )
        }
        login_button.setOnClickListener(this)
        sign_up_button.setOnClickListener(this)
        subscribeToObservers()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.login_button -> {
                viewModel.loginUser(
                    email_edit_text.text.toString().trim(),
                    password_edit_text.text.toString().trim()
                )
            }
            R.id.sign_up_button -> {
                this.findNavController().navigate(
                    SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
                )
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                when (it) {
                    SUCCESS -> {
                        progress_bar.visibility = View.INVISIBLE
                        this.findNavController().navigate(
                            SignInFragmentDirections.actionSignInFragmentToChatRoomFragment()
                        )
                    }
                    ERROR -> {
                        progress_bar.visibility = View.INVISIBLE
                        Toast.makeText(
                            activity!!,
                            "Something went wrong \n please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

}
