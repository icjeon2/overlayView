


import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.JsonObject
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.telephonyManager
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DrawOverlayViewService : Service(){

    lateinit var wm: WindowManager
    lateinit var mView : View
    var isViewAdded = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // onCreate -> onStartCommand
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        
      mView = makeFloatingView(resultVO, applicationContext)
      wm.addView(mView, setLayoutParams(Gravity.CENTER, -30)) 
      isViewAdded = true
        
      return super.onStartCommand(intent, flags, startId)
    }


    private fun setLayoutParams(gravity: Int, y : Int): WindowManager.LayoutParams {
        // 윈도우매니저 설정
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = gravity
        params.y = y

        return params
    }


    override fun onCreate() {
        super.onCreate()
        val noti = NotificationCompat.Builder(context, channelId).apply {
            //set builder
            
            }.build()
        
        startForeground(NOTIFICATION_ID, noti)
        wm = getSystemService(WINDOW_SERVICE) as WindowManager
    }

    override fun onDestroy() {
        super.onDestroy()
        removeView()
    }

    fun removeView(){
        Timber.d("removeView isViewAdded : $isViewAdded")
        if (isViewAdded) {
            wm.removeView(mView)
            isViewAdded = false
        }
    }

    fun makeFloatingView(mContext: Context) : View{
      
        mView = View.inflate(mContext, R.layout.your_layout, null)
        
        val tv_searchBtn = mView.findViewById(R.id.tv_phoneNum) as TextView
        val btn_img = mView.findViewById(R.id.floating_close) as ImageView

        
        btn_img.setOnClickListener {
            stopSelf()
        }

   
        tv_searchBtn.setOnClickListener{
            //to do something...
            stopSelf()
        }
        return mView
    }

}
