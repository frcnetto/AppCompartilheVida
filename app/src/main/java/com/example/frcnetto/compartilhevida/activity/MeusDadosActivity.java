package com.example.frcnetto.compartilhevida.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.frcnetto.compartilhevida.R;
import com.example.frcnetto.compartilhevida.dao.DaoUsuario;
import com.example.frcnetto.compartilhevida.model.Usuario;
import com.example.frcnetto.compartilhevida.controler.FormController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;

public class MeusDadosActivity extends AppCompatActivity {
    private EditText                    edTxNome;
    private EditText                    edTxIdade;
    private RadioGroup                  rdGrSexo;
    private CheckBox                    ckBxNaoEstouGravida;
    private TextView                    txVwGravida;
    private RadioGroup                  rdGrJaEsteveGravida;
    private TextView                    txVwParto;
    private RadioGroup                  rdGrParto;
    private TextView                    txVwDtParto;
    private Spinner                     spTipoSanguineo;
    private ArrayAdapter<CharSequence>  listTpSanguineos;
    private RadioGroup                  rdGrDoador;
    private CheckBox                    ckBxPrimeiraDoacao;
    private TextView                    txVwDtDoacao;
    private EditText                    edTxPeso;
    private RadioGroup                  rdGrTatuagem;
    private TextView                    txVwDtTatuagem;
    private CheckBox                    ckBxDiabetes;
    private CheckBox                    ckBxHepatite;
    private CheckBox                    ckBxAIDS;
    private CheckBox                    ckBxSifilis;
    private CheckBox                    ckBxHTLV;
    private CheckBox                    ckBxChagas;
    private CheckBox                    ckBxMalaria;
    private CheckBox                    ckBxDrogas;
    private FloatingActionButton        fab;
    private FormController formController;
    private DaoUsuario daoUser;
    private View v;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados);
        v = findViewById(R.id.activity_meus_dados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Meus dados");

        startVariables();

        rdGrSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                case R.id.RdBtSexoFeminino:
                    formController.setEnabled(true, ckBxNaoEstouGravida);
                    formController.setEnabled(true, rdGrJaEsteveGravida, txVwGravida);
                    break;
                case R.id.RdBtSexoMasculino:
                    formController.setEnabled(false, ckBxNaoEstouGravida);
                    formController.setEnabled(false, rdGrJaEsteveGravida, txVwGravida);
                    formController.setEnabled(false, rdGrParto, txVwParto);
                    formController.setEnabled(false, txVwDtParto, MeusDadosActivity.this);
            }}});
        ckBxNaoEstouGravida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ckBxNaoEstouGravida.setError("", null);
            }
        });
        rdGrJaEsteveGravida.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.RdBtGravidaSim:
                        formController.setEnabled(true, rdGrParto, txVwGravida);
                        formController.setEnabled(true, txVwDtParto, MeusDadosActivity.this);
                        break;
                    case R.id.RdBtGravidaNao:
                        formController.setEnabled(false, rdGrParto, txVwGravida);
                        formController.setEnabled(false, txVwDtParto, MeusDadosActivity.this);
                        break;
                }
            }
        });
        rdGrParto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                txVwParto.setError("", null);
            }
        });
        txVwDtParto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txVwDtParto.getText().toString().equals(getString(R.string.data)))
                    formController.showDatePickerDialog(v, "dtParto", MeusDadosActivity.this);
                else
                    formController.showDatePickerDialog(v, "dtParto", MeusDadosActivity.this, txVwDtParto.getText().toString());
                txVwDtParto.setError("", null);
            }
        });
        rdGrDoador.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.RdBtDoadorSim:
                        formController.setEnabled(true, ckBxPrimeiraDoacao);
                        formController.setEnabled(true, txVwDtDoacao, MeusDadosActivity.this);
                        break;
                    case R.id.RdBtDoadorNao:
                        formController.setEnabled(false, ckBxPrimeiraDoacao);
                        formController.setEnabled(false, txVwDtDoacao, MeusDadosActivity.this);
                        break;
                }
            }
        });
        ckBxPrimeiraDoacao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ckBxPrimeiraDoacao.setError("", null);
            }
        });
        txVwDtDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txVwDtDoacao.getText().toString().equals(getString(R.string.data)))
                    formController.showDatePickerDialog(v, "dtDoacao", MeusDadosActivity.this);
                else
                    formController.showDatePickerDialog(v, "dtDoacao", MeusDadosActivity.this, txVwDtDoacao.getText().toString());
                txVwDtDoacao.setError("", null);
            }
        });
        rdGrTatuagem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.RdBtTatuagemSim:
                        formController.setEnabled(true, txVwDtTatuagem, MeusDadosActivity.this);
                        break;
                    case R.id.RdBtTatuagemNao:
                        formController.setEnabled(false, txVwDtTatuagem, MeusDadosActivity.this);
                        break;
                }
            }
        });
        txVwDtTatuagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txVwDtTatuagem.getText().toString().equals(getString(R.string.data)))
                    formController.showDatePickerDialog(v, "dtTatuagem", MeusDadosActivity.this);
                else
                    formController.showDatePickerDialog(v, "dtTatuagem", MeusDadosActivity.this, txVwDtTatuagem.getText().toString());
                txVwDtTatuagem.setError("", null);
            }
        });
        ckBxDiabetes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ckBxDiabetes.setError("", null);
            }
        });
        ckBxHepatite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ckBxHepatite.setError("", null);
            }
        });ckBxAIDS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ckBxAIDS.setError("", null);
            }
        });ckBxSifilis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ckBxSifilis.setError("", null);
            }
        });ckBxHTLV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ckBxHTLV.setError("", null);
            }
        });ckBxChagas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ckBxChagas.setError("", null);
            }
        });ckBxMalaria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ckBxMalaria.setError("", null);
            }
        });ckBxDrogas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ckBxDrogas.setError("", null);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!validaForm()){
                        Usuario user = new Usuario();
                        user.setNome(edTxNome.getText().toString());
                        user.setIdade(Integer.parseInt(edTxIdade.getText().toString()));
                        user.setSexo(formController.getCheckedRadioButtonText(rdGrSexo));
                        user.setNaoEstouGravida(ckBxNaoEstouGravida.isChecked());
                        user.setEsteveGravida(formController.getCheckedRadioButton(rdGrJaEsteveGravida));
                        user.setTpParto(formController.getCheckedRadioButtonText(rdGrParto));
                        user.setDtParto(txVwDtParto.getText().toString());
                        user.setTpSanguineo(spTipoSanguineo.getSelectedItem().toString());
                        user.setJaDoador(formController.getCheckedRadioButton(rdGrDoador));
                        user.setPrimeiraDoacao(ckBxPrimeiraDoacao.isChecked());
                        user.setDtUltmDoacao(txVwDtDoacao.getText().toString());
                        user.setPeso(Float.parseFloat(edTxPeso.getText().toString()));
                        user.setTatuagem(formController.getCheckedRadioButton(rdGrTatuagem));
                        user.setDtTatuagem(txVwDtTatuagem.getText().toString());
                        user.setDiabetes(!ckBxDiabetes.isChecked());
                        user.setHepatite(!ckBxHepatite.isChecked());
                        user.setAids(!ckBxAIDS.isChecked());
                        user.setSifilis(!ckBxSifilis.isChecked());
                        user.setHtlv(!ckBxHTLV.isChecked());
                        user.setChagas(!ckBxChagas.isChecked());
                        user.setMalaria(!ckBxMalaria.isChecked());
                        user.setDrogas(!ckBxDrogas.isChecked());
                        daoUser.atualizaUsuario(user);
                        Intent home = new Intent(view.getContext(), MainActivity.class);
                        startActivity(home);
                        finish();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {super.onStart();}

    private boolean validaForm() throws ParseException {
        boolean erro = false;

        if (!formController.isNameValid(edTxNome))
            erro = true;

        if (!formController.isIdadeValid(edTxIdade))
            erro = true;

        if (!formController.isCheckboxValid(ckBxNaoEstouGravida))
            erro = true;

        if (!formController.isRadiogroupValid(rdGrJaEsteveGravida, txVwGravida))
            erro = true;

        if (!formController.isRadiogroupValid(rdGrParto, txVwParto))
            erro = true;

        if (!formController.isDataValid(txVwDtParto))
            erro = true;
        else if (!formController.comparaData(txVwDtParto, formController.getCheckedRadioButtonText(rdGrParto)))
            erro = true;

        if (!formController.isCheckboxValid(ckBxPrimeiraDoacao))
            erro = true;

        if (!formController.isDataValid(txVwDtDoacao))
            erro = true;
        else if (!formController.comparaData(txVwDtDoacao, formController.getCheckedRadioButtonText(rdGrSexo)))
            erro = true;

        if (!formController.isPesoValid(edTxPeso))
            erro = true;

        if (!formController.isDataValid(txVwDtTatuagem))
            erro = true;
        else if (!formController.comparaData(txVwDtTatuagem, formController.getCheckedRadioButtonText(rdGrTatuagem)))
            erro = true;

        if (!formController.isCheckboxValid(ckBxDiabetes))
            erro = true;

        if (!formController.isCheckboxValid(ckBxHepatite))
            erro = true;

        if (!formController.isCheckboxValid(ckBxAIDS))
            erro = true;

        if (!formController.isCheckboxValid(ckBxSifilis))
            erro = true;

        if (!formController.isCheckboxValid(ckBxHTLV))
            erro = true;

        if (!formController.isCheckboxValid(ckBxChagas))
            erro = true;

        if (!formController.isCheckboxValid(ckBxMalaria))
            erro = true;

        if (!formController.isCheckboxValid(ckBxDrogas))
            erro = true;

        return erro;
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

    private void startVariables() {
        edTxNome            = (EditText)                findViewById(R.id.EdTxNome);
        edTxIdade           = (EditText)                findViewById(R.id.EdTxIdade);
        rdGrSexo            = (RadioGroup)              findViewById(R.id.RdGrSexo);
        ckBxNaoEstouGravida = (CheckBox)                findViewById(R.id.CkBxNaoEstouGravida);
        txVwGravida         = (TextView)                findViewById(R.id.textViewGravida);
        rdGrJaEsteveGravida = (RadioGroup)              findViewById(R.id.RdGrGravida);
        txVwParto           = (TextView)                findViewById(R.id.textViewParto);
        rdGrParto           = (RadioGroup)              findViewById(R.id.RdGrParto);
        txVwDtParto         = (TextView)                findViewById(R.id.textViewDtParto);
        spTipoSanguineo     = (Spinner)                 findViewById(R.id.SpTipoSanguineo);
        rdGrDoador          = (RadioGroup)              findViewById(R.id.RdGrDoador);
        ckBxPrimeiraDoacao  = (CheckBox)                findViewById(R.id.CkBxPrimeiraDoacao);
        txVwDtDoacao        = (TextView)                findViewById(R.id.textViewDtUltimaDoacao);
        edTxPeso            = (EditText)                findViewById(R.id.EdTxPeso);
        rdGrTatuagem        = (RadioGroup)              findViewById(R.id.RdGrTatuagem);
        txVwDtTatuagem      = (TextView)                findViewById(R.id.textViewDtTatuagem);
        ckBxDiabetes        = (CheckBox)                findViewById(R.id.CkBxDiabetes);
        ckBxHepatite        = (CheckBox)                findViewById(R.id.CkBxHepatite);
        ckBxAIDS            = (CheckBox)                findViewById(R.id.CkBxAIDS);
        ckBxSifilis         = (CheckBox)                findViewById(R.id.CkBxSifilis);
        ckBxHTLV            = (CheckBox)                findViewById(R.id.CkBxHTLV);
        ckBxChagas          = (CheckBox)                findViewById(R.id.CkBxChagas);
        ckBxMalaria         = (CheckBox)                findViewById(R.id.CkBxMalaria);
        ckBxDrogas          = (CheckBox)                findViewById(R.id.CkBxDrogas);
        fab                 = (FloatingActionButton)    findViewById(R.id.fab_meus_dados);
        fab                 = (FloatingActionButton)    findViewById(R.id.fab_meus_dados);
        formController = new FormController(v);
        daoUser = new DaoUsuario();

        listTpSanguineos    = ArrayAdapter.createFromResource(this, R.array.tipos_sanguineos, android.R.layout.simple_spinner_dropdown_item);
        listTpSanguineos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoSanguineo.setAdapter(listTpSanguineos);
        formController.setEnabled(false, ckBxNaoEstouGravida);
        formController.setEnabled(false, rdGrJaEsteveGravida, txVwGravida);
        formController.setEnabled(false, rdGrParto, txVwParto);
        formController.setEnabled(false, txVwDtParto, this);
        formController.setEnabled(false, ckBxPrimeiraDoacao);
        formController.setEnabled(false, txVwDtDoacao, this);
        formController.setEnabled(false, txVwDtTatuagem, this);
    }
}