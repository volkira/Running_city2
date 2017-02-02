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

import java.util.ArrayList;

import a1.SPbRun2.adapter.QuestListAdapter;
import a1.SPbRun2.dto.QuestDTO;
import a1.SPbRun2.loader.QuestLoader;

import static a1.SPbRun2.util.Constants.URL.QUEST_LOADER_ID;

/**
 * Created by FreeWind on 28.01.2017.
 */

public class QuestChoice extends AbstractAsyncActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<QuestDTO>> {

    private String header;
    private QuestListAdapter adapter;
    private ArrayList<QuestDTO> questList;
    private RecyclerView rv;
    Loader<ArrayList<QuestDTO>> questLoader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_quest);

        Intent intent = getIntent();
        header = intent.getStringExtra("header");

        questList = new ArrayList<QuestDTO>();

        adapter = new QuestListAdapter(new ArrayList<QuestDTO>());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rv = (RecyclerView)findViewById(R.id.recycleView);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        rv.setAdapter(adapter);

        questLoader = getSupportLoaderManager().initLoader(QUEST_LOADER_ID, null, this);

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

    @Override
    public Loader<ArrayList<QuestDTO>> onCreateLoader(int arg0, Bundle arg1) {
        QuestLoader questLoader = new QuestLoader(this, questList);
        questLoader.header = this.header;
        questLoader.forceLoad();
        return questLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<QuestDTO>> arg0, ArrayList<QuestDTO> arg1) {
        adapter.setData(arg1);
        rv.setAdapter(adapter);
        dismissProgressDialog();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<QuestDTO>> arg0) {
        adapter.setData(null);
    }

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