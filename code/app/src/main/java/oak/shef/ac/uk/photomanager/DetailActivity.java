/*created by Chennan Luo, Yuchen Hung, Weishi Li in 2018-2019*/
package oak.shef.ac.uk.photomanager;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import oak.shef.ac.uk.photomanager.model.PhotoData;
import oak.shef.ac.uk.photomanager.viewModel.GalleryViewModel;
import static oak.shef.ac.uk.photomanager.ShowImageActivity.DetailAction;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextInputEditText  textTitle;
    private TextInputEditText  textDes;
    private TextInputEditText  textDate;
    private PhotoData photo = null;
    private String TITLE;
    private String DES;
    private double LAT;
    private double LON;
    private TextView photo_title2;
    GalleryViewModel myViewModel;
    static GoogleMap mMap;
    public static GoogleMap getMap() {
        return mMap;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar_detail = (Toolbar) findViewById(R.id.toolbar_detail);
        toolbar_detail.setTitle("");
        setSupportActionBar(toolbar_detail);
        photo_title2 = (TextView) findViewById(R.id.photo_title2);
        myViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

        ImageButton edit = (ImageButton) findViewById(R.id.imageButtonEdit);
        textTitle = (TextInputEditText) findViewById(R.id.textintitle);
        textDes = (TextInputEditText) findViewById(R.id.textindes);
        textDate = (TextInputEditText) findViewById(R.id.textindate);
        //get photodata from ShowImageActivity
        photo=getIntent().getParcelableExtra("photo");
        photo_title2.setText(photo.getTitle());
        //set detail information via received photodata
        TITLE = photo.getTitle();
        textTitle.setText(TITLE);
        textTitle.setEnabled(false);

        DES = photo.getDescription();
        textDes.setText(DES);
        textDes.setEnabled(false);

        textDate.setText(photo.getDate());
        textDate.setEnabled(false);

        LAT = photo.getLatitude();
        LON = photo.getLongitude();

        ImageView imageView = (ImageView) findViewById(R.id.imagedetail);
        if (photo.getFile()!=null) {
            Bitmap myBitmap = BitmapFactory.decodeFile(photo.getFile());
            imageView.setImageBitmap(myBitmap);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textTitle.setEnabled(true);
                textDes.setEnabled(true);
            }
        });
        //put map in the detail layout
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView3);
        mapFragment.getMapAsync(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String testflag1 = String.valueOf(textTitle.getText());
        String testflag2 = String.valueOf(textDes.getText());
        if (id == R.id.save) {
            textTitle.setEnabled(false);
            textDes.setEnabled(false);
            if(!(testflag1.equals(TITLE)&&testflag2.equals(DES))) {
                TITLE = String.valueOf(textTitle.getText());
                DES = String.valueOf(textDes.getText());
                photo_title2.setText(TITLE);
                if (photo != null) {
                    photo.setTitle(TITLE);
                    photo.setDescription(DES);
                    myViewModel.updateData(photo);
                    //send new photodata back to ShowImageActivity
                    Intent intent = new Intent(DetailAction);
                    intent.putExtra("photoback", photo);
                    sendBroadcast(intent);
                }
            }
            return true;
        } //else if (id == …) (…)
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        draw();
    }
    private void draw() {
        if (getMap() != null) {
            LatLng location = new LatLng(LAT,LON);
            mMap.addMarker(new MarkerOptions().position(location).title(TITLE));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f));
            }
    }
}
