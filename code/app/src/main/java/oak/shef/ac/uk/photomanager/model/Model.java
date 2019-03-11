package oak.shef.ac.uk.photomanager.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class Model extends ViewModel {

    LiveData<List<PhotoData>> numberDisplay; //live data from db
    LiveData<List<PhotoData>> resultDisplay;
    LiveData<List<AlbumnData>> albumnDisplay;

    private final PhotoDAO mDBDao;// DAO to acess to db
    private final AlbumnDAO albumnDao;
    public Model(Application application) { // init
            numberDisplay= new MutableLiveData();
            resultDisplay= new MutableLiveData();
            albumnDisplay= new MutableLiveData();
            PhotoDatabase db = PhotoDatabase.getDatabase(application);
            mDBDao = db.photoDao();
            albumnDao = db.albumnDao();
        }
    //retrieve method
    public LiveData<List<PhotoData>> getNumberToDisplay() {
            numberDisplay = mDBDao.retrieveAllData();
            return numberDisplay;
        }
    public LiveData<List<AlbumnData>> getAlbumnToDisplay() {
        albumnDisplay = albumnDao.retrieveAllData();
        return albumnDisplay;
    }
    //insert,delete,update photo data
    public void storeData(PhotoData photo) {//save data
        if(photo.getFile() != null)
            new insertAsyncTask(mDBDao).execute(photo);
    }
    public void deleteData(PhotoData photo) {//delete data
        if(photo.getId() >= 0)
            new deleteAsyncTask(mDBDao).execute(photo);
    }
    public void updateData(PhotoData photo) {//update data
        if(photo.getId() >= 0)
            new updateAsyncTask(mDBDao).execute(photo);
    }
    //search method
    public LiveData<List<PhotoData>> searchTitle(String keyWord) {//read data from db
        resultDisplay = mDBDao.searchTitle("%" + keyWord + "%");
        return resultDisplay;
    }
    public LiveData<List<PhotoData>> searchDescription(String keyWord) {//read data from db
        resultDisplay = mDBDao.searchDescription("%" + keyWord + "%");
        return resultDisplay;
    }
    public LiveData<List<PhotoData>> searchDate(String keyWord) {//read data from db
        resultDisplay = mDBDao.searchDate("%" + keyWord + "%");
        return resultDisplay;
    }
    public LiveData<List<PhotoData>> searchDesDate(String keyWord1, String keyWord2) {//read data from db
        resultDisplay = mDBDao.searchDesDate("%" + keyWord1 + "%","%" + keyWord2 + "%");
        return resultDisplay;
    }
    public LiveData<List<PhotoData>> searchDesTitle(String keyWord1, String keyWord2) {//read data from db
        resultDisplay = mDBDao.searchDesTitle("%" + keyWord1 + "%","%" + keyWord2 + "%");
        return resultDisplay;
    }
    public LiveData<List<PhotoData>> searchTitleDate(String keyWord1, String keyWord2) {//read data from db
        resultDisplay = mDBDao.searchTitleDate("%" + keyWord1 + "%","%" + keyWord2 + "%");
        return resultDisplay;
    }
    public LiveData<List<PhotoData>> search(String keyWord1, String keyWord2, String keyWord3) {//read data from db
        resultDisplay = mDBDao.search("%" + keyWord1 + "%","%" + keyWord2 + "%", "%" + keyWord3 + "%" );
        return resultDisplay;
    }

    public LiveData<List<PhotoData>> searchAlbumn(String keyWord) {//read data from db
        resultDisplay = mDBDao.searchAlbumn(keyWord);
        return resultDisplay;
    }

    public LiveData<List<PhotoData>> searchLike(String keyWord) {//read data from db
        resultDisplay = mDBDao.searchLike(keyWord);
        return resultDisplay;
    }

    //insert,delete,update for albumn
    public void storeAlbumn(AlbumnData albumn) {//save data
        if(albumn.getId() >= 0)
            new insertAlbumnAsyncTask(albumnDao).execute(albumn);
    }
    public void deleteAlbumn(AlbumnData albumn) {//delete data
        if(albumn.getId() >= 0)
            new deleteAlbumnAsyncTask(albumnDao).execute(albumn);
    }
    public void updateAlbumn(AlbumnData albumn) {//delete data
        if(albumn.getId() >= 0)
            new updateAlbumnAsyncTask(albumnDao).execute(albumn);
    }
    public LiveData<List<AlbumnData>> searchOne(String name) {//delete data
        return albumnDao.searchOne(name);
    }

    //query for map
    public LiveData<List<PhotoData>> searchMap(double l1, double l2, double r1, double r2) {//read data from db
        resultDisplay = mDBDao.searchMap(l1,l2,r1,r2);
        return resultDisplay;
    }
    //do in background
    private static class insertAsyncTask extends AsyncTask<PhotoData, Void, Void> {
            private PhotoDAO mAsynTaskDao;

            public insertAsyncTask(PhotoDAO dao){
                mAsynTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final PhotoData... params) {
                mAsynTaskDao.insert(params[0]);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }
    private static class deleteAsyncTask extends AsyncTask<PhotoData, Void, Void> {
            private PhotoDAO mAsynTaskDao;

            public deleteAsyncTask(PhotoDAO dao){
                mAsynTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final PhotoData... params) {
                mAsynTaskDao.delete(params[0]);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }
    private static class updateAsyncTask extends AsyncTask<PhotoData, Void, Void> {
        private PhotoDAO mAsynTaskDao;

        public updateAsyncTask(PhotoDAO dao){
            mAsynTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PhotoData... params) {
            mAsynTaskDao.update(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
    private static class insertAlbumnAsyncTask extends AsyncTask<AlbumnData, Void, Void> {
        private AlbumnDAO mAsynTaskDao;

        public insertAlbumnAsyncTask(AlbumnDAO dao){
            mAsynTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AlbumnData... params) {
            mAsynTaskDao.insert(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
    private static class deleteAlbumnAsyncTask extends AsyncTask<AlbumnData, Void, Void> {
        private AlbumnDAO mAsynTaskDao;

        public deleteAlbumnAsyncTask(AlbumnDAO dao){
            mAsynTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AlbumnData... params) {
            mAsynTaskDao.delete(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
    private static class updateAlbumnAsyncTask extends AsyncTask<AlbumnData, Void, Void> {
        private AlbumnDAO mAsynTaskDao;

        public updateAlbumnAsyncTask(AlbumnDAO dao){
            mAsynTaskDao = dao;

        }

        @Override
        protected Void doInBackground(final AlbumnData... params) {
            mAsynTaskDao.update(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


    }
