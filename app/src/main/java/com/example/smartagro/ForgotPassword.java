package com.example.smartagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {
    TextInputEditText mobile,otp;
    Button get_otp,submit_otp;
    ImageView arr;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    String mVerificationId,OTP;
    ProgressDialog loadingBar;
    ProgressBar pb,pb1;
    TextView t,signIn;
    public final boolean sr=false;
    RelativeLayout relativeLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mobile=(TextInputEditText)findViewById(R.id.mobileNo);


        FirebaseApp.initializeApp(this);
        otp=(TextInputEditText)findViewById(R.id.OTP);
        get_otp=(Button)findViewById(R.id.submit);
        submit_otp=(Button)findViewById(R.id.submitOTP);
        pb=(ProgressBar)findViewById(R.id.pb);
        arr=(ImageView)findViewById(R.id.arrow);
        pb1=(ProgressBar)findViewById(R.id.pb1);
        relativeLayout2=(RelativeLayout)findViewById(R.id.relativeLayout2);
      //  t=(TextView)findViewById(R.id.t);
        loadingBar=new ProgressDialog(this);
        loadingBar.setTitle("Please wait");
        loadingBar.setMessage("Please wait...... ");
        loadingBar.setCanceledOnTouchOutside(false);

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                userIsLoggedIn();
                //Toast.makeText(SignUpActivity.this,"Sparta"+mVerificationId+"\n"+etMobile.getText(),Toast.LENGTH_LONG).show();

                pb.setVisibility(View.VISIBLE);

                final DatabaseReference userREf;
                userREf = FirebaseDatabase.getInstance().getReference().child("users");
                userREf.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(mobile.getText().toString()).exists())
                        {
                            startPhoneNumberVerification();
                        }
                        else
                        {

                            pb.setVisibility(View.INVISIBLE);
                            Toast.makeText(ForgotPassword.this,"Mobile Number not registerd.. ", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        submit_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OTP=otp.getText().toString();
                verifyCodeWithPhoneNumber();
                if(mVerificationId != null)
                {

                    pb1.setVisibility(View.VISIBLE);

//                   Intent intent=new Intent(MobileOTP.this,RegistrationFormActivity.class);
//                  intent.putExtra("mobile",mobile.getText().toString());
//                  startActivity(intent);

                }
            }
        });
        mCallBacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                String code=phoneAuthCredential.getSmsCode();
                Toast.makeText(ForgotPassword.this, "Verification Completed", Toast.LENGTH_SHORT).show();



//                if(code !=null )
//                {
//                    submit_otp.setText(code);
//                }
//                else
                //singUpWithCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(ForgotPassword.this, "Verification Fialed", Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);


                mVerificationId=verificationId;
                get_otp.setVisibility(View.INVISIBLE);
                //t.setVisibility(View.INVISIBLE);
               // signIn.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.INVISIBLE);
                loadingBar.dismiss();
                mobile.setEnabled(false);
                relativeLayout2.setVisibility(View.VISIBLE);
                //  btnSignUp.setText("Verify OTP");
            }
        };
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(ForgotPassword.this,"exit from Next page",Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void singUpWithCredential(PhoneAuthCredential phoneAuthCredential) {

        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //userIsLoggedIn();
                    FirebaseUser currentuser=FirebaseAuth.getInstance().getCurrentUser();
                    if(currentuser != null)
                    {

//
                        //intent.putExtra("userMobile",etMobile.getText().toString());
                        pb1.setVisibility(View.INVISIBLE);
                        Intent intent=new Intent(getApplicationContext(),NewPasswordActivity.class);
                         intent.putExtra("Mobile",mobile.getText().toString());
                        startActivity(intent);
                        finish();
                        return;
                    }
                    //  Toast.makeText(MobileOTP.this,"OTP is TRUE",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(ForgotPassword.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
                    pb1.setVisibility(View.INVISIBLE);


//                    String message = "Somthing is wrong, we will fix it soon...";
//
//                    pb1.setVisibility(View.INVISIBLE);
//                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                        message = "Invalid code entered...";
//
//                    }
//
//                    final Snackbar snackbar = Snackbar.make(findViewById(R.id.relativeLayout2), message, Snackbar.LENGTH_LONG);
//                    snackbar.setActionTextColor(Color.WHITE);
//                    snackbar.setAction("Dismiss", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            snackbar.dismiss();
//                        }
//                    });
//                    snackbar.show();
                }
            }
        });
    }
    private void startPhoneNumberVerification() {
        String s=mobile.getText().toString();
        if(isValid(s)){
            PhoneAuthProvider.getInstance().verifyPhoneNumber(

                    "+91"+s,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    mCallBacks);
            //relativeLayout2.setVisibility();
            mobile.setEnabled(false);
        }
        else {
            mobile.setError("please enter valid Mobile Number");
            pb.setVisibility(View.INVISIBLE);
        }
    }

    private void verifyCodeWithPhoneNumber()
    {
        if(OTP.equals("")){
            Toast.makeText(ForgotPassword.this, "please enter the OPT", Toast.LENGTH_SHORT).show();
            pb1.setVisibility(View.INVISIBLE);
        }
        else {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,OTP);
            singUpWithCredential(credential);
            // Toast.makeText(MobileOTP.this, ""+mVerificationId, Toast.LENGTH_SHORT).show();
        }
        // startActivity(new Intent(getApplicationContext(),RegistrationFormActivity.class));
    }
    public boolean isValid(String s)
    {

        Pattern p;
        p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
}
