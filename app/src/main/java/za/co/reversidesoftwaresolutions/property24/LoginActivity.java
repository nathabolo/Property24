package za.co.reversidesoftwaresolutions.property24;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    //Declare objects here

    private static final int RC_SIGN_IN = 1;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    private EditText m_password, m_email;
    private Button btn_login;
    private TextView btn_register, btn_reset_password;
    private FirebaseAuth mFirebaseAuth;
    private ProgressBar mProgressBar;
    SignInButton googleSignin;
    GoogleApiClient mGoogleApiClient;
    private String TAG="string";

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        //Get the id of the google sign in button
//        googleSignin = (SignInButton) findViewById(R.id.google_sign_in_button);


        //Action bar for the back/home buttom

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Get firebase auth instance

        mFirebaseAuth = FirebaseAuth.getInstance();


        // set the view for the login screen
        setContentView(R.layout.activity_login);


        //Get the id's of the edit text fields

        m_password = (EditText) findViewById(R.id.m_password);
        m_email = (EditText) findViewById(R.id.m_email);


        //Get the id of the button Login

        btn_login = (Button) findViewById(R.id.btn_login);


        //Get the id's of the text views

        btn_register = (TextView) findViewById(R.id.btn_register);
        btn_reset_password = (TextView) findViewById(R.id.btn_reset_password);

//        googleSignin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentGoogleSignin = new Intent(LoginActivity.this, FirebaseAuth.class);
//                startActivity(intentGoogleSignin);
//            }
//        });

        //Get the id of the progress bar
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(LoginActivity.this, CompanySignUpActivity.class);
                startActivity(intentRegister);
            }
        });

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResetPassword = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intentResetPassword);
            }
        });

        mProgressBar.setVisibility(View.VISIBLE);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String password = m_password.getText().toString().trim();
                String email = m_email.getText().toString().trim();

                //Check to see whether all required fields are not empty

                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(getApplicationContext(), "Password field cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(getApplicationContext(), "Email field cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Password so that they do not contain less than six characters

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password field cannot contain less than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                //authenticate user
                mFirebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                mProgressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error

                                    if (password.length() < 6) {

                                        m_password.setError(getString(R.string.incorecct_password));

                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.incorecct_password), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, Property24MainActivity.class);//change the activity to home main activity
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });

            }
        });

//        // Build a GoogleApiClient with access to the Google Sign-In API and the
//        // options specified by gso.
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//                        //Display message if there is no connection
//                        Toast.makeText(LoginActivity.this, "The connection failed", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//    }


//
//    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//        mFirebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("TAG", "signInWithCredential:success");
//                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
//                            // updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            // updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });

//        //Initialize authstate listener
//
//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                //Check to see if the user logged in and navigate to Property24 main activity if the user successfully logged in with google account
//                if (null != FirebaseAuth.getInstance()) {
//
//                    Intent intentGoogleSignin = new Intent(LoginActivity.this, Property24MainActivity.class);
//                    startActivity(intentGoogleSignin);
//
//                }
//
//            }
//        };
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
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

//    //Set the onclick listener to the register button and reset password button
//
//    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build();
//
//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent((GoogleApiClient) mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if (result.isSuccess()) {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = result.getSignInAccount();
//                firebaseAuthWithGoogle(account);
//            } else {
//                Toast.makeText(LoginActivity.this, "Failed to connect to firebase auth", Toast.LENGTH_SHORT).show();
//            }
//        }
    //}


    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }
}
