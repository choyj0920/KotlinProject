package with.dee2.kotilnproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FriendListFragment:Fragment() {
    lateinit var rvprofile :RecyclerView
    public val user_database : DatabaseReference = FirebaseDatabase.getInstance().reference.child("users").child(LoginActivity.currentuseruid)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view=inflater.inflate(R.layout.fragment_friendlist, container,false)

        val profileList= arrayListOf(

            Profiles("null","이하영1",21,"조윤직유기범"),
            Profiles("null","조윤직",22,"유기견"),
            Profiles("null","이하영",23,"바보"),
            Profiles("null","조윤직",24,"멍청이"),
            Profiles("null","이하영",24,"조윤직폭행범"),
            Profiles("null","조윤직",25,"이하영 골칫덩이"),
            Profiles("null","엑스트라1",20,"엑스트라아아ㅏㅏ1"),
            Profiles("null","엑스트라2",21,"엑스트라아아아ㅏ2"),
            Profiles("null","엑스트라3",20,"엑스트라아아ㅏㅏ3"),
            Profiles("null","엑스트라4",21,"엑스트라아아아ㅏ4"),
            Profiles("null","엑스트라5",20,"엑스트라아아ㅏㅏ5"),
            Profiles("null","엑스트라6",21,"엑스트라아아아ㅏ6")
        )
        rvprofile = view.findViewById((R.id.rv_profile))as RecyclerView
        rvprofile.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        //rv_profile.setHasFixedSize((true)) // 성능개선
        rvprofile.adapter = ProfileAdapter(requireContext(), profileList)

        return view
    }

}