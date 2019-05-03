package li.sliit.alarm.data;

import android.provider.BaseColumns;

public final class AlarmContract {

    public AlarmContract() {
    }

    public static final class AlarmEntry implements BaseColumns{

        public static final String TABLE_NAME = "alarms";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_ACTIVATED = "activated";
    }
}
