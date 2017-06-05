package com.lupawktu.possqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lupawktu.possqlite.PublicMethod.SetDefaultStore;
import com.lupawktu.possqlite.db.DBApps;
import com.lupawktu.possqlite.productcategory.PCProses;
import com.lupawktu.possqlite.reponse.ResponseModel;
import com.lupawktu.possqlite.reponse.ResponseView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mind on 6/3/2017.
 */
public class ProductCategory extends AppCompatActivity implements View.OnClickListener, ResponseView {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_add)
    FloatingActionButton add;

    PCProses proses;
    SetDefaultStore defaultStore;
    DBApps dbApps;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_n_category);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product & Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbApps = new DBApps(this);
        proses = new PCProses(this);
        defaultStore = new SetDefaultStore(this);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if(getIntent().hasExtra("menu")){
            if(getIntent().getStringExtra("menu").equals("1")){
                viewPager.setCurrentItem(1);
            } else if(getIntent().getStringExtra("menu").equals("0")){
                viewPager.setCurrentItem(0);
            }
        }

        add.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(tabLayout.getSelectedTabPosition() == 0){
            addProduct();
        } else {
            addCategory();
        }
    }

    private void addProduct() {
        Intent a = new Intent(ProductCategory.this, AddProductActivity.class);
        startActivity(a);
    }

    private void addCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.form_add_category, null);
        builder.setView(view);
        final EditText name = (EditText) view.findViewById(R.id.name);
        builder.setTitle("Add Category");
        builder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                proses.saveCategory(name.getText().toString().trim(),
                        defaultStore.getDefaultStore().get(SetDefaultStore.KEY_ID), dbApps);
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void success(ResponseModel model) {
        Intent a = new Intent(ProductCategory.this, ProductCategory.class);
        if(tabLayout.getSelectedTabPosition() == 0) {
            a.putExtra("menu", "0");
        } else {
            a.putExtra("menu", "1");
        }
        startActivity(a);
        finish();
    }

    @Override
    public void failure(ResponseModel model) {
        Log.e("Response", model.getPesan());

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0 :
                    fragment = new FragmentProduct();
                    break;
                case 1 :
                    fragment = new FragmentCategory();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            switch (position){
                case 0 :
                    title = "Product";
                    break;
                case 1 :
                    title = "Category";
                    break;
            }
            return title;
        }
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
