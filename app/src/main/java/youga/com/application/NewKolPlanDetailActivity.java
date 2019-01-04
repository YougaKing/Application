package youga.com.application;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/01/03 11:24
 * @description: Kol计划详情页
 */
public class NewKolPlanDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.siv_picture)
    ImageView sivPicture;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_kol_plan_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

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
                for (int i = 0; i < mToolbar.getMenu().size(); i++) {
                    MenuItem menu = mToolbar.getMenu().getItem(i);
                    menu.setIcon(tintDrawable(menu.getIcon(), ColorStateList.valueOf(color)));
                }
            }

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int max = appBarLayout.getTotalScrollRange();
                int scrimHeight = collapsingToolbarLayout.getScrimVisibleHeightTrigger();
                int offset = Math.abs(verticalOffset);
                if (max - offset <= scrimHeight) {
                    //透明渐变黑色
                    float alpha = (scrimHeight - (max - offset)) * 1.0F / scrimHeight;
                    int color = alphaColor(Color.BLACK, alpha < 1F ? ((alpha = alpha - 0.3F) < 0F ? 0F : alpha) : alpha);
                    mToolbar.setNavigationIcon(tintDrawable(mToolbar.getNavigationIcon(), ColorStateList.valueOf(color)));
                    alphaMenuIcon(color);

                    int textColor = alphaColor(getResources().getColor(R.color.cn_textview_theme_color), alpha);
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
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share_more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                break;
            case R.id.menu_more:
//                String[] menus = getResources().getStringArray(R.array.cn_plan_chinese_array);
//                mToolbar.showPopupMenu(menus, (menu, position) -> {
//
//                });
                break;
            default:
                break;
        }
        return true;
    }
}
