package with.dee2.kotilnproject

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register.setOnClickListener {
           register()
        }
    }

   fun register(){
       FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString().trim(), password.text.toString().trim())
           .addOnCompleteListener(OnCompleteListener {
               task ->
                   if (task.isSuccessful) {
                       Toast.makeText(this,"회원가입 성공",Toast.LENGTH_SHORT).show()
                       val intent= Intent(this,MainActivity::class.java)
                       intent.putExtra("user",FirebaseAuth.getInstance().currentUser.toString())
                       startActivity(intent)
                       finish()
                   } else {
                       Toast.makeText(this,"회원가입 실패",Toast.LENGTH_SHORT).show()
                       Log.d("error",task.exception!!.message.toString())
                   }
           })
           }
   }

