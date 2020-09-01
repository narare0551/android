package com.example.providerapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.net.URI;

//실제로는 일일히 쓰는 경우는 많이 없고 다른 사람이 한거에서 가져옴
public class PersonProvider extends ContentProvider {
    //g현재 제공자
    private static final String AUTHORITY = "com.example.provider";
    //data type
    private static final String BASE_PATH = "person";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    //실제 data
    private static final int PERSONS = 1;
    private static final int PERSON_ID = 2;


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {

        uriMatcher.addURI(AUTHORITY, BASE_PATH, PERSONS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", PERSON_ID);

//    provide content를 요청할때 uri를 줌
        //select를 요청할때 uri matched형식을 넣어준다. ? 뭔소리야


    }

    private SQLiteDatabase database;


    public PersonProvider() {
    }

    //------------delete----------------------------
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case PERSONS:
                count = database.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("알수없는 URI" + uri);


        }
        getContext().getContentResolver().notifyChange(uri, null);
        //삭제된 갯수만큼 return
        return count;

    }

    //---------------------------- gettype------------------------------------------
    //우리가 type얻을때 씀
    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        //throw new UnsupportedOperationException("Not yet implemented");
        switch (uriMatcher.match(uri)) {

            case PERSONS:
                return "vnd.android.cursor.dir/persons";
            //default는 return이 아니라 throw이다 왜?


            default:
                throw new IllegalArgumentException("알수 없는 URI" + uri);

        }
    }


    @Override

    //함수 선언
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        //throw new UnsupportedOperationException("Not yet implemented");

        long id = database.insert(DatabaseHelper.TABLE_NAME, null, values);
        //id가 0보다 크면 정상적으로 insert가 된 것이다

        if (id > 0) {
            //contentURI 에 id를넣음
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
            //우리가 insert를 하고 나서도 resolver 로 현재 요청한 uri가 바뀌었다고
            getContext().getContentResolver().notifyChange(uri, null);
            return _uri;

        }
        throw new SQLException("추가 실패 -> URI:" + uri);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        //DB 생성
        DatabaseHelper helper = new DatabaseHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    //------------------query문 ------------------------
    @Override
    //projection 은 column , selection은 where절 (행)
    //selectionargs , sort( 오름차순 내림차순 정렬)
//query 요청하면 uri를 받음
    public Cursor query(Uri uri, String[] projection, String selection,
                        //select 하면 cursor가 생김
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //throw new UnsupportedOperationException("Not yet implemented");
        //커서 생성
        Cursor cursor = null;

        switch (uriMatcher.match(uri)) {
            //persons(1)이면
            case PERSONS:
                cursor = database.query(DatabaseHelper.TABLE_NAME,
                        DatabaseHelper.ALL_COLUMNS,
                        selection,
                        null,
                        null,
                        null,
                        DatabaseHelper.PERSON_NAME + " ASC");
                break;
            default:
                throw new IllegalArgumentException("알수 없는 URI " + uri);
        }
        //provide를 쓸때는 resolver를 사용해야한다.
        //현재제공 uri를 가지고 사용하면 된다.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        //cursor에 담아서 사용
        return cursor;
    }

    //------------ update-----------
    @Override
    //content value 안에는 set값 (file name, update value) update할 값
    // selection 은 조건
    //조건이 따르면 argument가 들어간다
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        // throw new UnsupportedOperationException("Not yet implemented");
        //update되는 갯수
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case PERSONS:
                count = database.update(DatabaseHelper.TABLE_NAME,
                        values, selection, selectionArgs);
//               sql =update table명 set 필드명 = 값 where name ="홍길동"
//                    //고칠게 mobile이면
//                    values.put("mobile","010-");
//               //이런식으로 고친게 contentvalue로 들어감
                break;


            default:
                //uri를 잘 못 넣었을때
                throw new IllegalArgumentException("알 수 없는 URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;

    }
}
