package com.lupawktu.possqlite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lupawktu.possqlite.PublicMethod.SessionLogin;
import com.lupawktu.possqlite.PublicMethod.SetDefaultStore;
import com.lupawktu.possqlite.adapter.CategoryAdapter;
import com.lupawktu.possqlite.db.DBApps;
import com.lupawktu.possqlite.productcategory.CategoryModel;
import com.lupawktu.possqlite.productcategory.PCProses;
import com.lupawktu.possqlite.reponse.ResponseModel;
import com.lupawktu.possqlite.reponse.ResponseView;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mind on 6/3/2017.
 */
public class FragmentCategory extends Fragment implements ResponseView {
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.nodata)
    TextView nodata;

    PCProses proses = new PCProses(this);
    SessionLogin sessionLogin;
    SetDefaultStore defaultStore;
    DBApps dbApps;

    BaseAdapter adapter;
    ArrayList<CategoryModel> data = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_n_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        dbApps = new DBApps(getActivity());
        sessionLogin = new SessionLogin(getActivity());
        defaultStore = new SetDefaultStore(getActivity());
        getData();
    }

    public void getData() {
        proses.showCategory(data, defaultStore.getDefaultStore().get(SetDefaultStore.KEY_ID),
                dbApps);
    }

    @Override
    public void success(final ResponseModel model) {
        nodata.setVisibility(View.GONE);
        adapter = new CategoryAdapter(getActivity(), model.getData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence[] menuItem = {"Edit", "Delete"};
                showDialogMenu(i, menuItem, model.getData());
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void showDialogMenu(final int pos, CharSequence[] menuItem, final ArrayList<CategoryModel> data) {
        new AlertDialog.Builder(getActivity())
                .setItems(menuItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                dialogEdit(pos,data);
                                break;
                            case 1 :
                                validationDialog(pos, data);
                                break;
                        }
                    }
                }).create().show();
    }

    private void validationDialog(final int pos, final ArrayList<CategoryModel> data) {
        new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure want to delete '"+data.get(pos).getName()+"' ?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(pos, data);
                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    private void delete(int pos, ArrayList<CategoryModel> data) {
        boolean delete = proses.deleteCategory(data.get(pos).getId_category(), dbApps);
        if(delete == true){
            data.clear();
            getData();
        } else {
            Toast.makeText(getActivity(),"Cannot delete.", Toast.LENGTH_LONG).show();
        }
    }

    private void dialogEdit(final int pos, final ArrayList<CategoryModel> data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.form_add_category, null);
        builder.setTitle("Edit Category");
        builder.setView(view);
        final EditText edit = (EditText) view.findViewById(R.id.name);
        edit.setText(data.get(pos).getName());
        builder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean update = proses.editCategory(data.get(pos).getId_category(), edit.getText().toString(),  dbApps);
                if(update == true){
                    data.clear();
                    getData();
                } else {
                    Toast.makeText(getActivity(), "cannot edit category", Toast.LENGTH_LONG).show();
                }
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
    public void failure(ResponseModel model) {
        listView.setVisibility(View.GONE);
        nodata.setText(model.getPesan());
    }
}
