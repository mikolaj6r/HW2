package com.mr.hw2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mr.hw2.attractions.AttractionListContent;


public class AttractionDetailsFragment extends Fragment {


    public AttractionDetailsFragment() {
        // Required empty public constructor
    }

    public static AttractionDetailsFragment newInstance(String param1, String param2) {
        AttractionDetailsFragment fragment = new AttractionDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attraction_details, container, false);
    }


    public void displayAttraction(AttractionListContent.Attraction attraction){
        FragmentActivity activity = getActivity();

        TextView attractionDescriptionTitle = activity.findViewById(R.id.attractionDetailsTitle);
        TextView attractionDetailsDescription = activity.findViewById(R.id.attractionDetailsDescription);
        TextView attractionDetailsLocalization = activity.findViewById(R.id.attractionDetailsLocalization);
        final ImageView attractionDetailsImage = activity.findViewById(R.id.attractionDetailsImage);

        attractionDescriptionTitle.setText(attraction.title);
        attractionDetailsDescription.setText(attraction.description);
        attractionDetailsLocalization.setText(attraction.localization);
        final String picPath = attraction.picPath;

        if(picPath !=null && !picPath.isEmpty()){
            Handler handler = new Handler();
            attractionDetailsImage.setVisibility(View.INVISIBLE);
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){

                    Bitmap cameraImage = PicUtils.decodePic(picPath, 200, 200);
                    if(cameraImage != null) attractionDetailsImage.setImageBitmap(cameraImage);
                    else attractionDetailsImage.setImageResource(getResources().getIdentifier(picPath, "drawable", getActivity().getPackageName()));
                    attractionDetailsImage.setVisibility(View.VISIBLE);
                }
            }, 200);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if(intent != null){
            AttractionListContent.Attraction receivedAttraction = ((Intent) intent).getParcelableExtra(MainActivity.attractionExtra);
            if(receivedAttraction != null){
                displayAttraction(receivedAttraction);
            }
        }
    }
}
