package com.lupawktu.possqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.lupawktu.possqlite.PublicMethod.SessionLogin;
import com.lupawktu.possqlite.adapter.DisplayStoreAdapter;
import com.lupawktu.possqlite.db.DBApps;
import com.lupawktu.possqlite.reponse.ResponseModel;
import com.lupawktu.possqlite.reponse.ResponseView;
import com.lupawktu.possqlite.store.StoreDetailModel;
import com.lupawktu.possqlite.store.StoreProses;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mind on 5/31/2017.
 */
public class DisplayStore extends AppCompatActivity implements ResponseView, AdapterView.OnItemClickListener {
    @BindView(R.id.grid)
    GridView grid;

    StoreProses proses = new StoreProses(this);
    DBApps dbApps = new DBApps(this);
    SessionLogin sessionLogin;
    ArrayList<StoreDetailModel> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_store);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        sessionLogin = new SessionLogin(this);
        sessionLogin.checkLogin();
        getDataDisplayStore();
    }

    private void getDataDisplayStore() {
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            //NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void success(ResponseModel model) {
        BaseAdapter adapter = new DisplayStoreAdapter(this, model.getData());
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(this);
    }

    @Override
    public void failure(ResponseModel model) {
        Toast.makeText(this, model.getPesan(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent a = new Intent(this, MainMenu.class);
        startActivity(a);
    }

    private void getData() {
        proses.displayStore(data, dbApps, sessionLogin.getUserDetails().get(SessionLogin.KEY_USERNAME));
    }
}
