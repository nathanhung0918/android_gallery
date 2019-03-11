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

public class AlbumnDAO_Impl implements AlbumnDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfAlbumnData;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfAlbumnData;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfAlbumnData;

  public AlbumnDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAlbumnData = new EntityInsertionAdapter<AlbumnData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `AlbumnData`(`id`,`title`,`path`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AlbumnData value) {
        stmt.bindLong(1, value.id);
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.path == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.path);
        }
      }
    };
    this.__deletionAdapterOfAlbumnData = new EntityDeletionOrUpdateAdapter<AlbumnData>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `AlbumnData` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AlbumnData value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfAlbumnData = new EntityDeletionOrUpdateAdapter<AlbumnData>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `AlbumnData` SET `id` = ?,`title` = ?,`path` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AlbumnData value) {
        stmt.bindLong(1, value.id);
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.path == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.path);
        }
        stmt.bindLong(4, value.id);
      }
    };
  }

  @Override
  public void insertAll(AlbumnData... albumndata) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfAlbumnData.insert(albumndata);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(AlbumnData albumndata) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfAlbumnData.insert(albumndata);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(AlbumnData albumndata) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfAlbumnData.handle(albumndata);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll(AlbumnData... albumndata) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfAlbumnData.handleMultiple(albumndata);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(AlbumnData albumndata) {
    __db.beginTransaction();
    try {
      __updateAdapterOfAlbumnData.handle(albumndata);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<AlbumnData>> retrieveAllData() {
    final String _sql = "SELECT * FROM AlbumnData ORDER BY title ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<AlbumnData>>() {
      private Observer _observer;

      @Override
      protected List<AlbumnData> compute() {
        if (_observer == null) {
          _observer = new Observer("AlbumnData") {
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
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final List<AlbumnData> _result = new ArrayList<AlbumnData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AlbumnData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item = new AlbumnData(_tmpTitle);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _item.path = _cursor.getString(_cursorIndexOfPath);
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
  public LiveData<List<AlbumnData>> searchTitle(String title) {
    final String _sql = "SELECT * FROM AlbumnData WHERE title LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    return new ComputableLiveData<List<AlbumnData>>() {
      private Observer _observer;

      @Override
      protected List<AlbumnData> compute() {
        if (_observer == null) {
          _observer = new Observer("AlbumnData") {
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
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final List<AlbumnData> _result = new ArrayList<AlbumnData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AlbumnData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item = new AlbumnData(_tmpTitle);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _item.path = _cursor.getString(_cursorIndexOfPath);
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
  public LiveData<List<AlbumnData>> searchOne(String title) {
    final String _sql = "SELECT * FROM AlbumnData WHERE title = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    return new ComputableLiveData<List<AlbumnData>>() {
      private Observer _observer;

      @Override
      protected List<AlbumnData> compute() {
        if (_observer == null) {
          _observer = new Observer("AlbumnData") {
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
          final int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
          final List<AlbumnData> _result = new ArrayList<AlbumnData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AlbumnData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item = new AlbumnData(_tmpTitle);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _item.path = _cursor.getString(_cursorIndexOfPath);
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
