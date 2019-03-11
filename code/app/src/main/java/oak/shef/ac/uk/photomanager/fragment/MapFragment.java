/*created by Yuchen Hung in 2018-2019*/

package oak.shef.ac.uk.photomanager.fragment;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oak.shef.ac.uk.photomanager.DetailActivity;
import oak.shef.ac.uk.photomanager.R;
import oak.shef.ac.uk.photomanager.model.PhotoData;
import oak.shef.ac.uk.photomanager.viewModel.GalleryViewModel;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static GoogleMap mMap;
    private static final int ACCESS_FINE_LOCATION = 123;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private MapView mapView;
    private GalleryViewModel myViewModel;
    public static GoogleMap getMap() {
        return mMap;
    }
    List<PhotoData> photo = new ArrayList<>();
    static private Context context;
    private ImageButton mButtonLoacte;
    private ImageButton mButtonQuery;
    double nowLa;
    double nowLn;
    double leftLa;
    double leftLn;
    double rightLa;
    double rightLn;
    View map_View;
    private GalleryFragment queryResult = new GalleryFragment();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        myViewModel.getNumberToDisplay().observe(this, new Observer<List<PhotoData>>(){
            @Override
            public void onChanged(@Nullable final List<PhotoData> newValue) {
                if(newValue != null){
                    for(PhotoData p : newValue)
                        photo.add(p);
                }
                draw();
            }

        });



    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            nowLa = getArguments().getDouble("locLa");
            nowLn = getArguments().getDouble("locLn");
        }
        context = container.getContext();
         map_View = inflater.inflate(R.layout.fragment_map, container, false);



        return map_View;
    }
@Override
public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    mapView = (MapView) map_View.findViewById(R.id.mmmap);
    mapView.onCreate(savedInstanceState);
    mapView.onResume();
    mapView.getMapAsync(this);
    mButtonLoacte = (ImageButton) getView().findViewById(R.id.mapButtonLocation);
    mButtonLoacte.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            moveToCurrnet();
        }
    });
    mButtonQuery = (ImageButton) getView().findViewById(R.id.mapButtonShow);
    mButtonQuery.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setUserCurrent();
            queryResult = GalleryFragment.newInstance("QueryFromMap",leftLa,rightLa,leftLn,rightLn);

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,queryResult).commit();
        }
    });
}
    @Override
    public void onResume() {
        super.onResume();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

    }

    private Location mCurrentLocation;
    private String mLastUpdateTime;
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
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                            mLocationCallback, null /* Looper */);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);



        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("photo", (PhotoData)marker.getTag());
                context.startActivity(intent);

            }
        });




    }
    private void setUserCurrent(){
        leftLa = mMap.getProjection().getVisibleRegion().latLngBounds.southwest.latitude;
        rightLa = mMap.getProjection().getVisibleRegion().latLngBounds.northeast.latitude;
        leftLn = mMap.getProjection().getVisibleRegion().latLngBounds.southwest.longitude;
        rightLn = mMap.getProjection().getVisibleRegion().latLngBounds.northeast.longitude;
    }
    private void moveToCurrnet(){
        LatLng nowLocat = new LatLng(nowLa,nowLn);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nowLocat, 14.0f));
    }
    private void draw() {
        if (getMap() != null) {
            for (PhotoData p : photo) {
                LatLng location = new LatLng(p.getLatitude(), p.getLongitude());
                Marker marker;
                marker = mMap.addMarker(new MarkerOptions().position(location).title(p.getTitle()).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(p,200,200))));
                marker.setTag(p);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f));
            }
        }

    }
    public Bitmap resizeMapIcons(PhotoData p, int width, int height){
        Bitmap myBitmap = BitmapFactory.decodeFile(p.getFile());
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(myBitmap, width, height, false);
        return resizedBitmap;
    }
    public static MapFragment newInstance(double t1,double t2) {
        MapFragment f = new MapFragment();
        Bundle args = new Bundle();
        args.putDouble("locLa", t1);
        args.putDouble("locLn", t2);
        f.setArguments(args);
        return f;
    }




}


