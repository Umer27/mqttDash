package com.example.mqttdash.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.ImageView;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.mqttdash.R;
import com.example.mqttdash.Model.ItemModel;
import com.example.mqttdash.MarginDecoration;
import com.example.mqttdash.Adapter.NumberedAdapter;
import com.example.mqttdash.utils.AppMqttClient;

import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private ImageView additem;
    private LinearLayout itemLayout;

    private NumberedAdapter adapter;
    private  AlertDialog.Builder builderSingle;

    ArrayList<ItemModel> list = new ArrayList<>();


    AppMqttClient appMqttClient;
    MqttAndroidClient client;

    final String serverUri = "ws://ec2-54-209-248-250.compute-1.amazonaws.com:3000/mqtt/";

    String clientId = "ExampleAndroidClient";
    final String subscriptionTopic = "exampleAndroidTopic";
    final String publishTopic = "exampleAndroidPublishTopic";
    final String publishMessage = "Hello World!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init_RecyclerView();

        itemLayout = (LinearLayout) findViewById(R.id.item);
        additem = (ImageView) findViewById(R.id.additem);


        clientId = clientId + System.currentTimeMillis();



    }

    private void init_RecyclerView(){


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_layout);
        recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("DialogBox");


        list.add(new ItemModel("camera","3 hours ago",0,"umer0"));
        list.add(new ItemModel("bulb ","3 hours ago",1,"umer1"));
        list.add(new ItemModel("ac ","3 hours ago",2,"umer2"));
        list.add(new ItemModel("fan ","3 hours ago",3,"umer3"));
        list.add(new ItemModel("fan ","3 hours ago",3,"umer4"));
        list.add(new ItemModel("ac ","3 hours ago",2,"umer5"));
        list.add(new ItemModel("fan ","3 hours ago",3,"umer6"));
        list.add(new ItemModel("bulb ","3 hours ago",1,"umer7"));
        list.add(new ItemModel("fan ","3 hours ago",3,"umer8"));


        adapter = new NumberedAdapter(list,this,builderSingle);
        recyclerView.setAdapter(adapter);


    }


}
