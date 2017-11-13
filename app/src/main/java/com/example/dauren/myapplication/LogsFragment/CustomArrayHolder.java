package com.example.dauren.myapplication.LogsFragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.dauren.myapplication.MainActivity;
import com.example.dauren.myapplication.R;
import com.example.dauren.myapplication.data.LogsDbHelper;
import com.example.dauren.myapplication.data.ScanLogs;

import java.util.List;

/**
 * Created by dauren on 1/25/17.
 */
public class CustomArrayHolder extends ArrayAdapter<LogsListItem> {

    public CustomArrayHolder(Context context, int resource, List<LogsListItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final LogsListViewHolder holder = new LogsListViewHolder();

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.fragment_logs_list_item, null);

        holder.textView = (TextView) convertView.findViewById(R.id.logs_list_text_view);
        holder.button   = (Button)   convertView.findViewById(R.id.logs_list_button);

        final LogsListItem item = getItem(position);

        holder.textView.setText(item.text);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(MainActivity.TAG, "Clicked on this item: " + position);

                SQLiteDatabase mDb = LogsDbHelper
                        .getInstance(getContext())
                        .getWritableDatabase();

                String table = ScanLogs.ScanLog.TABLE_NAME;
                String whereClause = "_id=?";
                String[] whereArgs = new String[] { String.valueOf(item.id) };
                mDb.delete(table, whereClause, whereArgs);

                CustomArrayHolder.this.remove(item);
                CustomArrayHolder.this.notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
