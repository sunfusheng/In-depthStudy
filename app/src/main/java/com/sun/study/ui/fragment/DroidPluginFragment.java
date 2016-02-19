package com.sun.study.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sun.study.R;
import com.sun.study.adapter.ApkAdapter;
import com.sun.study.control.SingleControl;
import com.sun.study.model.AppInfoEntity;
import com.sun.study.view.RefreshLayout;

import java.util.List;

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

        mControl.getApkListFromDownload(getContext());
    }

    public void getApkListFromDownloadCallBack() {
        List<AppInfoEntity> apkList = mModel.getList(1);
        ApkAdapter adapter = new ApkAdapter(getActivity(), apkList);
        lvApks.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        messageProxy.postRunnableDelay(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                mControl.getApkListFromDownload(getContext());
            }
        }, 500);
    }

}
