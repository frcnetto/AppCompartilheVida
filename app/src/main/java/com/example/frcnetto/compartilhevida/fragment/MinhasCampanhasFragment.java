package com.example.frcnetto.compartilhevida.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frcnetto.compartilhevida.activity.CadastroCampanhaActivity;
import com.example.frcnetto.compartilhevida.adapter.CampanhasAdapter;
import com.example.frcnetto.compartilhevida.R;
import com.example.frcnetto.compartilhevida.model.Campanha;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MinhasCampanhasFragment extends Fragment{
    private RecyclerView rvMinhasCampanhas;
    private RecyclerView.LayoutManager tvMinhasCampanhasLayoutManager;
    private RecyclerView.Adapter rvMinhasCampanhasAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference =  database.getReference("campanhas/");
    private ChildEventListener mChildEventListener;
    private List<String> campanhasId = new ArrayList<>();
    private List<Campanha> campanhas = new ArrayList<>();
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_inicio, null);

        rvMinhasCampanhas = (RecyclerView) view.findViewById(R.id.recyclerViewCampanhas);
        rvMinhasCampanhas.setHasFixedSize(true);

        tvMinhasCampanhasLayoutManager = new LinearLayoutManager(getContext());
        rvMinhasCampanhas.setLayoutManager(tvMinhasCampanhasLayoutManager);

        rvMinhasCampanhasAdapter = new CampanhasAdapter(campanhas, view.getContext());
        rvMinhasCampanhas.setAdapter(rvMinhasCampanhasAdapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.getValue(Campanha.class).getUid()
                        .equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    Campanha nova = dataSnapshot.getValue(Campanha.class);
                    campanhasId.add(dataSnapshot.getKey());
                    campanhas.add(nova);
                    rvMinhasCampanhasAdapter.notifyItemInserted(campanhasId.size() - 1);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.getValue(Campanha.class).getUid()
                        .equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    Campanha campanha = dataSnapshot.getValue(Campanha.class);
                    String campanhaKey = dataSnapshot.getKey();
                    int campanhaId = campanhasId.indexOf(campanhaKey);
                    if (campanhaId > -1) {
                        campanhas.set(campanhaId, campanha);
                        rvMinhasCampanhasAdapter.notifyItemChanged(campanhaId);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Campanha.class).getUid()
                        .equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    String campanhaKey = dataSnapshot.getKey();
                    int campanhaId = campanhasId.indexOf(campanhaKey);
                    if (campanhaId > -1) {
                        campanhasId.remove(campanhaId);
                        campanhas.remove(campanhaId);
                        rvMinhasCampanhasAdapter.notifyItemRemoved(campanhaId);
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(childEventListener);
        mChildEventListener = childEventListener;

        fab = (FloatingActionButton) view.findViewById(R.id.fab_cad_campanha);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CadastroCampanhaActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {super.onStart();}
}
