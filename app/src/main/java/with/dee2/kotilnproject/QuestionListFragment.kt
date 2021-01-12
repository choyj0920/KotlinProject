package with.dee2.kotilnproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

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
        rvQuestion.adapter=QuestionAdapter(requireContext(),dataList)

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