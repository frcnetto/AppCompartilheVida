package com.example.frcnetto.compartilhevida.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frcnetto.compartilhevida.R;
import com.example.frcnetto.compartilhevida.controler.FormController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;

public class LoginActivity extends AppCompatActivity{
    private static final String TAG = "LoginActivity";

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FormController formController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        formController = new FormController();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    // Se já existe uma sessão ativa, chama a tela principal
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    criaIntent(true, MainActivity.class);
                }
            }
        };

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    tentaLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.btnLogin);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tentaLogin();
            }
        });

        Button mCadastroManualButton = (Button) findViewById(R.id.btnCadastroManual);
        mCadastroManualButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                criaIntent(false, CadUsuarioActivity.class);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void tentaLogin() {
        boolean cancel = false;
        View focusView = null;
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String senha = mPasswordView.getText().toString();

        // Check for a valid password, if the user entered one.
            if (!formController.isPasswordValid(mPasswordView)) {
                focusView = mPasswordView;
                cancel = true;
            }

            // Check for a valid email address.
            if (!formController.isEmailValid(mEmailView)) {
                focusView = mEmailView;
                cancel = true;
            }

            if (cancel) {
                focusView.requestFocus();
            } else {
                // perform the user login attempt.
                login(email, senha);
            }
        }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(getApplicationContext(), R.string.login_failed_usuarionaoencontrado, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
                            criaIntent(true, MainActivity.class);
                        }
                    }
                });
    }

    private void criaIntent(Boolean finish, Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        if (finish)
            finish();
    }
}

