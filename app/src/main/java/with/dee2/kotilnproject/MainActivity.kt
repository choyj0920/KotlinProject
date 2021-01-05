package with.dee2.kotilnproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFlag(0)
        btn_friend.setOnClickListener{
            setFlag(0)
        }
        btn_questions.setOnClickListener{
            setFlag(1)
        }
        btn_board.setOnClickListener{
            setFlag(2)
        }
        btn_signup_activity.setOnClickListener{
            val Loginintent=Intent(this, LoginActivity::class.java)
            startActivity(Loginintent)
        }
    }

    private fun setFlag(fragNum : Int) {
        val ft =supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            0 -> {
                ft.replace(R.id.main_frame, FriendListActivity()).commit()
            }
            1 -> {
                ft.replace(R.id.main_frame, QuestionListActivity()).commit()
            }
            2 -> {
                ft.replace(R.id.main_frame, BoardActivity()).commit()
            }

        }

    }
}