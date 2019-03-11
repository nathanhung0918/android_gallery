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
public class PhotoData implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @android.support.annotation.NonNull
    public int id=0;
    private String title;
    private String description;
    private double longitude;
    private double latitude;
    private String date;
    public String path;
    public String albumn;
    public String favorite = "no";
    @Ignore
    public Bitmap picture;
    public PhotoData(String title, String description) {
        this.title= title;
        this.description= description;

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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Bitmap getPicture() {
        return picture;
    }
    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
    public String getFile() {
        return path;
    }
    public void setFile(String path) {
        this.path = path;
    }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getalbumn() {
        return albumn;
    }
    public void setalbumn(String albumn) {
        this.albumn = albumn;
    }
    public String getLike(){return favorite;}
    public void setLike(String like){this.favorite = like;}

    protected PhotoData(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        date = in.readString();
        path = in.readString();
        albumn = in.readString();
        favorite = in.readString();
    }

    public static final Creator<PhotoData> CREATOR = new Creator<PhotoData>() {
        @Override
        public PhotoData createFromParcel(Parcel in) {
            return new PhotoData(in);
        }

        @Override
        public PhotoData[] newArray(int size) {
            return new PhotoData[size];
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
        dest.writeString(description);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(date);
        dest.writeString(path);
        dest.writeString(albumn);
        dest.writeString(favorite);
    }
}
