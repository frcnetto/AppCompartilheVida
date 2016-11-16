package com.example.frcnetto.compartilhevida.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.frcnetto.compartilhevida.R;
import com.example.frcnetto.compartilhevida.dao.DaoUsuario;
import com.example.frcnetto.compartilhevida.controler.FormController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadUsuarioActivity extends AppCompatActivity {
    private static final String TAG = "CadUsuarioActivity";
    private EditText edTxEmail;
    private EditText edTxSenha;
    private EditText edTxConfirmSenha;
    private FloatingActionButton btnCadastra;
    private FormController formController;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference =  database.getReference();
    private DaoUsuario daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Cadastro manual");


        inicializaVariaveis();

        btnCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaForm()){
                    mAuth.createUserWithEmailAndPassword(edTxEmail.getText().toString(), edTxSenha.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                    if (task.isSuccessful()){
                                        daoUser.gravaUsuario(mAuth.getCurrentUser().getUid(), edTxEmail.getText().toString());
                                        criaIntent(MeusDadosActivity.class);
                                    } else{
                                        Log.w(TAG,"signInWithEmail:failed", task.getException());
                                        Toast.makeText(getApplicationContext(), R.string.auth_failed + ": " + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else{
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private boolean validaForm() {
        boolean success = true;
        if (!formController.isEmailValid(edTxEmail))
            success = false;
        if (!formController.isPasswordValid(edTxSenha))
            success = false;
        if (!formController.isFieldsTextEqual(edTxSenha, edTxConfirmSenha))
            success = false;
        return success;
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if (mAuthStateListener != null)
            mAuth.removeAuthStateListener(mAuthStateListener);
    }
    private void inicializaVariaveis(){
        edTxEmail = (EditText) findViewById(R.id.EdTxEmail);
        edTxSenha = (EditText) findViewById(R.id.EdTxSenha);
        edTxConfirmSenha = (EditText) findViewById(R.id.EdTxConfirmSenha);
        btnCadastra = (FloatingActionButton) findViewById(R.id.btnCadastrar);
        mAuth = FirebaseAuth.getInstance();
        formController = new FormController();
        daoUser = new DaoUsuario();

        edTxEmail.setText("");
        edTxSenha.setText("");
        edTxConfirmSenha.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent home = new Intent(this, LoginActivity.class);
                startActivity(home);
                finish();
                break;
        }
        return true;
    }

    private void criaIntent(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        finish();
    }
}
