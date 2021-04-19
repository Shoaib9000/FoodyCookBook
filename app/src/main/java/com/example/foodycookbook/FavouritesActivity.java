package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity
{
    private List<String> namelist,instructlist;
    SQLiteManager sqLiteManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        namelist=new ArrayList<>();
        instructlist=new ArrayList<>();
        sqLiteManager=new SQLiteManager(this);
        ArrayList<Meal> arrayList=sqLiteManager.getAllMeals();
        for(Meal meal:arrayList)
        {
            namelist.add(meal.getName());
            instructlist.add(meal.getDescription());
        }
        ListView listView=(ListView)findViewById(R.id.mealslist);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(FavouritesActivity.this, android.R.layout.simple_list_item_1,namelist);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                DetailActivity.name=namelist.get(position);
                DetailActivity.instruct=instructlist.get(position);
                Intent intent=new Intent(FavouritesActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume()
    {
        super.onResume();
        namelist=new ArrayList<>();
        instructlist=new ArrayList<>();
        ArrayList<Meal> arrayList=sqLiteManager.getAllMeals();
        for(Meal meal:arrayList)
        {
            namelist.add(meal.getName());
            instructlist.add(meal.getDescription());
        }
        ListView listView=(ListView)findViewById(R.id.mealslist);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(FavouritesActivity.this, android.R.layout.simple_list_item_1,namelist);
        arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(arrayAdapter);
    }
}