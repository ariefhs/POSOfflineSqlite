package com.lupawktu.possqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lupawktu.possqlite.PublicMethod.GetDateNow;
import com.lupawktu.possqlite.PublicMethod.SessionLogin;
import com.lupawktu.possqlite.PublicMethod.SetDefaultStore;
import com.lupawktu.possqlite.db.DBApps;
import com.lupawktu.possqlite.reponse.ResponseModel;
import com.lupawktu.possqlite.reponse.ResponseView;
import com.lupawktu.possqlite.store.StoreProses;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mind on 5/31/2017.
 */
public class CreateNewStore extends AppCompatActivity implements View.OnClickListener, ResponseView {
    @BindView(R.id.storename)
    EditText storename;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.create)
    Button create;

    StoreProses proses;
    SessionLogin sessionLogin;
    SetDefaultStore setDefult;
    DBApps dbApps;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        proses = new StoreProses(this);
        sessionLogin = new SessionLogin(this);
        dbApps = new DBApps(this);
        sessionLogin.checkLogin();
        setDefult = new SetDefaultStore(this);
        create.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String _storename = storename.getText().toString();
        String _description = description.getText().toString();
        String _address = address.getText().toString();
        if(!TextUtils.isEmpty(_storename)
                && !TextUtils.isEmpty(_description)
                && !TextUtils.isEmpty(_address)){

            proses.createNewStore(_storename, _description, _address,
                    sessionLogin.getUserDetails().get(SessionLogin.KEY_USERNAME), dbApps,
                    GetDateNow.getDateNow(), setDefult);

        } else {
            Toast.makeText(CreateNewStore.this, "please complete store data.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void success(ResponseModel model) {
        Intent a = new Intent(CreateNewStore.this, MainMenu.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
        finish();
    }

    @Override
    public void failure(ResponseModel model) {
        new AlertDialog.Builder(this)
                .setMessage(model.getPesan())
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
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
}
