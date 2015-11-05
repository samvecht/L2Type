package com.sam.l2type;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * The activity that displays the levels available and lets the user select one to play.
 * Created by Sam on 11/2/2015.
 */
public class LevelSelectScreen extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        TextView text;
        LevelsDB dbHelper = new LevelsDB(this);

        List<LevelPack> levels = dbHelper.getAllLevelPacks();

        List<String> listValues = new ArrayList<String>();
        for(int i = 0; i < levels.size(); i++) {
            listValues.add(levels.get(i).getName());
        }

        text = (TextView) findViewById(R.id.mainText);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>
                (this, R.layout.row_layout, R.id.listText, listValues);
        setListAdapter(myAdapter);
    }

    /**
     * Listener for a list item click.  Will eventually be used to bring the user to the
     * activity where they can play a level
     * @param list
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView list, View v, int position, long id) {
        super.onListItemClick(list, v, position,id);
        String levelName = (String) getListView().getItemAtPosition(position);

    }
}
