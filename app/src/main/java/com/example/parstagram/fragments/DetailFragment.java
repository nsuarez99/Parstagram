package com.example.parstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parstagram.Post;
import com.example.parstagram.R;
import com.parse.ParseFile;

public class DetailFragment extends Fragment {

    public static final String TAG = "DetailFragment";
    TextView username;
    TextView time;
    ImageView imagePost;
    TextView description;
    Toolbar toolbar;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Post post = getArguments().getParcelable("post");

        username = view.findViewById(R.id.detailUsername);
        time = view.findViewById(R.id.detailTime);
        imagePost = view.findViewById(R.id.detailImage);
        description = view.findViewById(R.id.detailDescription);

        description.setText(post.getDescription());
        username.setText(post.getUser().getUsername());
        time.setText(post.getTime());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(getContext()).load(image.getUrl()).into(imagePost);
        }

    }
}