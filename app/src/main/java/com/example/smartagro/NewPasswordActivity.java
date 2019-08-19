package com.example.smartagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewPasswordActivity extends AppCompatActivity {
    TextInputEditText newPass,confirmNewPass;
    Button submit;
    ProgressBar pb;
    Query pendingTasks;

   // ArrayList<FarmerObject> arrayList;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    String newPassword;
    String ConfirmnewPassword,mobileNumber;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        newPass=(TextInputEditText)findViewById(R.id.newPassword);
        confirmNewPass=(TextInputEditText)findViewById(R.id.ConfirmNewpassword);
        submit=(Button)findViewById(R.id.submit);
        pb=(ProgressBar)findViewById(R.id.pb);

       // arrayList=new ArrayList<FarmerObject>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        mobileNumber=getIntent().getStringExtra("Mobile");

        databaseReference = firebaseDatabase.getReference("users");
        pendingTasks = databaseReference.child(mobileNumber).orderByChild("password");


        loadingBar=new ProgressDialog(this);
        loadingBar.setTitle("Please wait");
        loadingBar.setMessage("Please wait...... ");
        loadingBar.setCanceledOnTouchOutside(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword=newPass.getText().toString();
                ConfirmnewPassword=confirmNewPass.getText().toString();
                pb.setVisibility(View.VISIBLE);
                pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                                //FarmerObject formerObject= snapshot.getValue(FarmerObject.class);
                                // arrayList.add(delailsObjects);
                                 if(newPassword.equals(ConfirmnewPassword))
                                {
                                    dataSnapshot.getRef().child("password").setValue(ConfirmnewPassword);
                                    Toast.makeText(NewPasswordActivity.this, "Reset Password is successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(NewPasswordActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                    pb.setVisibility(View.INVISIBLE);
                                }
                                else{
                                    Toast.makeText(NewPasswordActivity.this, "someThing wrong...", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(View.INVISIBLE);
                                }
                            //adapterClass.notifyDataSetChanged();
                        }
                        //catch (Exception ex) {
//                                Toast.makeText(LoginActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
//                                pb.setVisibility(View.INVISIBLE);
//                            }
                        // }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
