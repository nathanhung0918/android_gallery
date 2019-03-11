/*
 * Copyright (c) 2017. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package oak.shef.ac.uk.photomanager.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import oak.shef.ac.uk.photomanager.R;
import oak.shef.ac.uk.photomanager.ShowImageActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.View_Holder> {
    //adapt between gallery fragment to show image activity
    static private Context context;
    private static List<PhotoData> items;//photo to show
    public MyAdapter(List<PhotoData> items) {
        this.items = items;
    }
    private boolean multi_flag = false;
    private List<PhotoData> multichoice = new ArrayList<>();
    //Inflate the layout, initialize the View Holder
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image,
                parent, false);
        View_Holder holder = new View_Holder(v);
        context= parent.getContext();
        return holder;
    }
    //bind layout
    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
            final PhotoData photo = items.get(position);
            Bitmap myBitmap = BitmapFactory.decodeFile(photo.getFile());
            holder.imageView.setImageBitmap(myBitmap);
            holder.title.setText(photo.getTitle());
            holder.dsecription.setText(photo.getDescription());
//            holder.longitude.setText(String.valueOf(photo.getLongitude()));
//            holder.latitude.setText(String.valueOf(photo.getLatitude()));
//            holder.date.setText(photo.getDate());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!multi_flag){
                        Intent intent = new Intent(context, ShowImageActivity.class);
                        intent.putExtra("photo", photo);
                        context.startActivity(intent);
                    }else{
                        if(holder.select_state.getVisibility() == View.GONE) {
                            multichoice.add(photo);
                            holder.select_state.setVisibility(View.VISIBLE);
                        }else if(holder.select_state.getVisibility() == View.VISIBLE){
                            multichoice.remove(photo);
                            holder.select_state.setVisibility(View.GONE);
                        }
                    }
                }
            });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    //view holder class
    public class View_Holder extends RecyclerView.ViewHolder  {
        ImageView imageView;
        TextView title;
        TextView dsecription;
//        TextView longitude;
//        TextView latitude;
//        TextView date;
        ImageView select_state;
        View_Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_item);
            title = (TextView) itemView.findViewById(R.id.myTitle);
            dsecription = (TextView) itemView.findViewById(R.id.myDescription);
//            longitude = (TextView) itemView.findViewById(R.id.myLongitude);
//            latitude = (TextView) itemView.findViewById(R.id.myLatitude);
//            date = (TextView) itemView.findViewById(R.id.myDate);
            select_state = (ImageView) itemView.findViewById(R.id.select_state);
        }


    }
    //getter
    public static List<PhotoData> getItems() {
        return items;
    }
    //setter
    public void addItems(List<PhotoData> items) {
        MyAdapter.items = items;
        notifyDataSetChanged();
    }
    public void setMultiFlag(boolean flag){multi_flag = flag;}
    public List<PhotoData> getMultiPhoto(){return multichoice;}
    public void initMultiPhoto(){multichoice = new ArrayList<>();}
}