package com.lupawktu.possqlite.store;

/**
 * Created by Mind on 6/1/2017.
 */

public class StoreModel {
    private String id_store;
    private String username;
    private String create_at;

    public String getId_store() {
        return id_store;
    }

    public void setId_store(String id_store) {
        this.id_store = id_store;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }
}
