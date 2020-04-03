package com.example.chatapp.ui.chatroom

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.models.Message
import com.example.chatapp.utils.Firebase.auth
import kotlinx.android.synthetic.main.message_item.view.*

class ChatRoomAdapter(
    private var messages: MutableList<Message>
) : RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.message_item, parent, false)
        return ChatRoomViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        holder.onBind(messages[position])
    }

    fun setMessages(messages: MutableList<Message>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    class ChatRoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.message_text)

        fun onBind(message: Message) {
            if (auth.uid == message.user?.id) {
                itemView.root.apply {
                    background = resources.getDrawable(R.drawable.my_message_background, null)
                    val params = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        Gravity.END
                    )
                    this.layoutParams = params
                    text.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
            } else {
                itemView.root.apply {
                    background =
                        resources.getDrawable(R.drawable.other_users_message_background, null)
                    val params = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        Gravity.START
                    )
                    this.layoutParams = params
                    text.setTextColor(Color.parseColor("#000000"))
                }
            }
            text.text = message.text
        }
    }
}