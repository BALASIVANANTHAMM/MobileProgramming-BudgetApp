package com.example.budgetplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.budgetplan.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class dashboard extends AppCompatActivity {
ActivityDashboardBinding binding;
FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;

int sumExpense =0;
int sumIncome =0;

ArrayList<transactionModel> transactionModelArrayList;
TAdapter tAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        transactionModelArrayList=new ArrayList<>();
        binding.viewList.setLayoutManager(new LinearLayoutManager(this));
        binding.viewList.setHasFixedSize(true);

        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(dashboard.this,MainActivity.class));
                    finish();
                }
            }
        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateLogOut();
            }

        });

        binding.addValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(dashboard.this,addtransaction.class));
                }catch (Exception e){

                }
            }
        });
        binding.refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(dashboard.this,dashboard.class));
                }catch (Exception e){

                }
            }
        });

        loadData();
    }

    private void CreateLogOut() {
        AlertDialog.Builder builder=new AlertDialog.Builder(dashboard.this);
        builder.setTitle("LogOut")
                .setMessage("Are You Want To Logout This?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseAuth.signOut();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.create().show();


    }

    private void loadData() {
        firebaseFirestore.collection("Expense").document(firebaseAuth.getUid()).collection("Note")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot ds:task.getResult()){
                    transactionModel model=new transactionModel(
                            ds.getString("id"),
                            ds.getString("note"),
                            ds.getString("amount"),
                            ds.getString("check"),
                            ds.getString("time"));
                    int amount = Integer.parseInt(ds.getString("amount"));
                    if (ds.getString("check").equals("Expense")){
                        sumExpense=sumExpense+amount;
                    }else {
                        sumIncome=sumIncome+amount;
                    }
                    transactionModelArrayList.add(model);
                }
                binding.incAmount.setText(String.valueOf(sumIncome));
                binding.expAmount.setText(String.valueOf(sumExpense));
                binding.totAmount.setText(String.valueOf(sumIncome-sumExpense));

                tAdapter=new TAdapter(dashboard.this,transactionModelArrayList);
                binding.viewList.setAdapter(tAdapter);

            }
        });
    }
}