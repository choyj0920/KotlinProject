package with.dee2.kotilnproject

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapter(var context : Context, val questionList: ArrayList<Question>) : RecyclerView.Adapter<QuestionAdapter.CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAdapter.CustomViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_quesition,parent,false)
        return CustomViewHolder(view) // inflater -> 부착
    }

    override fun getItemCount(): Int {
        return questionList.size
    }
    //
    override fun onBindViewHolder(holder: QuestionAdapter.CustomViewHolder, position: Int) {
        holder.num.text = questionList.get(position).num
        holder.date.text = questionList.get(position).date
        holder.question.text = questionList.get(position).question

        holder.itemView.setOnClickListener {
            val intent= Intent(context,PostActivity::class.java)
            intent.putExtra("num",questionList.get(position).num)
            intent.putExtra("question",questionList.get(position).question)
            context.startActivity(intent)
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val num =itemView.findViewById<TextView>(R.id.num)         // 이름
        val date =itemView.findViewById<TextView>(R.id.date)           // 나이
        val question =itemView.findViewById<TextView>(R.id.question)           // 직업

    }

}