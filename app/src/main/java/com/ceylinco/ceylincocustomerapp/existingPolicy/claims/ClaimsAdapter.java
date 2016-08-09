package com.ceylinco.ceylincocustomerapp.existingPolicy.claims;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.ceylinco.ceylincocustomerapp.R;

/**
 * @author Prishanm
 * Used for Listview in MORE SHOPS
 */
public class ClaimsAdapter extends BaseAdapter{

    Context con;

    public ClaimsAdapter(Context context){
        this.con = context;
    }

    @Override
    public int getCount() {
        return 15;
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
            convertView = mInflater.inflate(R.layout.claim_list_row, null);
        }

        final LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.rowLayout);

        if(position%2==0){
            layout.setBackgroundResource(R.color.list_view_even_color);
        }else{
            layout.setBackgroundResource(R.color.list_view_odd_color);
        }

        /*final ImageView img1 = (ImageView)convertView.findViewById(R.id.imgView1);
        final TextView txtShopName = (TextView)convertView.findViewById(R.id.txtShopName);
        final TextView txtShopSlogan = (TextView)convertView.findViewById(R.id.txtShopSlogan);*/



        return convertView;
    }
}
