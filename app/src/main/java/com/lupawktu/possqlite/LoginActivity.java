package com.lupawktu.possqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lupawktu.possqlite.PublicMethod.SessionLogin;
import com.lupawktu.possqlite.user.UserProses;
import com.lupawktu.possqlite.db.DBApps;
import com.lupawktu.possqlite.reponse.ResponseModel;
import com.lupawktu.possqlite.reponse.ResponseView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ResponseView {
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;

    private DBApps dbApps = new DBApps(this);
    private UserProses proses = new UserProses(this);

    SessionLogin sessionLogin;
    boolean is_logged_in = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sessionLogin = new SessionLogin(this);
        if(is_logged_in == sessionLogin.isLoggedIn()){
            successLogin(sessionLogin.getUserDetails().get(SessionLogin.KEY_USERNAME), dbApps);
        }

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                String _username = username.getText().toString();
                String _password = password.getText().toString();

                if (!TextUtils.isEmpty(_username) && !TextUtils.isEmpty(_password)) {
                    proses.checkLoginUser(_username, _password, dbApps);
                } else {
                    Toast.makeText(this,"Please check your username / password.", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.register:
                Intent a = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(a);
                break;
        }
    }

    @Override
    public void success(ResponseModel model) {
//        success login
        sessionLogin.createLoginSession(username.getText().toString(), null);
        successLogin(username.getText().toString(), dbApps);
    }

    private void successLogin(String username, DBApps dbApps) {
        int haveStore = proses.checkStore(username, dbApps);
        if ( haveStore > 0 ){
            Intent a = new Intent(LoginActivity.this, MainMenu.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            finish();
        } else {
            Intent a = new Intent(LoginActivity.this, CreateNewStore.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            finish();
        }
    }

    @Override
    public void failure(ResponseModel model) {
        Toast.makeText(this, model.getPesan() + " Login Failed. Please check username / password..", Toast.LENGTH_SHORT).show();
    }
}
