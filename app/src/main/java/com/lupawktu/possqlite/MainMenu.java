package com.lupawktu.possqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.lupawktu.possqlite.PublicMethod.SetDefaultStore;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mind on 6/2/2017.
 */
public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.product)
    RelativeLayout product;
    @BindView(R.id.order)
    RelativeLayout order;
    @BindView(R.id.setting)
    RelativeLayout setting;
    @BindView(R.id.report)
    RelativeLayout report;
    @BindView(R.id.member)
    RelativeLayout member;
    @BindView(R.id.other)
    RelativeLayout other;

    SetDefaultStore setDefaultStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        setDefaultStore = new SetDefaultStore(this);

        getSupportActionBar().setTitle(setDefaultStore.getDefaultStore().get(SetDefaultStore.KEY_NAME));

        product.setOnClickListener(this);
        order.setOnClickListener(this);
        setting.setOnClickListener(this);
        report.setOnClickListener(this);
        member.setOnClickListener(this);
        other.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.product :
                Intent a1 = new Intent(MainMenu.this, ProductCategory.class);
                startActivity(a1);
                break;
            case R.id.order :
                break;
            case R.id.setting :
                break;
            case R.id.report :
                break;
            case R.id.member :
                break;
            case R.id.other :
                Intent a6 = new Intent(MainMenu.this, DisplayStore.class);
                startActivity(a6);
                break;
        }
    }
}
