package monim.blackice.business.data.model.user

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class User {

    lateinit var user_id: String
    lateinit var full_name: String
    lateinit var gender: String
    var website: String? = null
    lateinit var email: String
    lateinit var image: String
    lateinit var token: String
}