/*
 * Copyright (c) 2017. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 * modified by Chennan Luo, Yuchen Hung, Weishi Li in 2018-2019
 */

package oak.shef.ac.uk.photomanager;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import oak.shef.ac.uk.photomanager.model.PhotoData;
import oak.shef.ac.uk.photomanager.viewModel.GalleryViewModel;

public class ShowImageActivity extends AppCompatActivity {
    private static Activity activity;
    private GalleryViewModel myViewModel;
    private PhotoData photo = null;
    private TextView photo_title;
    private MenuItem likebutton;
    private MyBroadcastReceiver mBroadcastReceiver;
    public static final String DetailAction ="com.example.detailactivitysend.BroadcastAction";
    public  static Activity getActivity() {
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar_photo = (Toolbar) findViewById(R.id.toolbar_photo);
        toolbar_photo.setTitle("");
        setSupportActionBar(toolbar_photo);
        photo_title = (TextView) findViewById(R.id.photo_title);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.showphoto_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setCheckable(false);
        likebutton = navigation.getMenu().getItem(1);
        activity = this;
        myViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        //register a BroadcastReceiver for receiving new photodata from DetailActivity
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DetailAction);
        registerReceiver(mBroadcastReceiver, intentFilter);
        //receive data from MyAdapter
        Bundle b = getIntent().getExtras();
        photo=getIntent().getParcelableExtra("photo");
        photo_title.setText(photo.getTitle());
        if(photo.getLike().equals("yes")) likebutton.setChecked(true);
//        position=-1;
//        if(b != null) {
//            position = b.getInt("position");
//            if (position!=-1){
                ImageView imageView = (ImageView) findViewById(R.id.image);
//                PhotoData element= MyAdapter.getItems().get(position);
                 if (photo.getFile()!=null) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(photo.getFile());
                    imageView.setImageBitmap(myBitmap);
                }
//            }
//        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.addlike:
                    if(likebutton.isChecked()) {
                        likebutton.setCheckable(false);
                        photo.setLike("no");
                        myViewModel.updateData(photo);
                    }
                    else{
                        likebutton.setChecked(true);
                        photo.setLike("yes");
                        myViewModel.updateData(photo);
                    }
                    return true;
                case R.id.delete:
                    if(photo!=null) myViewModel.deleteData(photo);
                    finish();
                    return true;
                case R.id.single_add_to:
                    Intent intent = new Intent(ShowImageActivity.this, CameraActivity.class);
                    intent.putExtra("showimagejump",2);
                    intent.putExtra("jumpdata", photo);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.detail) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("photo", photo);
            startActivity(intent);            return true;
        } //else if (id == …) (…)
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
    //BroadcastReceiver for receiving new photodata from DetailActivity
    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if(DetailAction.equals(action))
            {
                photo=intent.getParcelableExtra("photoback");
                photo_title.setText(photo.getTitle());
            }
        }
    }

}
