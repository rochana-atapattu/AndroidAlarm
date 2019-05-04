package li.sliit.alarm.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class AlarmContract {

    public final static String CONTENT_AUTHORITY  = "li.sliit.alarm";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ALARM = "alarms";
    public AlarmContract() {
    }

    public static final class AlarmEntry implements BaseColumns{

        public static final String TABLE_NAME = "alarms";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_ACTIVATED = "activated";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_ALARM);
    }
}
