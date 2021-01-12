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

class FriendListFragment:Fragment() {
    lateinit var rvprofile :RecyclerView
    public var user_database : DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")
    lateinit var currentuser_database : DatabaseReference
    companion object{
        lateinit var usersnapshot :DataSnapshot
    }
    var profileList=arrayListOf<Profiles>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentuser_database = FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser?.uid.toString())

        var view=inflater.inflate(R.layout.fragment_friendlist, container,false)

        loadData()
        rvprofile = view.findViewById((R.id.rv_profile))as RecyclerView
        rvprofile.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        //rv_profile.setHasFixedSize((true)) // 성능개선

        rvprofile.adapter = ProfileAdapter(requireContext(), profileList)

        return view
    }

    fun loadData()  {
        user_database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                usersnapshot=snapshot
                profileList= arrayListOf<Profiles>()
                LoginActivity.currentuseruid= FirebaseAuth.getInstance().currentUser?.uid.toString()
                var currentusermap=snapshot.child(LoginActivity.currentuseruid).value as Map<String,Any>
                LoginActivity.currentuserimg= currentusermap["imageUrl"].toString()
                LoginActivity.currentusername= currentusermap["name"].toString()
                for (i in snapshot.child(LoginActivity.currentuseruid).child("chat").children){  // 현재 유저의 chat방으로 반복문
                    var map =i.value as Map<String,Any>
                    var theOtherPersonuid= map["theOtherPerson"].toString()
                    var lastmsg=map["Lastmsg"].toString()
                    var lasttimestamp=map["lasttimestamp"].toString()

                    var chatroomid =i.key.toString()
                    if(snapshot.child(theOtherPersonuid).exists()==false)
                        continue
                    var otherPerson= snapshot.child(theOtherPersonuid).value as Map<String,Any>

                    var img =otherPerson["imageUrl"].toString()
                    var age = otherPerson["age"].toString().toInt()
                    var uid = otherPerson["uid"].toString()
                    var name = otherPerson["name"].toString()



                    Log.d("error", "$chatroomid\n $img\n$uid $name\n$age")
                    profileList.add(Profiles(img,name,uid,age,lastmsg,chatroomid,lasttimestamp)) // 리스트 삽입
                }
                profileList.sortByDescending { data-> data.timestamp } // 가장 이른 시간 부터 정렬

                rvprofile.adapter=ProfileAdapter(requireContext(),profileList)

            }


        })

    }

}