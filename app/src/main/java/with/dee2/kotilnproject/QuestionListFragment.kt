package with.dee2.kotilnproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_questionlist.*
import kotlinx.android.synthetic.main.fragment_questionlist.view.*
import kotlinx.android.synthetic.main.item_quesition.view.*

class QuestionListFragment:Fragment() {
    var dataList=arrayListOf<Question>()

    lateinit var rvQuestion :RecyclerView
    val ref : DatabaseReference = FirebaseDatabase.getInstance().reference.child("questions")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_questionlist, container,false)

//        view.chat.setOnClickListener {
//            val intent= Intent(activity,ChatActivity::class.java)
//            startActivity(intent)
//        }

        loadData()
        var questionList= dataList

        rvQuestion  = view.findViewById((R.id.recyclerView))as RecyclerView
        rvQuestion.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        //rv_profile.setHasFixedSize((true)) // 성능개선
        return view
    }

    fun loadData()  {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

              for (i in snapshot.children){
                  var map =i.value as Map<String,Any>
                  var num=map["num"].toString()
                  var date=map["date"].toString()
                  var question=map["question"].toString()
                  dataList.add(Question(num,date,question))
              }

                rvQuestion.adapter=QuestionAdapter(requireContext(),dataList)

            }
        })

    }
}