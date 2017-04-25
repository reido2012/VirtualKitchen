package com.example.oreid.virtualkitchen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeFinderActivity extends AppCompatActivity {
    private static final String API_KEY = "4d9e0ece3328370e056b669b9634c731";
    private String TAG = MainActivity.class.getSimpleName();
    private static String query;
    //URL to get recipes from - hard coding it for now.
    private static String searchURL;

    ArrayList<HashMap<String, String>> recipeList;
    ArrayList<String> queryStrings;

    private ProgressDialog pDialog;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);

        queryStrings = getIntent().getStringArrayListExtra("FOOD");
        query = formatQuery(queryStrings);
        System.out.println(searchURL);
        searchURL = "http://food2fork.com/api/search?key="+ API_KEY + "&q="+query;

        recipeList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.recipe_list);

        new GetRecipes().execute();

    }

    private String formatQuery(ArrayList<String> queryStrings){
        StringBuilder sb = new StringBuilder();
        for(String str: queryStrings){
            str = toURL(str, str.length());
            sb.append(str + "%20");
        }

        return sb.toString();
    }

    public String toURL(String s1, int length){
        StringBuilder sb = new StringBuilder();
        char[] letters = s1.toCharArray();
        for (int i = 0; i < length; i++) {

            if(letters[i] == ' '){
                sb.append("%20");
            }else{
                sb.append(letters[i]);
            }

        }

        return sb.toString();
    }

    public void goToURL(View view) {

        int position = lv.getPositionForView((View)view.getParent());
        //recipe
        HashMap<String, String>  hm = recipeList.get(position);
        String url = hm.get("url");
        System.out.println("URL: " + url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetRecipes extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RecipeFinderActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Request to url and getting response
            String jsonStr = sh.makeServiceCall(searchURL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray recipes = jsonObj.getJSONArray("recipes");

                    // looping through All recipes
                    for (int i = 0; i < recipes.length(); i++) {
                        JSONObject r = recipes.getJSONObject(i);

                        String publisher= r.getString("publisher");
                        String title = r.getString("title");
                        String url = r.getString("source_url");
                        String rating = r.getString("social_rank");
                        ;

                        // tmp hash map for single recipe
                        HashMap<String, String> recipe = new HashMap<>();

                        // adding each child node to HashMap key => value
                        recipe.put("publisher", publisher);
                        recipe.put("title", title);
                        recipe.put("url", url);
                        recipe.put("rating", rating);

                        // adding contact to contact list
                        recipeList.add(recipe);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    RecipeFinderActivity.this, recipeList,
                    R.layout.recipe_list_item, new String[]{"title", "publisher",
                    "rating"}, new int[]{R.id.recipe_title,
                    R.id.recipe_publisher, R.id.recipe_rating});

            lv.setAdapter(adapter);

        }

    }
}
