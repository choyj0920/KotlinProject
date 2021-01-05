package with.dee2.kotilnproject

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
    }

    private fun setFlag(fragNum : Int) {
        val ft =supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            0 -> {
                ft.replace(R.id.main_frame, FriendListFragment()).commit()
            }
            1 -> {
                ft.replace(R.id.main_frame, QuestionListFragment()).commit()
            }
            2 -> {
                ft.replace(R.id.main_frame, BoardFragment()).commit()
            }
        }

    }
}