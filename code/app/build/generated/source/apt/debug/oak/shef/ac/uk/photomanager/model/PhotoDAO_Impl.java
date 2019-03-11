package oak.shef.ac.uk.photomanager.model;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PhotoDAO_Impl implements PhotoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPhotoData;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPhotoData;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfPhotoData;

  public PhotoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPhotoData = new EntityInsertionAdapter<PhotoData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `PhotoData`(`id`,`title`,`description`,`longitude`,`latitude`,`date`,`path`,`albumn`,`favorite`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PhotoData value) {
        stmt.bindLong(1, value.id);
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        stmt.bindDouble(4, value.getLongitude());
        stmt.bindDouble(5, value.getLatitude());
        if (value.getDate() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDate());
        }
        if (value.path == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.path);
        }
        if (value.albumn == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.albumn);
        }
        if (value.favorite == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.favorite);
        }
      }
    };
    this.__deletionAdapterOfPhotoData = new EntityDeletionOrUpdateAdapter<PhotoData>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `PhotoData` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PhotoData value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfPhotoData = new EntityDeletionOrUpdateAdapter<PhotoData>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `PhotoData` SET `id` = ?,`title` = ?,`description` = ?,`longitude` = ?,`latitude` = ?,`date` = ?,`path` = ?,`albumn` = ?,`favorite` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PhotoData value) {
        stmt.bindLong(1, value.id);
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        stmt.bindDouble(4, value.getLongitude());
        stmt.bindDouble(5, value.getLatitude());
        if (value.getDate() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDate());
        }
        if (value.path == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.path);
        }
        if (value.albumn == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.albumn);
        }
        if (value.favorite == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.favorite);
        }
        stmt.bindLong(10, value.id);
      }
    };
  }

  @Override
  public void insertAll(PhotoData... photodata) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPhotoData.insert(photodata);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(PhotoData photodata) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPhotoData.insert(photodata);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(PhotoData photoData) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPhotoData.handle(photoData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll(PhotoData... photoData) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPhotoData.handleMultiple(photoData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(PhotoData photoData) {
    __db.beginTransaction();
    try {
      __updateAdapterOfPhotoData.handle(photoData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<PhotoData>> retrieveAllData() {
    final String _sql = "SELECT * FROM PhotoData ORDER BY title ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<PhotoData>>() {
      private Observer _observer;

      @Override
      protected List<PhotoData> compute() {
        if (_observer == null) {
          _observer = new Observer("PhotoData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final int _cursorIndexOfAlbumn = _cursor.getColumnIndexOrThrow("albumn");
          final int _cursorIndexOfFavorite = _cursor.getColumnIndexOrThrow("favorite");
          final List<PhotoData> _result = new ArrayList<PhotoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PhotoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new PhotoData(_tmpTitle,_tmpDescription);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            _item.path = _cursor.getString(_cursorIndexOfPath);
            _item.albumn = _cursor.getString(_cursorIndexOfAlbumn);
            _item.favorite = _cursor.getString(_cursorIndexOfFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PhotoData>> searchTitle(String title) {
    final String _sql = "SELECT * FROM PhotoData WHERE title LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    return new ComputableLiveData<List<PhotoData>>() {
      private Observer _observer;

      @Override
      protected List<PhotoData> compute() {
        if (_observer == null) {
          _observer = new Observer("PhotoData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final int _cursorIndexOfAlbumn = _cursor.getColumnIndexOrThrow("albumn");
          final int _cursorIndexOfFavorite = _cursor.getColumnIndexOrThrow("favorite");
          final List<PhotoData> _result = new ArrayList<PhotoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PhotoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new PhotoData(_tmpTitle,_tmpDescription);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            _item.path = _cursor.getString(_cursorIndexOfPath);
            _item.albumn = _cursor.getString(_cursorIndexOfAlbumn);
            _item.favorite = _cursor.getString(_cursorIndexOfFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PhotoData>> searchDescription(String description) {
    final String _sql = "SELECT * FROM PhotoData WHERE description LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (description == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, description);
    }
    return new ComputableLiveData<List<PhotoData>>() {
      private Observer _observer;

      @Override
      protected List<PhotoData> compute() {
        if (_observer == null) {
          _observer = new Observer("PhotoData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final int _cursorIndexOfAlbumn = _cursor.getColumnIndexOrThrow("albumn");
          final int _cursorIndexOfFavorite = _cursor.getColumnIndexOrThrow("favorite");
          final List<PhotoData> _result = new ArrayList<PhotoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PhotoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new PhotoData(_tmpTitle,_tmpDescription);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            _item.path = _cursor.getString(_cursorIndexOfPath);
            _item.albumn = _cursor.getString(_cursorIndexOfAlbumn);
            _item.favorite = _cursor.getString(_cursorIndexOfFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PhotoData>> searchDate(String date) {
    final String _sql = "SELECT * FROM PhotoData WHERE date LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return new ComputableLiveData<List<PhotoData>>() {
      private Observer _observer;

      @Override
      protected List<PhotoData> compute() {
        if (_observer == null) {
          _observer = new Observer("PhotoData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final int _cursorIndexOfAlbumn = _cursor.getColumnIndexOrThrow("albumn");
          final int _cursorIndexOfFavorite = _cursor.getColumnIndexOrThrow("favorite");
          final List<PhotoData> _result = new ArrayList<PhotoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PhotoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new PhotoData(_tmpTitle,_tmpDescription);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            _item.path = _cursor.getString(_cursorIndexOfPath);
            _item.albumn = _cursor.getString(_cursorIndexOfAlbumn);
            _item.favorite = _cursor.getString(_cursorIndexOfFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PhotoData>> searchDesDate(String description, String date) {
    final String _sql = "SELECT * FROM PhotoData WHERE description LIKE ? UNION SELECT * FROM PhotoData WHERE date LIKE ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (description == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, description);
    }
    _argIndex = 2;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return new ComputableLiveData<List<PhotoData>>() {
      private Observer _observer;

      @Override
      protected List<PhotoData> compute() {
        if (_observer == null) {
          _observer = new Observer("PhotoData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final int _cursorIndexOfAlbumn = _cursor.getColumnIndexOrThrow("albumn");
          final int _cursorIndexOfFavorite = _cursor.getColumnIndexOrThrow("favorite");
          final List<PhotoData> _result = new ArrayList<PhotoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PhotoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new PhotoData(_tmpTitle,_tmpDescription);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            _item.path = _cursor.getString(_cursorIndexOfPath);
            _item.albumn = _cursor.getString(_cursorIndexOfAlbumn);
            _item.favorite = _cursor.getString(_cursorIndexOfFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PhotoData>> searchDesTitle(String description, String title) {
    final String _sql = "SELECT * FROM PhotoData WHERE description LIKE ? UNION SELECT * FROM PhotoData WHERE title LIKE ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (description == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, description);
    }
    _argIndex = 2;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    return new ComputableLiveData<List<PhotoData>>() {
      private Observer _observer;

      @Override
      protected List<PhotoData> compute() {
        if (_observer == null) {
          _observer = new Observer("PhotoData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final int _cursorIndexOfAlbumn = _cursor.getColumnIndexOrThrow("albumn");
          final int _cursorIndexOfFavorite = _cursor.getColumnIndexOrThrow("favorite");
          final List<PhotoData> _result = new ArrayList<PhotoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PhotoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new PhotoData(_tmpTitle,_tmpDescription);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            _item.path = _cursor.getString(_cursorIndexOfPath);
            _item.albumn = _cursor.getString(_cursorIndexOfAlbumn);
            _item.favorite = _cursor.getString(_cursorIndexOfFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PhotoData>> searchTitleDate(String title, String date) {
    final String _sql = "SELECT * FROM PhotoData WHERE title LIKE ? UNION SELECT * FROM PhotoData WHERE date LIKE ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    _argIndex = 2;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return new ComputableLiveData<List<PhotoData>>() {
      private Observer _observer;

      @Override
      protected List<PhotoData> compute() {
        if (_observer == null) {
          _observer = new Observer("PhotoData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final int _cursorIndexOfAlbumn = _cursor.getColumnIndexOrThrow("albumn");
          final int _cursorIndexOfFavorite = _cursor.getColumnIndexOrThrow("favorite");
          final List<PhotoData> _result = new ArrayList<PhotoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PhotoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new PhotoData(_tmpTitle,_tmpDescription);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            _item.path = _cursor.getString(_cursorIndexOfPath);
            _item.albumn = _cursor.getString(_cursorIndexOfAlbumn);
            _item.favorite = _cursor.getString(_cursorIndexOfFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PhotoData>> search(String description, String title, String date) {
    final String _sql = "SELECT * FROM PhotoData WHERE description LIKE ? UNION SELECT * FROM PhotoData WHERE title LIKE ? UNION SELECT * FROM PhotoData WHERE date LIKE ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (description == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, description);
    }
    _argIndex = 2;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    _argIndex = 3;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return new ComputableLiveData<List<PhotoData>>() {
      private Observer _observer;

      @Override
      protected List<PhotoData> compute() {
        if (_observer == null) {
          _observer = new Observer("PhotoData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final int _cursorIndexOfAlbumn = _cursor.getColumnIndexOrThrow("albumn");
          final int _cursorIndexOfFavorite = _cursor.getColumnIndexOrThrow("favorite");
          final List<PhotoData> _result = new ArrayList<PhotoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PhotoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new PhotoData(_tmpTitle,_tmpDescription);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            _item.path = _cursor.getString(_cursorIndexOfPath);
            _item.albumn = _cursor.getString(_cursorIndexOfAlbumn);
            _item.favorite = _cursor.getString(_cursorIndexOfFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PhotoData>> searchMap(double l1, double l2, double r1, double r2) {
    final String _sql = "SELECT * FROM PhotoData WHERE (latitude BETWEEN ? AND ?) AND (longitude BETWEEN ? AND ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindDouble(_argIndex, l1);
    _argIndex = 2;
    _statement.bindDouble(_argIndex, l2);
    _argIndex = 3;
    _statement.bindDouble(_argIndex, r1);
    _argIndex = 4;
    _statement.bindDouble(_argIndex, r2);
    return new ComputableLiveData<List<PhotoData>>() {
      private Observer _observer;

      @Override
      protected List<PhotoData> compute() {
        if (_observer == null) {
          _observer = new Observer("PhotoData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final int _cursorIndexOfAlbumn = _cursor.getColumnIndexOrThrow("albumn");
          final int _cursorIndexOfFavorite = _cursor.getColumnIndexOrThrow("favorite");
          final List<PhotoData> _result = new ArrayList<PhotoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PhotoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new PhotoData(_tmpTitle,_tmpDescription);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            _item.path = _cursor.getString(_cursorIndexOfPath);
            _item.albumn = _cursor.getString(_cursorIndexOfAlbumn);
            _item.favorite = _cursor.getString(_cursorIndexOfFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PhotoData>> searchAlbumn(String name) {
    final String _sql = "SELECT * FROM PhotoData WHERE albumn =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    return new ComputableLiveData<List<PhotoData>>() {
      private Observer _observer;

      @Override
      protected List<PhotoData> compute() {
        if (_observer == null) {
          _observer = new Observer("PhotoData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final int _cursorIndexOfAlbumn = _cursor.getColumnIndexOrThrow("albumn");
          final int _cursorIndexOfFavorite = _cursor.getColumnIndexOrThrow("favorite");
          final List<PhotoData> _result = new ArrayList<PhotoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PhotoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new PhotoData(_tmpTitle,_tmpDescription);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            _item.path = _cursor.getString(_cursorIndexOfPath);
            _item.albumn = _cursor.getString(_cursorIndexOfAlbumn);
            _item.favorite = _cursor.getString(_cursorIndexOfFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PhotoData>> searchLike(String yes) {
    final String _sql = "SELECT * FROM PhotoData WHERE favorite = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (yes == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, yes);
    }
    return new ComputableLiveData<List<PhotoData>>() {
      private Observer _observer;

      @Override
      protected List<PhotoData> compute() {
        if (_observer == null) {
          _observer = new Observer("PhotoData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final int _cursorIndexOfAlbumn = _cursor.getColumnIndexOrThrow("albumn");
          final int _cursorIndexOfFavorite = _cursor.getColumnIndexOrThrow("favorite");
          final List<PhotoData> _result = new ArrayList<PhotoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PhotoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new PhotoData(_tmpTitle,_tmpDescription);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            _item.path = _cursor.getString(_cursorIndexOfPath);
            _item.albumn = _cursor.getString(_cursorIndexOfAlbumn);
            _item.favorite = _cursor.getString(_cursorIndexOfFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
