package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Date;
import java.util.List;

public class ComposeActivity extends AppCompatActivity {

    public static final String TAG = "ComposeActivity";
    private EditText composeDescription;
    private Toolbar toolbar;
    private ImageView composeImage;
    private Button pictureButton;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        composeDescription = findViewById(R.id.description);
        composeImage = findViewById(R.id.image);
        pictureButton = findViewById(R.id.pictureButton);
        submitButton = findViewById(R.id.submitButton);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        queryPosts();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = composeDescription.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(ComposeActivity.this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    savePost(description, currentUser);
                }
            }
        });

    }

    private void savePost(String description, ParseUser currentUser) {
        Post post = new Post();
        post.setDescription(description);
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(ComposeActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.i(TAG, "Succesfully saved post");
                    composeDescription.setText("");
                }
            }
        });
    }

    private void queryPosts() {
        // Specify which class to query
        final ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Query issue", e);
                }
                else{
                    Log.i(TAG, "Query success");
                    for (Post post: objects) {
                        Log.i(TAG, "Post: " + post.getDescription() + "author: " + post.getUser().getUsername());
                    }
                }

            }
        });
    }
}