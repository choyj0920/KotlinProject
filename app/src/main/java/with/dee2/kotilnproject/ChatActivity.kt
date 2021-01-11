package with.dee2.kotilnproject

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ChatActivity() : AppCompatActivity() {
    lateinit var rv_chat : RecyclerView
    lateinit var chatroomid : String
    public val chat_database : DatabaseReference = FirebaseDatabase.getInstance().reference.child("chatroom")
    var chatlist=arrayListOf<ChatMessage>()
    lateinit var chatcontext:Context
    companion object{
        lateinit var theOtherPersonuid:String
        lateinit var theOtherPersonimg:String

    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        theOtherPersonuid=intent.getStringExtra("theOtherPersonuid").toString()
        theOtherPersonimg=intent.getStringExtra("theOtherPersonimg").toString()
        chatroomid=intent.getStringExtra("chatroomid").toString()
        chatcontext=this
        loadData()
        rv_chat = findViewById((R.id.recycler_chat))as RecyclerView
        var layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true)
        layoutManager.stackFromEnd=true  //역순
        rv_chat.layoutManager=layoutManager
        //rvprofile.adapter = ProfileAdapter(requireContext(), profileList)
        rv_chat.adapter=ChatMessagesAdapter(chatcontext,chatlist)


    }



    fun loadData()  {
        chat_database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                chatlist= arrayListOf<ChatMessage>()


                for (i in snapshot.child(chatroomid).children){  // 채팅방 반복
                    var map =i.value as Map<String,Any>
                    var msgtext=map["msgtext"].toString()
                    var timestamp=map["timestamp"].toString()
                    var username=map["username"].toString()
                    var isCurrentUser= map["useruid"].toString()==LoginActivity.currentuseruid



                    chatlist.add(ChatMessage(isCurrentUser,username,timestamp,msgtext))// 리스트 삽입
                }
                chatlist.sortByDescending{ data-> data.timestamp } // 가장 이른 시간 부터 정렬

                rv_chat.adapter=ChatMessagesAdapter(chatcontext,chatlist)

            }


        })

    }

}