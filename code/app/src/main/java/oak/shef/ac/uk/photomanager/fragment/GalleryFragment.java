/*created by Chennan Luo, Yuchen Hung, Weishi Li in 2018-2019*/

package oak.shef.ac.uk.photomanager.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import oak.shef.ac.uk.photomanager.R;
import oak.shef.ac.uk.photomanager.model.MyAdapter;
import oak.shef.ac.uk.photomanager.model.PhotoData;
import oak.shef.ac.uk.photomanager.viewModel.GalleryViewModel;

public class GalleryFragment extends Fragment {

    private List<PhotoData> photoToSave= new ArrayList<>();
    MyAdapter  mAdapter;
    RecyclerView mRecyclerView;
    public GalleryViewModel myViewModel;
    String lock = "no";
    double leftLa;
    double leftLn;
    double rightLa;
    double rightLn;
    String albumn;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /*GalleryFragment is called by album and map fragments, each time will receive massages to judge different situations
    *and show some perticular data via Adapter and ViewModel
    * There are four situations, 1.original gallery 2.one album containers 3. Photos we can see in screen from Map
    * 4.like-marked photos
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            lock = getArguments().getString("locker");
            albumn = getArguments().getString("albumn");
            leftLa = getArguments().getDouble("l1");
            rightLa = getArguments().getDouble("l2");
            leftLn = getArguments().getDouble("r1");
            rightLn = getArguments().getDouble("r2");
        }
        // Inflate the layout for this fragment
        View galleryview = inflater.inflate(R.layout.fragment_gallery, container, false);

        mRecyclerView = (RecyclerView) galleryview.findViewById(R.id.grid_recycler_view);
        int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(galleryview.getContext(), numberOfColumns));
        mAdapter= new MyAdapter(photoToSave);
        mRecyclerView.setAdapter(mAdapter);
        myViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        if(lock == "no") {
            myViewModel.getNumberToDisplay().observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {
                        mAdapter.addItems(newValue);
                    }

                }

            });
        }
        else if (lock == "QueryFromAlbumn"){
            myViewModel.searchAlbumn(albumn).observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {
                        mAdapter.addItems(newValue);
                    }
                }

            });

        }
        else if (lock == "QueryFromMap"){
            myViewModel.searchMap(leftLa,rightLa,leftLn,rightLn).observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {
                        mAdapter.addItems(newValue);
                    }
                }

            });

        }
        else if(lock == "Favorite"){
            myViewModel.searchLike("yes").observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {
                        mAdapter.addItems(newValue);
                    }
                }

            });
        }

        return galleryview;
    }
    //methods to send data to a fragment via newInstance
    public static GalleryFragment newInstance(String t1,double l1,double l2,double r1,double r2) {
        GalleryFragment f = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString("locker", t1);
        args.putDouble("l1", l1);
        args.putDouble("l2", l2);
        args.putDouble("r1", r1);
        args.putDouble("r2", r2);


        f.setArguments(args);
        return f;
    }

    public static GalleryFragment newAlbumnInstance(String t1,String t2) {
        GalleryFragment f = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString("locker", t1);
        args.putString("albumn", t2);

        f.setArguments(args);
        return f;
    }
    public static GalleryFragment newAlbumnInstance(String t1) {
        GalleryFragment f = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString("locker", t1);
        f.setArguments(args);
        return f;
    }

}
