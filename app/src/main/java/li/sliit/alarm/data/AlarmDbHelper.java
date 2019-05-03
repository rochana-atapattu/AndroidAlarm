package li.sliit.alarm.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import li.sliit.alarm.data.AlarmContract.AlarmEntry;

public class AlarmDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alarm.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ AlarmEntry.TABLE_NAME +" ("
            +AlarmEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +AlarmEntry.COLUMN_NAME + " TEXT NOT NULL, "
            +AlarmEntry.COLUMN_TIME+ " TEXT, "
            +AlarmEntry.COLUMN_ACTIVATED + " INTEGER NOT NULL ); ";

    public AlarmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
