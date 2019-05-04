package li.sliit.alarm.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import li.sliit.alarm.data.AlarmContract.AlarmEntry;

public class AlarmProvider extends ContentProvider{

    //constants to match URI
    private static final int ALARMS = 1;
    private static final int ALARMS_ID = 2;

    //use UriMatcher class to make the matching
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    private AlarmDbHelper mDbHelper;

    static{
        sUriMatcher.addURI(AlarmContract.CONTENT_AUTHORITY,AlarmContract.PATH_ALARM,ALARMS);
        sUriMatcher.addURI(AlarmContract.CONTENT_AUTHORITY,AlarmContract.PATH_ALARM + "/#",ALARMS_ID);
    }


    @Override
    public boolean onCreate() {
        //return true if provider was successfully loaded, false otherwise
        mDbHelper = new AlarmDbHelper(getContext());
        return mDbHelper != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        //readable db
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        //cursor to hold data from db
        Cursor cursor;

        //match URI
        int match = sUriMatcher.match(uri);
        switch(match){
            case ALARMS:
                cursor = db.query(AlarmEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case ALARMS_ID:
                selection = AlarmEntry._ID + "=?";
                //add the id to selectArgs string array
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(AlarmEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

                break;
            default:
                throw new IllegalArgumentException("Cannot resolve URI :" + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {

            case ALARMS:
                return AlarmEntry.CONTENT_LIST_TYPE;
            case ALARMS_ID:
                return AlarmEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Cannot resolve URI :" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int match = sUriMatcher.match(uri);
        switch (match){
            case ALARMS:
                return insertAlarm(uri, values);
            default:
                throw new IllegalArgumentException("Cannot resolve URI :" + uri);
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        switch (match) {

            case ALARMS:
                return db.delete(AlarmEntry.TABLE_NAME,selection,selectionArgs);
            case ALARMS_ID:
                selection = AlarmEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return db.delete(AlarmEntry.TABLE_NAME,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Cannot resolve URI :" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        switch (match) {

            case ALARMS:
                return updatePet(uri,values,selection,selectionArgs);
            case ALARMS_ID:
                selection = AlarmEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri,values,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Cannot resolve URI :" + uri);
        }

        }

    private Uri insertAlarm(Uri uri, ContentValues values){

        //data check
        String name = values.getAsString(AlarmEntry.COLUMN_NAME);
        if (name == null){
            throw new IllegalArgumentException("Invalid name");
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //insert new data
        long id = db.insert(AlarmEntry.TABLE_NAME,null,values);

        if (id == -1){
            return null;
        }

        //return uri with the id
        return ContentUris.withAppendedId(uri,id);
    }
    private int updatePet(Uri uri,ContentValues values,String selection,String[] selectionArgs){

        //data check
        String name = values.getAsString(AlarmEntry.COLUMN_NAME);
        if (name == null){
            throw new IllegalArgumentException("Invalid name");
        }

        //dont update if values is empty
        if (values.size() == 0){
            return 0;
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        return  db.update(AlarmEntry.TABLE_NAME,values,selection,selectionArgs);
    }
}
