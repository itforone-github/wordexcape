package com.vocaescape.vocaescape.menu;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vocaescape.vocaescape.ActivityManager;
import com.vocaescape.vocaescape.R;

import butterknife.ButterKnife;

public class MenuintroduceActivity extends AppCompatActivity {
    private ActivityManager am = ActivityManager.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu01);

        am.addActivity(this);
        ButterKnife.bind(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }
}
