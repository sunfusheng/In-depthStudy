package com.sun.study.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.view.BodyTextView;
import com.arlib.floatingsearchview.util.view.IconImageView;
import com.sun.study.R;
import com.sun.study.model.AmapPoiEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/12/11.
 */
public class AmapActivity extends BaseActivity implements LocationSource, AMapLocationListener, View.OnClickListener {

    @Bind(R.id.mapView)
    MapView mapView;
    @Bind(R.id.floating_search_view)
    FloatingSearchView floatingSearchView;

    private AMap aMap;
    private OnLocationChangedListener mOnLocationChangedListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private AMapLocation aMapLocation;
    private int zoomLevel = 16;
    private List<AmapPoiEntity> swapPois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        aMap = mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));
        myLocationStyle.strokeColor(Color.TRANSPARENT);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(zoomLevel));
    }

    private void initView() {
        swapPois = new ArrayList<AmapPoiEntity>();
    }

    private void initListener() {
        floatingSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_location) {
                    if (aMapLocation != null) {
                        aMap.animateCamera(CameraUpdateFactory.changeLatLng(
                                new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    }
                }
            }
        });

        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!TextUtils.isEmpty(newQuery)) {
                    doPoiSearchQuery(newQuery);
                }
            }
        });
        floatingSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(IconImageView leftIcon, BodyTextView bodyText, SearchSuggestion item, int itemPosition) {
                if (swapPois != null && swapPois.size() > itemPosition) {
                    AmapPoiEntity entity = swapPois.get(itemPosition);
                    aMap.animateCamera(CameraUpdateFactory.changeLatLng(entity.getLatLng()));
                }
            }
        });
    }

    protected void doPoiSearchQuery(String keyWord) {
        floatingSearchView.showProgress();
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", aMapLocation == null? "北京":aMapLocation.getCity());
        query.setPageSize(50);
        query.setPageNum(0);

        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                floatingSearchView.hideProgress();
                if (poiResult != null && poiResult.getPois() != null) {
                    List<PoiItem> pois = poiResult.getPois();
                    for (PoiItem item:pois) {
                        AmapPoiEntity entity = new AmapPoiEntity();
                        LatLng latLng = new LatLng(item.getLatLonPoint().getLatitude(), item.getLatLonPoint().getLongitude());
                        entity.setLatLng(latLng);
                        entity.setTitle(item.getTitle());
                        swapPois.add(entity);
                    }
                    floatingSearchView.swapSuggestions(swapPois);
                }
            }
        });
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mOnLocationChangedListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mOnLocationChangedListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
            mlocationClient = null;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mOnLocationChangedListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                this.aMapLocation = aMapLocation;
                mOnLocationChangedListener.onLocationChanged(aMapLocation);
                deactivate();
                Log.d("---> ", "定位成功: " + aMapLocation.getAddress());
            } else {
                Log.d("---> ", "定位失败: " + aMapLocation.getErrorInfo());
            }
        }
    }

}
