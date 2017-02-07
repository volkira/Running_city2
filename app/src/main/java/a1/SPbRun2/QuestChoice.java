package a1.SPbRun2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import a1.SPbRun2.adapter.QuestItemClickListener;
import a1.SPbRun2.adapter.QuestListAdapter;
import a1.SPbRun2.dto.QuestDTO;
import a1.SPbRun2.loader.QuestLoader;
import a1.SPbRun2.util.SecurePreferences;

import static a1.SPbRun2.util.Constants.URL.QUEST_LOADER_ID;

/**
 * Created by FreeWind on 28.01.2017.
 */

public class QuestChoice extends AbstractAsyncActivity  {

    private String header;
    private QuestListAdapter adapter;
    private ArrayList<QuestDTO> questList;

    private RecyclerView rv;
    Loader<ArrayList<QuestDTO>> questLoader;
    private int questId;
    private int questNumberOfQuestions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_quest);

        SecurePreferences preferences = new SecurePreferences(this, "my-preferences", "998811223377446655", true);
        header = preferences.getString("header");

        questList = new ArrayList<QuestDTO>();

        adapter = new QuestListAdapter(new ArrayList<QuestDTO>());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rv = (RecyclerView)findViewById(R.id.recycleView);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        rv.addOnItemTouchListener(
                new QuestItemClickListener(this, new QuestItemClickListener.OnItemClickListener(){
                    @Override public void onItemClick(View view, int position) {
                        questId = adapter.getQuestId(position);
                        questNumberOfQuestions = adapter.getNumberOfQuestions(position);
                        SecurePreferences preferences = new SecurePreferences(getApplicationContext(), "my-preferences", "998811223377446655", true);
                        preferences.put("questId", Integer.toString(questId));
                        preferences.put("questNumberOfQuestions", Integer.toString(questNumberOfQuestions));
                        Intent intent = new Intent (getApplicationContext(), Question.class);
                        startActivity(intent);
                    }
                })
        );

        questLoader = getSupportLoaderManager().initLoader(QUEST_LOADER_ID, null, questResultLoaderListener);

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        questLoader.forceLoad();

    }

    @Override
    protected void onStart() {
        super.onStart();
        questLoader.forceLoad();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        questLoader.forceLoad();

    }

    private LoaderManager.LoaderCallbacks<ArrayList<QuestDTO>> questResultLoaderListener
            = new LoaderManager.LoaderCallbacks<ArrayList<QuestDTO>>() {

        @Override
        public Loader<ArrayList<QuestDTO>> onCreateLoader(int arg0, Bundle arg1) {
            QuestLoader questLoader = new QuestLoader(getApplicationContext(), questList);
            questLoader.header = header;
            questLoader.forceLoad();
            return questLoader;
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<QuestDTO>> arg0, ArrayList<QuestDTO> arg1) {
            adapter.setData(arg1);
            rv.setAdapter(adapter);
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<QuestDTO>> arg0) {
            adapter.setData(null);
        }


    };

//



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.toolbar_map) {
            Intent intent = new Intent(this.getApplicationContext(), Map.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}