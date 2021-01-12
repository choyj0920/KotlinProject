package with.dee2.kotilnproject

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_setting.*
import java.net.URL

class SettingActivity : AppCompatActivity() {

    val ref : DatabaseReference = FirebaseDatabase.getInstance().reference
    val currentUser =FirebaseAuth.getInstance().currentUser?.uid.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        loadData()

        setting_logout.setOnClickListener {
            logout()
        }

        setting_delete.setOnClickListener {
            delete()
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun loadData(){
        ref.child("users").child(currentUser)
            .addValueEventListener(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    var map =snapshot.value as Map<String,Any>
                    setting_age.text=map["age"].toString()
                    setting_name.text=map["name"].toString()
                    setting_msg.text=map["msg"].toString()
                    if(map["imageUrl"].toString()=="null"){
                        setting_img.setImageResource(R.drawable.man)
                    }else{
                        var image_task: URLtoBitmapTask = URLtoBitmapTask()
                        image_task = URLtoBitmapTask().apply {
                            url = URL(map["imageUrl"].toString())
                        }

                        var bitmap: Bitmap = image_task.execute().get()
                        setting_img.setImageBitmap(bitmap)
                    }

                }

            })
    }

    fun delete(){
        FirebaseAuth.getInstance().currentUser?.delete()
        ref.child("users").child(currentUser).removeValue()
        ref.child("community").child(currentUser).removeValue()
    }
}