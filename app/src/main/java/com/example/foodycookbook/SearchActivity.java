package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity
{
    Button searchbutton;
    EditText searchtext;
    private List<String> namelist,instructlist;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchbutton=(Button)findViewById(R.id.searchbutton);
        searchtext=(EditText)findViewById(R.id.searchtext);
        namelist=new ArrayList<>();
        instructlist=new ArrayList<>();
        searchbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(SearchActivity.this,"Hello",Toast.LENGTH_LONG).show();
                LoadMeals();
                ListView listView=(ListView)findViewById(R.id.mealslist);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        DetailActivity.name=namelist.get(position);
                        DetailActivity.instruct=instructlist.get(position);
                        Intent intent=new Intent(SearchActivity.this, DetailActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
    public void LoadMeals()
    {
        OkHttpClient client=new OkHttpClient();
        String url="https://www.themealdb.com/api/json/v1/1/search.php?s="+searchtext.getText().toString();
        Log.d("url: ",url);
        Log.d("entered","here");
        Request request=new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String myResponse=response.body().string();
                    SearchActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try
                            {
                                JSONObject obj = new JSONObject(myResponse);
                                JSONArray arr = obj.getJSONArray("meals");
                                namelist=new ArrayList<>();
                                instructlist=new ArrayList<>();
                                Log.d("entered","entered");
                                for(int i=0;i<arr.length();i++)
                                {
                                    String meal = arr.getJSONObject(i).getString("strMeal");
                                    Log.d("meal: ",meal);
                                    namelist.add(meal);
                                    String instruct= arr.getJSONObject(i).getString("strInstructions");
                                    instructlist.add(instruct);
                                }
                                ListView listView=(ListView)findViewById(R.id.mealslist);
                                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1,namelist);
                                arrayAdapter.notifyDataSetChanged();
                                listView.setAdapter(arrayAdapter);

                            }
                            catch(Exception e)
                            {
                                //textView.setText(textView.getText().toString()+" ++++++++++++++ "+e.toString());
                            }
                        }
                    });
                }
            }
        });
    }
}