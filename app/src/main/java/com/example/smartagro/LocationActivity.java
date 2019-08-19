package com.example.smartagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class LocationActivity extends AppCompatActivity {

    Button Next;
    AutoCompleteTextView country,city,state;
    ImageView date;
    EditText txtDate;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String countryArr[]={"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};

    String cityArr[]={"jhansi","kanpur","varanasi","bhadohi","aurai","lucknow"};
    String StatesArr[]={"Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand,Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal","Andaman and Nicobar","Chandigarh","Dadra and Nagar Haveli","Daman and Diu","Lakshadweep","Delhi","Puducherry"};
    ArrayAdapter<String> countryAdt,cityApt,stateApt;
    private DatabaseReference userRef;
    private ProgressDialog loadingBar;

    String farmerName,farmerAadhar,farmerMobile,password,farmerCountry,farmerCity,farmerStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        country=(AutoCompleteTextView)findViewById(R.id.country);
        city=(AutoCompleteTextView)findViewById(R.id.city);
        state=(AutoCompleteTextView)findViewById(R.id.state);
        Next=(Button)findViewById(R.id.submit);
        autoCompleteAdapter();

        farmerName=getIntent().getStringExtra("farmerName");
        farmerAadhar=getIntent().getStringExtra("farmerAadharNo");
        farmerMobile=getIntent().getStringExtra("FarmerMobile");
        password=getIntent().getStringExtra("password");

        loadingBar=new ProgressDialog(LocationActivity.this);
        userRef= FirebaseDatabase.getInstance().getReference().child("users");


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateAllCtrls();
                Toast.makeText(LocationActivity.this,""+farmerAadhar+farmerName+farmerCountry+farmerCity,Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void autoCompleteAdapter()
    {

        countryAdt=new ArrayAdapter<String>(LocationActivity.this,R.layout.support_simple_spinner_dropdown_item,countryArr);
        country.setAdapter(countryAdt);

        cityApt=new ArrayAdapter<String>(LocationActivity.this,R.layout.support_simple_spinner_dropdown_item,cityArr);
        city.setAdapter(cityApt);

        stateApt=new ArrayAdapter<String>(LocationActivity.this,R.layout.support_simple_spinner_dropdown_item,StatesArr);
        state.setAdapter(stateApt);
    }

    public static String getRandomKey(int i)
    {
        final String characters="0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder stringBuilder=new StringBuilder();
        while (i>0)
        {
            Random ran=new Random();
            stringBuilder.append(characters.charAt(ran.nextInt(characters.length())));
            i--;
        }
        return stringBuilder.toString();
    }

    private void validateAllCtrls() {


        String  farmerCountry=country.getText().toString();
        String farmerCity=city.getText().toString();
        String farmerStates=state.getText().toString();


        if( farmerCountry.equals("")) {
            country.setError("select Country");
        }
        else if(farmerCity.equals(""))
        {
            city.setError("Please select city");
        }
        else if(farmerStates.equals(""))
        {
            state.setError("Please select state");
        }

        else
        {
            loadingBar.setTitle("Add new Former");
            loadingBar.setMessage("Please wait, while verifying your information..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            storeInputData();
        }

    }

    private void storeInputData() {


        String  farmerCountry=country.getText().toString();
        String farmerCity=city.getText().toString();
        String farmerStates=state.getText().toString();


        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        String saveDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        String saveTime=currentTime.format(calendar.getTime());



        HashMap<String,Object> Users= new HashMap<>();
        String userId=getRandomKey(6);
        Users.put("farmerId",userId);
        Users.put("farmerName",farmerName);
        Users.put("farmerAadhar",farmerAadhar);
        Users.put("farmerMobile",farmerMobile);
        Users.put("password",password);
        Users.put("farmerCountry",farmerCountry);
        Users.put("farmerCity",farmerCity);
        Users.put("farmerStates",farmerStates);
        Users.put("date",saveDate);
        Users.put("time",saveTime);





        userRef.child(farmerMobile).updateChildren(Users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    loadingBar.dismiss();
                    Toast.makeText(LocationActivity.this,"Registered Successfully...",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LocationActivity.this,LoginActivity.class);
                    startActivity(intent);

                    //finish();
                }
                else
                {
                    loadingBar.dismiss();
                    Toast.makeText(LocationActivity.this,"Somthing is wrong \n Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
