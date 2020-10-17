package com.example.utube.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.utube.Adapter.VideoAdapter;
import com.example.utube.Modal.Video;
import com.example.utube.R;
import com.example.utube.Utils.Api;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Content extends Fragment {

    private RecyclerView rv;
    private Api api;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Video video;
    private String id;
    private static final String AUTH_KEY="AIzaSyDMgPSoiy-6UHxd3fs1zKpmIjvWi2r7rXA";

    public static Content newInstance(String categoryId){
        Content fragment = new Content();
        Bundle bundle = new Bundle();
        bundle.putString("id",categoryId);
        fragment.setArguments(bundle);
        fragment.pass(categoryId);
        return fragment;
    }

    private void pass(String id) {
//        Log.d("hello",id);
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_content, container, false);
        rv = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipe);

        progressDialog = new ProgressDialog(getContext());

        final String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2Cstatistics&chart=mostPopular&regionCode=IN&maxResults=20&key="+AUTH_KEY+"&videoCategoryId="+id;

        setRetrofit();
        fetchVideoByTag(url);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(video!=null){
                    String token=video.getNextPageToken();
                    if(token!=null){
                        fetchVideoByTag(url+"&pageToken="+token);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        return view;
    }

    public void setRecyclerView(Video data) {
        if(data!=null){
            VideoAdapter videoAdapter = new VideoAdapter(getActivity(), data);
            videoAdapter.notifyDataSetChanged();
            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            rv.setLayoutManager(lm);
            rv.setHasFixedSize(true);
            rv.setAdapter(videoAdapter);
        }
    }

    private void setRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private void fetchVideoByTag(String url) {
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        Call<Video> call = api.getVideoList(url);

        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                progressDialog.dismiss();
                video = response.body();
                setRecyclerView(video);
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}