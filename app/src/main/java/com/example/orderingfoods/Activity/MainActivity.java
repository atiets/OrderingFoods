package com.example.orderingfoods.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Data.DatabaseHandler;
import com.example.orderingfoods.R;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this);

    }

}