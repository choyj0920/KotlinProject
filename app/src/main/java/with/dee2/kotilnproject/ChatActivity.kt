package with.dee2.kotilnproject

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

class ChatActivity() : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    lateinit var rv_chat : RecyclerView
    lateinit var chatroomid : String
    public val chat_database : DatabaseReference = FirebaseDatabase.getInstance().reference.child("chatroom")
    var chatlist=arrayListOf<ChatMessage>()
    lateinit var chatcontext:Context
    companion object{
        lateinit var theOtherPersonuid:String
        lateinit var theOtherPersonimg:String
        lateinit var theOtherPersonname:String


    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        theOtherPersonuid=intent.getStringExtra("theOtherPersonuid").toString()
        theOtherPersonimg=intent.getStringExtra("theOtherPersonimg").toString()
        theOtherPersonname=intent.getStringExtra("theOtherPersonname").toString()

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


        send.setOnClickListener {
            saveData(message,LoginActivity.currentusername,LoginActivity.currentuseruid,chatroomid)

        }


    }
    @IgnoreExtraProperties
    data class Chat( var useruid: String? = "", var timestamp: String? = "", var msgtext: String? = "", var username: String? = "") {
        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf( "useruid" to useruid,  "timestamp" to timestamp,  "msgtext" to msgtext,  "username" to username ) }
    }

    private fun saveData(text: EditText, username:String?, useruid: String?,chatroomid : String) {  // 메시지 전송함수
        val currentDateTime = Calendar.getInstance().time
        var dateFormat = SimpleDateFormat("yyMMddHHmmssZ", Locale.KOREA).format(currentDateTime)
        val data=Chat(useruid=useruid,timestamp=dateFormat,msgtext=text.text.toString(),username=username)
        val currentuid=LoginActivity.currentuseruid
        var key = FirebaseDatabase.getInstance().reference.child("chatroom").child(chatroomid).push().key
        if (key==null){
            Log.d("error","Couldn't get push key for 채팅메시지 저장\n")
            return
        }
        val datavalues = data.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/users/$theOtherPersonuid/chat/$chatroomid" to mapOf("Lastmsg" to text.text.toString(),"lasttimestamp" to dateFormat, "theOtherPerson" to currentuid),
            "/users/$currentuid/chat/$chatroomid" to mapOf("Lastmsg" to text.text.toString(),"lasttimestamp" to dateFormat, "theOtherPerson" to theOtherPersonuid),

            "/chatroom/$chatroomid/$key" to datavalues
        )
        FirebaseDatabase.getInstance().reference.updateChildren(childUpdates)
            .addOnSuccessListener {
                // Write was successful!
                text.setText("")


        }
            .addOnFailureListener {
                // Write failed
                // ...
                Log.d("error","chat msg update error\n\n")
            }


//        database= FirebaseDatabase.getInstance().reference.child("users")
//            .child(theOtherPersonuid).child("chat").child(chatroomid)
//        database.setValue(mapOf("Lastmsg" to text.text.toString(),"lasttimestamp" to dateFormat, "theOtherPerson" to LoginActivity.currentuseruid))
//
//        database= FirebaseDatabase.getInstance().reference.child("users")
//            .child(LoginActivity.currentuseruid).child("chat").child(chatroomid)
//        database.setValue(mapOf("Lastmsg" to text.text.toString(),"lasttimestamp" to dateFormat, "theOtherPerson" to theOtherPersonuid))
    }


    fun loadData()  { // 채팅방 메시지 값 로드 함수
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