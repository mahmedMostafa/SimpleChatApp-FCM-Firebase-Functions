package com.example.chatapp.ui.chatroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R

import com.example.chatapp.models.Message
import kotlinx.android.synthetic.main.chat_room_fragment.*
import com.example.chatapp.utils.Util


class ChatRoomFragment : Fragment(R.layout.chat_room_fragment) {

    private val viewModel: ChatRoomViewModel by lazy {
        ViewModelProvider(this).get(ChatRoomViewModel::class.java)
    }

    private lateinit var adapter: ChatRoomAdapter
    private var messages = mutableListOf<Message>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        adapter = ChatRoomAdapter(messages)
        recycler_view.layoutManager = LinearLayoutManager(activity!!)
        recycler_view.adapter = this.adapter

        send_button.setOnClickListener {
            viewModel.sendMessage(message_edit_text.text.toString().trim())
            message_edit_text.text.clear()
            Util.hideKeyboard(activity!!)
            recycler_view.smoothScrollToPosition(messages.size - 1)
        }
    }

    private fun subscribeToObservers() {
        // messages.add(Message("Hello there", "asd"))
        viewModel.addUserToChatRoom()

        viewModel.messages.observe(viewLifecycleOwner, Observer { messages ->
            this.messages = messages as MutableList<Message>
            adapter.setMessages(messages)
        })

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                when (it) {
                    chatStatus.SUCCESS -> progress_bar.visibility = View.INVISIBLE
                    chatStatus.LOADING -> progress_bar.visibility = View.VISIBLE
                    chatStatus.ERROR -> {
                        progress_bar.visibility = View.INVISIBLE
                        Toast.makeText(activity!!, "Something wen Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
