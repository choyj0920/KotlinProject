package with.dee2.kotilnproject.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import with.dee2.kotilnproject.Fragment.BoardFragment
import with.dee2.kotilnproject.Model.Board
import with.dee2.kotilnproject.R
import with.dee2.kotilnproject.URLtoBitmapTask
import java.net.URL

class BoardAdapter(var boardFragment: BoardFragment, var context : Context, val boardList: ArrayList<Board>) : RecyclerView.Adapter<BoardAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_community,parent,false)
        return CustomViewHolder(view) // inflater -> 부착
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if (boardList.get(position).img=="null"){
            holder.img.setImageResource(R.drawable.man)
        }
        else {
            var image_task: URLtoBitmapTask =
                URLtoBitmapTask()
            image_task = URLtoBitmapTask().apply {
                url = URL(boardList.get(position).img)
            }

            var bitmap: Bitmap = image_task.execute().get()
            holder.img.setImageBitmap(bitmap)

        }
        holder.img.setOnClickListener{
            if(boardList.get(position).name=="By 익명"){ // 익명일 경우 프로필 접근 못함
                Toast.makeText(context,"글 작성자가 익명이에요 ",Toast.LENGTH_SHORT).show()
            }
            else{                                       // 익명이 아닐 경우 프로필 접근
                boardFragment.ViewProfileOn(boardList.get(position).uid)
            }
        }
        holder.content.text=boardList.get(position).content
        holder.date.text=boardList.get(position).date
        holder.name.text=boardList.get(position).name
        holder.question.text=boardList.get(position).question

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name =itemView.findViewById<TextView>(R.id.community_post_name)
        val date =itemView.findViewById<TextView>(R.id.community_post_date)
        val question =itemView.findViewById<TextView>(R.id.community_post_question)
        val content=itemView.findViewById<TextView>(R.id.community_post_content)
        var img =itemView.findViewById<ImageView>(R.id.community_post_img)
    }

}