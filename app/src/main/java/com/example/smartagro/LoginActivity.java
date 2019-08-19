package com.example.smartagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    TextView textSignUp,forgotPassword;
    TextInputEditText mobile,pass;
    String mobileNo;
    String password;
    Button login,adminlogin;
    ImageView arr;
    TextView userText,adminText;
    ProgressBar pb;

   ArrayList<FarmerObject> arrayList;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    String ms;
    String ps;
    private ProgressDialog loadingBar;

    SharedPreferences SM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textSignUp=(TextView)findViewById(R.id.textSignUp);
        mobile=(TextInputEditText)findViewById(R.id.mobileNo);
        pass=(TextInputEditText)findViewById(R.id.password);
        userText=(TextView)findViewById(R.id.userPanel);
        adminText=(TextView)findViewById(R.id.adminPanel);
        arr=(ImageView)findViewById(R.id.arrow);
        pb=(ProgressBar)findViewById(R.id.pb);
        login=(Button)findViewById(R.id.submit);
        adminlogin=(Button)findViewById(R.id.adminBtn);
        forgotPassword=(TextView)findViewById(R.id.forgotPassword);



        arrayList=new ArrayList<FarmerObject>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("users");
        loadingBar=new ProgressDialog(this);
        loadingBar.setTitle("Please wait");
        loadingBar.setMessage("Please wait...... ");
        loadingBar.setCanceledOnTouchOutside(false);

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,FarmerNameActivity.class);

                startActivity(intent);

            }
        });
        SM = getSharedPreferences("userrecord", 0);
        Boolean islogin = SM.getBoolean("userlogin", false);
        if(islogin){
            Intent intent = new Intent(LoginActivity.this, FarmerNavigationViewDrawer.class);
            startActivity(intent);
            finish();
            return;
        }
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this, ForgotPassword.class);


                startActivity(intent);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ms=mobile.getText().toString();
                ps=pass.getText().toString();
                pb.setVisibility(View.VISIBLE);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

//                            try {
                            for(DataSnapshot snapshot:dataSnapshot.getChildren())
                            {

                                FarmerObject formerObject= snapshot.getValue(FarmerObject.class);
                                // arrayList.add(delailsObjects);
                                if(ms.equals("") ){
                                    mobile.setError("enter the  mobile no");
                                    pb.setVisibility(View.INVISIBLE);


                                }
                                else if(ps.equals("")){
                                    pass.setError("enter the valid password");
                                    pb.setVisibility(View.INVISIBLE);
                                }
                                else if(formerObject.getFarmerMobile().toString().equals(ms))
                                {
                                    if(formerObject.getPassword().equals(ps)){
                                        Toast.makeText(LoginActivity.this, "Login is successfull", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(LoginActivity.this, FarmerNavigationViewDrawer.class);

                                        intent.putExtra("stName",formerObject.getFarmerName().toString());
                                        intent.putExtra("stMobile",formerObject.getFarmerMobile().toString());
                                        intent.putExtra("stGmail",formerObject.getFarmerAadhar().toString());

                                        startActivity(intent);
                                        SharedPreferences.Editor edit = SM.edit();
                                        edit.putBoolean("userlogin", true);
                                        edit.commit();
                                        finish();
                                        pass.setText("");
                                        mobile.setText("");
                                        pb.setVisibility(View.INVISIBLE);
                                        break;
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "wrong password....", Toast.LENGTH_SHORT).show();
                                        pb.setVisibility(View.INVISIBLE);
                                    }
                                }
                                else{
                                   // Toast.makeText(LoginActivity.this, "please registration ...", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(View.INVISIBLE);
                                }
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
