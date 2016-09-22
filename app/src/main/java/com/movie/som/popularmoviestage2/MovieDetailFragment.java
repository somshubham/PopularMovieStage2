package com.movie.som.popularmoviestage2;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.movie.som.popularmoviestage2.listviewHelper.Helper;
import com.movie.som.popularmoviestage2.reviewCustomClass.FetchMovieReviewData;
import com.movie.som.popularmoviestage2.reviewCustomClass.MovieReview;
import com.movie.som.popularmoviestage2.reviewCustomClass.ReviewAdapter;
import com.movie.som.popularmoviestage2.trailerCustomClass.FetchMovieTrailerData;
import com.movie.som.popularmoviestage2.trailerCustomClass.MovieTrailer;
import com.movie.som.popularmoviestage2.trailerCustomClass.TrailerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieDetailFragment extends Fragment {

public static TrailerAdapter adapter;
    public static ReviewAdapter adapterReview;


    private String data;


   public static   String [] urls;
    public static   String [] urls2;
   public static ListView listView;
    public static ListView listView2;
    String[] trailername;


    public static   ArrayList<MovieTrailer> trailersCount = new ArrayList<MovieTrailer>();
    public static   ArrayList<MovieReview> reviewCount = new ArrayList<MovieReview>();

    CollapsingToolbarLayout collapsingToolbarLayout;

    Toolbar toolbar;


    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);


        Intent intent = getActivity().getIntent();
        data = intent.getStringExtra("title");


        data = intent.getStringExtra("overview");
        TextView t=(TextView)rootView.findViewById(R.id.overview);
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

        updateMovieDetail(movieIdfetched);



        //  trailersCount.add(new MovieTrailer("trailer1"));
     //  reviewCount.add(new MovieReview("",""));


        ArrayList<MovieTrailer> arrayOfTrailer = trailersCount;
                 adapter=new TrailerAdapter(getActivity(),arrayOfTrailer);
        ArrayList<MovieReview> arrayOfReview =reviewCount;
                 adapterReview=new ReviewAdapter(getActivity(),arrayOfReview);
        //geting the expandable layout android library for adding the list views .......
        ExpandableHeightListView expandableListView = (ExpandableHeightListView)rootView.findViewById(R.id.expandable_listview);

         listView=(ListView)rootView.findViewById(R.id.listView);
        // listView2=(ListView)rootView.findViewById(R.id.listView2);


                    listView.setAdapter(adapter);
                    expandableListView.setAdapter(adapterReview);
        //to set the expand of the list view ........
                    expandableListView.setExpanded(true);
        //to disable the listview click
                    expandableListView.setEnabled(false);

        Helper.getListViewSize(listView);
        // Helper.getListViewSize(listView2);

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

    public static void updateList2() {

        //Helper.getListViewSize(listView2);

    }






    //my code.......
    private void updateMovieDetail(String movieId) {
        FetchMovieTrailerData fetchMovieTrailerData = new FetchMovieTrailerData();
        FetchMovieReviewData fetchMovieReviewData = new FetchMovieReviewData();
        fetchMovieTrailerData.execute(movieId);
        fetchMovieReviewData.execute(movieId);
    }




}
