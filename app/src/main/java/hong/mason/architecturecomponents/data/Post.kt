package hong.mason.architecturecomponents.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by kakao on 2017. 11. 21..
 */
data class Post (
        val id : Long = -1,
        val writer : String,
        val date : Long,
        val writeDate : Long,
        val title : String = "",
        val content : String = "",
        val degree : Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(writer)
        parcel.writeLong(date)
        parcel.writeLong(writeDate)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeInt(degree)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }

}