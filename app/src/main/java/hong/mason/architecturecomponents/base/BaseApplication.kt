package hong.mason.architecturecomponents.base

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * Created by kakao on 2017. 11. 21..
 */

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}