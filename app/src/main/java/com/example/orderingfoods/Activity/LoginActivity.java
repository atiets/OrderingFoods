package com.example.orderingfoods.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Models.User;
import com.example.orderingfoods.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText loginUsername, loginPassword;
    Button loginButton;
    ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.editTextUsername);
        loginPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.bt_login);

        // Nhận danh sách người dùng từ Intent
        Intent intent = getIntent();
        userList = (ArrayList<User>) intent.getSerializableExtra("userList");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {
                    // Do nothing if validation fails
                } else {
                    checkUser();
                }
            }
        });
    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username không được để trống");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password không được để trống");
            return false;
        } else if (val.length() < 6) {
            loginPassword.setError("Password phải có ít nhất 6 ký tự");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    private void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        boolean userFound = false;
        for (User user : userList) {
            if (user.getUsername().equals(userUsername)) {
                userFound = true;
                if (user.getPassword().equals(userPassword)) {
                    // Đăng nhập thành công
                    Intent intent = new Intent(LoginActivity.this, TableActivity.class);
                    intent.putExtra("username", user.getUsername());
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else {
                    // Mật khẩu sai
                    loginPassword.setError("Password sai");
                    loginPassword.requestFocus();
                }
                break;
            }
        }

        if (!userFound) {
            // Người dùng không tồn tại
            loginUsername.setError("Người dùng không tồn tại");
            loginUsername.requestFocus();
        }
    }
}
