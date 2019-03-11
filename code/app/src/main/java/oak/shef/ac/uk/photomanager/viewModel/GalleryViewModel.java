package oak.shef.ac.uk.photomanager.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;
import oak.shef.ac.uk.photomanager.model.AlbumnData;
import oak.shef.ac.uk.photomanager.model.Model;
import oak.shef.ac.uk.photomanager.model.PhotoData;

public class GalleryViewModel extends AndroidViewModel {
    private final Model mModel;

    LiveData<List<PhotoData>> numberToDisplay;
    LiveData<List<AlbumnData>> albumnToDisplay;

    public GalleryViewModel (Application application) {
        super(application);
        // creation and connection to the Repository
        mModel = new Model(application);
        // connection to the live data
        numberToDisplay = mModel.getNumberToDisplay();
        albumnToDisplay = mModel.getAlbumnToDisplay();

    }


    /**
     * getter for the live data
     * @return
     */
    public LiveData<List<PhotoData>> getNumberToDisplay() {
        return numberToDisplay;
    }
    public LiveData<List<AlbumnData>> getAlbumnToDisplay() {
        return albumnToDisplay;
    }

    /**
     * request by the UI to generate a new random number
     */
    public void storeData(PhotoData photo) {
        mModel.storeData(photo);
    }
    public void deleteData(PhotoData photo) {
        mModel.deleteData(photo);
    }
    public void updateData(PhotoData photo) {
        mModel.updateData(photo);
    }
    public LiveData<List<PhotoData>> searchTitle(String keyWord) {
        return mModel.searchTitle(keyWord);
    }
    public LiveData<List<PhotoData>> searchDescription( String keyWord) {
        return mModel.searchDescription(keyWord);
    }
    public LiveData<List<PhotoData>> searchDate(String keyWord) {
        return mModel.searchDate(keyWord);
    }
    public LiveData<List<PhotoData>> searchDesDate(String keyWord1, String keyWord2) {
        return mModel.searchDesDate(keyWord1, keyWord2);
    }
    public LiveData<List<PhotoData>> searchDesTitle(String keyWord1, String keyWord2) {
        return mModel.searchDesTitle(keyWord1, keyWord2);
    }
    public LiveData<List<PhotoData>> searchTitleDate(String keyWord1, String keyWord2) {
        return mModel.searchTitleDate(keyWord1, keyWord2);
    }
    public LiveData<List<PhotoData>> search(String keyWord1, String keyWord2, String keyWord3) {
        return mModel.search(keyWord1, keyWord2,keyWord3);
    }
    public LiveData<List<PhotoData>> searchMap(double l1, double l2, double r1, double r2) {
        return mModel.searchMap(l1,l2,r1,r2);
    }
    public LiveData<List<PhotoData>> searchAlbumn(String keyWord) {
        return mModel.searchAlbumn(keyWord);
    }
    public LiveData<List<PhotoData>> searchLike(String keyWord) {
        return mModel.searchLike(keyWord);
    }

    public void storeAlbumnData(AlbumnData albumn) {
        mModel.storeAlbumn(albumn);
    }
    public void deleteAlbumnData(AlbumnData albumn) {
        mModel.deleteAlbumn(albumn);
    }
    public void updateAlbumnData(AlbumnData albumn) {
        mModel.updateAlbumn(albumn);
    }
    public LiveData<List<AlbumnData>> searchOne(String name) {
        return mModel.searchOne(name);
    }
}
