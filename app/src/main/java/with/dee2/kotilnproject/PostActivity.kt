package with.dee2.kotilnproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_post.*
import java.text.SimpleDateFormat
import java.util.*

class PostActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        post_date.text=intent.getStringExtra("num")
        post_question.text=intent.getStringExtra("question")

        postButton.setOnClickListener{
            saveData()
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun saveData() {
        val time =SimpleDateFormat("yy.MM.dd").format(Date())
        val data=Post(FirebaseAuth.getInstance().currentUser?.uid.toString(),post_question.text.toString(),post_data.text.toString(),time)
        database=FirebaseDatabase.getInstance().reference.child("users")
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .child("questions")
        database.setValue(data)

        database= FirebaseDatabase.getInstance().reference.child("community")
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .child(post_question.text.toString())
        database.setValue(data)


    }
}