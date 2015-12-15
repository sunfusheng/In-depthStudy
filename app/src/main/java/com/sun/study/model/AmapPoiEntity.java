package com.sun.study.model;

import android.os.Parcel;

import com.amap.api.maps2d.model.LatLng;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by sunfusheng on 15/12/14.
 */
public class AmapPoiEntity implements SearchSuggestion {

    private String title;
    private LatLng latLng;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public static Creator<AmapPoiEntity> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
    }

    public AmapPoiEntity() {
    }

    protected AmapPoiEntity(Parcel in) {
        this.title = in.readString();
    }

    public static final Creator<AmapPoiEntity> CREATOR = new Creator<AmapPoiEntity>() {
        public AmapPoiEntity createFromParcel(Parcel source) {
            return new AmapPoiEntity(source);
        }

        public AmapPoiEntity[] newArray(int size) {
            return new AmapPoiEntity[size];
        }
    };

    @Override
    public String getBody() {
        return title;
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}
