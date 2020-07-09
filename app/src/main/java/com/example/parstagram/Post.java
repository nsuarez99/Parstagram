package com.example.parstagram;

import android.text.format.DateUtils;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Parcel(analyze={Post.class})
@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String TAG = "PostClass";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_TIME = "createdAt";

    public Post(){}

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser){
        put(KEY_USER, parseUser);
    }

    public String getTime(){
        return parseTime(getCreatedAt().toString());
    }

    // parses from Wed Oct 10 20:19:24 +0000 2018 to xx minutes/hours ago
    public static String parseTime(String rawJsonTime){
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        dateFormat.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = dateFormat.parse(rawJsonTime).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "parsing date error: " + e);
            relativeDate = rawJsonTime;
        }
        return relativeDate;

    }

}
