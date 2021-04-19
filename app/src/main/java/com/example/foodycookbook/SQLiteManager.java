package com.example.foodycookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper
{
    private static final String DATABASENAME="favouritesdb";
    private static final String TABLENAME="favourites";
    private static final int DATABASEVERSION=1;
    Context context;
    public SQLiteManager(Context context)
    {
        super(context, DATABASENAME, null, DATABASEVERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String createtable="CREATE TABLE IF NOT EXISTS "+TABLENAME+"(name text,description text)";
        sqLiteDatabase.execSQL(createtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        onCreate(sqLiteDatabase);
    }

    public void addfavourites(String name,String description)
    {
        name=name.replace("'","");
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("description",description);
        db.insert(TABLENAME,null,contentValues);
        db.close();
    }
    public void deletefavourites(String name)
    {
        name=name.replace("'","");
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from "+TABLENAME+" where name='"+name+"'");
        db.close();
    }

    public boolean MealExists(String name)
    {
        name=name.replace("'","");
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from favourites where name='"+name+"'",null);
        if(cursor.getCount()!=0)
            return true;
        return false;
    }

    public void droptable(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
    }

    public ArrayList<Meal> getAllMeals()
    {
        ArrayList<Meal> arrayList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from favourites",null);
        if(cursor.getCount()!=0)
        {
            if(cursor.moveToFirst())
            {
                do
                    {
                        Meal meal=new Meal(cursor.getString(0),cursor.getString(1));
                        arrayList.add(meal);
                    }while(cursor.moveToNext());
            }
        }
        return arrayList;
    }

}
