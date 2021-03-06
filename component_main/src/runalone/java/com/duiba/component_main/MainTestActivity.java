package com.duiba.component_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.alibaba.android.arouter.launcher.ARouter;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_base.component.share.path.ShareRouterPath;

/**
 * @author Jin
 */
public class MainTestActivity extends AppCompatActivity {

    @BindView(R2.id.btn)
    Button mBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_test);
        ButterKnife.bind(this);
        mBtn.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(MainRouterPath.MAIN_ACTIVITY_MAIN)
                    .withInt("aaa", 123)
                    .navigation();
        });
    }
}
