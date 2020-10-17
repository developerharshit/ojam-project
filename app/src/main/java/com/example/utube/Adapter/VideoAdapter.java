package com.example.utube.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utube.Modal.Video;
import com.example.utube.R;
import com.example.utube.Utils.YoutubeActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Video data;
    private Context context;
    private SharedPreferences preferences;

    public VideoAdapter(Context context, Video data) {
        this.data = data;
        this.context = context;
        preferences = context.getSharedPreferences("likeCount.app",Context.MODE_PRIVATE);
    }
    private void setLikeCount(String id,int count){
        preferences.edit().putInt(id,count).apply();
    }
    private int getLikeCount(String id){
        return preferences.getInt(id,0);
    }

    public void setPicassoImage(final ImageView iv, final String imgSrc) {
        if (true) {
            Picasso.with(context).load(imgSrc).placeholder(R.drawable.placeholder).fit().networkPolicy(NetworkPolicy.OFFLINE).into(iv, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(imgSrc).placeholder(R.drawable.placeholder).fit().into(iv);
                }
            });
        }
    }

    String setViews(String s){
        long v=Long.parseLong(s);

        if(v<1000) return String.valueOf(v);
        else if(v < 1000000) return String.valueOf(v/1000)+"K";
        else if(v < 1000000000) return String.valueOf(v/1000000)+"M";
        else return String.valueOf(v/1000000000)+"B";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_video,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        setPicassoImage(holder.thumbnail,data.getItems().get(position).getSnippet().getThumbnails().getMedium().getUrl());
        holder.videoName.setText(data.getItems().get(position).getSnippet().getTitle());
        String channelText = data.getItems().get(position).getSnippet().getChannelTitle()+" . "+setViews(data.getItems().get(position).getStatistics().getViewCount())+" views";
        holder.channelName.setText(channelText);

        int count = getLikeCount(data.getItems().get(position).getId());
        holder.likeCount.setText(String.valueOf(count));
        holder.likebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getLikeCount(data.getItems().get(position).getId());
                count++;
                setLikeCount(data.getItems().get(position).getId(),count);
                holder.likeCount.setText(String.valueOf(count));
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YoutubeActivity.class);
                intent.putExtra("videoId",data.getItems().get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.getItems().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail,like;
        TextView videoName,channelName,likeCount;
        LinearLayout likebox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            videoName = itemView.findViewById(R.id.videoName);
            channelName = itemView.findViewById(R.id.channelName);
            like = itemView.findViewById(R.id.like);
            likeCount = itemView.findViewById(R.id.numLikes);
            likebox = itemView.findViewById(R.id.likebox);
        }
    }
}
