package with.dee2.kotilnproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_questionlist.*
import kotlinx.android.synthetic.main.fragment_questionlist.view.*

class QuestionListFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_questionlist, container,false)

        view.chat.setOnClickListener {
            val intent= Intent(activity,ChatActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}