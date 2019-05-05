package com.mr.hw2.attractions;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttractionListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Attraction> ITEMS = new ArrayList<Attraction>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Attraction> ITEM_MAP = new HashMap<String, Attraction>();


    public static void addItem(Attraction item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static Attraction createAttraction(int position, String title, String localization, String details, String picPath) {
        return new Attraction(String.valueOf(position), title, localization, details, picPath);
    }

    public static Attraction getAttraction(int i){
        return ITEMS.get(i);
    }
    public static void clearList(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static void removeItem(int position){
        String itemID = ITEMS.get(position).id;
        ITEMS.remove(position);
        ITEM_MAP.remove(itemID);
    }

    public static class Attraction implements Parcelable {
        public final String id;
        public final String title;
        public final String localization;
        public final String description;
        public String picPath;


        public Attraction(String id, String title, String localization, String description, String picPath) {
            this.id = id;
            this.title = title;
            this.localization = localization;
            this.description = description;
            this.picPath = picPath;
        }

        protected Attraction(Parcel in) {
            id = in.readString();
            title = in.readString();
            localization = in.readString();
            description = in.readString();
            picPath = in.readString();
        }
        public void setPicPath(String path){
            this.picPath = path;
        }

        public static final Creator<Attraction> CREATOR = new Creator<Attraction>() {
            @Override
            public Attraction createFromParcel(Parcel in) {
                return new Attraction(in);
            }

            @Override
            public Attraction[] newArray(int size) {
                return new Attraction[size];
            }
        };

        @Override
        public String toString() {
            return title;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(title);
            dest.writeString(localization);
            dest.writeString(description);
            dest.writeString(picPath);
        }
    }
}
