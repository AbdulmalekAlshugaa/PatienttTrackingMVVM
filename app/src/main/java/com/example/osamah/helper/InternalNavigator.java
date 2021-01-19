package com.example.osamah.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class InternalNavigator implements Parcelable {
    private int mNavigatorIcon;
    private String mNavigatorName;

    public InternalNavigator(int navigatorIcon, String navigatorName) {
        this.mNavigatorIcon = navigatorIcon;
        this.mNavigatorName = navigatorName;
    }

    public int getNavigatorIcon() {
        return mNavigatorIcon;
    }

    public String getNavigatorName() {
        return mNavigatorName;
    }

    private InternalNavigator(Parcel in) {
        mNavigatorIcon = in.readInt();
        mNavigatorName = in.readString();
    }

    public static final Creator<InternalNavigator> CREATOR = new Creator<InternalNavigator>() {
        @Override
        public InternalNavigator createFromParcel(Parcel in) {
            return new InternalNavigator(in);
        }

        @Override
        public InternalNavigator[] newArray(int size) {
            return new InternalNavigator[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mNavigatorIcon);
        parcel.writeString(mNavigatorName);
    }
}
