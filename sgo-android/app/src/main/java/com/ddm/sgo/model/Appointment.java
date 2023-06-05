package com.ddm.sgo.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "appointments")
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    public long uid;

    @NonNull
    public long date;

    @NonNull
    public String type;

    // TODO add database relationship
    @NonNull
    @ColumnInfo(name = "project_uid")
    public long projectUid;

    // TODO add database relationship
    @Nullable
    @ColumnInfo(name = "present_team_uid")
    public long presentTeamUid;

    @Nullable
    public double latitude;

    @Nullable
    public double longitude;

    @Nullable
    @Ignore
    public Bitmap image;

    @Nullable
    @ColumnInfo(name = "image_path")
    public String imagePath;

    @Nullable
    @ColumnInfo(name = "synchronized_at")
    public long synchronizedAt;

    @Nullable
    @ColumnInfo(name = "deleted_at")
    public long deletedAt;

    @NonNull
    @ColumnInfo(name = "created_at")
    public long createdAt;

    public Appointment() {
    }

}
