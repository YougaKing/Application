package youga.com.application;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/01/04 16:34
 * @description:
 */
public class AppBarLayoutOverScrollViewBehavior extends AppBarLayout.Behavior {

    private static final String TAG = "overScroll";

    private static final float TARGET_HEIGHT = 500;
    private View mTargetView;
    private int mParentHeight;
    private int mTargetViewHeight;
    private float mTotalDy;
    private float mLastScale;
    private int mLastBottom;
    private boolean isAnimate;

    public AppBarLayoutOverScrollViewBehavior(Context context) {
        this(context, null);
    }

    public AppBarLayoutOverScrollViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
        boolean handled = super.onLayoutChild(parent, abl, layoutDirection);
        // 需要在调用过super.onLayoutChild()方法之后获取
        if (mTargetView == null) {
            mTargetView = parent.findViewWithTag(TAG);
            if (mTargetView != null) {
                initial(abl);
            }
        }
        return handled;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
        isAnimate = true;
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (mTargetView != null && ((dy < 0 && child.getBottom() >= mParentHeight) || (dy > 0 && child.getBottom() > mParentHeight))) {
            scale(child, target, dy);
        } else {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        if (velocityY > 100) {
            isAnimate = false;
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
        recovery(abl);
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
    }

    private void initial(AppBarLayout abl) {
        abl.setClipChildren(false);
        mParentHeight = abl.getHeight();
        mTargetViewHeight = mTargetView.getHeight();
    }

    private void scale(AppBarLayout abl, View target, int dy) {
        mTotalDy += -dy;
        mTotalDy = Math.min(mTotalDy, TARGET_HEIGHT);
        mLastScale = Math.max(1f, 1f + mTotalDy / TARGET_HEIGHT);
        ViewCompat.setScaleX(mTargetView, mLastScale);
        ViewCompat.setScaleY(mTargetView, mLastScale);
        mLastBottom = mParentHeight + (int) (mTargetViewHeight / 2 * (mLastScale - 1));
        abl.setBottom(mLastBottom);
        target.setScrollY(0);
    }

    private void recovery(final AppBarLayout abl) {
        if (mTotalDy > 0) {
            mTotalDy = 0;
            if (isAnimate) {
                ValueAnimator anim = ValueAnimator.ofFloat(mLastScale, 1f).setDuration(200);
                anim.addUpdateListener(animation -> {
                    float value = (float) animation.getAnimatedValue();
                    ViewCompat.setScaleX(mTargetView, value);
                    ViewCompat.setScaleY(mTargetView, value);
                    abl.setBottom((int) (mLastBottom - (mLastBottom - mParentHeight) * animation.getAnimatedFraction()));
                });
                anim.start();
            } else {
                ViewCompat.setScaleX(mTargetView, 1f);
                ViewCompat.setScaleY(mTargetView, 1f);
                abl.setBottom(mParentHeight);
            }
        }
    }
}