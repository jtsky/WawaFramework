package com.duiba.component_main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_base.component.DuibaMvpPresenter;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: jintai
 * @time: 2017/11/9-18:36
 * @Email: jintai@duiba.com.cn
 * @desc: 新手引导页
 */
@Route(path = MainRouterPath.MAIN_ACTIVITY_IO)
public class IOActivity extends BaseActivity {


    @BindView(R.id.btn_io)
    Button mBtnIo;
    @BindView(R.id.btn_nio)
    Button mBtnNio;
    @BindView(R.id.btn_okio)
    Button mBtnOkio;
    @BindView(R.id.tv_read)
    TextView mTvRead;
    private String filePath = "file:///android_asset/text.txt";
    private File file = new File(filePath);

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_io);
        ButterKnife.bind(this);

        RxView.clicks(mBtnIo).subscribe(aVoid -> {
            readTextWithIo(file);
        });
        RxView.clicks(mBtnNio).subscribe(aVoid -> {

        });
        RxView.clicks(mBtnOkio).subscribe(aVoid -> {

        });


    }

    @Override
    protected ViewModel createViewModel() {
        return null;
    }

    @Override
    protected void performViewModelSubscribe(ViewModel viewModel) {

    }

    /**
     * 普通io
     */
    private void readTextWithIo(File file) {
        BufferedReader br = null;
        String sCurrentLine;
        try {
            br = new BufferedReader(
                    new FileReader(file));
            while ((sCurrentLine = br.readLine()) != null) {
                Logger.v(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * nio
     */
    private void readTextWithNio(File file) {

    }

    /**
     * okio
     */
    private void readTextWithOkio(File file) {

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected boolean isMVP() {
        return false;
    }




    @Override
    public DuibaMvpPresenter onCreatePresenter() {
        return null;
    }
}
