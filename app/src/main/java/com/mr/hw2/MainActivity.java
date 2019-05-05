package com.mr.hw2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mr.hw2.attractions.AttractionListContent;

import java.util.Random;

import static com.mr.hw2.attractions.AttractionListContent.addItem;

public class MainActivity extends AppCompatActivity implements AttractionFragment.OnListFragmentInteractionListener, DeleteDialog.OnDeleteDialogInteractionListener {

    public static final String attractionExtra = "attractionExtra";
    private final String ATTRACTIONS_SHARED_PREFS = "AttractionsSharedPrefs";
    private final String NUM_ATTRACTIONS = "NumofAttractions";
    private final String ATTRACTION = "attraction_";
    private final String DESC = "description_";
    private final String PIC = "pic_";
    private final String LOCAL = "localization_";
    private int currentItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restoreAttractionsFromSP();


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            onListFragmentClickInteraction(AttractionListContent.getAttraction(0), 0);
        }
    }


    public void addClicked(View view) {
        Intent intent = new Intent(view.getContext(), AddActivity.class);
        startActivityForResult(intent, 1);
    }
    public void cameraClicked(View view) {
        Intent intent = new Intent(view.getContext(), AddActivity.class);
        intent.putExtra("camera", 1);
        startActivityForResult(intent, 1);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == AddActivity.RESULT_OK) {
                String title = data.getStringExtra("titleRet");
                String description = data.getStringExtra("descriptionRet");
                String localization = data.getStringExtra("localizationRet");
                String picPath = data.getStringExtra("picPath");

                AttractionListContent.addItem(AttractionListContent.createAttraction(AttractionListContent.ITEMS.size(), title, description, localization, picPath));
                saveAttractionsToSP();
                restoreAttractionsFromSP();
                ((AttractionFragment) getSupportFragmentManager().findFragmentById(R.id.attractionFragment)).notifyDataChange();

            } else if (resultCode == AddActivity.RESULT_CANCELED) {
            }

        }

    }

    private void saveAttractionsToSP() {
        SharedPreferences attractions = getSharedPreferences(ATTRACTIONS_SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = attractions.edit();
        editor.clear();


        editor.putInt(NUM_ATTRACTIONS, AttractionListContent.ITEMS.size() );
        for(int i=0; i< AttractionListContent.ITEMS.size(); i++){
            AttractionListContent.Attraction attraction = AttractionListContent.ITEMS.get(i);
            editor.putString(ATTRACTION + i, attraction.title);
            editor.putString(DESC + i, attraction.description);
            editor.putString(LOCAL + i, attraction.localization);
            editor.putString(PIC + i, attraction.picPath);
        }
        editor.apply();
    }

    private void restoreAttractionsFromSP() {
        SharedPreferences attractions = getSharedPreferences(ATTRACTIONS_SHARED_PREFS, MODE_PRIVATE);
        int numOfAttractions = attractions.getInt(NUM_ATTRACTIONS, 0);
        if(numOfAttractions != 0){
            AttractionListContent.clearList();
            for(int i=0; i< numOfAttractions; i++){
                String title = attractions.getString(ATTRACTION + i, "");
                String description = attractions.getString(DESC + i, "");
                String localization = attractions.getString(LOCAL + i, "");
                String picPath = attractions.getString(PIC + i, "");

                AttractionListContent.addItem(AttractionListContent.createAttraction( i, title, description, localization, picPath));
                ((AttractionFragment) getSupportFragmentManager().findFragmentById(R.id.attractionFragment)).notifyDataChange();
            }

        }

    }


    @Override
    public void onListFragmentClickInteraction(AttractionListContent.Attraction attraction, int position) {

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            displayAttractionInFragment(attraction);
        }
        else{
            startSecondActivity(attraction, position);
        }
    }

    @Override
    public void onDeleteButtonClickListener(int position) {
        showDeleteDialog();
        currentItemPosition = position;
    }


    private void startSecondActivity(AttractionListContent.Attraction attraction, int position){
        Intent intent = new Intent(this, AttractionDetailsActivity.class);
        intent.putExtra(attractionExtra, attraction);
        startActivity(intent);
    }
    private void displayAttractionInFragment(AttractionListContent.Attraction attraction){
        AttractionDetailsFragment fragment = ((AttractionDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.attractionDetailsFragment));
        if(fragment != null)
            fragment.displayAttraction(attraction);
    }

    private void showDeleteDialog(){
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < AttractionListContent.ITEMS.size()){
            AttractionListContent.removeItem(currentItemPosition);
            saveAttractionsToSP();
            ((AttractionFragment) getSupportFragmentManager().findFragmentById(R.id.attractionFragment)).notifyDataChange();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        View v = findViewById(R.id.fabAdd);
        if(v != null){
            Snackbar.make(v, getString(R.string.delete_cancel_msg), Snackbar.LENGTH_LONG).setAction(getString(R.string.retry_msg), new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    showDeleteDialog();
                }
            });
        }
    }
}
