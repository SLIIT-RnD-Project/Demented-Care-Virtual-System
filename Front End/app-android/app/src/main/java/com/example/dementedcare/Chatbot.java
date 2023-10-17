package com.example.dementedcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Chatbot extends AppCompatActivity {

    private RecyclerView chatRV;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatModel> chatsModelArrayList;
    private ChatRVAdapter chatRVAdapter;

    private EditText userMsgEdt;
    private FloatingActionButton sendMsgFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        chatRV = findViewById(R.id.chatRV);
        userMsgEdt = findViewById(R.id.idEditMessage);
        sendMsgFAB = findViewById(R.id.idFABSend);

        chatsModelArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModelArrayList, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        chatRV.setLayoutManager(manager);
        chatRV.setAdapter(chatRVAdapter);

        sendMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = userMsgEdt.getText().toString().trim();
                if (message.isEmpty()) {
                    Toast.makeText(Chatbot.this, "Please enter your message", Toast.LENGTH_SHORT).show();
                } else {
                    getResponse(message);
                    userMsgEdt.setText("");
                }
            }
        });
    }


    private void getResponse(String message) {
        chatsModelArrayList.add(new ChatModel(message, USER_KEY));
        chatRVAdapter.notifyDataSetChanged();

        String baseUrl = "http://api.brainshop.ai";
        String bid = "176237";
        String key = "dFzMjo08SPUjw9zJ";
        String uid = "[uid]"; // Replace [uid] with the appropriate user ID if needed
        String messageParam = "";

        try {
            messageParam = URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = baseUrl + "/get?bid=" + bid + "&key=" + key + "&uid=" + uid + "&msg=" + messageParam;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MsgModel> call = retrofitAPI.getMessage(url);
        call.enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    MsgModel model = response.body();
                    chatsModelArrayList.add(new ChatModel(model.getCnt(), BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                chatsModelArrayList.add(new ChatModel("Hi, this is a chatbot test mode.", BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
            }
        });
    }
}