package youga.com.application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/01/03 11:33
 * @description:
 */
public class Toolbar extends android.support.v7.widget.Toolbar {


    private CharSequence mTitleText;
    private AppCompatTextView mTitleTextView;
    private int mTitleTextColor;

    public Toolbar(@NonNull Context context) {
        this(context, null);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setContentInsetsAbsolute(0, 0);
        setContentInsetsRelative(0, 0);
        setPopupTheme(R.style.Toolbar_PopupOverlay);
        // 返回按钮和标题间距
        setContentInsetStartWithNavigation(0);
        // 标题字体大小
        setTitleTextAppearance(context, R.style.Toolbar_TitleTextAppearance);
    }

    @Override
    public void setSubtitle(int resId) {
        setSubtitle(getContext().getText(resId));
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        if (!TextUtils.isEmpty(subtitle)) {
            if (mTitleText == null) {
                Context context = this.getContext();
                mTitleTextView = new AppCompatTextView(context);
                mTitleTextView.setSingleLine();
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                mTitleTextView.setTextAppearance(context, R.style.Toolbar_TitleTextAppearance);
                if (mTitleTextColor != 0) {
                    mTitleTextView.setTextColor(mTitleTextColor);
                }
                Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER;
                addView(mTitleTextView, params);
            }
        }

        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText(subtitle);
        }
        mTitleText = subtitle;
    }

    @Override
    public void setSubtitleTextColor(int color) {
        mTitleTextColor = color;
        if (mTitleTextView != null) {
            mTitleTextView.setTextColor(color);
        }
    }

    public void showPopupMenu(String[] menus) {
        showPopupMenu(menus, null);
    }

    public void showPopupMenu(String[] menus, OnPopupMenuItemClickListener menuItemClickListener) {
        if (menus == null) {
            return;
        }
        //getContext().getApplicationContext() 非常重要,getContext() Theme的背景色会影响PopupMenuWindow的背景色 所以用getContext().getApplicationContext()
        PopupMenuWindow popupWindow = new PopupMenuWindow(getContext(), menus, menuItemClickListener);
        popupWindow.show(this, getHeight());
    }

    public interface OnPopupMenuItemClickListener {
        void onItemClick(String menu, int position);
    }
}