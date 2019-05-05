package com.mr.hw2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mr.hw2.AttractionFragment.OnListFragmentInteractionListener;
import com.mr.hw2.attractions.AttractionListContent;

import java.util.List;

public class MyAttractionRecyclerViewAdapter extends RecyclerView.Adapter<MyAttractionRecyclerViewAdapter.ViewHolder> {

    private final List<AttractionListContent.Attraction> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAttractionRecyclerViewAdapter(List<AttractionListContent.Attraction> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_attraction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        AttractionListContent.Attraction attraction = mValues.get(position);
        holder.mItem = attraction;
        holder.mTitleView.setText(attraction.title);
        final String picPath = attraction.picPath;

        if(picPath !=null && !picPath.isEmpty()){
            Handler handler = new Handler();
            holder.mImageView.setVisibility(View.INVISIBLE);
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){

                    Bitmap cameraImage = PicUtils.decodePic(picPath, 200, 200);
                    if(cameraImage != null) holder.mImageView.setImageBitmap(cameraImage);
                    else holder.mImageView.setImageResource(holder.mView.getResources().getIdentifier(picPath, "drawable", holder.mView.getContext().getPackageName()));
                    holder.mImageView.setVisibility(View.VISIBLE);
                }
            }, 200);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentClickInteraction(holder.mItem, position);
                }
            }
        });

        holder.mView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View w){
               mListener.onDeleteButtonClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final ImageView mImageView;
        public AttractionListContent.Attraction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.attractionFragmentTitle);
            mImageView = (ImageView) view.findViewById(R.id.attractionFragmentImage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
