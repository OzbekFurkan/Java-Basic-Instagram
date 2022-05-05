package com.zeruk.instagramclonejava.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zeruk.instagramclonejava.databinding.RecyclerRowBinding;
import com.zeruk.instagramclonejava.model.Post;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    ArrayList<Post> posts;

    public PostAdapter(ArrayList<Post> posts)
    {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostHolder(recyclerRowBinding);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.recyclerRowBinding.recyclerViewMailText.setText(posts.get(position).mail);
        holder.recyclerRowBinding.recyclerViewCommentText.setText(posts.get(position).comment);
        Picasso.get().load(posts.get(position).url).into(holder.recyclerRowBinding.recyclerViewImageView);
    }

    class PostHolder extends RecyclerView.ViewHolder
    {
        RecyclerRowBinding recyclerRowBinding;

        public PostHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}
