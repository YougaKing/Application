package youga.com.application;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_kol_plan_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
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
