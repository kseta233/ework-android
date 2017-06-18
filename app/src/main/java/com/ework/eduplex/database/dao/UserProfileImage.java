package com.ework.eduplex.database.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by eWork on 4/20/2016.
 */
@DatabaseTable(tableName = "user_profile_image")
public class UserProfileImage {
    @DatabaseField(columnName = "referalCode", id = true)
    private String referalCode;
    @DatabaseField(columnName = "profileImage")
    private String profileImage;

    public UserProfileImage() {
    }

    public String getReferalCode() {
        return referalCode;
    }

    public void setReferalCode(String referalCode) {
        this.referalCode = referalCode;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public UserProfileImage(String referalCode, String profileImage) {

        this.referalCode = referalCode;
        this.profileImage = profileImage;
    }
}
