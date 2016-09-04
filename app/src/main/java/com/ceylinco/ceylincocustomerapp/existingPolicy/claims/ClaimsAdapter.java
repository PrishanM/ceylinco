package com.ceylinco.ceylincocustomerapp.existingPolicy.claims;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.Claim;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Prishanm
 * Used for Listview in Claims
 */
class ClaimsAdapter extends BaseAdapter{

    private final Context con;
    private ArrayList<Claim> claims;

    public ClaimsAdapter(Context context, List<Claim> claimList){
        this.con = context;
        this.claims = new ArrayList<>(claimList);
    }

    @Override
    public int getCount() {
        return claims.size();
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
        convertView = null;

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) con
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.claim_list_row, parent,false);
        }

        final LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.rowLayout);

        if(position%2==0){
            layout.setBackgroundResource(R.color.list_view_even_color);
        }else{
            layout.setBackgroundResource(R.color.list_view_odd_color);
        }
        final TextView txtDate = (TextView)convertView.findViewById(R.id.txtDate);
        final TextView txtRefNo = (TextView)convertView.findViewById(R.id.txtRefNo);

        txtDate.setText(claims.get(position).getDate());
        txtRefNo.setText(claims.get(position).getRef());


        return convertView;
    }
}
