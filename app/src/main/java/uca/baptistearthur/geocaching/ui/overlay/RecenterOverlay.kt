package uca.baptistearthur.geocaching.ui.overlay

import android.graphics.*
import android.location.Location
import android.util.Log
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.mylocation.IMyLocationConsumer
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider
import uca.baptistearthur.geocaching.R


class RecenterOverlay(val myLocationProvider: IMyLocationProvider, val mapView: MapView) : Overlay(), IMyLocationConsumer {

    private var circleRectF=RectF()
    private var mIsFollowing = false
    private var mLocation: Location? = null
    override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {

        val circleSize = 300f
        val circlePadding = 20f
        val circleX = canvas.width - circleSize - circlePadding
        val circleY = canvas.height - circleSize - circlePadding
        circleRectF=RectF(circleX, circleY, circleX + circleSize, circleY + circleSize)

        val paint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        canvas.drawCircle(
            circleX + circleSize / 2,
            circleY + circleSize / 2,
            circleSize / 2,
            paint
        )

        val iconSize = 180
        val icon = ContextCompat.getDrawable(mapView.context, R.drawable.center)

        val iconX = (circleX + circleSize / 2 - iconSize / 2).toInt()
        val iconY = (circleY + circleSize / 2 - iconSize / 2).toInt()

        icon?.setBounds(iconX, iconY, iconX + iconSize, iconY + iconSize)
        icon?.draw(canvas)
    }

    override fun onLocationChanged(location: Location, source: IMyLocationProvider) {
        mLocation = myLocationProvider.lastKnownLocation
        if (mIsFollowing) {
            mapView.controller.animateTo(GeoPoint(location.latitude, location.longitude));
        } else {
            mapView.postInvalidate();
        }
    }

    fun enableMyLocation() {
        mLocation = myLocationProvider.lastKnownLocation
        mIsFollowing = true;
        if(mLocation!=null) {
            mapView.controller?.animateTo(GeoPoint(mLocation!!.latitude, mLocation!!.longitude))
        }
        myLocationProvider.startLocationProvider(this);
    }

    fun disableMyLocation(){
        mapView.controller?.stopAnimation(false)
        mIsFollowing = false
        myLocationProvider.stopLocationProvider();
    }

    override fun onTouchEvent(event: MotionEvent?, mapView: MapView?): Boolean {
        val isSingleFingerDrag = event!!.action == MotionEvent.ACTION_MOVE && event.pointerCount == 1
        if (event.action == MotionEvent.ACTION_DOWN) {
            disableMyLocation()
        } else if (isSingleFingerDrag && mIsFollowing) {
            return true // prevent the pan
        }
        return super.onTouchEvent(event, mapView)
    }

    override fun onSingleTapConfirmed(e: MotionEvent?, mapView: MapView?) =
        myLocationProvider.lastKnownLocation?.let {
            if (e != null && circleRectF.contains(e.x, e.y)) {
                Log.d("GeoRoad", "RECENTER")
                mapView?.controller?.setCenter(GeoPoint(it.latitude, it.longitude))
                mapView?.controller?.setZoom(21.0);
                enableMyLocation()
                true
            }else{
                false
            }
        } ?: false


    override fun onResume() {
        super.onResume()
        enableMyLocation()
    }

    override fun onPause(){
        disableMyLocation()
        super.onPause()
    }

    override fun onDetach(mapView: MapView?) {
        disableMyLocation()
        super.onDetach(mapView)
    }
}
