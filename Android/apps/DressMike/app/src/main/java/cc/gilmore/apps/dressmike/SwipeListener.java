package cc.gilmore.apps.dressmike;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by Mike on 4/6/2016.
 */
public class SwipeListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 150;
    private static final int SWIPE_MAX_OFF_PATH = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float dX = e2.getX()-e1.getX();
        float dY = e1.getY()-e2.getY();

        if (Math.abs(dY)<SWIPE_MAX_OFF_PATH ||
            Math.abs(velocityX)>=SWIPE_THRESHOLD_VELOCITY ||
            Math.abs(dX)>=SWIPE_MIN_DISTANCE ) {

            if (dX>0) {
                Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
            }

            return true;

        } else if (Math.abs(dX)<SWIPE_MAX_OFF_PATH ||
            Math.abs(velocityY)>=SWIPE_THRESHOLD_VELOCITY ||
            Math.abs(dY)>=SWIPE_MIN_DISTANCE ) {

            if (dY>0) {
                Toast.makeText(getApplicationContext(), "Up Swipe", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Down Swipe", Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        return false;
    }
}
