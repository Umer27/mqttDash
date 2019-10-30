package com.example.mqttdash.ViewHolder;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

import com.example.mqttdash.Model.ItemModel;
import com.example.mqttdash.R;
import com.example.mqttdash.utils.AppMqttClient;

import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class TextViewHolder extends RecyclerView.ViewHolder{

  public int value = 0;
  public String topic;
  Context context;
  public TextView timeView;
  public TextView titleView;
  public ImageView imageView;
  public LinearLayout itemLayout;
  long millisecondTime, startTime, bufTime, updateTime = 0L ;
  Handler handler;
  int Seconds, Minutes, MilliSeconds ;
  Runnable runnable;
  AppMqttClient appMqttClient;
  MqttAndroidClient mqttAndroidClient;

  final String serverUri = "ws://ec2-54-209-248-250.compute-1.amazonaws.com:3000/mqtt/";
  String clientId = "ExampleAndroidClient";

  public TextViewHolder(View itemView, final Context context) {
    super(itemView);

    timeView = (TextView) itemView.findViewById(R.id.timeView);
    itemLayout = (LinearLayout) itemView.findViewById(R.id.item);
    titleView = (TextView) itemView.findViewById(R.id.titleView);
    imageView = (ImageView) itemView.findViewById(R.id.imageView);

    this.context=context;
    handler = new Handler() ;

    runnable = new Runnable() {

      public void run() {

        millisecondTime = SystemClock.uptimeMillis() - startTime;
        updateTime = bufTime + millisecondTime;
        Seconds = (int) (updateTime / 1000);
        Minutes = Seconds / 60;
        Seconds = Seconds % 60;
        MilliSeconds = (int) (updateTime % 1000);
        timeView.setText("" + Minutes + ":"
                + String.format("%02d", Seconds) + ":"
                + String.format("%03d", MilliSeconds));
        handler.postDelayed(this, 0);        }

    };

    clientId = clientId + System.currentTimeMillis();

    appMqttClient = new AppMqttClient(context,serverUri,clientId);
    mqttAndroidClient = appMqttClient.getMqttClient();



  }


  public void setview(final ItemModel model, int position){
    this.topic = model.getTopic();
    if(model.getType()==0){
      titleView.setText(model.getDevice());
      imageView.setImageResource(R.drawable.ic_surveillance_camera1);
      imageView.setTag("0");
      timeView.setText(model.getTime());
    }
    else if(model.getType()==1){
      titleView.setText(model.getDevice());
      imageView.setImageResource(R.drawable.ic_bulb_off);
      imageView.setTag("1");
      timeView.setText(model.getTime());
    }
    else if(model.getType()==2){
      titleView.setText(model.getDevice());
      imageView.setImageResource(R.drawable.ic_air_conditioner_off);
      imageView.setTag("2");
      timeView.setText(model.getTime());
    }
    else if(model.getType()==3){
      titleView.setText(model.getDevice());
      imageView.setImageResource(R.drawable.ic_fan_off);
      imageView.setTag("3");
      timeView.setText(model.getTime());
    }

    mqttAndroidClient.setCallback(new MqttCallbackExtended() {
      @Override
      public void connectComplete(boolean reconnect, String s) {
        if(!reconnect) {
          try {
            appMqttClient.subscribe(mqttAndroidClient, model.getTopic(), 0);
          } catch (MqttException e) {
            e.printStackTrace();
          }
        }
      }
      @Override
      public void connectionLost(Throwable throwable) {
      }
      @Override
      public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        //change view
        action();

      }
      @Override
      public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
      }
    });


  }


  public void setupClick(View view, final TextViewHolder holder){


    imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        action();


      }
    });



  }


  public void action(){
    String tagId = imageView.getTag().toString();
    if(value==0) {
      switch (tagId){
        case "0":
          imageView.setImageResource(R.drawable.ic_surveillance_camera);
          imageView.setTag("5");
          break;
        case "1":
          imageView.setImageResource(R.drawable.ic_bulb);
          imageView.setTag("6");
          break;
        case "2":
          imageView.setImageResource(R.drawable.ic_air_conditioner);
          imageView.setTag("7");
          break;
        case "3":
          imageView.setImageResource(R.drawable.ic_fan);
          imageView.setTag("8");
          break;
      }
      value=1;
      try {
        appMqttClient.publishMessage(mqttAndroidClient,value+" "+topic,0,topic);
      } catch (MqttException e) {
        e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      startTime = SystemClock.uptimeMillis();
      handler.postDelayed(runnable, 0);
    }
    else {
      switch (tagId){
        case "5":
          imageView.setImageResource(R.drawable.ic_surveillance_camera1);
          imageView.setTag("0");
          break;
        case "6":
          imageView.setImageResource(R.drawable.ic_bulb_off);
          imageView.setTag("1");
          break;
        case "7":
          imageView.setImageResource(R.drawable.ic_air_conditioner_off);
          imageView.setTag("2");
          break;
        case "8":
          imageView.setImageResource(R.drawable.ic_fan_off);
          imageView.setTag("3");
          break;
      }
      value=0;

      try {
        appMqttClient.publishMessage(mqttAndroidClient,value+" "+topic,0,topic);
      } catch (MqttException e) {
        e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      millisecondTime = 0L ;
      startTime = 0L ;
      bufTime= 0L ;
      updateTime = 0L ;
      Seconds = 0 ;
      Minutes = 0 ;
      MilliSeconds = 0 ;
      handler.removeCallbacks(runnable);
      timeView.setText("00:00:00");
    }
  }





}