package com.lupawktu.possqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lupawktu.possqlite.db.TableClass;

/**
 * Created by Mind on 5/29/2017.
 */

public class DBApps extends SQLiteOpenHelper{
    private TableClass tableClass = new TableClass();
    public DBApps(Context context) {
        super(context, "dbPos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tableClass.getTbUserCreate());
        sqLiteDatabase.execSQL(tableClass.getTbStoreCreate());
        sqLiteDatabase.execSQL(tableClass.getTbDetailStoreCreate());
        sqLiteDatabase.execSQL(tableClass.getTbOrderCreate());
        sqLiteDatabase.execSQL(tableClass.getTbOrderDetailCreate());
        sqLiteDatabase.execSQL(tableClass.getTbCategoryProductCreate());
        sqLiteDatabase.execSQL(tableClass.getTbProductCreate());
        sqLiteDatabase.execSQL(tableClass.getTbDetailProduct());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(tableClass.getTbUserDelete());
        sqLiteDatabase.execSQL(tableClass.getTbOrderDelete());
        sqLiteDatabase.execSQL(tableClass.getTbProductDelete());
        sqLiteDatabase.execSQL(tableClass.getTbOrderDetailDelete());
        sqLiteDatabase.execSQL(tableClass.getTbCategoryProductDelete());
        sqLiteDatabase.execSQL(tableClass.getTbStoreDelete());
        sqLiteDatabase.execSQL(tableClass.getTbDetailStoreDelete());
        sqLiteDatabase.execSQL(tableClass.getTbDetailProductDelete());
        onCreate(sqLiteDatabase);
    }
}
