package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.sun.study.R;
import com.sun.study.framework.dialog.ToastTip;
import com.sun.study.ui.fragment.CustomViewFragment;
import com.sun.study.ui.fragment.HomeFragment;
import com.sun.study.ui.fragment.NetworkFragment;
import com.sun.study.ui.fragment.RxJavaFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fl_container)
    FrameLayout flContainer;
    @Bind(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;
    @Bind(R.id.nv_left_layout)
    NavigationView nvLeftLayout;
    @Bind(R.id.nv_right_layout)
    NavigationView nvRightLayout;

    private MenuItem leftCheckedItem;
    private MenuItem rightCheckedItem;

    public int lastFragmentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }

    private void initData() {

    }

    private void initView() {
        initToolBar(toolbar, false, R.string.app_name);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mainDrawerLayout, toolbar, 0, 0);
        drawerToggle.syncState();
        controlShowFragment(lastFragmentIndex);
    }

    private void initListener() {
        //左侧菜单
        nvLeftLayout.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (rightCheckedItem != null && rightCheckedItem.isChecked()) {
                    rightCheckedItem.setChecked(false);
                }
                leftCheckedItem = item;
                switch (item.getItemId()) {
                    case R.id.left_menu_animator:
                        controlShowFragment(1);
                        toolbar.setTitle(R.string.left_menu_animator);
                        break;
                    case R.id.left_menu_custom_view:
                        controlShowFragment(2);
                        toolbar.setTitle(R.string.left_menu_custom_view);
                        break;
                    case R.id.left_menu_refresh:
                        controlShowFragment(3);
                        toolbar.setTitle(R.string.left_menu_refresh);
                        break;
                    case R.id.left_menu_network:
                        controlShowFragment(4);
                        toolbar.setTitle(R.string.left_menu_network);
                        break;
                    case R.id.left_menu_image:
                        controlShowFragment(5);
                        toolbar.setTitle(R.string.left_menu_image);
                        break;
                    case R.id.left_menu_map:
                        controlShowFragment(6);
                        toolbar.setTitle(R.string.left_menu_map);
                        break;
                    case R.id.left_menu_thread:
                        controlShowFragment(7);
                        toolbar.setTitle(R.string.left_menu_thread);
                        break;
                    default:
                        controlShowFragment(0);
                        toolbar.setTitle(R.string.app_name);
                        break;
                }
                getSettingsSharedPreferences().lastFragmentIndex(lastFragmentIndex);
                item.setChecked(true);
                mainDrawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        });

        //右侧菜单
        nvRightLayout.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (leftCheckedItem != null && leftCheckedItem.isChecked()) {
                    leftCheckedItem.setChecked(false);
                }
                rightCheckedItem = item;
                switch (item.getItemId()) {
                    case R.id.right_menu_share:
                        controlShowFragment(21);
                        toolbar.setTitle(R.string.right_menu_share);
                        break;
                    case R.id.right_menu_pay:
                        controlShowFragment(22);
                        toolbar.setTitle(R.string.right_menu_pay);
                        break;
                    case R.id.right_menu_push:
                        controlShowFragment(23);
                        toolbar.setTitle(R.string.right_menu_push);
                        break;
                    case R.id.right_menu_RxJava:
                        controlShowFragment(24);
                        toolbar.setTitle(R.string.right_menu_RxJava);
                        break;
                    case R.id.right_menu_binder:
                        controlShowFragment(25);
                        toolbar.setTitle(R.string.right_menu_binder);
                        break;
                    default:
                        controlShowFragment(0);
                        toolbar.setTitle(R.string.app_name);
                        break;
                }
                item.setChecked(true);
                mainDrawerLayout.closeDrawer(Gravity.RIGHT);
                return true;
            }
        });
    }

    //显示相应的Fragment
    private void controlShowFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment lastFragment = fragmentManager.findFragmentByTag(makeTag(lastFragmentIndex));
        if (lastFragment != null) {
            fragmentTransaction.hide(lastFragment);
        }
        lastFragmentIndex = position;

        Fragment currentFragment = fragmentManager.findFragmentByTag(makeTag(position));
        if (currentFragment != null) {
            fragmentTransaction.show(currentFragment);
        } else {
            if (mainDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                fragmentTransaction.add(R.id.fl_container, getLeftFragment(position), makeTag(position));
            } else if (mainDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                fragmentTransaction.add(R.id.fl_container, getRightFragment(position), makeTag(position));
            } else {
                fragmentTransaction.add(R.id.fl_container, getRightFragment(0), makeTag(position));
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private String makeTag(int position) {
        return R.id.fl_container + "_" + position;
    }

    private Fragment getLeftFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 2:
                fragment = new CustomViewFragment();
                break;
            case 4:
                fragment = new NetworkFragment();
                break;
            case 0:
            default:
                fragment = new HomeFragment();
                break;
        }
        return fragment;
    }

    private Fragment getRightFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 24:
                fragment = new RxJavaFragment();
                break;
            case 0:
            default:
                fragment = new HomeFragment();
                break;
        }
        return fragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //空操作解决Fragment重叠问题
    }

    @Override
    public void onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(Gravity.LEFT) || mainDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mainDrawerLayout.closeDrawers();
            return ;
        }
        if (System.currentTimeMillis() - lastTime < 2000) {
            super.onBackPressed();
        } else {
            lastTime = System.currentTimeMillis();
            ToastTip.show(getString(R.string.toast_exit_app));
        }
    }

}
