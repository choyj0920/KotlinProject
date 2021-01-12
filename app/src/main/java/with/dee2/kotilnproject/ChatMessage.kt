package with.dee2.kotilnproject

class ChatMessage(val isCurrentUser: Boolean, val username:String,val timestamp: String, val msgtext: String){
    companion object {
        const val msg_left = 0
        const val msg_right = 1
    }
}