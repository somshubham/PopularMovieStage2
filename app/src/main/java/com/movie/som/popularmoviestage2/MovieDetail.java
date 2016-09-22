package com.movie.som.popularmoviestage2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.movie.som.popularmoviestage2.databaseHelper.DatabaseHandler;
import com.movie.som.popularmoviestage2.databaseHelper.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetail extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    private String data;
    public  String movieid;
    private String title;
    private String overview;
    private String vote;
    private String releasedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Stetho.initializeWithDefaults(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar = (Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        Intent intent = getIntent();
        data=intent.getStringExtra("title");
        collapsingToolbarLayout.setTitle(data);
        title=data;
        data = intent.getStringExtra("overview");
        overview=data;
        data = intent.getStringExtra("vote_average");
        vote=data;
        data = intent.getStringExtra("release_date");
        releasedate=data;
        data=intent.getStringExtra("id");
        movieid=data;

        Log.v("Title",""+data);
        //t.setText(data);
        data=intent.getStringExtra("url2");
        ImageView imageView2=(ImageView)findViewById(R.id.backdrop_image_view);
        Log.v("backdroup_image",""+data);
        Picasso.with(this).load(data).into(imageView2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.favorite_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(MovieDetail.this, "ok ", Toast.LENGTH_SHORT).show();

                //adding the fab movies to the database.......

                DatabaseHandler db = new DatabaseHandler(MovieDetail.this);

                // Inserting Contacts
                Log.d("Insert: ", "Inserting ..");
                db.addMovie(new Movie(movieid,title,overview,vote,releasedate));
                // db.addContact(new Contact("Ravi", "9100000000"));
                //db.addContact(new Contact("Srinivas", "9199999999"));
                // db.addContact(new Contact("Tommy", "9522222222"));
                // db.addContact(new Contact("Karthik", "9533333333"));

                // Reading all contacts
                Log.d("Reading: ", "Reading all contacts..");
                List<Movie> movies = db.getAllMovies();

                for (Movie mv : movies) {
                    String log = "Id: "+mv.getId()+"\n   Movie ID: " + mv.getId()+",\n   Title : "+mv.getTitle()+"\n   overview : "+mv.getOverview()+"\n   vote average : "+mv.getVote_average()+"\n   Release Date : "+mv.getOverview();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }






















            }
        });



    }



    public void video(View view){

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=cxLG2wtE7TM")));



    }

}
