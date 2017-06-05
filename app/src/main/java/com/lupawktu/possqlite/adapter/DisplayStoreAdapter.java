package com.lupawktu.possqlite.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lupawktu.possqlite.DisplayStore;
import com.lupawktu.possqlite.R;
import com.lupawktu.possqlite.store.StoreDetailModel;

import java.util.ArrayList;

/**
 * Created by Mind on 6/2/2017.
 */
public class DisplayStoreAdapter extends BaseAdapter {
    private DisplayStore activity;
    private ArrayList<StoreDetailModel> data;
    private static LayoutInflater inflater = null;
    public DisplayStoreAdapter(DisplayStore activity, ArrayList<StoreDetailModel> data) {
        this.activity = activity;
        this.data = data;
        inflater = (LayoutInflater)  activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vView = inflater.inflate(R.layout.item_grid_display_store, null);
        TextView name = (TextView) vView.findViewById(R.id.name);
        ImageView image = (ImageView) vView.findViewById(R.id.image);

        name.setText(data.get(i).getName());
        return vView;
    }
}
