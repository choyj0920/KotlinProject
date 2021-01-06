package with.dee2.kotilnproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val adapter= GroupAdapter<ViewHolder>()
        adapter.add(UserItem())


    }
}

class UserItem: Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.item_message_left
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

}