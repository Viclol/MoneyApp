package com.example.moneyapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{



    private ViewPager viewPager;
    private MainViewPagerAdapter pagerAdapter;
    private TickerView amountText;
    private TextView dateText;
    private int currentPagerPosition = 0;
    private Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化数据库
        GlobalUtil.getInstance().setContext(getApplicationContext());
        GlobalUtil globalUtil = GlobalUtil.getInstance();
        globalUtil.databaseHelper = new RecordDatabaseHelper(context, RecordDatabaseHelper.DB_NAME, null, 1);

        GlobalUtil.getInstance().mainActivity = this;

        getSupportActionBar().setElevation(0);

        amountText =findViewById(R.id.amount_text);
        amountText.setCharacterLists(TickerUtils.provideNumberList());		// 设置amountText使用数字切换效果

        dateText = findViewById(R.id.date_text);

        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(pagerAdapter.getLastIndex());

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddRecordActivity.class);
                startActivityForResult(intent,1);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pagerAdapter.reload();
        updateHeader();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        currentPagerPosition = i;
        updateHeader();
    }

    public void updateHeader(){
        String amount = String.valueOf(pagerAdapter.getTotalCost(currentPagerPosition)) + " ";
        amountText.setText(amount);
        String date = pagerAdapter.getDateStr(currentPagerPosition);
        dateText.setText(DateUtil.getWeekDay(date));
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
