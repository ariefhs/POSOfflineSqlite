package com.lupawktu.possqlite;

import android.content.DialogInterface;
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
import com.lupawktu.possqlite.db.DBApps;
import com.lupawktu.possqlite.reponse.ResponseModel;
import com.lupawktu.possqlite.user.UserProses;
import com.lupawktu.possqlite.reponse.ResponseView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mind on 5/30/2017.
 */

public class RegisterActivity extends AppCompatActivity implements ResponseView {
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;

    UserProses proses = new UserProses(this);
    DBApps dbApps = new DBApps(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name = name.getText().toString();
                String _username = username.getText().toString();
                String _email = email.getText().toString();
                String _password = password.getText().toString();
                if(!TextUtils.isEmpty(_name) && !TextUtils.isEmpty(_username)
                        && !TextUtils.isEmpty(_email) && !TextUtils.isEmpty(_password)) {
                    proses.SaveDataUserToDbLokal(
                            _username,
                            _name,
                            _email,
                            _password,
                            GetDateNow.getDateNow(),
                            dbApps
                    );
                } else {
                    Toast.makeText(RegisterActivity.this,"Please check your field.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void success(ResponseModel model) {
        new AlertDialog.Builder(this)
                .setMessage(model.getPesan())
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).create().show();
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
