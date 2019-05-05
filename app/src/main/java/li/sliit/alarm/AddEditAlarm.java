package li.sliit.alarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TimePicker;

public class AddEditAlarm extends AppCompatActivity {

    private TimePicker mTimePicker;
    private TextView mText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_alarm);

        mTimePicker = findViewById(R.id.edit_alarm_time_picker);
        mTimePicker.setIs24HourView(true);
    }
}
