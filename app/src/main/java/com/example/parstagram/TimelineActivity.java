package com.example.parstagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 20;
    public static final String TAG = "TimelineActivity";
    private static final int NUMBER_POSTS = 20;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<Post> posts;
    private PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        recyclerView = findViewById(R.id.recyclerViewPosts);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set toolbar composing action
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.composeIcon) {
                    Log.i(TAG, "composition has been clicked");
                    Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                    startActivityForResult(i, REQUEST_CODE);
                }
                return true;
            }
        });

        PostsAdapter.OnClickListener onClickListener = new PostsAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Post post = posts.get(position);
                Intent i = new Intent(TimelineActivity.this, DetailActivity.class);
                i.putExtra("post", Parcels.wrap(post));
                startActivity(i);
            }
        };

        //set adapter and recycler view
        posts = new ArrayList<>();
        adapter = new PostsAdapter(TimelineActivity.this, posts, onClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        queryPosts();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        queryPosts();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    private void queryPosts() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        // limit query to latest 20 items
        query.setLimit(NUMBER_POSTS);
        // order posts by creation date (newest first)
        query.addDescendingOrder(Post.KEY_TIME);
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, com.parse.ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                // save received posts to list and notify adapter of new data
                posts.addAll(objects);
                adapter.notifyDataSetChanged();
            }
            });
    }
}