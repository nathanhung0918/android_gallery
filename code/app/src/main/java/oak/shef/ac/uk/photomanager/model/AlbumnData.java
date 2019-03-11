package oak.shef.ac.uk.photomanager.model;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.Date;

import io.reactivex.annotations.NonNull;

@Entity(indices={@Index(value={"title"})})
public class AlbumnData implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @android.support.annotation.NonNull
    public int id=0;
    private String title;
    public String path;

    public AlbumnData(String title) {
        this.title= title;
    }


    @android.support.annotation.NonNull
    public int getId() {
        return id;
    }
    public void setId(@android.support.annotation.NonNull int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getFile() {
        return path;
    }
    public void setFile(String path) {
        this.path = path;
    }

    protected AlbumnData(Parcel in) {
        id = in.readInt();
        title = in.readString();
        path = in.readString();
    }

    public static final Creator<AlbumnData> CREATOR = new Creator<AlbumnData>() {
        @Override
        public AlbumnData createFromParcel(Parcel in) {
            return new AlbumnData(in);
        }

        @Override
        public AlbumnData[] newArray(int size) {
            return new AlbumnData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(path);
    }
}
