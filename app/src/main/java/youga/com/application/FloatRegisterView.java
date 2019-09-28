package youga.com.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/9/27 18:11
 * @description:
 */
public class FloatRegisterView extends View {

    private int mWidth;
    private int mHeight;
    private float mRegionTop;
    private float mRegionWidth;
    private float mRegionHeight;
    private float mRegionRadius;
    private PorterDuffXfermode mDuffXfermode;
    private Paint mPaint = new Paint();
    private Bitmap mRegionBitmap;
    private RectF mRegionRectF = new RectF();
    private Bitmap mBackgroundBitmap;

    public FloatRegisterView(Context context) {
        this(context, null);
    }

    public FloatRegisterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatRegisterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    public void setRegion(float top, float width, float height, float radius) {
        mRegionTop = top;
        mRegionWidth = width;
        mRegionHeight = height;
        mRegionRadius = radius;
        if (mWidth == 0 || mHeight == 0) return;
        mRegionBitmap = createRegionBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mRegionBitmap == null) mRegionBitmap = createRegionBitmap();
        if (mBackgroundBitmap == null) mBackgroundBitmap = createBackgroundBitmap();

        mPaint.setFilterBitmap(false);

        canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mRegionBitmap, 0, 0, mPaint);
        mPaint.setXfermode(mDuffXfermode);

        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);
    }

    @Override
    public boolean performClick() {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mRegionRectF.contains(event.getX(), event.getY())) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private Bitmap createRegionBitmap() {
        Bitmap bm = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        float left = (mWidth - mRegionWidth) / 2;
        float top = mRegionTop;
        float right = left + mRegionWidth;
        float bottom = top + mRegionHeight;
        mRegionRectF.set(left, top, right, bottom);
        canvas.drawRoundRect(mRegionRectF, mRegionRadius, mRegionRadius, paint);
        return bm;
    }

    private Bitmap createBackgroundBitmap() {
        Bitmap bm = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#CC000000"));
        canvas.drawRect(new RectF(0, 0, mWidth, mHeight), paint);
        return bm;
    }
}
