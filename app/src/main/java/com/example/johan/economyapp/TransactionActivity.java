package com.example.johan.economyapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.johan.economyapp.R.id.action_favorite;

public class TransactionActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter mStringAdaptor;
    private String[] drawerOptions;
    private TransactionController controller;
    private Bundle bundle;
    private TransactionFragment transactionFragment;
    private TransactionFragmentBarcode fragmentBarcode;
    private static final String TAG_TRANSACTION_FRAGMENT = "TransactionFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        init();
        setupDrawer();
        controller = new TransactionController(getApplicationContext());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        android.app.FragmentManager fm = getFragmentManager();
        transactionFragment = (TransactionFragment)fm.findFragmentByTag(TAG_TRANSACTION_FRAGMENT);

        if(transactionFragment == null)
        {
            transactionFragment = new TransactionFragment();
            fm.beginTransaction().add(R.id.fragmentLayout,transactionFragment,TAG_TRANSACTION_FRAGMENT).commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_camera, menu);
        return true;
    }

    private void setupDrawer()
    {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.open, R.string.closed)
        {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView)
            {
                getSupportActionBar().hide();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view)
            {
                getSupportActionBar().show();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0)
                    startActivity(new Intent(TransactionActivity.this, MainActivity.class));

                if (position == 1)
                    startActivity(new Intent(TransactionActivity.this, HistoryActivity.class));

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

        if (item.getItemId() == action_favorite)
        {
            new IntentIntegrator(TransactionActivity.this).initiateScan();
        }

        return super.onOptionsItemSelected(item);


    }

    private void init()
    {
        drawerOptions = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mStringAdaptor = new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerOptions);
        mDrawerList.setAdapter(mStringAdaptor);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            if (result.getContents() == null) //om resultatet inte är null
            {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else
            {
                BarcodeItem item = controller.getBarcodeItem(result.getContents());

                if (item == null) //om resultatet inte är null och barcoden ej är sparad
                {
                    bundle = new Bundle();
                    bundle.putString("barcode", result.getContents());

                    fragmentBarcode = new TransactionFragmentBarcode();
                    fragmentBarcode.setArguments(bundle);

                    android.app.FragmentManager fragManager = getFragmentManager();

                    android.app.FragmentTransaction transaction = fragManager.beginTransaction();
                    transaction.replace(R.id.fragmentLayout, fragmentBarcode, "barcode");
                    transaction.commit();
                }else
                {

                    transactionFragment.setValues(item.getTitle(), item.getSum(), item.getCategory());

                }
            }
        } else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
