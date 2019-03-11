/*created by Chennan Luo in 2018-2019*/
/*There are two situations, album multiselect and photo multiselect
* Through set multiflag in adapters to choose a multichoice mode
* */
package oak.shef.ac.uk.photomanager.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import oak.shef.ac.uk.photomanager.R;
import oak.shef.ac.uk.photomanager.model.AlbumnAdapter;
import oak.shef.ac.uk.photomanager.model.AlbumnData;
import oak.shef.ac.uk.photomanager.model.MyAdapter;
import oak.shef.ac.uk.photomanager.model.PhotoData;
import oak.shef.ac.uk.photomanager.viewModel.GalleryViewModel;

public class MultiChoiceFragment extends Fragment {
    private List<PhotoData> multichoicephoto= new ArrayList<>();
    private List<AlbumnData> multichoicealbum= new ArrayList<>();
    private MyAdapter photoAdapter;
    private AlbumnAdapter albumAdapter;
    private RecyclerView mRecyclerView;
    private GalleryViewModel myViewModel;
    private boolean photo_album_multiflag;// true is multiphotos, false is multialbums
    public MultiChoiceFragment() {
        // Required empty public constructor
    }

    public static MultiChoiceFragment newInstance(boolean select_flag) {
        MultiChoiceFragment fragment = new MultiChoiceFragment();
        Bundle args = new Bundle();
        args.putBoolean("select_flag", select_flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View multichoiceview;
        if (getArguments() != null) {
            photo_album_multiflag = getArguments().getBoolean("select_flag");
        }
        if(photo_album_multiflag){
            multichoiceview = inflater.inflate(R.layout.fragment_gallery, container, false);
            mRecyclerView = (RecyclerView) multichoiceview.findViewById(R.id.grid_recycler_view);
            int numberOfColumns = 3;
            mRecyclerView.setLayoutManager(new GridLayoutManager(multichoiceview.getContext(), numberOfColumns));
            photoAdapter= new MyAdapter(multichoicephoto);
            photoAdapter.setMultiFlag(true);
            mRecyclerView.setAdapter(photoAdapter);
            myViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
            myViewModel.getNumberToDisplay().observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {
                        photoAdapter.addItems(newValue);
                    }
                }
            });
        }else{
            multichoiceview = inflater.inflate(R.layout.fragment_albumn, container, false);
            mRecyclerView = (RecyclerView) multichoiceview.findViewById(R.id.grid_recycler_view2);
            int numberOfColumns = 2;
            mRecyclerView.setLayoutManager(new GridLayoutManager(multichoiceview.getContext(), numberOfColumns));
            albumAdapter= new AlbumnAdapter(multichoicealbum);
            albumAdapter.setMultiFlag(true);
            mRecyclerView.setAdapter(albumAdapter);
            myViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
            myViewModel.getAlbumnToDisplay().observe(this, new Observer<List<AlbumnData>>() {
                @Override
                public void onChanged(@Nullable final List<AlbumnData> newValue) {
                    if (newValue != null) {
                        albumAdapter.addItems(newValue);
                    }
                }
            });
        }

        return multichoiceview;
    }


    public List<PhotoData> getMultiPhoto(){return photoAdapter.getMultiPhoto();}
    public void initMultiPhoto(){photoAdapter.initMultiPhoto();}
    public List<AlbumnData> getMultiAlbum(){return albumAdapter.getMultiAlbum();}
    public void initMultiAlbum(){albumAdapter.initMultiAlbum();}
    public List<String> getDeletetitle(){return albumAdapter.getDeletetitle();}
    public void initDeletetitle(){albumAdapter.initDeletetitle();}
}
