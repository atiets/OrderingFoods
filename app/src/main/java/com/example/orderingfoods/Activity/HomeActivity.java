package com.example.orderingfoods.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.Models.User;
import com.example.orderingfoods.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    Button loginButton;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loginButton = findViewById(R.id.bt_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.putExtra("userList", userList); // truyền arrayList qua Intent
                startActivity(intent);
            }
        });

        // Tạo danh sách các user
        userList = new ArrayList<>();
        userList.add(new User(1, "Minh", "minh123"));
        userList.add(new User(2, "Lan", "lan123"));
        userList.add(new User(3, "Huy", "huy123"));

    }
}
