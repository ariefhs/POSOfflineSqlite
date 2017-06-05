package com.lupawktu.possqlite.store;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lupawktu.possqlite.PublicMethod.GetTimeMiliSecond;
import com.lupawktu.possqlite.PublicMethod.SetDefaultStore;
import com.lupawktu.possqlite.db.DBApps;
import com.lupawktu.possqlite.db.TableClass;
import com.lupawktu.possqlite.reponse.ResponseModel;
import com.lupawktu.possqlite.reponse.ResponseView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Mind on 6/1/2017.
 */

public class StoreProses {
    private ResponseModel model;
    private ResponseView view;
    private StoreModel storeModel;
    private StoreDetailModel storeDetailModel;

    public StoreProses(ResponseView view){
        this.view = view;
    }

    public void createNewStore(String _storename, String description, String address, String username, DBApps dbApps, String dateNow, SetDefaultStore setDefult) {
        String id_store = "STORE-" + new GetTimeMiliSecond().timeMiliSecond(dateNow);
        storeModel = new StoreModel();
        storeModel.setId_store(id_store);
        storeModel.setUsername(username);
        storeModel.setCreate_at(dateNow);

        boolean hasInsert = insertStoreData(storeModel, dbApps);
        if(hasInsert == true){
            storeDetailModel = new StoreDetailModel();
            storeDetailModel.setId_store(id_store);
            storeDetailModel.setAddress(address);
            storeDetailModel.setName(_storename);
            storeDetailModel.setDescripition(description);
            storeDetailModel.setLatitude("0");
            storeDetailModel.setLongitude("0");
            storeDetailModel.setImage(null);
            boolean successInsert = insertStoreDetailData(storeDetailModel, dbApps);
            if(successInsert == true){
                setDefult.setDefault(id_store, _storename);
                model = new ResponseModel();
                model.setData(null);
                model.setPesan("success");
                model.setCode(200);
                view.success(model);
            } else {
                model = new ResponseModel();
                model.setData(null);
                model.setPesan("cannot create store");
                model.setCode(400);
                view.failure(model);
            }

        } else {
            model = new ResponseModel();
            model.setData(null);
            model.setPesan("cannot create store");
            model.setCode(400);
            view.failure(model);
        }
    }

    private boolean insertStoreDetailData(StoreDetailModel storeDetailModel, DBApps dbApps) {
        SQLiteDatabase sql = dbApps.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_store", storeDetailModel.getId_store());
        values.put("name", storeDetailModel.getName());
        values.put("address", storeDetailModel.getAddress());
        values.put("latitude", storeDetailModel.getLatitude());
        values.put("longitude", storeDetailModel.getLongitude());
        values.put("description", storeDetailModel.getDescripition());
        values.put("image", storeDetailModel.getImage());
        sql.insert(TableClass.store_detail, null, values);
        return true;
    }

    private boolean insertStoreData(StoreModel storeModel, DBApps dbApps) {
        SQLiteDatabase sql = dbApps.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_store", storeModel.getId_store());
        values.put("username", storeModel.getUsername());
        values.put("create_at", storeModel.getCreate_at());
        sql.insert(TableClass.store, null, values);
        return true;
    }

    public void displayStore(ArrayList<StoreDetailModel> data, DBApps dbApps, String username) {
        Cursor cursor = getDataStore(dbApps, username);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                storeDetailModel = new StoreDetailModel();
                storeDetailModel.setId_store(cursor.getString(cursor.getColumnIndex("id_store")));
                storeDetailModel.setName(cursor.getString(cursor.getColumnIndex("name")));
                storeDetailModel.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                storeDetailModel.setDescripition(cursor.getString(cursor.getColumnIndex("description")));
                storeDetailModel.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
                storeDetailModel.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
                storeDetailModel.setImage(cursor.getBlob(cursor.getColumnIndex("image")));
                data.add(storeDetailModel);
                cursor.moveToNext();
            }
            cursor.close();
            model = new ResponseModel();
            model.setData(data);
            model.setPesan("success");
            model.setCode(200);
            view.success(model);
        } else {
            model = new ResponseModel();
            model.setData(null);
            model.setPesan("data not found.");
            model.setCode(422);
            view.failure(model);
        }
    }

    private Cursor getDataStore(DBApps dbApps, String username) {
        SQLiteDatabase db = dbApps.getReadableDatabase();
        String sqlQuery = "" +
                "select * from "+TableClass.store+" " +
                "join "+TableClass.store_detail+" on " +
                ""+TableClass.store+".id_store = "+TableClass.store_detail+".id_store " +
                "where "+TableClass.store+".username = ?";
        Cursor data = db.rawQuery(sqlQuery, new String[]{username});
        return data;
    }
}
