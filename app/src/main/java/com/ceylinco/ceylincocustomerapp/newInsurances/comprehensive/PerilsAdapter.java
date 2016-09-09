package com.ceylinco.ceylincocustomerapp.newInsurances.comprehensive;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ceylinco.ceylincocustomerapp.models.PerilType;

import java.util.List;

/**
 * Created by Prishan Maduka on 9/9/2016.
 */
public class PerilsAdapter extends BaseAdapter {

    private List<PerilType> perilTypes;
    private Context context;

    public PerilsAdapter(Context context, List<PerilType> perilTypes){
        this.perilTypes = perilTypes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return perilTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
