package com.example.frcnetto.compartilhevida.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.frcnetto.compartilhevida.R;
import com.example.frcnetto.compartilhevida.activity.CadastroCampanhaActivity;
import com.example.frcnetto.compartilhevida.dao.DaoCampanha;
import com.example.frcnetto.compartilhevida.model.Campanha;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;


public class CampanhasAdapter extends RecyclerView.Adapter<CampanhasAdapter.CampanhaViewHolder> {
    private List<Campanha> campanhas;
    private static Context context;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static class CampanhaViewHolder extends RecyclerView.ViewHolder{
        public TextView cid;
        public TextView uid;
        public TextView beneficiario;
        public TextView cidade;
        public TextView estado;
        public TextView tpSanguineo;
        public TextView contato;
        public Toolbar  toolbar;

        public CampanhaViewHolder(View v) {
            super(v);
            cid = (TextView) v.findViewById(R.id.campanha_item_cid);
            uid = (TextView) v.findViewById(R.id.campanha_item_uid);
            beneficiario = (TextView) v.findViewById(R.id.campanha_item_beneficiario);
            cidade = (TextView) v.findViewById(R.id.campanha_item_cidade);
            estado = (TextView) v.findViewById(R.id.campanha_item_estado);
            tpSanguineo = (TextView) v.findViewById(R.id.campanha_item_tpsanquineo);
            contato = (TextView) v.findViewById(R.id.campanha_item_telefone);
            toolbar = (Toolbar) v.findViewById(R.id.card_toolbar);
            if (toolbar != null && FirebaseAuth.getInstance().getCurrentUser() != null){
                toolbar.inflateMenu(R.menu.menu_campanhas);
                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_editar:
                                Intent edit = new Intent(context, CadastroCampanhaActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("beneficiario",beneficiario.getText().toString());
                                bundle.putString("contato", contato.getText().toString());
                                bundle.putString("cidade",cidade.getText().toString());
                                bundle.putString("estado",estado.getText().toString());
                                bundle.putString("tpSanguineo",tpSanguineo.getText().toString());
                                bundle.putString("cid", cid.getText().toString());
                                bundle.putString("uid", uid.getText().toString());
                                edit.putExtras(bundle);
                                context.startActivity(edit);
                                return true;
                            case R.id.action_compartilhar:
                                FacebookSdk.sdkInitialize(context);
                                CallbackManager callbackManager = CallbackManager.Factory.create();
                                ShareDialog shareDialog = new ShareDialog((Activity)context);
                                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                                    @Override
                                    public void onSuccess(Sharer.Result result) {}
                                    @Override
                                    public void onCancel() {}
                                    @Override
                                    public void onError(FacebookException error) {}
                                });
                                if (ShareDialog.canShow(ShareLinkContent.class)) {
                                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                            .setContentTitle("Preciso da sua ajuda!")
                                            .setContentDescription(
                                                    "Acabei de criar uma campanha de doação de sangue através do aplicativo Compartilhe Vida")
                                            .setContentUrl(Uri.parse("https://compartilhe-vida.firebaseapp.com/index.html?" +
                                                    "nome=" + beneficiario.getText().toString() +
                                                    "&telefone=" + contato.getText().toString() +
                                                    "&cidade=" + cidade.getText().toString() +
                                                    "&estado=" + estado.getText().toString() +
                                                    "&tpsangue=" + tpSanguineo.getText().toString()))
                                            .build();

                                    shareDialog.show(linkContent);
                                }
                                return true;
                            case R.id.action_excluir:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage(R.string.alerta_exclusao)
                                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                DaoCampanha dao = new DaoCampanha();
                                                dao.deleteCampanha(cid.getText().toString());
                                            }
                                        })
                                        .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {}});
                                AlertDialog alert = builder.create();
                                alert.show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        }
    }

    public CampanhasAdapter(List<Campanha> campanhas, Context context) {
        Collections.reverse(campanhas);
        this.campanhas = campanhas;
        this.context = context;
    }

    @Override
    public CampanhaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recyclerview_item_campanha, parent, false);
        return new CampanhaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CampanhaViewHolder holder, int position) {
        Campanha campanha = campanhas.get(position);
        holder.cid.setText(campanha.getCid());
        holder.uid.setText(campanha.getUid());
        holder.beneficiario.setText(campanha.getNomeBeneficiario());
        holder.cidade.setText(campanha.getCidade());
        holder.estado.setText(campanha.getEstado());
        holder.tpSanguineo.setText(campanha.getTpSanguineo());
        holder.contato.setText(campanha.getContato());
        if (!holder.uid.getText().toString().equals(user.getUid())){
            holder.toolbar.getMenu().removeItem(R.id.action_excluir);
            holder.toolbar.getMenu().removeItem(R.id.action_editar);
        }
    }

    @Override
    public int getItemCount() {
        return campanhas.size();

    }
}