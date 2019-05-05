package com.mr.hw2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mr.hw2.attractions.AttractionListContent;


public class AddFragment extends Fragment {
    OnAddButtonClicked listener;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnAddButtonClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onAddButtonClicked");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_add, container, false);
        Button addButton = (Button) v.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentActivity activity = getActivity();
                TextView title = activity.findViewById(R.id.input_title);
                TextView localization = activity.findViewById(R.id.input_localization);
                TextView description = activity.findViewById(R.id.input_description);

                String titleString = title.getText().toString();
                String localizationString = localization.getText().toString();
                String descriptionString = description.getText().toString();

                if(!TextUtils.isEmpty(titleString) && !TextUtils.isEmpty(localizationString) && !TextUtils.isEmpty(descriptionString)){
                    listener.onAddButtonClicked(titleString,descriptionString, localizationString );
                }
                else{
                    if(TextUtils.isEmpty(titleString)) title.setError("Podaj tytuł!");
                    if(TextUtils.isEmpty(localizationString)) localization.setError("Podaj lokalizacje!");
                    if(TextUtils.isEmpty(descriptionString)) description.setError("Podaj opis!");
                }
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    // Container Activity must implement this interface
    public interface OnAddButtonClicked {
        public void onAddButtonClicked(String title, String description, String localization);
    }
}
