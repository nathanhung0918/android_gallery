package oak.shef.ac.uk.photomanager.model;

/*
 * Copyright (c) 2017. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import oak.shef.ac.uk.photomanager.CameraActivity;
import oak.shef.ac.uk.photomanager.fragment.GalleryFragment;
import oak.shef.ac.uk.photomanager.R;
import oak.shef.ac.uk.photomanager.viewModel.GalleryViewModel;

public class AlbumnAdapter extends RecyclerView.Adapter<AlbumnAdapter.View_Holder> {
    //adapt between gallery fragment to show image activity
    static private CameraActivity activity;
    static private Context context;
    private static List<AlbumnData> items;//photo to show
    private int action_state = 0;
    private PhotoData singlephoto;
    private List<PhotoData> multiphotos;
    private GalleryViewModel myViewModel;
    private List<String> albumntitle = new ArrayList<>();
    private boolean multi_flag = false;
    private List<AlbumnData> multichoice = new ArrayList<>();

    public AlbumnAdapter(List<AlbumnData> items) {
        this.items = items;
    }
    //Inflate the layout, initialize the View Holder
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_albumn,
                parent, false);
        View_Holder holder = new View_Holder(v);
        context= parent.getContext();
        activity = (CameraActivity) context;
        myViewModel = ViewModelProviders.of(activity).get(GalleryViewModel.class);
        return holder;
    }
    //bind layout
    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        final AlbumnData album = items.get(position);
//        Bitmap myBitmap = BitmapFactory.decodeFile(photo.getFile());
//        holder.imageView.setImageBitmap(myBitmap);
        holder.title.setText(album.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!multi_flag) {
                    Fragment fragment = null;
                    fragment = new GalleryFragment();
                    switch (action_state) {
                        case 0:
                            activity.MainTitle.setText(album.getTitle());
                            activity.menuflag = 2;
                            activity.invalidateOptionsMenu();
                            activity.toolbar.setNavigationIcon(null);
                            fragment = GalleryFragment.newAlbumnInstance("QueryFromAlbumn", album.getTitle());
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
                            break;
                        case 1:
                            for (PhotoData photo : multiphotos) {
                                photo.setalbumn(album.getTitle());
                                myViewModel.updateData(photo);
                            }
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
                            activity.menuflag = 0;
                            activity.MainTitle.setText("gallery");
                            activity.InitNavigationMenu();
                            activity.toolbar.setNavigationIcon(R.drawable.ic_add_a_photo_white_24dp);
                            activity.invalidateOptionsMenu();
                            break;
                        case 2:
                            singlephoto.setalbumn(album.getTitle());
                            myViewModel.updateData(singlephoto);
                            activity.onBackPressed();
                            break;
                    }
                }else{
                    if(holder.select_state.getVisibility() == View.GONE) {
                        multichoice.add(album);
                        albumntitle.add(album.getTitle());
                        holder.select_state.setVisibility(View.VISIBLE);
                    }else if(holder.select_state.getVisibility() == View.VISIBLE){
                        multichoice.remove(album);
                        albumntitle.remove(album.getTitle());
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
        ImageView select_state;

        View_Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.album_image);
            title = (TextView) itemView.findViewById(R.id.album_title);
            select_state = (ImageView) itemView.findViewById(R.id.album_select_state);

        }


    }
    //getter
    public static List<AlbumnData> getItems() {
        return items;
    }
    //setter
    public void addItems(List<AlbumnData> items) {
        AlbumnAdapter.items = items;
        notifyDataSetChanged();
    }

    public void setAction_state(int f) {action_state = f;}
    public void setSinglephoto(PhotoData p){this.singlephoto = p;}
    public void setMultiphotos(ArrayList<PhotoData> p) {this.multiphotos = (List<PhotoData>) p; }

    public void setMultiFlag(boolean flag){multi_flag = flag;}
    public List<AlbumnData> getMultiAlbum(){return multichoice;}
    public void initMultiAlbum(){multichoice = new ArrayList<>();}
    public List<String> getDeletetitle(){return albumntitle;}
    public void initDeletetitle(){albumntitle = new ArrayList<>();}
}
