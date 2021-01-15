package with.dee2.kotilnproject.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import with.dee2.kotilnproject.Model.Question
import with.dee2.kotilnproject.PostActivity
import with.dee2.kotilnproject.R

class QuestionAdapter(var context : Context, val questionList: ArrayList<Question>) : RecyclerView.Adapter<QuestionAdapter.CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_quesition,parent,false)
        return CustomViewHolder(
            view
        ) // inflater -> 부착
    }

    override fun getItemCount(): Int {
        return questionList.size
    }
    //
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.num.text = questionList.get(position).num
        holder.date.text = questionList.get(position).date
        holder.question.text = questionList.get(position).question

        holder.itemView.setOnClickListener {
            val intent= Intent(context, PostActivity::class.java)
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