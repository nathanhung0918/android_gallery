package oak.shef.ac.uk.photomanager.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PhotoDAO {
    @Insert
    void insertAll(PhotoData... photodata);

    @Insert
    void insert(PhotoData photodata);

    @Delete
    void delete (PhotoData photoData);

    @Query("SELECT * FROM PhotoData ORDER BY title ASC")
    LiveData<List<PhotoData>> retrieveAllData();

    @Delete
    void deleteAll(PhotoData...photoData);

    @Update
    void update(PhotoData photoData);

    @Query("SELECT * FROM PhotoData WHERE title LIKE :title")
    LiveData<List<PhotoData>> searchTitle(String title);

    @Query("SELECT * FROM PhotoData WHERE description LIKE :description" )
    LiveData<List<PhotoData>> searchDescription(String description);

    @Query("SELECT * FROM PhotoData WHERE date LIKE :date")
    LiveData<List<PhotoData>> searchDate(String date);

    @Query("SELECT * FROM PhotoData WHERE description LIKE :description UNION SELECT * FROM PhotoData WHERE date LIKE :date " )
    LiveData<List<PhotoData>> searchDesDate(String description,String date);

    @Query("SELECT * FROM PhotoData WHERE description LIKE :description UNION SELECT * FROM PhotoData WHERE title LIKE :title " )
    LiveData<List<PhotoData>> searchDesTitle(String description, String title);

    @Query("SELECT * FROM PhotoData WHERE title LIKE :title UNION SELECT * FROM PhotoData WHERE date LIKE :date " )
    LiveData<List<PhotoData>> searchTitleDate(String title, String date);

    @Query("SELECT * FROM PhotoData WHERE description LIKE :description UNION SELECT * FROM PhotoData WHERE title LIKE :title UNION SELECT * FROM PhotoData WHERE date LIKE :date " )
    LiveData<List<PhotoData>> search(String description, String title, String date);

    @Query("SELECT * FROM PhotoData WHERE (latitude BETWEEN :l1 AND :l2) AND (longitude BETWEEN :r1 AND :r2)")
    LiveData<List<PhotoData>> searchMap(double l1, double l2, double r1, double r2);

    @Query("SELECT * FROM PhotoData WHERE albumn =:name")
    LiveData<List<PhotoData>> searchAlbumn(String name);

    @Query("SELECT * FROM PhotoData WHERE favorite = :yes")
    LiveData<List<PhotoData>> searchLike(String yes);
}
