package com.example.foodycookbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{
    Toolbar toolbar;
    Menu menu;
    private TextView textView;
    private List<String> namelist,instructlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Foody CookBook");
        setSupportActionBar(toolbar);
        namelist=new ArrayList<>();
        instructlist=new ArrayList<>();
        for(int i=0;i<10;i++)
            LoadMeals();
        ListView listView=(ListView)findViewById(R.id.mealslist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                DetailActivity.name=namelist.get(position);
                DetailActivity.instruct=instructlist.get(position);
                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }
    public void LoadMeals()
    {
        OkHttpClient client=new OkHttpClient();
        String url= "https://www.themealdb.com/api/json/v1/1/random.php";
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
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try
                            {
                                JSONObject obj = new JSONObject(myResponse);
                                JSONArray arr = obj.getJSONArray("meals");
                                String meal = arr.getJSONObject(0).getString("strMeal");
                                namelist.add(meal);
                                String instruct= arr.getJSONObject(0).getString("strInstructions");
                                instructlist.add(instruct);
                                ListView listView=(ListView)findViewById(R.id.mealslist);
                                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,namelist);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menuforhome, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.search)
        {
            Intent intent=new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);
            return true;
        }
        else
        if(id==R.id.favourite)
        {
            Intent intent=new Intent(MainActivity.this,FavouritesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}