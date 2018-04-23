package com.duiba.component_main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.duiba.component_base.util.ActivitySwitcher;
import com.duiba.component_main.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/4/21-14:32
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class TransitionActivity extends AppCompatActivity {
    @BindView(R2.id.bSwitchActivity)
    Button mBSwitchActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_transition);
        ButterKnife.bind(this);

        mBSwitchActivity.setOnClickListener(v -> animatedStartActivity());
    }

    @Override
    protected void onResume() {
        // animateIn this activity
        ActivitySwitcher.animationIn(findViewById(R.id.container),
                getWindowManager());
        super.onResume();
    }

    private void animatedStartActivity() {
        // we only animateOut this activity here.
        // The new activity will animateIn from its onResume() - be sure to
        // implement it.
        final Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        // disable default animation for new intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ActivitySwitcher.animationOut(findViewById(R.id.container),
                getWindowManager(),
                () -> startActivity(intent));
    }
}
