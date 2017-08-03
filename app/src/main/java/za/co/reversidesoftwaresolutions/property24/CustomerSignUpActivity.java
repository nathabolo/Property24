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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerSignUpActivity extends AppCompatActivity {

    //Declare objects

    private EditText m_name, m_phone, m_password, m_email;
    private Button btn_register;
    private TextView btn_login;
    private ProgressBar mProgressBar;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);

        //Action bar for the back/home buttom

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Get the Id's of the Edit texts field

        m_name = (EditText) findViewById(R.id.name);
        m_phone = (EditText) findViewById(R.id.contact);
        m_password = (EditText) findViewById(R.id.password);
        m_email = (EditText) findViewById(R.id.email);

        //Get the id of the text view

        btn_login = (TextView) findViewById(R.id.btn_login);


        //Get the id of the buttons

        btn_register = (Button) findViewById(R.id.btn_register);


        //Get the id of the progress bar

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);


        //Get firebase auth instance

        mFirebaseAuth = FirebaseAuth.getInstance();

        //Set the onclick listener to the login button

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Clicking this button takes you back to the company login activity

                Intent intentLogin = new Intent(CustomerSignUpActivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });

        //Register users when the register button is clicked

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = m_name.getText().toString().trim();
                String phone = m_phone.getText().toString().trim();
                String password = m_password.getText().toString().trim();
                String email = m_email.getText().toString().trim();

                //Check to see whether all required fields are not empty

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Customer name field cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Customer phone field cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Customer password field cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Customer email field cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Validate the phone and password so that they do not contain less than six characters

                if (phone.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Phone number field cannot contain less than 10 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password field cannot contain less than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Validate the email address and passwords so that they are not null

                if (email == null) {
                    Toast.makeText(getApplicationContext(), "Email field cannot be null", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password == null) {
                    Toast.makeText(getApplicationContext(), "Password field cannot be null", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                //Create company account
                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CustomerSignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(CustomerSignUpActivity.this, "A new customer account created" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.VISIBLE);
                                // If registration fails, display a message to the user. If registration succeeds
                                // the auth state listener will be notified and logic to handle the
                                // registered user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(CustomerSignUpActivity.this, "Customer Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(CustomerSignUpActivity.this, LoginActivity.class));
                                    finish();
                                }

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
