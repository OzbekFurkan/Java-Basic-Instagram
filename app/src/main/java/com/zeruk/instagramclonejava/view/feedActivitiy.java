package com.zeruk.instagramclonejava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.zeruk.instagramclonejava.R;
import com.zeruk.instagramclonejava.adapter.PostAdapter;
import com.zeruk.instagramclonejava.databinding.ActivityFeedActivitiyBinding;
import com.zeruk.instagramclonejava.model.Post;

import java.util.ArrayList;
import java.util.Map;

public class feedActivitiy extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Post> posts;
    ActivityFeedActivitiyBinding binding;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedActivitiyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        posts = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        
        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(posts);
        binding.recyclerView.setAdapter(postAdapter);
    }

    public void getData()
    {
        CollectionReference collectionReference = firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {
                    Toast.makeText(feedActivitiy.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
                if(value!=null)
                {
                    for(DocumentSnapshot snapshot : value.getDocuments())
                    {
                        Map<String,Object> data = snapshot.getData();
                        String mail = (String) data.get("mail");
                        String comment = (String) data.get("comment");
                        String url = (String) data.get("url");
                        Post post = new Post(mail, comment, url);
                        posts.add(post);

                    }
                    postAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.addPost)
        {
            Intent intentToUpload = new Intent(feedActivitiy.this, uploadActivity.class);
            startActivity(intentToUpload);
        }
        else if(item.getItemId() == R.id.signout)
        {
            auth.signOut();
            Intent intentToMain = new Intent(feedActivitiy.this, MainActivity.class);
            startActivity(intentToMain);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}