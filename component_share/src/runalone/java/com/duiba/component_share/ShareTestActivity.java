package com.duiba.component_share;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.alibaba.android.arouter.launcher.ARouter;
import com.duiba.component_base.component.share.path.ShareRouterPath;

/**
 * @author Jin
 */
public class ShareTestActivity extends AppCompatActivity {

    @BindView(R2.id.btn)
    Button mBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity_test);
        ButterKnife.bind(this);
        mBtn.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(ShareRouterPath.SHARE_ACTIVITY_SHARE)
                    .withInt("aaa", 123)
                    .navigation();
        });
    }
}
