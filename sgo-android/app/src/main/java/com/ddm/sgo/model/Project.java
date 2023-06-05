package com.ddm.sgo.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "projects")
public class Project {
    @PrimaryKey(autoGenerate = true)
    public long uid;

    @NonNull
    public String name;

    public String city;

    public String state;

    public String country;

    @ColumnInfo(name = "should_point_geolocation", defaultValue = "false")
    public boolean shouldPointGeolocation;

    @ColumnInfo(name = "should_point_image", defaultValue = "false")
    public boolean shouldPointImage;

    @Nullable
    @ColumnInfo(name = "synchronized_at")
    public long synchronizedAt;

    @Nullable
    public double latitude;

    @Nullable
    public double longitude;

    @Nullable
    @ColumnInfo(name = "deleted_at")
    public long deletedAt;

    @NonNull
    @ColumnInfo(name = "created_at")
    public long createdAt;

    public Project() {
    }

    @Ignore
    public Project(String name, String city, String state, String country, boolean shouldPointGeolocation, boolean shouldPointImage, long createdAt, double latitude, double longitude) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.shouldPointGeolocation = shouldPointGeolocation;
        this.shouldPointImage = shouldPointImage;
        this.createdAt = createdAt;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
