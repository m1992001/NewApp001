package com.example.newapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView signup;
    private EditText input_username;
    private EditText input_userpsd;
    private String name1;
    private String psd1;

    OkHttpClient client = new OkHttpClient();
    LoginBean loginBean = new LoginBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginButton = findViewById(R.id.btn_login);
        signup = findViewById(R.id.link_signup);
        input_username = findViewById(R.id.input_username);
        input_userpsd = findViewById(R.id.input_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1 = input_username.getText().toString().trim();
                psd1 = input_userpsd.getText().toString().trim();

                postRequest(name1,psd1);

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    public void postRequest(String username, String password){
        loginBean.setUsername(username);
        loginBean.setPassword(password);
        String loginJson = new Gson().toJson(loginBean);
        Log.d("response",loginJson);

        RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"),loginJson);
        final Request request = new Request.Builder()
                .post(requestBody)
                .url( HttpUtil.IP + "/app/login")
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        String state = response.body().string();
                        Log.d("response",state);
                        try {
                            final JSONObject stateJson = new JSONObject(state);
                            final String reg_class = (String) stateJson.get("title");
                            if(stateJson.get("flag").equals("success")){
                                LoginActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("input_username",name1);
                                        intent.putExtra("reg_class",reg_class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }else{
                                LoginActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
