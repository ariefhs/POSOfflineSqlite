package com.lupawktu.possqlite.productcategory;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.lupawktu.possqlite.PublicMethod.GetDateNow;
import com.lupawktu.possqlite.PublicMethod.GetTimeMiliSecond;
import com.lupawktu.possqlite.db.DBApps;
import com.lupawktu.possqlite.db.TableClass;
import com.lupawktu.possqlite.reponse.ResponseModel;
import com.lupawktu.possqlite.reponse.ResponseView;

import java.util.ArrayList;

/**
 * Created by Mind on 6/3/2017.
 */

public class PCProses {
    private ResponseView view;
    private CategoryModel categoryModel;
    private ProductModel productModel;
    private ProductDetailModel productDetailModel;
    private ResponseModel model;

    public PCProses(ResponseView view){
        this.view = view;
    }

    public void saveCategory(String name, String id_store, DBApps dbApps) {
        String id_category = "C-" + new GetTimeMiliSecond().timeMiliSecond(GetDateNow.getDateNow());
        categoryModel = new CategoryModel();
        categoryModel.setId_category(id_category);
        categoryModel.setId_store(id_store);
        categoryModel.setName(name);
        categoryModel.setCreate_at(GetDateNow.getDateNow());
        boolean hasSave = addCategory(categoryModel, dbApps);
        if(hasSave == true){
            model = new ResponseModel();
            model.setCode(200);
            model.setData(null);
            model.setPesan("success");
            view.success(model);
        }else{
            model = new ResponseModel();
            model.setCode(422);
            model.setData(null);
            model.setPesan("add category failed.");
            view.success(model);
        }
    }

    private boolean addCategory(CategoryModel categoryModel, DBApps dbApps) {
        SQLiteDatabase db = dbApps.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_category", categoryModel.getId_category());
        values.put("id_store", categoryModel.getId_store());
        values.put("name", categoryModel.getName());
        values.put("create_at", categoryModel.getCreate_at());
        db.insert(TableClass.category, null, values);
        return true;
    }

    public void showCategory(ArrayList<CategoryModel> data, String id_store, DBApps dbApps) {
        SQLiteDatabase db = dbApps.getReadableDatabase();
        String sqlQuery = "select * from "+TableClass.category+" " +
                "where id_store =?";
        Cursor detailData = db.rawQuery(sqlQuery, new String[]{id_store});
        Log.e("Count", String.valueOf(detailData.getCount()));
        if(detailData.getCount() > 0) {
            detailData.moveToFirst();
            while (!detailData.isAfterLast()) {
                categoryModel = new CategoryModel();
                categoryModel.setId_category(detailData.getString(detailData.getColumnIndex("id_category")));
                categoryModel.setId_store(detailData.getString(detailData.getColumnIndex("id_store")));
                categoryModel.setName(detailData.getString(detailData.getColumnIndex("name")));
                categoryModel.setCreate_at(detailData.getString(detailData.getColumnIndex("create_at")));
                data.add(categoryModel);
                detailData.moveToNext();
            }
            detailData.close();
            model = new ResponseModel();
            model.setCode(200);
            model.setData(data);
            model.setPesan("success");
            view.success(model);
        } else {
            model = new ResponseModel();
            model.setCode(422);
            model.setData(null);
            model.setPesan("No data category.");
            view.failure(model);
        }
    }

    public boolean editCategory(String id_category, String name, DBApps dbApps) {
        SQLiteDatabase db = dbApps.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        db.update(TableClass.category, values, "id_category=?", new String[]{id_category});
        return true;
    }

    public boolean deleteCategory(String id_category, DBApps dbApps) {
        SQLiteDatabase db = dbApps.getWritableDatabase();
        db.delete(TableClass.category, "id_category=?", new String[]{id_category});
        return true;
    }
}
