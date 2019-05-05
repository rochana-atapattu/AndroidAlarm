package li.sliit.alarm;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import li.sliit.alarm.data.AlarmContract.AlarmEntry;

public class AlarmCursorAdapter extends BaseCursorAdapter<AlarmCursorAdapter.AlarmViewHolder> {



    public AlarmCursorAdapter() {
        super(null);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, Cursor cursor) {

        String name = cursor.getString(cursor.getColumnIndex(AlarmEntry.COLUMN_NAME));
        long id = cursor.getInt(cursor.getColumnIndex(AlarmEntry._ID));

        holder.nameTextView.setText(name);
        holder.itemView.setTag(id);
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
       return new AlarmViewHolder(v);

    }

    class AlarmViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        public AlarmViewHolder(@NonNull View v) {
            super(v);

            nameTextView = (TextView) v.findViewById(R.id.firstLine);
        }
    }
}
