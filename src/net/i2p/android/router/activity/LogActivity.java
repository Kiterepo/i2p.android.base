package net.i2p.android.router.activity;

import net.i2p.android.router.R;
import net.i2p.android.router.fragment.LogDetailFragment;
import net.i2p.android.router.fragment.LogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public class LogActivity extends I2PActivityBase implements
        LogFragment.OnEntrySelectedListener {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private static final String SELECTED_LEVEL = "selected_level";

    @Override
    protected boolean canUseTwoPanes() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up action bar for drop-down list
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        mDrawerToggle.setDrawerIndicatorEnabled(false);

        if (findViewById(R.id.detail_fragment) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.log_level_list, android.R.layout.simple_spinner_dropdown_item);

        ActionBar.OnNavigationListener mNavigationListener = new ActionBar.OnNavigationListener() {
            String[] levels = getResources().getStringArray(R.array.log_level_list);
            
            public boolean onNavigationItemSelected(int position, long itemId) {
                String level = levels[position];
                LogFragment f = LogFragment.newInstance(level);
                // In two-pane mode, list items should be given the
                // 'activated' state when touched.
                if (mTwoPane)
                    f.setActivateOnItemClick(true);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment, f, levels[position]).commit();
                return true;
            }
        };

        actionBar.setListNavigationCallbacks(mSpinnerAdapter, mNavigationListener);

        if (savedInstanceState != null) {
            int selected = savedInstanceState.getInt(SELECTED_LEVEL);
            actionBar.setSelectedNavigationItem(selected);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_LEVEL,
                getSupportActionBar().getSelectedNavigationIndex());
    }

    // LogFragment.OnEntrySelectedListener

    public void onEntrySelected(String entry) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            LogDetailFragment detailFrag = LogDetailFragment.newInstance(entry);
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_fragment, detailFrag).commit();
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, LogDetailActivity.class);
            detailIntent.putExtra(LogDetailFragment.LOG_ENTRY, entry);
            startActivity(detailIntent);
        }
    }
}
