package youga.com.application;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MenuItem;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/01/05 9:15
 * @description:
 */
public class AlphaOnOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    public AlphaOnOffsetChangedListener(Toolbar toolbar, CollapsingToolbarLayout collapsingToolbarLayout) {
        mToolbar = toolbar;
        mCollapsingToolbarLayout = collapsingToolbarLayout;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mCollapsingToolbarLayout == null) {
            return;
        }
        int max = appBarLayout.getTotalScrollRange();
        int scrimHeight = mCollapsingToolbarLayout.getScrimVisibleHeightTrigger();
        int offset = Math.abs(verticalOffset);
        if (max - offset <= scrimHeight) {
            //透明渐变黑色
            float alpha = (scrimHeight - (max - offset)) * 1.0F / scrimHeight;
            int color = alphaColor(Color.BLACK, alpha < 1F ? ((alpha = alpha - 0.3F) < 0F ? 0F : alpha) : alpha);
            mToolbar.setNavigationIcon(tintDrawable(mToolbar.getNavigationIcon(), ColorStateList.valueOf(color)));
            alphaMenuIcon(color);

            int textColor = alphaColor(appBarLayout.getResources().getColor(R.color.cn_textview_theme_color), alpha);
            mToolbar.setSubtitleTextColor(textColor);
        } else {
            //白色渐变透明
            int offsetMax = max - scrimHeight;
            int color = alphaColor(Color.WHITE, (offsetMax - offset) * 1.0F / offsetMax);
            mToolbar.setNavigationIcon(tintDrawable(mToolbar.getNavigationIcon(), ColorStateList.valueOf(color)));
            alphaMenuIcon(color);

            mToolbar.setSubtitleTextColor(Color.TRANSPARENT);
        }
    }

    private int alphaColor(int color, float alpha) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & color;
        return a + rgb;
    }

    private Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    private void alphaMenuIcon(int color) {
        if (mToolbar == null) {
            return;
        }
        for (int i = 0; i < mToolbar.getMenu().size(); i++) {
            MenuItem menu = mToolbar.getMenu().getItem(i);
            Drawable drawable = menu.getIcon();
            if (drawable != null) {
                menu.setIcon(tintDrawable(drawable, ColorStateList.valueOf(color)));
            }
        }
    }
}
