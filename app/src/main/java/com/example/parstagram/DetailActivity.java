package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";
    TextView username;
    TextView time;
    ImageView imagePost;
    TextView description;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));

        username = findViewById(R.id.detailUsername);
        time = findViewById(R.id.detailTime);
        imagePost = findViewById(R.id.detailImage);
        description = findViewById(R.id.detailDescription);
        toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);


        description.setText(post.getDescription());
        username.setText(post.getUser().getUsername());
        time.setText(post.getTime());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(DetailActivity.this).load(image.getUrl()).into(imagePost);
        }

        //set toolbar composing and setting action
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.settingIcon){
                    Log.i(TAG, "profile has been clicked");
                    Intent i = new Intent(DetailActivity.this, SettingsActivity.class);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}