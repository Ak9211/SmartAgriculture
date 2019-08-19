package com.example.smartagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MobPassActivity extends AppCompatActivity {
    EditText Mobile,Pass;
    Button Next;

    String farmerName,farmerAadharNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_pass);

        Next=(Button)findViewById(R.id.submit);
        Mobile=(EditText)findViewById(R.id.editForMobile);
        Pass=(EditText)findViewById(R.id.editPassword);

        farmerName=getIntent().getStringExtra("farmerName");
        farmerAadharNo=getIntent().getStringExtra("farmerAadharNo");

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FarmerMobile = Mobile.getText().toString();
                String password = Pass.getText().toString();
                if (FarmerMobile.equals("")) {
                    Mobile.setError("Please enter the Former Mobile");
                } else if (password.equals("")) {
                    Pass.setError("Please choose password");
                } else {
                    Intent intent = new Intent(MobPassActivity.this,LocationActivity.class);
                    intent.putExtra("FarmerMobile",FarmerMobile);
                    intent.putExtra("password",password);
                    intent.putExtra("farmerName",farmerName);
                    intent.putExtra("farmerAadharNo",farmerAadharNo);
                    startActivity(intent);



                }
            }
        });
    }
}
