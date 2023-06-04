package com.example.budgetplan;

import static com.example.budgetplan.R.color.green;
import static com.example.budgetplan.R.color.red;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TAdapter extends RecyclerView.Adapter<TAdapter.MyView>{
    Context context;
    ArrayList<transactionModel> transactionModelArrayList;

    public TAdapter(Context context, ArrayList<transactionModel> transactionModelArrayList) {
        this.context = context;
        this.transactionModelArrayList = transactionModelArrayList;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.one_recycler_item,parent,false);
        return new MyView(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TAdapter.MyView holder, @SuppressLint("RecyclerView") int position) {
         transactionModel model=transactionModelArrayList.get(position);
         String view = model.getCheck();
         if (view.equals("Expense")){
             holder.view.setBackgroundResource(red);
         }else{
             holder.view.setBackgroundResource(green);
         }
         holder.amount.setText(model.getAmount());
         holder.time.setText(model.getTime());
         holder.note.setText(model.getNote());

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(context,updation.class);
                 intent.putExtra("id",transactionModelArrayList.get(position).getId());
                 intent.putExtra("amount",transactionModelArrayList.get(position).getAmount());
                 intent.putExtra("note",transactionModelArrayList.get(position).getNote());
                 intent.putExtra("check",transactionModelArrayList.get(position).getCheck());
                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {

        return transactionModelArrayList.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
TextView note,amount,time;
View view;
        public MyView(@NonNull View itemView) {

            super(itemView);
            note=itemView.findViewById(R.id.note_one);
            amount=itemView.findViewById(R.id.amount_one);
            time=itemView.findViewById(R.id.time);
            view=itemView.findViewById(R.id.view_prior);

        }
    }
}
