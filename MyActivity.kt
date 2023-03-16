/*
  overlayView needs Settings.canDrawOverlays permission
*/

private fun requestPermission(){
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
        !Settings.canDrawOverlays(applicationContext)) { // check DrawOverlay permission.
    alert("for using overlayView needs permission") {
        positiveButton("set permission") {
            startActivityForResult(
              // Request DrawOverlay permission.
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${applicationContext.packageName}")
                ),
                REQUEST_CODE
            )
        }
        negativeButton("cancel") {
            // todo... when canceled...
        }

    }.show()
}
  
/*
  Draw overlayView using Service..
*/
fun startDrawOverlayViewService(){
  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
      if(Settings.canDrawOverlays(applicationContext)){
          val mIntent = Intent(applicationContext, DrawOverlayViewService::class.java)

          if(applicationContext.isRunning<DrawOverlayViewService>()) return

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
              startForegroundService(mIntent)
          else
              startService(mIntent)
      }
  }
}
