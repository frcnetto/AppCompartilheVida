package com.example.frcnetto.compartilhevida.controler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.frcnetto.compartilhevida.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthControler {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }

    public void signOut(){
        auth.signOut();
    }

    public boolean signInWithEmailAndPassword(String email, String password, final String TAG, final Context context){
        boolean retorno = false;
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(context, R.string.login_failed_usuarionaoencontrado, Toast.LENGTH_SHORT).show();
                            //retorno = false;
                        } else {
                            Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
                            //criaIntent(MainActivity.class);
                        }
                    }
                });
        return retorno;
    }
}
