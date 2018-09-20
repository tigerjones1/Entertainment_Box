package com.example.ahmadhasssan.entertainmentbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahmadhasssan.entertainmentbox.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ResultPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        String keyword = getIntent().getExtras().getString("movieTitle");
        keyword = keyword.replaceAll("\\s", "+");

        MovieDatabase db = new MovieDatabase(this);
        String jsonResult = db.search(keyword);

        if(jsonResult == null){
            jsonResult = runSearch(keyword);
            db.addRecord(keyword,jsonResult);
        }

        ArrayList<String> searchResults = getValuesFromJSON(jsonResult);
        if (searchResults.size() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No Results Found",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        final ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, searchResults
        );
        listView.setAdapter(arrayAdapter);
    }

    private String runSearch(String keyword) {
        String jsonResult = null;

        try {
            URL url = new URL("http://www.omdbapi.com/?apikey=d59770fd&s="+keyword);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream input = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(input, "UTF-8").useDelimiter("\\A");
            jsonResult = scanner.hasNext() ? scanner.next() : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    private ArrayList<String> getValuesFromJSON(String jsonResult){
        ArrayList<String> results = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONArray jsonArray = jsonObject.getJSONArray("Search");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject result = jsonArray.getJSONObject(i);
                StringBuilder sb = new StringBuilder();
                sb.append(result.getString("Title"));
                sb.append(" _ ");
                sb.append(result.getInt("Year"));
                results.add(sb.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    public void gotoHomeScreen(View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}

