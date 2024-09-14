package com.example.orderingfoods.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Data.DatabaseHandler;
import com.example.orderingfoods.R;

public class LoginActivity extends AppCompatActivity {
    EditText loginUsername, loginPassword;
    Button loginButton;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.editTextUsername);
        loginPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.bt_login);

        databaseHandler = new DatabaseHandler(this);

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
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else if (val.length() < 6) { // Kiểm tra mật khẩu có ít nhất 6 ký tự
            loginPassword.setError("Password must be at least 6 characters long");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    private void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        // Lấy Cursor từ phương thức getUser trong DatabaseHandler
        Cursor cursor = databaseHandler.getUser(userUsername);

        if (cursor != null && cursor.moveToFirst()) {
            // Lấy các chỉ số của cột từ Cursor
            int usernameIndex = cursor.getColumnIndex(DatabaseHandler.KEY_TABLE_USERNAME_USER);
            int passwordIndex = cursor.getColumnIndex(DatabaseHandler.KEY_TABLE_PASSWORD_USER);

            // Kiểm tra nếu các chỉ số cột hợp lệ
            if (usernameIndex != -1 && passwordIndex != -1) {
                String usernameFromDB = cursor.getString(usernameIndex);
                String passwordFromDB = cursor.getString(passwordIndex);

                // So sánh mật khẩu từ cơ sở dữ liệu với mật khẩu người dùng nhập
                if (passwordFromDB.equals(userPassword)) {
                    // Đăng nhập thành công, chuyển sang HomeActivity
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    intent.putExtra("username", usernameFromDB);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish(); // Kết thúc LoginActivity sau khi chuyển sang MainActivity
                } else {
                    // Mật khẩu sai
                    loginPassword.setError("Password sai");
                    loginPassword.requestFocus();
                }
            } else {
                // Nếu không tìm thấy cột mong muốn trong Cursor
                loginUsername.setError("Lỗi đọc dữ liệu người dùng");
            }
            cursor.close(); // Đóng Cursor để tránh rò rỉ tài nguyên
        } else {
            // Người dùng không tồn tại trong cơ sở dữ liệu
            loginUsername.setError("Người dùng không tồn tại");
            loginUsername.requestFocus();
        }
    }
}