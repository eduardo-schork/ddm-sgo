package com.ddm.sgo.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "teams")
public class Team {
    public String name;
    @ColumnInfo(name = "number_of_members")
    public Integer numberOfMembers;

    @PrimaryKey(autoGenerate = true)
    public long uid;

    @Nullable
    @ColumnInfo(name = "synchronized_at")
    public long synchronizedAt;

    @Nullable
    @ColumnInfo(name = "deleted_at")
    public long deletedAt;

    @NonNull
    @ColumnInfo(name = "created_at")
    public long createdAt;

    public Team() {
    }

    @Ignore
    public Team(String name, Integer numberOfMembers, long createdAt) {
        this.name = name;
        this.numberOfMembers = numberOfMembers;
        this.createdAt = createdAt;
    }
}
