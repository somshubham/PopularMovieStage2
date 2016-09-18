package com.movie.som.popularmoviestage2;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.som.popularmoviestage2.listviewHelper.Helper;
import com.movie.som.popularmoviestage2.trailerCustomClass.FetchMovieTrailerData;
import com.movie.som.popularmoviestage2.trailerCustomClass.MovieTrailer;
import com.movie.som.popularmoviestage2.trailerCustomClass.TrailerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieDetailFragment extends Fragment {

public static TrailerAdapter adapter;


    private String data;


   public static   String [] urls;
   public static ListView listView;
    String[] trailername;


    public static   ArrayList<MovieTrailer> trailersCount = new ArrayList<MovieTrailer>();




    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);


        Intent intent = getActivity().getIntent();










        data = intent.getStringExtra("title");
        TextView t=(TextView)rootView.findViewById(R.id.Title);
        t.setText(data);
        data = intent.getStringExtra("overview");
        t=(TextView)rootView.findViewById(R.id.overview);
        t.setText(data);
        data = intent.getStringExtra("vote_average");
        t=(TextView)rootView.findViewById(R.id.User_Rating);
        t.setText(data);
        data = intent.getStringExtra("release_date");
        t=(TextView)rootView.findViewById(R.id.Release_Date);
        t.setText(data);

        data = intent.getStringExtra("url");
        ImageView imageView=(ImageView)rootView.findViewById(R.id.movie_poster);
        Picasso.with(getActivity()).load(data).into(imageView);

       //my movie id ......   to fetch the data from the movie db .....
        data=intent.getStringExtra("id");
        String movieIdfetched=data;
        TextView t1=(TextView)rootView.findViewById(R.id.movieid);
        t1.setText(data);


        // making the call to the function .....

        updateMovie(movieIdfetched);



        //  trailersCount.add(new MovieTrailer("trailer1"));

        ArrayList<MovieTrailer> arrayOfTrailer = trailersCount;
        adapter=new TrailerAdapter(getActivity(),arrayOfTrailer);



         listView=(ListView)rootView.findViewById(R.id.listView);
         listView.setAdapter(adapter);
         Helper.getListViewSize(listView);
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
              //Toast.makeText(getActivity(), "hi this"+adapter.getItem(position), Toast.LENGTH_SHORT).show();


              int id=position;
                          try {
                                        String youtube=""+urls[id];
                                        Log.v("youtube",""+urls[id]);
                                         //start the youtube trailer on click @trailer
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtube)));
                                   }catch (ArrayIndexOutOfBoundsException aiob)
                                          {
                                             Toast.makeText(getActivity(), "Video not available", Toast.LENGTH_SHORT).show();
                                          }

          }
      });




        return rootView;






    }



    //update the list size for the total number of
    public static void updateList() {

        Helper.getListViewSize(listView);

    }






    //my code.......
    private void updateMovie(String movieId) {
        FetchMovieTrailerData movieTask = new FetchMovieTrailerData();
        movieTask.execute(movieId);
    }




}
