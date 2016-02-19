package com.sun.study.ui.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sun.study.R;
import com.sun.study.adapter.ApkAdapter;
import com.sun.study.control.SingleControl;
import com.sun.study.model.ApkInfoEntity;
import com.sun.study.view.RefreshLayout;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/2/18.
 */
public class DroidPluginFragment extends BaseFragment<SingleControl> implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.lv_apks)
    ListView lvApks;
    @Bind(R.id.refresh_layout)
    RefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_droidplugin, container, false);
        ButterKnife.bind(this, rootView);

        initView();
        return rootView;
    }

    private void initView() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setHaveLoadingView(false);
        refreshLayout.setOnRefreshListener(this);

        ApkAdapter adapter = new ApkAdapter(getActivity(), getApkFromDownload());
        lvApks.setAdapter(adapter);
    }

    private void handleApkList() {
        ApkAdapter adapter = new ApkAdapter(getActivity(), getApkFromDownload());
        lvApks.setAdapter(adapter);
    }

    // 从下载文件夹获取Apk列表
    private ArrayList<ApkInfoEntity> getApkFromDownload() {
        File files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        PackageManager pm = getActivity().getPackageManager();
        ArrayList<ApkInfoEntity> apkList = new ArrayList<>();

        for (File file : files.listFiles()) {
            if (file.exists() && file.getPath().toLowerCase().endsWith(".apk")) {
                final PackageInfo info = pm.getPackageArchiveInfo(file.getPath(), 0);
                apkList.add(new ApkInfoEntity(pm, info, file.getPath()));
            }
        }
        return apkList;
    }

    @Override
    public void onRefresh() {
        messageProxy.postRunnableDelay(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                handleApkList();
            }
        }, 500);
    }

}
