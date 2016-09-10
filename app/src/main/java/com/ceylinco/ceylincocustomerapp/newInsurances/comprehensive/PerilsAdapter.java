package com.ceylinco.ceylincocustomerapp.newInsurances.comprehensive;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.PerilType;
import com.ceylinco.ceylincocustomerapp.util.AppController;

import java.util.HashMap;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = null;

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.peril_list_row, parent,false);
        }
        final CheckBox chkPeril = (CheckBox)convertView.findViewById(R.id.perilItem);
        chkPeril.setText(perilTypes.get(position).getPeril());

        chkPeril.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HashMap<Integer,Boolean> map = AppController.getIntegerHashMap();
                map.put(position,isChecked);
                AppController.setIntegerHashMap(map);
            }
        });

        return convertView;
    }
}
