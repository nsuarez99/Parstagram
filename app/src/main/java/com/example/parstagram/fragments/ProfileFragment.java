package com.example.parstagram.fragments;

import android.util.Log;

import com.example.parstagram.Post;
import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends TimelineFragment {

    @Override
    protected void queryPosts() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        // limit query to latest 20 items
        query.setLimit(NUMBER_POSTS);
        //only get posts by user
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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
                adapter.clear();
                adapter.addAll(objects);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
