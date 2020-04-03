package com.example.chatapp.ui.register

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
import com.example.chatapp.ui.login.SignInFragmentDirections
import com.example.chatapp.ui.register.status.*
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.android.synthetic.main.sign_in_fragment.*
import kotlinx.android.synthetic.main.sign_in_fragment.progress_bar
import kotlinx.android.synthetic.main.sign_in_fragment.sign_up_button

class RegisterFragment : Fragment(R.layout.register_fragment) {

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register_register_button.setOnClickListener {
            viewModel.signUp(
                register_email_edit_text.text.toString().trim(),
                register_password_edit_text.text.toString().trim(),
                name_edit_text.text.toString().trim()
            )
        }

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                when (it) {
                    SUCCESS -> {
                        progress_bar.visibility = View.INVISIBLE
                        this.findNavController().navigate(
                            RegisterFragmentDirections.actionRegisterFragmentToChatRoomFragment()
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
