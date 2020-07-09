package com.example.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private OnClickListener onClickListener;

    public interface OnClickListener{
        void onClick(int posititon);
    }

    public PostsAdapter(Context context, List<Post> posts, OnClickListener onClickListener) {
        this.context = context;
        this.posts = posts;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView time;
        ImageView imagePost;
        TextView description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.postUsername);
            time = itemView.findViewById(R.id.postTime);
            imagePost = itemView.findViewById(R.id.postImage);
            description = itemView.findViewById(R.id.postDescription);
        }

        public void bind(Post post) {
            description.setText(post.getDescription());
            username.setText(post.getUser().getUsername());
            time.setText(post.getTime());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(imagePost);
            }
        }
    }
}