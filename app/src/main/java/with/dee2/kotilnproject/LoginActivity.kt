package with.dee2.kotilnproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth=FirebaseAuth.getInstance()
        var currentUser =auth.currentUser
        setContentView(R.layout.activity_login)



        signup_button.setOnClickListener(){
            createEmailId()
        }
    }

    fun createEmailId(){
        var email = emil_edittext.text.toString()
        var password = password_edittext.text.toString()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task ->
            if(task.isSuccessful){
                println("SignUp Success")
                val user = auth.currentUser

            }

        }

    }
}