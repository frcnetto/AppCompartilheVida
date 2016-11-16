package com.example.frcnetto.compartilhevida.dao;

import com.example.frcnetto.compartilhevida.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DaoUsuario {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference =  database.getReference("users");
    private Usuario user = new Usuario();

    public void gravaUsuario(String uid, String email) {
        Usuario user = new Usuario();
        user.setUid(uid);
        user.setEmail(email);

        reference.child(user.getUid()).setValue(user);
    }

    public void atualizaUsuario(Usuario user){
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
            user.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            reference.child(user.getUid()).setValue(user);
        }
    }

    public void desativaUsuario(Usuario user){
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

        }
    }

    public Usuario buscaUsuario(final String uid){
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.setNome(dataSnapshot.getValue(Usuario.class).getNome());
                user.setIdade(dataSnapshot.getValue(Usuario.class).getIdade());
                user.setSexo(dataSnapshot.getValue(Usuario.class).getSexo());
                user.setNaoEstouGravida(dataSnapshot.getValue(Usuario.class).isNaoEstouGravida());
                user.setEsteveGravida(dataSnapshot.getValue(Usuario.class).isEsteveGravida());
                user.setTpParto(dataSnapshot.getValue(Usuario.class).getTpParto());
                user.setDtParto(dataSnapshot.getValue(Usuario.class).getDtParto());
                user.setTpSanguineo(dataSnapshot.getValue(Usuario.class).getTpSanguineo());
                user.setJaDoador(dataSnapshot.getValue(Usuario.class).isJaDoador());
                user.setPrimeiraDoacao(dataSnapshot.getValue(Usuario.class).isPrimeiraDoacao());
                user.setDtUltmDoacao(dataSnapshot.getValue(Usuario.class).getDtUltmDoacao());
                user.setTatuagem(dataSnapshot.getValue(Usuario.class).isTatuagem());
                user.setDtTatuagem(dataSnapshot.getValue(Usuario.class).getDtTatuagem());
                user.setDiabetes(dataSnapshot.getValue(Usuario.class).isDiabetes());
                user.setHepatite(dataSnapshot.getValue(Usuario.class).isHepatite());
                user.setAids(dataSnapshot.getValue(Usuario.class).isAids());
                user.setSifilis(dataSnapshot.getValue(Usuario.class).isSifilis());
                user.setHtlv(dataSnapshot.getValue(Usuario.class).isHtlv());
                user.setChagas(dataSnapshot.getValue(Usuario.class).isChagas());
                user.setMalaria(dataSnapshot.getValue(Usuario.class).isMalaria());
                user.setDrogas(dataSnapshot.getValue(Usuario.class).isDrogas());
                System.out.println(dataSnapshot.getValue(Usuario.class).getNome());
                System.out.println(dataSnapshot.getValue(Usuario.class).getIdade());
                System.out.println(dataSnapshot.getValue(Usuario.class).getSexo());
                System.out.println(dataSnapshot.getValue(Usuario.class).isNaoEstouGravida());
                System.out.println(dataSnapshot.getValue(Usuario.class).isEsteveGravida());
                System.out.println(dataSnapshot.getValue(Usuario.class).getTpParto());
                System.out.println(dataSnapshot.getValue(Usuario.class).getDtParto());
                System.out.println(dataSnapshot.getValue(Usuario.class).getTpSanguineo());
                System.out.println(dataSnapshot.getValue(Usuario.class).isJaDoador());
                System.out.println(dataSnapshot.getValue(Usuario.class).isPrimeiraDoacao());
                System.out.println(dataSnapshot.getValue(Usuario.class).getDtUltmDoacao());
                System.out.println(dataSnapshot.getValue(Usuario.class).isTatuagem());
                System.out.println(dataSnapshot.getValue(Usuario.class).getDtTatuagem());
                System.out.println(dataSnapshot.getValue(Usuario.class).isDiabetes());
                System.out.println(dataSnapshot.getValue(Usuario.class).isHepatite());
                System.out.println(dataSnapshot.getValue(Usuario.class).isAids());
                System.out.println(dataSnapshot.getValue(Usuario.class).isSifilis());
                System.out.println(dataSnapshot.getValue(Usuario.class).isHtlv());
                System.out.println(dataSnapshot.getValue(Usuario.class).isChagas());
                System.out.println(dataSnapshot.getValue(Usuario.class).isMalaria());
                System.out.println(dataSnapshot.getValue(Usuario.class).isDrogas());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
            System.out.println("User " + user.getNome());
            System.out.println("User " + user.getIdade());
            System.out.println("User " + user.getSexo());
            System.out.println("User " + user.isNaoEstouGravida());
            System.out.println("User " + user.isEsteveGravida());
            System.out.println("User " + user.getTpParto());
            System.out.println("User " + user.getDtParto());
            System.out.println("User " + user.getTpSanguineo());
            System.out.println("User " + user.isJaDoador());
            System.out.println("User " + user.isPrimeiraDoacao());
            System.out.println("User " + user.getDtUltmDoacao());
            System.out.println("User " + user.isTatuagem());
            System.out.println("User " + user.getDtTatuagem());
            System.out.println("User " + user.isDiabetes());
            System.out.println("User " + user.isHepatite());
            System.out.println("User " + user.isAids());
            System.out.println("User " + user.isSifilis());
            System.out.println("User " + user.isHtlv());
            System.out.println("User " + user.isChagas());
            System.out.println("User " + user.isMalaria());
            System.out.println("User " + user.isDrogas());
            return user;
    }
}
