package com.example.foodycookbook;

public class Meal
{
    private String name,description;
    Meal(String name,String description)
    {
        this.name=name;
        this.description=description;
    }
    String getName()
    {
        return name;
    }
    String getDescription()
    {
        return description;
    }
}
