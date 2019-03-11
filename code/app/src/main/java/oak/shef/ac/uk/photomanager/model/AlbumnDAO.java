package oak.shef.ac.uk.photomanager.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AlbumnDAO {
    @Insert
    void insertAll(AlbumnData... albumndata);

    @Insert
    void insert(AlbumnData albumndata);

    @Delete
    void delete (AlbumnData albumndata);

    @Query("SELECT * FROM AlbumnData ORDER BY title ASC")
    LiveData<List<AlbumnData>> retrieveAllData();

    @Delete
    void deleteAll(AlbumnData...albumndata);

    @Update
    void update(AlbumnData albumndata);

    @Query("SELECT * FROM AlbumnData WHERE title LIKE :title")
    LiveData<List<AlbumnData>> searchTitle(String title);

    @Query("SELECT * FROM AlbumnData WHERE title = :title LIMIT 1")
    LiveData<List<AlbumnData>> searchOne(String title);



}
