package with.dee2.kotilnproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class BoardFragment:Fragment() {
    var dataList=arrayListOf<Board>()

    lateinit var rvBoard: RecyclerView
    val ref : DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_board, container,false)

        rvBoard  = view.findViewById((R.id.recyclerView))as RecyclerView
        rvBoard.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        loadData()

        return view
    }

    fun loadData(){
        ref.child("community").addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children){
                    var uid=""
                    var date=""
                    var content=""
                    var question=""
                    for(j in i.children){
                        var map =j.value as Map<String,Any>
                        uid=map["uid"].toString()
                        date=map["date"].toString()
                        content=map["content"].toString()
                        question=map["question"].toString()
                    }
                    ref.child("users").child(uid)
                        .addValueEventListener(object : ValueEventListener{
                            override fun onCancelled(error: DatabaseError) {

                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                var map =snapshot.value as Map<String,Any>
                                var name =map["name"].toString()
                                var img=map["imageUrl"].toString()
                                dataList.add(Board(img,name,question,content,date))
                                rvBoard.adapter=BoardAdapter(requireContext(),dataList)
                            }

                        })
                }

            }

        })


    }
}