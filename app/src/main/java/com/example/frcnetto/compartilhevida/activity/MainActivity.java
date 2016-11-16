package com.example.frcnetto.compartilhevida.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frcnetto.compartilhevida.R;
import com.example.frcnetto.compartilhevida.dao.DaoUsuario;
import com.example.frcnetto.compartilhevida.fragment.InicioFragment;
import com.example.frcnetto.compartilhevida.fragment.MeusDadosFragment;
import com.example.frcnetto.compartilhevida.fragment.MinhasCampanhasFragment;
import com.example.frcnetto.compartilhevida.fragment.SaibaMaisFragment;
import com.example.frcnetto.compartilhevida.model.Usuario;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private FragmentManager manager = getSupportFragmentManager();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager callbackManager;
    private NavigationView navigationView;
    private TextView nomeUsuario;
    private TextView emailUsuario;
    private DaoUsuario dao;
    private DatabaseReference mUsuarioReference;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {}
        };

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                mAuth.signOut();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                mAuth.signOut();
            }
        });

        mUsuarioReference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());

        inicializaVariaveis();

        if(savedInstanceState == null){
            InicioFragment inicio = new InicioFragment();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.app_bar_main, inicio);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void inicializaVariaveis() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        nomeUsuario = (TextView) header.findViewById(R.id.header_nome_usuario);
        emailUsuario = (TextView) header.findViewById(R.id.header_email_usuario);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ValueEventListener usuarioListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                nomeUsuario.setText(user.getNome());
                emailUsuario.setText(user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(MainActivity.this, "Falha ao carregar dados do usuário.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUsuarioReference.addValueEventListener(usuarioListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            InicioFragment inicio = new InicioFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.app_bar_main, inicio, "inicio");
            transaction.commit();

        } else if (id == R.id.nav_minhas_campanhas) {
            MinhasCampanhasFragment minhasCampanhasFragment = new MinhasCampanhasFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.app_bar_main, minhasCampanhasFragment, "minhasCampanhasFragment");
            transaction.commit();
        } else if (id == R.id.nav_meus_dados) {
            MeusDadosFragment meusDados = new MeusDadosFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.app_bar_main, meusDados, "meusDados");
            transaction.commit();
        } else if (id == R.id.nav_saiba_mais) {
            SaibaMaisFragment saibaMaisFragment = new SaibaMaisFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.app_bar_main, saibaMaisFragment, "saibaMais");
            transaction.commit();
        } else if (id == R.id.nav_facebook) {
            tentaLoginFacebook();
        } else if(id == R.id.nav_sair){
            FirebaseAuth.getInstance().signOut();
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void tentaLoginFacebook(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    private void handleFacebookAccessToken(AccessToken token){
        Log.d(TAG, "handleFacebookAccessTolken:" + token);
        AuthCredential credencial = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.getCurrentUser().linkWithCredential(credencial).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "linkWithCredential:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Já conectado",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
