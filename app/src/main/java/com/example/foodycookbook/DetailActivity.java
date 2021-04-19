package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity
{
    public static String name="",instruct="";
    private Switch addswitch;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView textView=(TextView)findViewById(R.id.detailtextView);
        String display="Meal name: "+name+"\nMeal instructions: "+instruct;
        textView.setText(display);
        textView.setMovementMethod(new ScrollingMovementMethod());
        addswitch=(Switch)findViewById(R.id.addswitch);
        SQLiteManager sqLiteManager=new SQLiteManager(this);
        if(sqLiteManager.MealExists(name))
            addswitch.setChecked(true);
        addswitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                 if(addswitch.isChecked())
                     sqLiteManager.addfavourites(name,instruct);
                 else
                     sqLiteManager.deletefavourites(name);
            }
        });
    }
}