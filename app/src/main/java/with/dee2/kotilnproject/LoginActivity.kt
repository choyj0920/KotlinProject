package with.dee2.kotilnproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        auth=FirebaseAuth.getInstance()
        var currentUser =auth.currentUser
        setContentView(R.layout.activity_login)



        signIn_button.setOnClickListener(){
            login()
        }

        register_button.setOnClickListener {
            val intent= Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun login(){
        var email = email_edittext.text.toString()
        var password = password_edittext.text.toString()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task ->
            if(task.isSuccessful){
                val intent= Intent(this,MainActivity::class.java)
                intent.putExtra("user",auth.currentUser)
                startActivity(intent)
            }else{
                Toast.makeText(this,"로그인 실패", Toast.LENGTH_SHORT).show()
                Log.d("error",task.exception!!.message.toString())
            }

        }

  }
}