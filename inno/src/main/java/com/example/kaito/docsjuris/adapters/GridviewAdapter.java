package com.example.kaito.docsjuris.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.kaito.docsjuris.R;

/**
 * Created by Rp on 3/30/2016.
 */
public class GridviewAdapter extends BaseAdapter {

    Context context;
    int[] IMAGES;

    public GridviewAdapter(Context context, int[] IMAGES) {
        this.IMAGES = IMAGES;
        this.context = context;
    }


    @Override
    public int getCount() {
        return IMAGES.length;
    }

    @Override
    public Object getItem(int position) {
        return IMAGES[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            viewHolder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.grid_item, null);


            viewHolder.image = convertView.findViewById(R.id.gridimage);
            //viewHolder.image.setOnClickListener(e->context.startActivity(new Intent(context,ListinfoActivity.class)));


            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }


        Integer beans = (Integer) getItem(position);

        viewHolder.image.setImageResource(beans);
        return convertView;
    }

    private class ViewHolder {
        ImageView image;

    }

}