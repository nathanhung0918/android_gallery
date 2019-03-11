/*
 * Copyright (c) 2017. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 *
 * some inspiration taken from https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
 *
 * modified by Chennan Luo, Yuchen Hung, Weishi Li in 2018-2019
 */

package oak.shef.ac.uk.photomanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import oak.shef.ac.uk.photomanager.fragment.AlbumnFragment;
import oak.shef.ac.uk.photomanager.fragment.GalleryFragment;
import oak.shef.ac.uk.photomanager.fragment.MapFragment;
import oak.shef.ac.uk.photomanager.fragment.MultiChoiceFragment;
import oak.shef.ac.uk.photomanager.fragment.SearchFragment;
import oak.shef.ac.uk.photomanager.model.AlbumnData;
import oak.shef.ac.uk.photomanager.model.PhotoData;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class CameraActivity extends AppCompatActivity implements OnMapReadyCallback ,BottomNavigationView.OnNavigationItemSelectedListener {

    /*fields for camera*/
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2987;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 7829;
    private static final String TAG = "CameraActivity";
    private static Activity activity;
    static private Context context;
    /*fields for PhotoData, location and date, and map*/
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int ACCESS_FINE_LOCATION = 123;
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
    private static GoogleMap mMap;
    /*fields for instance of fragments needed, data need to be sent*/
    private GalleryFragment galleryfg = new GalleryFragment();
    private MultiChoiceFragment multicgoicefragment = new MultiChoiceFragment();
    private List<PhotoData> multiphotodelete = new ArrayList<>();
    private List<AlbumnData> multialbumdelete = new ArrayList<>();
    private List<PhotoData> multiphotoadd = new ArrayList<>();
    public TextView MainTitle;
    public int menuflag = 0;
    public Toolbar toolbar;
    private Menu nav_Menu;
    String albumnName = "";
    AlertDialog dialog;

    public static GoogleMap getMap() {
        return mMap;
    }
    public  static Activity getActivity() {
        return activity;
    }

    /*start activity with gallery fragment, checkPermissions of camera and photo's gallery*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getParent();
        setContentView(R.layout.activity_camera);
        toolbar = (Toolbar) findViewById(R.id.toolbar_gallery);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        MainTitle = (TextView) findViewById(R.id.main_title);
        activity= this;
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        nav_Menu = navigation.getMenu();
        InitNavigationMenu();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,galleryfg).commit();

        // required by Android 6.0 +
        checkPermissions(getApplicationContext());
        initEasyImage();
        LayoutInflater inflater = LayoutInflater.from(CameraActivity.this);
        final View v = inflater.inflate(R.layout.dialog_layout, null);

        final TextView etName = (EditText) v.findViewById(R.id.dialogText);
        dialog = new AlertDialog.Builder(CameraActivity.this)
                .setTitle("New Albumn Name:")
                .setView(v)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        albumnName = etName.getText().toString();
                        if(albumnName != ""){
                        Fragment fragment = new AlbumnFragment();
                        fragment = AlbumnFragment.newInstance(albumnName);
                        LoadFragment(fragment);}
                    }
                })
                .setNegativeButton("Cancel", null).create();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }
    boolean select_edit_flag = false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                EasyImage.openCamera(getActivity(), 0);
                return true;
            case R.id.add_gallery:
                EasyImage.openGallery(getActivity(), 0);
                return true;
            case R.id.select:
                menuflag = 3;
                select_edit_flag = true;
                nav_Menu.findItem(R.id.navigation_gallery).setVisible(false);
                nav_Menu.findItem(R.id.navigation_album).setVisible(false);
                nav_Menu.findItem(R.id.navigation_map).setVisible(false);
                nav_Menu.findItem(R.id.navigation_search).setVisible(false);
                nav_Menu.findItem(R.id.delete_all).setVisible(true);
                toolbar.setNavigationIcon(null);
                MainTitle.setText("Select Items");
                invalidateOptionsMenu();
                multicgoicefragment = MultiChoiceFragment.newInstance(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,multicgoicefragment).commit();
                return true;
            case R.id.cancel:
                if(select_edit_flag){
                    InitNavigationMenu();
                    menuflag = 0;
                    toolbar.setNavigationIcon(R.drawable.ic_add_a_photo_white_24dp);
                    MainTitle.setText("Gallery");
                    invalidateOptionsMenu();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,galleryfg).commit();
                }else{
                    InitNavigationMenu();
                    menuflag = 1;
                    toolbar.setNavigationIcon(R.drawable.ic_add_a_photo_white_24dp);
                    MainTitle.setText("Album");
                    invalidateOptionsMenu();
                    Fragment fragment = new AlbumnFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
                }
                return true;
            case R.id.add_to:
                nav_Menu.findItem(R.id.delete_all).setVisible(false);
                multiphotoadd = multicgoicefragment.getMultiPhoto();//get multiphotos
                multicgoicefragment.initMultiPhoto();//delete coach of multiphotos data
                Fragment fragment = new AlbumnFragment();
                fragment = AlbumnFragment.newInstance(1,(ArrayList<PhotoData>)multiphotoadd);
                LoadFragment(fragment);
                return true;
            case R.id.new_album:
                dialog.show();
                return true;
            case R.id.edit_album:
                menuflag = 4;
                select_edit_flag = false;
                invalidateOptionsMenu();
                nav_Menu.findItem(R.id.navigation_gallery).setVisible(false);
                nav_Menu.findItem(R.id.navigation_album).setVisible(false);
                nav_Menu.findItem(R.id.navigation_map).setVisible(false);
                nav_Menu.findItem(R.id.navigation_search).setVisible(false);
                nav_Menu.findItem(R.id.delete_all).setVisible(true);
                toolbar.setNavigationIcon(null);
                MainTitle.setText("Select Albums");
                multicgoicefragment = MultiChoiceFragment.newInstance(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,multicgoicefragment).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (menuflag) {
            case 0:
                menu.findItem(R.id.add_gallery).setVisible(true);
                menu.findItem(R.id.select).setVisible(true);
                menu.findItem(R.id.new_album).setVisible(false);
                menu.findItem(R.id.edit_album).setVisible(false);
                menu.findItem(R.id.add_to).setVisible(false);
                menu.findItem(R.id.cancel).setVisible(false);
                break;
            case 1:
                menu.findItem(R.id.add_gallery).setVisible(false);
                menu.findItem(R.id.select).setVisible(false);
                menu.findItem(R.id.new_album).setVisible(true);
                menu.findItem(R.id.edit_album).setVisible(true);
                menu.findItem(R.id.add_to).setVisible(false);
                menu.findItem(R.id.cancel).setVisible(false);
                break;
            case 2:
                menu.findItem(R.id.add_gallery).setVisible(false);
                menu.findItem(R.id.select).setVisible(false);
                menu.findItem(R.id.new_album).setVisible(false);
                menu.findItem(R.id.edit_album).setVisible(false);
                menu.findItem(R.id.add_to).setVisible(false);
                menu.findItem(R.id.cancel).setVisible(false);
                break;
            case 3:
                menu.findItem(R.id.add_gallery).setVisible(false);
                menu.findItem(R.id.select).setVisible(false);
                menu.findItem(R.id.new_album).setVisible(false);
                menu.findItem(R.id.edit_album).setVisible(false);
                menu.findItem(R.id.add_to).setVisible(true);
                menu.findItem(R.id.cancel).setVisible(true);
                break;
            case 4:
                menu.findItem(R.id.add_gallery).setVisible(false);
                menu.findItem(R.id.select).setVisible(false);
                menu.findItem(R.id.new_album).setVisible(false);
                menu.findItem(R.id.edit_album).setVisible(false);
                menu.findItem(R.id.add_to).setVisible(false);
                menu.findItem(R.id.cancel).setVisible(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void initEasyImage() {
        EasyImage.configuration(this)
                .setImagesFolderName("EasyImage sample")
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                .setAllowMultiplePickInGallery(true);
    }

    private void checkPermissions(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    android.support.v7.app.AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                }

            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Writing external storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
                    android.support.v7.app.AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }

            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                for(PhotoData m : getImageElements(imageFiles))//store data
                    if(m.getFile()!=null){
                        galleryfg.myViewModel.storeData(m);
                        startLocationUpdates();
                    }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getActivity());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    private List<PhotoData> getImageElements(List<File> returnedPhotos) {
        List<PhotoData> PhotoList= new ArrayList<>();
        for (File file: returnedPhotos){
            PhotoData mPhotoData = new PhotoData("New Title","New Description");
            mPhotoData.setFile(file.getPath());
            String mDate = dateFormat.format(Calendar.getInstance().getTime());
            mPhotoData.setDate(mDate);
            mPhotoData.setLatitude(mCurrentLocation.getLatitude());
            mPhotoData.setLongitude(mCurrentLocation.getLongitude());
            PhotoList.add(mPhotoData);
        }
        return PhotoList;
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_FINE_LOCATION);
            }
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null /* Looper */);
    }
    @Override
    protected void onResume() {
        super.onResume();
        /*When jump from ShowImageActivity back to CameraActivity via add_album button, show AlbumFragment*/
        int showimagejump = getIntent().getIntExtra("showimagejump",0);
        PhotoData jumpdata = getIntent().getParcelableExtra("jumpdata");
        if(showimagejump == 2)
        {
            menuflag = 2;
            toolbar.setNavigationIcon(null);
            Fragment fragment = new AlbumnFragment();
            fragment = AlbumnFragment.newInstance(showimagejump,jumpdata);
            LoadFragment(fragment);
            MainTitle.setText("Album");
            invalidateOptionsMenu();
        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        startLocationUpdates();
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            mCurrentLocation = locationResult.getLastLocation();
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                            mLocationCallback, null /* Looper */);
                }
                return;
            }
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
    //load fragment
    private boolean LoadFragment (Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    //use to change fragment
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch(menuItem.getItemId()) {

            case R.id.navigation_gallery:
                menuflag = 0;
                toolbar.setNavigationIcon(R.drawable.ic_add_a_photo_white_24dp);
                fragment = new GalleryFragment();
                MainTitle.setText("Gallery");
                invalidateOptionsMenu();
                break;
            case R.id.navigation_album:
                menuflag = 1;
                toolbar.setNavigationIcon(R.drawable.ic_add_a_photo_white_24dp);
                fragment = new AlbumnFragment();
                MainTitle.setText("Album");
                invalidateOptionsMenu();
                break;
            case R.id.navigation_map:
                menuflag = 2;
                toolbar.setNavigationIcon(null);
                fragment = new MapFragment();
                fragment = MapFragment.newInstance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
                MainTitle.setText("Map");
                invalidateOptionsMenu();
                break;
            case R.id.navigation_search:
                menuflag = 2;
                toolbar.setNavigationIcon(null);
                fragment = new SearchFragment();
                MainTitle.setText("Search");
                invalidateOptionsMenu();
                break;
            case R.id.delete_all:
                if(select_edit_flag){
                    multiphotodelete = multicgoicefragment.getMultiPhoto();//get multiphotos
                    multicgoicefragment.initMultiPhoto();//delete coach of multiphotos data
                    for(PhotoData photo : multiphotodelete)
                        galleryfg.myViewModel.deleteData(photo);
                    menuflag = 0;
                    InitNavigationMenu();
                    toolbar.setNavigationIcon(R.drawable.ic_add_a_photo_white_24dp);
                    fragment = new GalleryFragment();
                    MainTitle.setText("Gallery");
                    invalidateOptionsMenu();
                }else{
                    multialbumdelete = multicgoicefragment.getMultiAlbum();//get multiphotos
                    List<String> albumtitleUpdate = multicgoicefragment.getDeletetitle();
                    multicgoicefragment.initMultiAlbum();//delete coach of multiphotos data
                    for(AlbumnData album : multialbumdelete)
                        galleryfg.myViewModel.deleteAlbumnData(album);
                    //update photodata
                    for(String updatephotoalbum : albumtitleUpdate){
                        galleryfg.myViewModel.searchAlbumn(updatephotoalbum).observe(this, new Observer<List<PhotoData>>() {
                            @Override
                            public void onChanged(@Nullable final List<PhotoData> newValue) {
                                if (newValue != null) {
                                    for(PhotoData p : newValue){
                                        p.setalbumn(null);
                                        galleryfg.myViewModel.updateData(p);
                                    }
                                }
                            }
                        });
                    }
                    menuflag = 1;
                    InitNavigationMenu();
                    toolbar.setNavigationIcon(R.drawable.ic_add_a_photo_white_24dp);
                    fragment = new AlbumnFragment();
                    MainTitle.setText("Album");
                    invalidateOptionsMenu();
                }
                break;
        }
        return LoadFragment(fragment);
    }
    //set items of bottom navigation bar of main fragments, gallery, album, search, map
    public void InitNavigationMenu(){
        nav_Menu.findItem(R.id.navigation_gallery).setVisible(true);
        nav_Menu.findItem(R.id.navigation_album).setVisible(true);
        nav_Menu.findItem(R.id.navigation_map).setVisible(true);
        nav_Menu.findItem(R.id.navigation_search).setVisible(true);
        nav_Menu.findItem(R.id.delete_all).setVisible(false);
    }
    
}