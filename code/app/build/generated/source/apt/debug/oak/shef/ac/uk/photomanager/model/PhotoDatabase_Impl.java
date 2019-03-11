package oak.shef.ac.uk.photomanager.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class PhotoDatabase_Impl extends PhotoDatabase {
  private volatile PhotoDAO _photoDAO;

  private volatile AlbumnDAO _albumnDAO;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(12) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `PhotoData` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `description` TEXT, `longitude` REAL NOT NULL, `latitude` REAL NOT NULL, `date` TEXT, `path` TEXT, `albumn` TEXT, `favorite` TEXT)");
        _db.execSQL("CREATE  INDEX `index_PhotoData_title` ON `PhotoData` (`title`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `AlbumnData` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `path` TEXT)");
        _db.execSQL("CREATE  INDEX `index_AlbumnData_title` ON `AlbumnData` (`title`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"9c51e60842cb43a04be38c31eb75b736\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `PhotoData`");
        _db.execSQL("DROP TABLE IF EXISTS `AlbumnData`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPhotoData = new HashMap<String, TableInfo.Column>(9);
        _columnsPhotoData.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsPhotoData.put("title", new TableInfo.Column("title", "TEXT", false, 0));
        _columnsPhotoData.put("description", new TableInfo.Column("description", "TEXT", false, 0));
        _columnsPhotoData.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0));
        _columnsPhotoData.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0));
        _columnsPhotoData.put("date", new TableInfo.Column("date", "TEXT", false, 0));
        _columnsPhotoData.put("path", new TableInfo.Column("path", "TEXT", false, 0));
        _columnsPhotoData.put("albumn", new TableInfo.Column("albumn", "TEXT", false, 0));
        _columnsPhotoData.put("favorite", new TableInfo.Column("favorite", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPhotoData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPhotoData = new HashSet<TableInfo.Index>(1);
        _indicesPhotoData.add(new TableInfo.Index("index_PhotoData_title", false, Arrays.asList("title")));
        final TableInfo _infoPhotoData = new TableInfo("PhotoData", _columnsPhotoData, _foreignKeysPhotoData, _indicesPhotoData);
        final TableInfo _existingPhotoData = TableInfo.read(_db, "PhotoData");
        if (! _infoPhotoData.equals(_existingPhotoData)) {
          throw new IllegalStateException("Migration didn't properly handle PhotoData(oak.shef.ac.uk.photomanager.model.PhotoData).\n"
                  + " Expected:\n" + _infoPhotoData + "\n"
                  + " Found:\n" + _existingPhotoData);
        }
        final HashMap<String, TableInfo.Column> _columnsAlbumnData = new HashMap<String, TableInfo.Column>(3);
        _columnsAlbumnData.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsAlbumnData.put("title", new TableInfo.Column("title", "TEXT", false, 0));
        _columnsAlbumnData.put("path", new TableInfo.Column("path", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAlbumnData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAlbumnData = new HashSet<TableInfo.Index>(1);
        _indicesAlbumnData.add(new TableInfo.Index("index_AlbumnData_title", false, Arrays.asList("title")));
        final TableInfo _infoAlbumnData = new TableInfo("AlbumnData", _columnsAlbumnData, _foreignKeysAlbumnData, _indicesAlbumnData);
        final TableInfo _existingAlbumnData = TableInfo.read(_db, "AlbumnData");
        if (! _infoAlbumnData.equals(_existingAlbumnData)) {
          throw new IllegalStateException("Migration didn't properly handle AlbumnData(oak.shef.ac.uk.photomanager.model.AlbumnData).\n"
                  + " Expected:\n" + _infoAlbumnData + "\n"
                  + " Found:\n" + _existingAlbumnData);
        }
      }
    }, "9c51e60842cb43a04be38c31eb75b736");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "PhotoData","AlbumnData");
  }

  @Override
  public PhotoDAO photoDao() {
    if (_photoDAO != null) {
      return _photoDAO;
    } else {
      synchronized(this) {
        if(_photoDAO == null) {
          _photoDAO = new PhotoDAO_Impl(this);
        }
        return _photoDAO;
      }
    }
  }

  @Override
  public AlbumnDAO albumnDao() {
    if (_albumnDAO != null) {
      return _albumnDAO;
    } else {
      synchronized(this) {
        if(_albumnDAO == null) {
          _albumnDAO = new AlbumnDAO_Impl(this);
        }
        return _albumnDAO;
      }
    }
  }
}
