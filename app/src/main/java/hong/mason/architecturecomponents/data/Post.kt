package hong.mason.architecturecomponents.data

/**
 * Created by kakao on 2017. 11. 21..
 */
data class Post(
        val id : Long,
        val writer : String,
        val title : String,
        val content : String,
        val writeDate : Long
)