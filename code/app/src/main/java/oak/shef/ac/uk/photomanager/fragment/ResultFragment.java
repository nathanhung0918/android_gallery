/*created by Chennan Luo in 2018-2019*/
/*there are 7 situations when search via title description and date
* when search via two and three label, get results of both labels as search with single label*/
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


public class ResultFragment extends Fragment {
    private List<PhotoData> resultphoto= new ArrayList<>();
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private GalleryViewModel myViewModel;

    private String date_keyword;
    private String title_keyword;
    private String description_keyword;

    private int flag_date;
    private int flag_title;
    private int flag_description;
    private int flag;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View resultview = inflater.inflate(R.layout.fragment_gallery, container, false);
        if (getArguments() != null) {
            date_keyword = getArguments().getString("kdate");
            title_keyword = getArguments().getString("ktitle");
            description_keyword = getArguments().getString("kdescription");
        }
        myViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        mRecyclerView = (RecyclerView) resultview.findViewById(R.id.grid_recycler_view);
        int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(resultview.getContext(), numberOfColumns));
        mAdapter= new MyAdapter(resultphoto);
        mRecyclerView.setAdapter(mAdapter);
        myViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        //there is 8 cases of search in total
        if(title_keyword.equals("")) flag_title =0;
        else flag_title = 1;
        if(date_keyword.equals("")) flag_date =0;
        else flag_date = 1;
        if(description_keyword.equals("")) flag_description =0;
        else flag_description = 1;
        setflag(flag_title,flag_date,flag_description);
        switch(flag){
            case 0:
            case 7:
                myViewModel.search(description_keyword,title_keyword,date_keyword).observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {mAdapter.addItems(newValue); }
                }});break;
            case 1:myViewModel.searchDescription(description_keyword).observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {mAdapter.addItems(newValue); }
                }});break;
            case 2:myViewModel.searchDate(date_keyword).observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {mAdapter.addItems(newValue); }
                }});break;
            case 3:myViewModel.searchDesDate(description_keyword,date_keyword).observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {mAdapter.addItems(newValue); }
                }});break;
            case 4:
                myViewModel.searchTitle(title_keyword).observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {mAdapter.addItems(newValue); }
                }});break;
            case 5:myViewModel.searchDesTitle(description_keyword,title_keyword).observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {mAdapter.addItems(newValue); }
                }});break;
            case 6:myViewModel.searchTitleDate(title_keyword,date_keyword).observe(this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoData> newValue) {
                    if (newValue != null) {mAdapter.addItems(newValue); }
                }});break;
        }
        return resultview;
    }

    public static ResultFragment newInstance(String t1,String t2,String t3) {
        ResultFragment f = new ResultFragment();
        Bundle args = new Bundle();
        args.putString("kdate", t1);
        args.putString("ktitle", t2);
        args.putString("kdescription", t3);
        f.setArguments(args);
        return f;
    }
    public void setflag(int a, int b, int c){ flag = 4*a+2*b+c;}
}
