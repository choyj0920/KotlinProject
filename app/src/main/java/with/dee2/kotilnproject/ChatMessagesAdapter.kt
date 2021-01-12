package with.dee2.kotilnproject

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.net.URL


class ChatMessagesAdapter(var context : Context, val chatmsglist: ArrayList<ChatMessage>) : RecyclerView.Adapter<ChatMessagesAdapter.CustomViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ChatMessagesAdapter.CustomViewHolder {
        val view =
            LayoutInflater.from(context).inflate(if(viewType==ChatMessage.msg_right) R.layout.item_message_right else R.layout.item_message_left,parent,false)
        return CustomViewHolder(view) // inflater -> 부착
    }

    override fun getItemCount(): Int {
        return chatmsglist.size
    }
    // 
    override fun onBindViewHolder(holder: ChatMessagesAdapter.CustomViewHolder, position: Int) {
        var imgurl =if(chatmsglist[position].isCurrentUser) LoginActivity.currentuserimg else ChatActivity.theOtherPersonimg
        if (imgurl=="null"){
            holder.image.setImageResource(R.drawable.man)
        }
        else {
            var image_task: URLtoBitmapTask = URLtoBitmapTask()
            image_task = URLtoBitmapTask().apply {
                url = URL(imgurl)
            }
            var bitmap: Bitmap = image_task.execute().get()
            holder.image.setImageBitmap(bitmap)

        }
        holder.name.text = chatmsglist.get(position).msgtext



    }
    override fun getItemViewType(position: Int): Int {
        return if(chatmsglist[position].isCurrentUser) ChatMessage.msg_right else ChatMessage.msg_left
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image =itemView.findViewById<ImageView>(R.id.imageView2)         // 프사뷰
        val name =itemView.findViewById<TextView>(R.id.textView2)           // 닉네임뷰
        

    }
}

