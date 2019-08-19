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

public class FarmerNameActivity extends AppCompatActivity {
    EditText FarName,Aadhar;
    Button Next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_name);

        Next=(Button)findViewById(R.id.submit);
        FarName=(EditText)findViewById(R.id.editForName);
        Aadhar=(EditText)findViewById(R.id.editAadhar);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Farname = FarName.getText().toString();
                String aadharNo = Aadhar.getText().toString();
                if (Farname.equals("")) {
                    FarName.setError("Please enter the Former Name");
                } else if (Aadhar.equals("")) {
                    Aadhar.setError("Please enter the Former Aadhar");
                } else {
                    Intent intent = new Intent(FarmerNameActivity.this,MobPassActivity.class);
                    intent.putExtra("farmerName",Farname);
                    intent.putExtra("farmerAadharNo",aadharNo);
                    startActivity(intent);

                }
            }
        });


    }
}
