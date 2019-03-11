/*created by Chennan Luo in 2018-2019*/
/*There are three situations when AlbumFragment shows albums
* 1.originally browse album 2. browse album via add_to albume button in ShowImageActivity, set action_state=2
* 3.browse album via multiselect add_to albume button, set action_state=1
* */
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
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import oak.shef.ac.uk.photomanager.CameraActivity;
import oak.shef.ac.uk.photomanager.R;
import oak.shef.ac.uk.photomanager.model.AlbumnAdapter;
import oak.shef.ac.uk.photomanager.model.AlbumnData;
import oak.shef.ac.uk.photomanager.model.PhotoData;
import oak.shef.ac.uk.photomanager.viewModel.GalleryViewModel;

public class AlbumnFragment extends Fragment {

    private List<AlbumnData> albumToSave= new ArrayList<>();
    private AlbumnAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private GalleryViewModel myViewModel;
    private String newName;
    private int action_state;
    private PhotoData singlephoto;
    private ArrayList<PhotoData> multiphotos;
    private ImageButton pictures;
    private ImageButton favorite;
    private CameraActivity activity;
    public AlbumnFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View galleryview;
        activity = (CameraActivity) getActivity();
        mAdapter= new AlbumnAdapter(albumToSave);
        if (getArguments() != null) {
            newName = getArguments().getString("name");
            action_state = getArguments().getInt("state");
            singlephoto = getArguments().getParcelable("single");
            multiphotos = getArguments().getParcelableArrayList("multi");
        }
        if(action_state == 1||action_state ==2)
        {
            galleryview = inflater.inflate(R.layout.fragment_albumn2, container, false);
            mRecyclerView = (RecyclerView) galleryview.findViewById(R.id.grid_recycler_view3);
            mAdapter.setAction_state(action_state);
            mAdapter.setSinglephoto(singlephoto);
            mAdapter.setMultiphotos(multiphotos);
        }
        else {
            galleryview = inflater.inflate(R.layout.fragment_albumn, container, false);
            mRecyclerView = (RecyclerView) galleryview.findViewById(R.id.grid_recycler_view2);
            pictures = galleryview.findViewById(R.id.pictures);
            favorite = galleryview.findViewById(R.id.favorite);
            pictures.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.MainTitle.setText("All");
                    activity.menuflag = 2;
                    activity.invalidateOptionsMenu();
                    activity.toolbar.setNavigationIcon(null);
                    Fragment fragment = new GalleryFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
                }
            });

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.MainTitle.setText("favorite");
                    activity.menuflag = 2;
                    activity.invalidateOptionsMenu();
                    activity.toolbar.setNavigationIcon(null);
                    Fragment fragment = GalleryFragment.newAlbumnInstance("Favorite");
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
                }
            });
        }
        int numberOfColumns = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(galleryview.getContext(), numberOfColumns));
        mRecyclerView.setAdapter(mAdapter);
        myViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        myViewModel.getAlbumnToDisplay().observe(this, new Observer<List<AlbumnData>>() {
            @Override
            public void onChanged(@Nullable final List<AlbumnData> newValue) {
                if (newValue != null) {
                    mAdapter.addItems(newValue);
                }

            }

        });

        if(newName != null)
        {
            AlbumnData albumn= new AlbumnData(newName);
            myViewModel.storeAlbumnData(albumn);
        }
        return galleryview;
    }
    //methods to send data to a fragment via newInstance
    public static AlbumnFragment newInstance(String t1) {
        AlbumnFragment f = new AlbumnFragment();
        Bundle args = new Bundle();
        args.putString("name", t1);
        f.setArguments(args);
        return f;
    }
    public static AlbumnFragment newInstance(int t, PhotoData p) {
        AlbumnFragment f = new AlbumnFragment();
        Bundle args = new Bundle();
        args.putInt("state", t);
        args.putParcelable("single", p);
        f.setArguments(args);
        return f;
    }
    public static AlbumnFragment newInstance(int t, ArrayList<PhotoData> p) {
        AlbumnFragment f = new AlbumnFragment();
        Bundle args = new Bundle();
        args.putInt("state", t);
        args.putParcelableArrayList("multi", p);
        f.setArguments(args);
        return f;
    }

}
