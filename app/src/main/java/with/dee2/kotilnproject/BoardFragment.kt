package with.dee2.kotilnproject

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_board.*
import java.net.URL

class BoardFragment:Fragment() {
    var dataList=arrayListOf<Board>()
    lateinit var  getthis: BoardFragment
    lateinit var profileFrame:FrameLayout
    lateinit var rvBoard: RecyclerView
    val ref : DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_board, container,false)

        getthis=this

        rvBoard  = view.findViewById((R.id.recyclerView))as RecyclerView
        rvBoard.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        loadData()
        profileFrame= view.findViewById(R.id.boardprofileframe) as FrameLayout
        (view.findViewById(R.id.board_profilebackground) as View).setOnClickListener{
            ViewProfileOff()
        }
        ViewProfileOff()
        return view
    }
    fun ViewProfileOff(){
        profileFrame.setVisibility(View.GONE)

    }
    fun ViewProfileOn(uid: String){
        var userProfile= FriendListFragment.usersnapshot.child(uid).value as Map<String,Any>
        if(userProfile.isEmpty()) return
        var img=userProfile["imageUrl"].toString()
        var name = userProfile["name"].toString()
        var age = userProfile["age"].toString()
        var msg = userProfile["msg"].toString()

        if (img=="null"){
            board_img.setImageResource(R.drawable.man)
        }
        else {
            var image_task: URLtoBitmapTask = URLtoBitmapTask()
            image_task = URLtoBitmapTask().apply {
                url = URL(img)
            }
            var bitmap: Bitmap = image_task.execute().get()
            board_img.setImageBitmap(bitmap)
        }
        board_age.text=age
        board_name.text=name
        board_job.text=msg

        profileFrame.setVisibility(View.VISIBLE)
        // 자신이면 대화하기 없애기
        var talkstart=profileFrame.findViewById<Button>(R.id.talkstart)
        talkstart.visibility=if(LoginActivity.currentuseruid== uid) View.GONE else View.VISIBLE
        talkstart.setOnClickListener{
            chatStart(uid,img,name)
        }
    }
    fun chatStart(uid:String,img:String,name:String){
        val intent= Intent(requireContext(),ChatActivity::class.java)
        var chatroomid:String?=null
        // 원래 이야기하던 채팅방이 있었으면..
        for (i in FriendListFragment.usersnapshot.child(LoginActivity.currentuseruid).child("chat").children){
            var map=i.value as Map<String,Any>
            if (map["theOtherPerson"]==uid) {
                chatroomid=i.key.toString()
                break
            }
        }
        if (chatroomid == null) chatroomid= FirebaseDatabase.getInstance().reference.child("chatroom").push().key
        if (chatroomid==null) {
            Log.d("error","Couldn't get push key for 채팅방 생성\n")
            return
        }
        intent.putExtra("chatroomid",chatroomid)
        intent.putExtra("theOtherPersonuid",uid)
        intent.putExtra("theOtherPersonimg",img)
        intent.putExtra("theOtherPersonname",name)

        requireContext().startActivity(intent)

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
                    if (uid!="private"){
                        ref.child("users").child(uid)
                            .addValueEventListener(object : ValueEventListener{
                                override fun onCancelled(error: DatabaseError) {

                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    var map =snapshot.value as Map<String,Any>
                                    var name =map["name"].toString()
                                    var img=map["imageUrl"].toString()
                                    dataList.add(Board(img,"By "+name,question,content,date,uid))
                                    rvBoard.adapter=BoardAdapter(getthis,requireContext(),dataList)
                                }

                            })
                    }else{
                        val imgUri="https://firebasestorage.googleapis.com/v0/b/kotilnproject-8e7fe.appspot.com/o/images%2Fvector-creator%20(1).png?alt=media&token=a4d0b071-7e00-4f87-886b-58c2232b258c"
                        dataList.add(Board(imgUri,"By 익명",question,content,date,"private"))
                        rvBoard.adapter=BoardAdapter(getthis ,requireContext(),dataList)
                    }

                }

            }

        })


    }
}