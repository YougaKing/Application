package youga.com.application;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/01/03 11:33
 * @description:
 */
public class Toolbar extends android.support.v7.widget.Toolbar {

    private static final String TAG = "Toolbar";
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvMiddleTitle;

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

        View view = LayoutInflater.from(context).inflate(R.layout.toolbar, this, false);
        mIvBack = view.findViewById(R.id.iv_back);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvMiddleTitle = view.findViewById(R.id.tv_middle_title);
        addView(view);


        TypedArray a = context.obtainStyledAttributes(attrs, android.support.v7.appcompat.R.styleable.Toolbar, defStyleAttr, 0);
        Drawable navIcon = a.getDrawable(android.support.v7.appcompat.R.styleable.Toolbar_navigationIcon);
        if (navIcon != null) {
            setNavigationIcon(navIcon);
        }
        CharSequence title = a.getText(android.support.v7.appcompat.R.styleable.Toolbar_title);
        Log.d(TAG, "title:" + title);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        a.recycle();
    }

    @Override
    public void setNavigationIcon(int resId) {
        if (mIvBack == null) {
            return;
        }
        mIvBack.setVisibility(VISIBLE);
        mIvBack.setImageResource(resId);
    }

    @Override
    public void setNavigationIcon(@Nullable Drawable icon) {
        if (mIvBack == null) {
            return;
        }
        mIvBack.setVisibility(VISIBLE);
        mIvBack.setImageDrawable(icon);
    }

    @Override
    public void setTitle(int resId) {
        if (mTvTitle == null) {
            return;
        }
        mTvTitle.setVisibility(VISIBLE);
        mTvTitle.setText(resId);
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mTvTitle == null) {
            return;
        }
        Log.d(TAG, "setTitle:" + title);
        mTvTitle.setVisibility(VISIBLE);
        mTvTitle.setText(title);
    }

}