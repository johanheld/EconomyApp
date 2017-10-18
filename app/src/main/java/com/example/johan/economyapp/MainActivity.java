package com.example.johan.economyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements MainFragment1.OnDateSelectedListener
{
    private Toolbar toolbar;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter mStringAdaptor;
    private String[] drawerOptions;
    private ActionBarDrawerToggle mDrawerToggle;
    private SharedPreferences pref;
    private FloatingActionButton fab;
    private MainFragmentPie pieFragment;
    private static final String TAG_PIE_FRAGMENT = "TransactionFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkIfFirstStart();
        init();
        setupDrawer();

        MainFragmentPie fragmentPie = new MainFragmentPie();

//        android.app.FragmentManager fm = getFragmentManager();
//        pieFragment = (MainFragmentPie)fm.findFragmentByTag(TAG_PIE_FRAGMENT);
//
//        if(pieFragment == null)
//        {
//            pieFragment = new MainFragmentPie();
//            fm.beginTransaction().add(R.id.fragmentFrame,pieFragment,TAG_PIE_FRAGMENT).commit();

//        }



        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentFrame, fragmentPie, "pieFragment");
        transaction.commit();
    }

    private void checkIfFirstStart()
    {
        pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = pref.getBoolean("pref_previously_started", false);

        if (!previouslyStarted)
        {
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean("pref_previously_started", Boolean.TRUE);
            edit.commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void init()
    {
        drawerOptions = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mStringAdaptor = new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerOptions);
        mDrawerList.setAdapter(mStringAdaptor);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new fabL());
        fab.show();
    }

    private void setupDrawer()
    {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.open, R.string.closed)
        {

            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView)
//            {
//                getSupportActionBar().hide();
//            }

            /** Called when a drawer has settled in a completely closed state. */
//            public void onDrawerClosed(View view)
//            {
//                getSupportActionBar().show();
//            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                if (position == 1)
                    startActivity(new Intent(MainActivity.this, HistoryActivity.class));

                if (position == 2)
                    startActivity(new Intent(MainActivity.this, TransactionActivity.class));

            }
        });
    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onArticleSelected(String dateFrom, String dateTo)
    {
        android.app.FragmentManager fm = getFragmentManager();
        MainFragmentPie mainPie = (MainFragmentPie)fm.findFragmentByTag("pieFragment");
        mainPie.createPie(dateFrom, dateTo);
    }

    private class fabL implements View.OnClickListener{

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
            startActivity(intent);
        }
    }
}
