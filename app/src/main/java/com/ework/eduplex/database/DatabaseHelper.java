package com.ework.eduplex.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ework.eduplex.database.dao.MyAsset;
import com.ework.eduplex.database.dao.UserCompanyRegion;
import com.ework.eduplex.database.dao.UserEmergencyContactRegion;
import com.ework.eduplex.database.dao.UserProfileImage;
import com.ework.eduplex.utils.Constant;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by eWork on 3/11/2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Context context;

    private RuntimeExceptionDao<MyAsset, Integer> myAssetDao = null;
    private RuntimeExceptionDao<UserProfileImage, Integer> userProfileImageDao = null;
    private RuntimeExceptionDao<UserCompanyRegion, Integer> userCompanyRegionDao = null;
    private RuntimeExceptionDao<UserEmergencyContactRegion, Integer> userEmergencyContactRegionDao = null;

    public DatabaseHelper(Context context) {
        super(context, Constant.Database.NAME, null, Constant.Database.VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        createAllTable(connectionSource);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        resetDatabase();
    }

    public void resetDatabase() {
        dropAllTable(getConnectionSource());
        createAllTable(getConnectionSource());
    }

    private void createAllTable(ConnectionSource connectionSource) {
//        List<String> classess = FileUtil.getClasses(context,
//                Constant.Database.DAO_PACKAGE_NAME);
//
//        for (String strClass : classess) {
//            @SuppressWarnings("rawtypes")
//            Class c = null;
//            try {
//                c = Class.forName(strClass);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            if (c != null) {
//                try {
//                    TableUtils.createTableIfNotExists(connectionSource, c);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        try {
            TableUtils.createTable(connectionSource, MyAsset.class);
            TableUtils.createTable(connectionSource, UserProfileImage.class);
            TableUtils.createTable(connectionSource, UserCompanyRegion.class);
            TableUtils.createTable(connectionSource, UserEmergencyContactRegion.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropAllTable(ConnectionSource connectionSource) {

//        List<String> classess = FileUtil.getClasses(context,
//                Constant.Database.DAO_PACKAGE_NAME);
//        for (String strClass : classess) {
//            @SuppressWarnings("rawtypes")
//            Class c = null;
//            try {
//                c = Class.forName(strClass);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            if (c != null) {
//                try {
//                    TableUtils.dropTable(connectionSource, c, true);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        try {
            TableUtils.dropTable(connectionSource, MyAsset.class, true);
            TableUtils.dropTable(connectionSource, UserProfileImage.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
    }


    //USER ASSET
    public RuntimeExceptionDao<MyAsset, Integer> getMyAssetDao() {
        if (myAssetDao == null) {
            myAssetDao = getRuntimeExceptionDao(MyAsset.class);
        }
        return myAssetDao;
    }

    public List<MyAsset> getListMyAssetById(String assetId) {
        RuntimeExceptionDao<MyAsset, Integer> dao;
        dao = getMyAssetDao();
        try {
            return dao.queryBuilder().where().eq("assetId", assetId).query();
        } catch (Exception e) {
            return null;
        }
    }

    public List<MyAsset> getListMyAsset() {
        RuntimeExceptionDao<MyAsset, Integer> dao;
        dao = getMyAssetDao();
        try {
            return dao.queryForAll();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteMyAssetById(int assetId) {
        RuntimeExceptionDao<MyAsset, Integer> dao;
        dao = getMyAssetDao();
        try {
            DeleteBuilder deleteBuilder = dao.deleteBuilder();
            deleteBuilder.where().eq("assetId", assetId);
            deleteBuilder.delete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    //USER PROFILE IMAGE
    public RuntimeExceptionDao<UserProfileImage, Integer> getUserProfileImageDao() {
        if (userProfileImageDao == null) {
            userProfileImageDao = getRuntimeExceptionDao(UserProfileImage.class);
        }
        return userProfileImageDao;
    }

    public List<UserProfileImage> getUserProfileByReferalCode(String referalCode) {
        RuntimeExceptionDao<UserProfileImage, Integer> dao;
        dao = getUserProfileImageDao();
        try {
            return dao.queryBuilder().where().eq("referalCode", referalCode).query();
        } catch (Exception e) {
            return null;
        }
    }


    //USER COMPANY REGION
    public RuntimeExceptionDao<UserCompanyRegion, Integer> getUserCompanyRegionDao() {
        if (userCompanyRegionDao == null) {
            userCompanyRegionDao = getRuntimeExceptionDao(UserCompanyRegion.class);
        }
        return userCompanyRegionDao;
    }

    public List<UserCompanyRegion> getUserCompanyRegionByReferalCode(String referalCode) {
        RuntimeExceptionDao<UserCompanyRegion, Integer> dao;
        dao = getUserCompanyRegionDao();
        try {
            return dao.queryBuilder().where().eq("referalCode", referalCode).query();
        } catch (Exception e) {
            return null;
        }
    }


    //USER EMERGENCY CONTACT REGION
    public RuntimeExceptionDao<UserEmergencyContactRegion, Integer> getUserEmergencyContactRegionDao() {
        if (userEmergencyContactRegionDao == null) {
            userEmergencyContactRegionDao = getRuntimeExceptionDao(UserEmergencyContactRegion.class);
        }
        return userEmergencyContactRegionDao;
    }

    public List<UserEmergencyContactRegion> getUserEmergencyContactRegionByReferalCode(String referalCode) {
        RuntimeExceptionDao<UserEmergencyContactRegion, Integer> dao;
        dao = getUserEmergencyContactRegionDao();
        try {
            return dao.queryBuilder().where().eq("referalCode", referalCode).query();
        } catch (Exception e) {
            return null;
        }
    }
}
