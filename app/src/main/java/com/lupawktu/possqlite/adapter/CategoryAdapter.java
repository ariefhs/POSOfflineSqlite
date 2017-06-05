package com.lupawktu.possqlite.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lupawktu.possqlite.R;
import com.lupawktu.possqlite.productcategory.CategoryModel;

import java.util.ArrayList;

/**
 * Created by Mind on 6/3/2017.
 */
public class CategoryAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<CategoryModel> data;
    private static LayoutInflater inflater = null;
    public CategoryAdapter(Activity activity, ArrayList<CategoryModel> data) {
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
        View vView = inflater.inflate(R.layout.item_list_category, null);
        TextView item = (TextView) vView.findViewById(R.id.item);
        item.setText(data.get(i).getName());
        return vView;
    }
}
