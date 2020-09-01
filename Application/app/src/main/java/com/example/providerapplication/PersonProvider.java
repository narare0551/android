package com.example.providerapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PersonProvider extends ContentProvider {
    private static final String AUTHORITY="com.example.provider";
    private static final String BASE_PATH="person";
public static final Uri CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/"+BASE_PATH);

private static final int PERSONS=1;
private static final int PERSON_ID=2;


private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


static
{

    uriMatcher.addURI(AUTHORITY,BASE_PATH,PERSONS);
    uriMatcher.addURI(AUTHORITY, BASE_PATH+"/#",PERSON_ID);

//    provide content를 요청할때 uri를 줌
    //select를 요청할때 uri matched형식을 넣어준다. ? 뭔소리야

    



}
private SQLiteDatabase database;









    public PersonProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        DatabaseHelper helper = new DatabaseHelper(getContext());
        database=helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
