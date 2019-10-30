package com.example.mqttdash.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mqttdash.R;
import com.example.mqttdash.Model.ItemModel;
import com.example.mqttdash.ViewHolder.TextViewHolder;


public class NumberedAdapter extends RecyclerView.Adapter<TextViewHolder> {
  private Context context;
  private  List<ItemModel> itemModelList;
  private AlertDialog.Builder box;

  public NumberedAdapter(List<ItemModel> itemModelList, Context context, AlertDialog.Builder box) {
  this.itemModelList =itemModelList;
  this.context = context;
  this.box = box;
  }

  public void updateAdapter(List<ItemModel> itemModelList){
    this.itemModelList = itemModelList;
    notifyDataSetChanged();
  }

  @Override
  public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
    TextViewHolder holder = new TextViewHolder(view,context);

    if (holder != null) {
      holder.setupClick(view,holder);
    }

    return holder;
  }

  @Override
  public void onBindViewHolder(final TextViewHolder holder, final int position) {

    holder.setview(itemModelList.get(position), position);


//    holder.itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
//      @Override
//      public boolean onLongClick(View view) {
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
//        arrayAdapter.add("Edit");
//        arrayAdapter.add("Delete");
//        box.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//          @Override
//          public void onClick(DialogInterface dialog, int which) {
//         switch (which){
//           case 0:
//             break;
//           case 1:
//             itemModelList.remove(position);
//             notifyDataSetChanged();
//         }
//          }
//        });
//        box.show();
//        return false;
//      }
//    });

  }

  @Override
  public int getItemCount() {
    return itemModelList.size();
  }



}