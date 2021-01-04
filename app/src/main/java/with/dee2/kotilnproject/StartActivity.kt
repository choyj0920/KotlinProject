package with.dee2.kotilnproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

       Glide.with(this).load(R.raw.big_head).into(imageView)

        moveMainActivity()
    }

    fun moveMainActivity() {
        Handler().postDelayed({startActivity(Intent(this,MainActivity::class.java))},3000L)
    }
}
