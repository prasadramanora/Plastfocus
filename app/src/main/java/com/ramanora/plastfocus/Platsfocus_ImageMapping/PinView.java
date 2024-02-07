package com.ramanora.plastfocus.Platsfocus_ImageMapping;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ramanora.plastfocus.R;


public class PinView extends SubsamplingScaleImageView {

    private final Paint paint = new Paint();
    private final PointF vPin = new PointF();
    private PointF sPin;
    private Bitmap pin;

    //for destination pin declaration

    private final Paint dpaint = new Paint();
    private final PointF dvPin = new PointF();
    private PointF dsPin;
    private Bitmap dpin;
    private  String FromName;
    private  String ToName;

    LayoutInflater inflater;



    public PinView(Context context) {
        this(context, null);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in_top);

    }

    public PinView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();

    }

    public void setPin(PointF sPin) {
        this.sPin = sPin;
        initialise();
        invalidate();
    }

    public void setPinWithName(PointF sPin,String FromName,String ToName) {
        this.sPin = sPin;
        this.FromName=FromName;
        this.ToName=ToName;
        initialise();
        invalidate();
    }

    public void setDestination(PointF dPin) {
        this.dsPin = dPin;
        initialiseDestination();
        invalidate();
    }

    private void initialise() {


        float density = getResources().getDisplayMetrics().densityDpi;
        pin = BitmapFactory.decodeResource(this.getResources(), R.drawable.pushpin_blue);
        float w = (density / 720f) * pin.getWidth();
        float h = (density / 720f) * pin.getHeight();
        pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);



    }

    private void initialiseDestination() {


        float density = getResources().getDisplayMetrics().densityDpi;
        dpin = BitmapFactory.decodeResource(this.getResources(), R.drawable.destination);
        float w = (density / 720f) * dpin.getWidth();
        float h = (density / 720f) * dpin.getHeight();
        dpin = Bitmap.createScaledBitmap(dpin, (int) w, (int) h, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pin before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }

        paint.setAntiAlias(true);

        if (sPin != null && pin != null) {
            sourceToViewCoord(sPin, vPin);
            float vX = vPin.x - (pin.getWidth() / 2);
            float vY = vPin.y - pin.getHeight();
            canvas.drawBitmap(pin, vX, vY, paint);
        }

        dpaint.setAntiAlias(true);


        if (dsPin != null && dpin != null) {
            sourceToViewCoord(dsPin, dvPin);
            float vX = dvPin.x - (dpin.getWidth() / 2);
            float vY = dvPin.y - dpin.getHeight();
            canvas.drawBitmap(dpin, vX, vY, dpaint);
        }

    }

}