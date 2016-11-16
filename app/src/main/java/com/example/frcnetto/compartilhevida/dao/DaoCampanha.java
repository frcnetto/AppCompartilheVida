package com.example.frcnetto.compartilhevida.dao;

import com.example.frcnetto.compartilhevida.model.Campanha;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DaoCampanha {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference =  database.getReference("campanhas");

    public void insertCampanha(String nomeBeneficiario, String contato, String cidade, String estado, String tpSanguineo) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference newReference = reference.push();
        String cid = newReference.getKey();
        Campanha campanha = new Campanha(cid, uid, cidade, estado, nomeBeneficiario, tpSanguineo, contato);
        reference.child(campanha.getCid()).setValue(campanha);
    }

    public void updateCampanha(String cid, String uid, String nomeBeneficiario, String contato, String cidade, String estado, String tpSanguineo){
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Campanha campanha = new Campanha(cid, uid, cidade, estado, nomeBeneficiario, tpSanguineo, contato);
            reference.child(campanha.getCid()).setValue(campanha);
        }
    }

    public void deleteCampanha(String cid){
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            reference.child(cid).removeValue();
        }
    }

    public void buscaCampanha(final String cid){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(childEventListener);
    }
}
