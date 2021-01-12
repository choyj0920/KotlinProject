package with.dee2.kotilnproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    companion object{
        // 현재 로그인 되어있는 유저 uid
        public lateinit var currentuseruid : String
        public lateinit var currentuserimg : String
        public lateinit var currentusername : String

    }

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
        if (email=="" || password == ""){
            Toast.makeText(this,"빈 곳이 있습니다.",Toast.LENGTH_SHORT).show()
            return

        }

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task ->
            if(task.isSuccessful){
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"로그인 실패", Toast.LENGTH_SHORT).show()
                Log.d("error",task.exception!!.message.toString())
            }

        }
        currentuseruid =FirebaseAuth.getInstance().currentUser?.uid.toString()

    }
}