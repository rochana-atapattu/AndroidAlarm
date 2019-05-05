package li.sliit.alarm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import li.sliit.alarm.data.AlarmContract.AlarmEntry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/

    private RecyclerView recyclerView;
    private AlarmCursorAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertAlarm();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AlarmCursorAdapter();
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        recyclerView.setOnClickListener(new );

        LoaderManager.getInstance(MainActivity.this).initLoader(1,null,MainActivity.this);
        // Setup FAB to open AddEdit screen
        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditAlarm.class);
                startActivity(intent);
                //insertAlarm();
            }
        });


    }
    private void insertAlarm(){
        ContentValues values = new ContentValues();
        values.put(AlarmEntry.COLUMN_NAME,"morning");
        values.put(AlarmEntry.COLUMN_ACTIVATED,"1");
        Uri uri = getContentResolver().insert(AlarmEntry.CONTENT_URI,values);

        if (uri == null){
            Toast.makeText(this,"failed",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"added",Toast.LENGTH_LONG).show();
        }
    }
    private void removeItem(long id){
        int uri = getContentResolver().delete(AlarmEntry.CONTENT_URI,AlarmEntry._ID + "=" + id,null);

        if (uri == -1){
            Toast.makeText(this,"failed to delete : " + id,Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"deleted : " + uri,Toast.LENGTH_LONG).show();
        }

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        if(i == 1){
            return alarmLoader();
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    private Loader<Cursor> alarmLoader(){
        Uri alarmUri =  AlarmEntry.CONTENT_URI;
        String[] projection = {AlarmEntry.COLUMN_NAME,AlarmEntry._ID};

        return new CursorLoader(getApplicationContext(),alarmUri,projection,null,null,null);
    }


}
