package com.movie.som.popularmoviestage2;


import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.movie.som.popularmoviestage2.listviewHelper.Helper;
import com.movie.som.popularmoviestage2.trailerCustomClass.MovieTrailer;
import com.movie.som.popularmoviestage2.trailerCustomClass.TrailerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MovieDetailFragment extends Fragment {

private TrailerAdapter adapter;

   // private ArrayAdapter<String> adapter;
    private String data;
   //  private String data;
    String youtubeLink[]=new String[]{"x"};

    String [] urls;
    ListView listView;
    String[] st;

    MovieTrailer[] movieTrailers = {
            new MovieTrailer("sdsaa")


    };

    ArrayList<MovieTrailer> trailersCount = new ArrayList<MovieTrailer>();




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

              String youtube=""+urls[id];
              Log.v("youtube",""+urls[id]);
              //start the youtube trailer on click @trailer
              startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtube)));
          }
      });




        return rootView;






    }


//#####################################################
    //update the list size for the total number of
    private void updateList() {

        Helper.getListViewSize(listView);

    }

    //##########################################






    //my code.......
    private void updateMovie(String movieId) {
        FetchMovieData movieTask = new FetchMovieData();
        movieTask.execute(movieId);
    }


    //fetching the key for the youtube trailer.........


    public class FetchMovieData extends AsyncTask<String , Void,String[]> {

        private final String LOG_TAG = FetchMovieData.class.getSimpleName();

        String k;


        private String[] getMovieDataFromJson(String MovieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "results";
            final String id = "key";
            final String name = "name";


            JSONObject MovieJson = new JSONObject(MovieJsonStr);
            JSONArray MovieArray = MovieJson.getJSONArray(OWM_LIST);
            int k=MovieArray.length();

            String[] resultStrs=new String[k] ;
            urls=new String[k];

            int count=0;
            for(int i = 0; i < MovieArray.length(); i++) {


                // Get the JSON object representing the movie
                JSONObject moviearraydata = MovieArray.getJSONObject(i);

                // int idvalue = moviearraydata.getInt(id);
                String idvalue = moviearraydata.getString(id);


                //resultStrs[i] ="http://image.tmdb.org/t/p/w185"+idvalue;
                urls[i]="http://www.youtube.com/watch?v=" +idvalue;


            }


            for (String s : urls) {
                Log.v(LOG_TAG, "Movie id: " + s);

//mForecastAdapter.add(s);


            }
            return urls;

        }

        @Override
        protected String[] doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }



            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String MovieJsonStr = null;

            String format = "json";




            try {
//String s="http://www.youtube.com/watch?v="+adapter.getItem(position);
                //           Log.v("youtube",""+s);
                //         startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s)));
//http://api.themoviedb.org/3/movie/271110/videos?api_key=e822607761aa05893b4c213f5b8df335

                String s=""+params[0];

                //271110
                //  String m1="http://api.themoviedb.org/3/movie/"+s+"/videos?api_key=";
                final String Movie_BASE_URL2 =
                        "http://api.themoviedb.org/3/movie/"+s+"/videos?api_key="+ BuildConfig.Movie_db_key;




                URL url = new URL(Movie_BASE_URL2);

                Log.v(LOG_TAG, "Built URI " + Movie_BASE_URL2);

                // Create the request to themoviedb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                MovieJsonStr = buffer.toString();



                Log.v("Movie",""+MovieJsonStr);
                Log.v(LOG_TAG, "Movie string: " + MovieJsonStr);


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(MovieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }




            return null;
        }






        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        protected void onPostExecute(String[] strings) {

            if (strings != null) {
                //adding the number to  add the number of trailers for the movies ........
                   int i=1;
               //    adapter.clear();
              //  ArrayList<MovieTrailer> newUsers = new ArrayList<MovieTrailer>();
            for (String s : strings) {
                Log.v("mylogfile", "trailer id: " + s);
                trailersCount.add(new MovieTrailer("Trailer"+i));
               // adapter.add("Trailer "+i);
                i++;

            }


//update the list size for the total number of the trailers .....................
      updateList();

            }

        }




    }










    //my code.......






}
