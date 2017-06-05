package com.lupawktu.possqlite.reponse;

/**
 * Created by Mind on 5/30/2017.
 */

public interface ResponseView {
    void success(ResponseModel model);
    void failure(ResponseModel model);
}
