package com.lupawktu.possqlite.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lupawktu.possqlite.PublicMethod.SHA512Password;
import com.lupawktu.possqlite.db.DBApps;
import com.lupawktu.possqlite.db.TableClass;
import com.lupawktu.possqlite.reponse.ResponseModel;
import com.lupawktu.possqlite.reponse.ResponseView;

/**
 * Created by Mind on 5/30/2017.
 */

public class UserProses {
    private ResponseView view;
    private ResponseModel model;
    private UserModel userModel;
    public UserProses(ResponseView view){
        this.view = view;
    }

    public void SaveDataUserToDbLokal(String username, String name, String email, String password, String dateNow, DBApps dbApps) {
        String secret_key = new SHA512Password().Hash(username+"&"+email, new SHA512Password().acak());
        String _password = new SHA512Password().Hash(password, new SHA512Password().acak());
        userModel = new UserModel();
        userModel.setUsername(username);
        userModel.setName(name);
        userModel.setEmail(email);
        userModel.setLevel("owner");
        userModel.setCreate_at(dateNow);
        userModel.setPassword(_password);
        userModel.setSecret_key(secret_key);
        boolean insertData = insertDataUser(userModel, dbApps);
        Log.e("status", String.valueOf(insertData));

        if(insertData == true){
            model = new ResponseModel();
            model.setData(null);
            model.setPesan("Registration Success.");
            model.setCode(200);
            view.success(model);
        } else {
            model = new ResponseModel();
            model.setData(null);
            model.setPesan("Registration fail. Please change username or email.");
            model.setCode(422);
            view.failure(model);
        }
    }

    private boolean insertDataUser(UserModel userModel, DBApps dbApps) {
        SQLiteDatabase sqLiteDatabase = dbApps.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", userModel.getUsername());
        values.put("name", userModel.getName());
        values.put("email", userModel.getEmail());
        values.put("level", userModel.getLevel());
        values.put("create_at", userModel.getCreate_at());
        values.put("password", userModel.getPassword());
        values.put("secret_key", userModel.getSecret_key());
        sqLiteDatabase.insert(TableClass.user, null, values);
        return true;
    }

    public void checkLoginUser(String username, String password, DBApps dbApps) {
        String _password = new SHA512Password().Hash(password, new SHA512Password().acak());
        int count = checkUser(username, _password, dbApps);

        if(count > 0){
            Log.e("count user", String.valueOf(count));
            model = new ResponseModel();
            model.setData(null);
            model.setPesan(String.valueOf(count));
            model.setCode(200);
            view.success(model);
        } else {
            Log.e("no user", "no user");
            model = new ResponseModel();
            model.setData(null);
            model.setPesan("no user "+count);
            model.setCode(422);
            view.failure(model);
        }
    }

    private int checkUser(String username, String password, DBApps dbApps) {
        SQLiteDatabase sqLiteDatabase = dbApps.getReadableDatabase();

        Cursor res = sqLiteDatabase.rawQuery("select username from "+ TableClass.user +" "+
                "where username = ? and password = ?",
                new String[]{username,password});

        return res.getCount();
    }

    public int checkStore(String username, DBApps dbApps) {
        SQLiteDatabase sqLiteDatabase = dbApps.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("" +
                "select id_store from "+ TableClass.store +" " +
                "join user " +
                "on "+TableClass.store+".username = "+TableClass.user+".username " +
                "where "+TableClass.user+".username = ?",
                new String[]{username});
        return cursor.getCount();
    }
}
