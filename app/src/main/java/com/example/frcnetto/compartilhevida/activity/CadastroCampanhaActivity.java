package com.example.frcnetto.compartilhevida.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.frcnetto.compartilhevida.R;
import com.example.frcnetto.compartilhevida.controler.FormController;
import com.example.frcnetto.compartilhevida.dao.DaoCampanha;

public class CadastroCampanhaActivity extends AppCompatActivity {
    private String cid;
    private String uid;
    private EditText beneficiario;
    private EditText contato;
    private EditText cidade;
    private Spinner spTipoSanguineo;
    private ArrayAdapter<CharSequence>  listTpSanguineos;
    private Spinner spEstados;
    private ArrayAdapter<CharSequence>  listEstados;
    private DaoCampanha daoCampanha;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_campanha);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Cadastrar campanha");
        savedInstanceState = getIntent().getExtras();

        v = findViewById(R.id.activity_cadastro_campanha);

        inicializaVariaveis(savedInstanceState);

        validaForm();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_meus_dados);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validaForm()) {
                    if (cid.isEmpty()) {
                        daoCampanha.insertCampanha(
                                beneficiario.getText().toString(),
                                contato.getText().toString(),
                                cidade.getText().toString(),
                                spEstados.getSelectedItem().toString(),
                                spTipoSanguineo.getSelectedItem().toString()
                        );
                    } else {
                        daoCampanha.updateCampanha(
                                cid,
                                uid,
                                beneficiario.getText().toString(),
                                contato.getText().toString(),
                                cidade.getText().toString(),
                                spEstados.getSelectedItem().toString(),
                                spTipoSanguineo.getSelectedItem().toString()
                        );
                    }
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent home = new Intent(this, MainActivity.class);
                startActivity(home);
                finish();
                break;
        }
        return true;
    }

    private boolean validaForm(){
        FormController controller = new FormController(v);
        if (!controller.isNameValid(beneficiario))
            return false;
        if (!controller.isCidadeValid(cidade))
            return false;
        else
            return true;
    }

    private void inicializaVariaveis(Bundle bundle){
        cid = "";
        uid = "";
        beneficiario = (EditText) findViewById(R.id.editTextBeneficiario);
        contato = (EditText) findViewById(R.id.editTextContato);
        cidade = (EditText) findViewById(R.id.editTextCidade);
        spTipoSanguineo = (Spinner) findViewById(R.id.spinnerTpSanguineo);
        listTpSanguineos = ArrayAdapter.createFromResource(this, R.array.tipos_sanguineos, android.R.layout.simple_spinner_dropdown_item);
        listTpSanguineos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoSanguineo.setAdapter(listTpSanguineos);
        spEstados = (Spinner) findViewById(R.id.spinnerEstado);
        listEstados = ArrayAdapter.createFromResource(this, R.array.estados, android.R.layout.simple_spinner_dropdown_item);
        listEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstados.setAdapter(listEstados);
        daoCampanha = new DaoCampanha();

        if (bundle != null){
            cid = bundle.getString("cid");
            uid = bundle.getString("uid");
            beneficiario.setText(bundle.getString("beneficiario"));
            contato.setText(bundle.getString("contato"));
            cidade.setText(bundle.getString("cidade"));
            spEstados.setSelection(listEstados.getPosition(bundle.getString("estado")));
            spTipoSanguineo.setSelection(listTpSanguineos.getPosition(bundle.getString("tpSanguineos")));
        }
    }
}
