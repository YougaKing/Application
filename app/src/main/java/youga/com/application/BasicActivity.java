package youga.com.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import youga.com.application.Toolbar.OnPopupMenuItemClickListener;

public class BasicActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private Toolbar mToolbar;
    private Chronometer mChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Example of a call to a native method
        TextView tvChronometer = (TextView) findViewById(R.id.tv_chronometer);
        TextView tv = (TextView) findViewById(R.id.sample_text);
        TextView tv1 = (TextView) findViewById(R.id.sample_text_1);
        TextView tv2 = (TextView) findViewById(R.id.sample_text_2);
        FloatRegisterView floatRegisterView = findViewById(R.id.floatRegisterView);
        tv.setText(stringFromJNI());

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNewKolPlanDetail();
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), tv.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), tv1.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), tv1.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        mChronometer = new Chronometer();
        mChronometer.setOnChronometerTickListener(duration -> {
            runOnUiThread(() -> {
                String text = mChronometer.isRunning() ? "开始:" : "暂停:";
                tvChronometer.setText(String.format(Locale.CHINA, "%s%d", text, duration));
            });
        });
        tvChronometer.setOnClickListener(v -> {
            if (mChronometer.isRunning()) {
                mChronometer.stop();
            } else {
                mChronometer.start();
            }
        });
    }

    private void gotoNewKolPlanDetail() {
        Intent intent = new Intent(this, NewKolPlanDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_basic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_more) {
            String[] strings = new String[]{
                    "删除计划课程",
                    "退出计划",
            };
            mToolbar.showPopupMenu(strings, new OnPopupMenuItemClickListener() {
                @Override
                public void onItemClick(String menu, int position) {
                    Toast.makeText(getApplicationContext(), menu, Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
