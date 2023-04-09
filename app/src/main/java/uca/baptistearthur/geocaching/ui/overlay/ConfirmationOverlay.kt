package uca.baptistearthur.geocaching.ui.overlay

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay
import uca.baptistearthur.geocaching.R

abstract class ConfirmationOverlay(val points: MutableCollection<PlaceMarker>) : Overlay() {

    private var circleRectF=RectF()

    override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {

        val circleSize = 300f
        val circlePadding = 20f
        val circleY = canvas.height - circleSize - circlePadding
        circleRectF= RectF(circlePadding, circleY, circlePadding + circleSize, circleY + circleSize)

        val paint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }

        canvas.drawCircle(
            circleSize / 2 + circlePadding,
            circleY + circleSize / 2,
            circleSize / 2,
            paint
        )

        val iconSize = 180
        val icon = ContextCompat.getDrawable(mapView.context, R.drawable.check)

        val iconX = (circleSize / 2 - iconSize / 2 + circlePadding).toInt()
        val iconY = (circleY + circleSize / 2 - iconSize / 2).toInt()

        icon?.setBounds(iconX, iconY, iconX + iconSize, iconY + iconSize)
        icon?.draw(canvas)
    }

    override fun onSingleTapConfirmed(e: MotionEvent?, mapView: MapView?) =
        if (e != null && circleRectF.contains(e.x, e.y)) {
            mapView?.let{
                confirm(it)
            }
            true
        }else{
            false
        }

    abstract fun confirm(mapView: MapView)

}