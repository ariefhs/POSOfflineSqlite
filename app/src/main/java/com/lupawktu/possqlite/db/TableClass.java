package com.lupawktu.possqlite.db;

/**
 * Created by Mind on 5/30/2017.
 */
public class TableClass {
    public static String user = "user";
    public static String order = "order_product";
    public static String store = "store";
    public static String category = "category";
    public static String order_detail = "order_detail";
    public static String store_detail = "store_detail";
    public static String category_detail = "category_detail";
    public static String product = "product";
    public static String product_detail = "product_detail";

    private String tbUserCreate = "create table if not exists "+user+"(" +
            "username text PRIMARY KEY," +
            "name text," +
            "email text," +
            "level text," +
            "create_at datetime default current_timestamp," +
            "password text," +
            "secret_key text," +
            "UNIQUE (email) ON CONFLICT REPLACE)";

    private String tbStoreCreate = "create table if not exists "+store+"(" +
            "id_store text PRIMARY KEY," +
            "username text," +
            "create_at datetime default current_timestamp)";

    private String tbDetailStoreCreate = "create table if not exists "+store_detail+"(" +
            "id_detail_store integer PRIMARY KEY AUTOINCREMENT," +
            "id_store text," +
            "name text," +
            "address text," +
            "latitude text," +
            "longitude text," +
            "description text," +
            "image blob)";

    private String tbCategoryProductCreate = "create table if not exists "+category+"(" +
            "id_category text primary_text," +
            "id_store text," +
            "name text," +
            "create_at datetime default current_timestamp)";

    private String tbProductCreate = "create table if not exists "+product+"(" +
            "id_product text PRIMARY KEY," +
            "id_category integer," +
            "id_store text," +
            "create_at datetime default current_timestamp)";

    private String tbDetailProduct = "create table if not exists "+product_detail+"(" +
            "id_detail_product integer primary key AUTOINCREMENT," +
            "id_product text," +
            "name text," +
            "price_buy real," +
            "price_sell real," +
            "qty integer," +
            "description text," +
            "image blob)";

    private String tbOrderCreate = "create table if not exists "+order+"(" +
            "id_order text PRIMARY KEY," + //generate from user+timespan
            "buyer_name text," +
            "status text," + //done, not done
            "create_at datetime default current_timestamp)";

    private String tbOrderDetailCreate = "create table if not exists "+order_detail+"(" +
            "id_order_detail integer PRIMARY KEY AUTOINCREMENT," +
            "id_order text," +
            "id_product text," +
            "name text," +
            "price_sell real," +
            "total_price real," +
            "qty integer," +
            "create_at datetime default current_timestamp)";

    private String tbUserDelete = "drop table if exists "+user;
    private String tbStoreDelete = "drop table if exists "+store;
    private String tbDetailStoreDelete = "drop table if exists "+store_detail;
    private String tbDetailProductDelete = "drop table if exists "+product_detail;
    private String tbProductDelete = "drop table if exists "+product;
    private String tbOrderDelete = "drop table if exists "+order;
    private String tbOrderDetailDelete = "drop table if exists "+order_detail;
    private String tbCategoryProductDelete = "drop table if exists "+category;

    public String getTbUserCreate() {
        return tbUserCreate;
    }

    public String getTbStoreCreate() {
        return tbStoreCreate;
    }

    public String getTbProductCreate() {
        return tbProductCreate;
    }

    public String getTbUserDelete() {
        return tbUserDelete;
    }

    public String getTbStoreDelete() {
        return tbStoreDelete;
    }

    public String getTbOrderCreate() {
        return tbOrderCreate;
    }

    public String getTbOrderDelete() {
        return tbOrderDelete;
    }

    public String getTbProductDelete() {
        return tbProductDelete;
    }

    public String getTbCategoryProductCreate() {
        return tbCategoryProductCreate;
    }

    public String getTbOrderDetailCreate() {
        return tbOrderDetailCreate;
    }

    public String getTbOrderDetailDelete() {
        return tbOrderDetailDelete;
    }

    public String getTbCategoryProductDelete() {
        return tbCategoryProductDelete;
    }

    public String getTbDetailStoreCreate() {
        return tbDetailStoreCreate;
    }

    public String getTbDetailStoreDelete() {
        return tbDetailStoreDelete;
    }

    public String getTbDetailProduct() {
        return tbDetailProduct;
    }

    public String getTbDetailProductDelete() {
        return tbDetailProductDelete;
    }

}
