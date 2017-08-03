package za.co.reversidesoftwaresolutions.property24;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class ResetPasswordActivity extends AppCompatActivity {

    //Declare objects

    private EditText m_email;
    private Button btn_reset_password;
    private TextView btn_login;
    private ProgressBar mProgressBar;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        //Action bar for the back/home buttom
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Find the Id of the edit text
        m_email = (EditText)findViewById(R.id.m_email);


        //Find the Id of the reset button
        btn_reset_password = (Button)findViewById(R.id.btn_reset_password);


        //Find the Id of the login text view
        btn_login = (TextView)findViewById(R.id.btn_login);


        //Find the Id of the progress bar
        mProgressBar = (ProgressBar)findViewById(R.id.mProgressBar);


        //Get firebase instance uathentication
        mFirebaseAuth = FirebaseAuth.getInstance();


        //Navigate to login activity when a login button is clicked
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });


        //Send an email to a specified email account if a reset password button is clicked

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = m_email.getText().toString().trim();

                //Check to see whether an email is entered into the email field
                if (TextUtils.isEmpty(email)){


                    //Notify the user that the email field is empty
                    Toast.makeText(getApplicationContext(), "Please enter email in the email field to reset a password", Toast.LENGTH_SHORT).show();
                    return;

                }


                //Set the visibility of the progress bar
                mProgressBar.setVisibility(View.VISIBLE);
                mFirebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPasswordActivity.this, "Password reset instructions sent", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this, "Failed to send password reset email!", Toast.LENGTH_SHORT).show();
                                }

                                mProgressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }
}
