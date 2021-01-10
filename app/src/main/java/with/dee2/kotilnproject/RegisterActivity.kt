package with.dee2.kotilnproject

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register.setOnClickListener {
           register()
        }
        profile_image.setOnClickListener{
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }
    }

    var selectedPhtoUri: Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0 &&resultCode== Activity.RESULT_OK &&data!= null){
            selectedPhtoUri=data.data
            val bitmap =MediaStore.Images.Media.getBitmap(contentResolver,selectedPhtoUri)
            val bitmapDrawable=BitmapDrawable(bitmap)
            profile_image.setBackgroundDrawable(bitmapDrawable)
        }
    }

   fun register(){
       FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString().trim(), password.text.toString().trim())
           .addOnCompleteListener(OnCompleteListener {
               task ->
                   if (task.isSuccessful) {
                       val currentUser=FirebaseAuth.getInstance().currentUser
                       uploadImage()
                       val intent= Intent(this,MainActivity::class.java)
                       intent.putExtra("user",currentUser.toString())
                       startActivity(intent)
                       finish()
                   } else {
                       Toast.makeText(this,"회원가입 실패",Toast.LENGTH_SHORT).show()
                       Log.d("error",task.exception!!.message.toString())
                   }
           })
   }

    fun uploadImage() {
        if(selectedPhtoUri==null) {
            saveUserToDatabase("null")
            return
        }
        val filename=UUID.randomUUID().toString()
        val ref=FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhtoUri!!).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                saveUserToDatabase(it.toString())
            }
        }
    }
    fun saveUserToDatabase(profileImage:String) {
        val uid=FirebaseAuth.getInstance().uid ?:""
        val ref=FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user=User(uid,name.text.toString(),profileImage,age.text.toString(),msg.text.toString())

        ref.setValue(user).addOnSuccessListener {
            Toast.makeText(this,"회원가입 성공",Toast.LENGTH_SHORT).show()
        }
        LoginActivity.currentuseruid =FirebaseAuth.getInstance().uid ?:""

    }
}


data class User(val uid:String, val name:String, val imageUrl:String,val age:String, val msg:String)