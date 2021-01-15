package with.dee2.kotilnproject.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import with.dee2.kotilnproject.ChatActivity
import with.dee2.kotilnproject.Model.Profiles
import with.dee2.kotilnproject.R
import with.dee2.kotilnproject.URLtoBitmapTask
import java.net.URL


class ProfileAdapter(var context : Context, val profileList: ArrayList<Profiles>) : RecyclerView.Adapter<ProfileAdapter.CustomViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.profile_list_item,parent,false)
        return CustomViewHolder(
            view
        ) // inflater -> 부착
    }

    override fun getItemCount(): Int {
        return profileList.size
    }
    // 
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if (profileList.get(position).img=="null"){
            holder.img.setImageResource(R.drawable.man)
        }
        else {
            var image_task: URLtoBitmapTask =
                URLtoBitmapTask()
            image_task = URLtoBitmapTask().apply {
                url = URL(profileList.get(position).img)
            }
            var bitmap: Bitmap = image_task.execute().get()
            holder.img.setImageBitmap(bitmap)

        }
        holder.name.text = profileList.get(position).name
        holder.age.text = profileList.get(position).age.toString()
        holder.lastmsg.text = profileList.get(position).lastmsg

        holder.itemView.setOnClickListener{
            val intent= Intent(context, ChatActivity::class.java)
            intent.putExtra("chatroomid",profileList.get(position).chatroomid)
            intent.putExtra("theOtherPersonuid",profileList.get(position).uid)
            intent.putExtra("theOtherPersonimg",profileList.get(position).img)
            intent.putExtra("theOtherPersonname",profileList.get(position).name)

            context.startActivity(intent)
        }

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img= itemView.findViewById<ImageView>(R.id.board_img)      // 프사
        val name =itemView.findViewById<TextView>(R.id.board_name)         // 이름
        val age =itemView.findViewById<TextView>(R.id.board_age)           // 나이
        val lastmsg =itemView.findViewById<TextView>(R.id.board_job)           // 마지막 메시지

    }
}

