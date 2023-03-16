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
