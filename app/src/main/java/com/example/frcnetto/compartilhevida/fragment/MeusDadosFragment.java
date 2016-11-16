package com.example.frcnetto.compartilhevida.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.frcnetto.compartilhevida.R;
import com.example.frcnetto.compartilhevida.controler.FormController;
import com.example.frcnetto.compartilhevida.dao.DaoUsuario;
import com.example.frcnetto.compartilhevida.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;

public class MeusDadosFragment extends Fragment{

    private EditText edTxNome;
    private EditText                    edTxIdade;
    private EditText                    edTxSexo;
    private RadioGroup rdGrSexo;
    private CheckBox ckBxNaoEstouGravida;
    private TextView                    txVwGravida;
    private RadioGroup                  rdGrJaEsteveGravida;
    private TextView                    txVwParto;
    private RadioGroup                  rdGrParto;
    private TextView                    txVwDtParto;
    private Spinner spTipoSanguineo;
    private ArrayAdapter<CharSequence> listTpSanguineos;
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
    private FloatingActionButton fab;
    private FormController formController;
    private DaoUsuario daoUser;
    private View v;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        v = inflater.inflate(R.layout.fragment_meus_dados,null);

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
                        formController.setEnabled(false, txVwDtParto, v.getContext());
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
                        formController.setEnabled(true, txVwDtParto, v.getContext());
                        break;
                    case R.id.RdBtGravidaNao:
                        formController.setEnabled(false, rdGrParto, txVwGravida);
                        formController.setEnabled(false, txVwDtParto, v.getContext());
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
                    formController.showDatePickerDialog(v, "dtParto", getActivity());
                else
                    formController.showDatePickerDialog(v, "dtParto", getActivity(), txVwDtParto.getText().toString());
                txVwDtParto.setError("", null);
            }
        });
        rdGrDoador.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.RdBtDoadorSim:
                        formController.setEnabled(true, ckBxPrimeiraDoacao);
                        formController.setEnabled(true, txVwDtDoacao, v.getContext());
                        break;
                    case R.id.RdBtDoadorNao:
                        formController.setEnabled(false, ckBxPrimeiraDoacao);
                        formController.setEnabled(false, txVwDtDoacao, v.getContext());
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
                    formController.showDatePickerDialog(v, "dtDoacao", getActivity());
                else
                    formController.showDatePickerDialog(v, "dtDoacao", getActivity(), txVwDtDoacao.getText().toString());
                txVwDtDoacao.setError("", null);
            }
        });
        rdGrTatuagem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.RdBtTatuagemSim:
                        formController.setEnabled(true, txVwDtTatuagem, v.getContext());
                        break;
                    case R.id.RdBtTatuagemNao:
                        formController.setEnabled(false, txVwDtTatuagem, v.getContext());
                        break;
                }
            }
        });
        txVwDtTatuagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txVwDtTatuagem.getText().toString().equals(getString(R.string.data)))
                    formController.showDatePickerDialog(v, "dtTatuagem", getActivity());
                else
                    formController.showDatePickerDialog(v, "dtTatuagem", getActivity(), txVwDtTatuagem.getText().toString());
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

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_cad_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        InicioFragment inicio = new InicioFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.app_bar_main, inicio, "inicio");
                        transaction.commit();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                edTxNome.setText(usuario.getNome());
                edTxIdade.setText(String.valueOf(usuario.getIdade()));
                formController.setRadioButtonCheckedByName(rdGrSexo, usuario.getSexo());
                ckBxNaoEstouGravida.setChecked(usuario.isNaoEstouGravida());
                formController.setRadioButtonCheckedByName(rdGrJaEsteveGravida, usuario.isEsteveGravida());
                formController.setRadioButtonCheckedByName(rdGrParto, usuario.getTpParto());
                if (!usuario.getDtParto().isEmpty())
                    txVwDtParto.setText(usuario.getDtParto());
                spTipoSanguineo.setSelection(listTpSanguineos.getPosition(usuario.getTpSanguineo()));
                formController.setRadioButtonCheckedByName(rdGrDoador, usuario.isJaDoador());
                ckBxPrimeiraDoacao.setChecked(usuario.isPrimeiraDoacao());
                if (!usuario.getDtUltmDoacao().isEmpty())
                    txVwDtDoacao.setText(usuario.getDtUltmDoacao());
                edTxPeso.setText(String.valueOf(usuario.getPeso()));
                formController.setRadioButtonCheckedByName(rdGrTatuagem, usuario.isTatuagem());
                if (!usuario.getDtTatuagem().isEmpty())
                    txVwDtTatuagem.setText(usuario.getDtTatuagem());
                ckBxDiabetes.setChecked(!usuario.isDiabetes());
                ckBxHepatite.setChecked(!usuario.isHepatite());
                ckBxAIDS.setChecked(!usuario.isAids());
                ckBxSifilis.setChecked(!usuario.isSifilis());
                ckBxHTLV.setChecked(!usuario.isHtlv());
                ckBxChagas.setChecked(!usuario.isChagas());
                ckBxMalaria.setChecked(!usuario.isMalaria());
                ckBxDrogas.setChecked(!usuario.isDrogas());

                if (formController.getCheckedRadioButtonText(rdGrSexo).equals("Masculino")){
                    formController.setRadioButtonCheckedByName(rdGrJaEsteveGravida, false);
                    formController.setEnabled(false, rdGrParto, txVwParto);
                    formController.setEnabled(false, txVwDtParto, v.getContext());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        database.addValueEventListener(userListener);
    }

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

    private void startVariables() {
        edTxNome            = (EditText)                v.findViewById(R.id.EdTxNome);
        edTxIdade           = (EditText)                v.findViewById(R.id.EdTxIdade);
        rdGrSexo            = (RadioGroup)              v.findViewById(R.id.RdGrSexo);
        ckBxNaoEstouGravida = (CheckBox)                v.findViewById(R.id.CkBxNaoEstouGravida);
        txVwGravida         = (TextView)                v.findViewById(R.id.textViewGravida);
        rdGrJaEsteveGravida = (RadioGroup)              v.findViewById(R.id.RdGrGravida);
        txVwParto           = (TextView)                v.findViewById(R.id.textViewParto);
        rdGrParto           = (RadioGroup)              v.findViewById(R.id.RdGrParto);
        txVwDtParto         = (TextView)                v.findViewById(R.id.textViewDtParto);
        spTipoSanguineo     = (Spinner)                 v.findViewById(R.id.SpTipoSanguineo);
        rdGrDoador          = (RadioGroup)              v.findViewById(R.id.RdGrDoador);
        ckBxPrimeiraDoacao  = (CheckBox)                v.findViewById(R.id.CkBxPrimeiraDoacao);
        txVwDtDoacao        = (TextView)                v.findViewById(R.id.textViewDtUltimaDoacao);
        edTxPeso            = (EditText)                v.findViewById(R.id.EdTxPeso);
        rdGrTatuagem        = (RadioGroup)              v.findViewById(R.id.RdGrTatuagem);
        txVwDtTatuagem      = (TextView)                v.findViewById(R.id.textViewDtTatuagem);
        ckBxDiabetes        = (CheckBox)                v.findViewById(R.id.CkBxDiabetes);
        ckBxHepatite        = (CheckBox)                v.findViewById(R.id.CkBxHepatite);
        ckBxAIDS            = (CheckBox)                v.findViewById(R.id.CkBxAIDS);
        ckBxSifilis         = (CheckBox)                v.findViewById(R.id.CkBxSifilis);
        ckBxHTLV            = (CheckBox)                v.findViewById(R.id.CkBxHTLV);
        ckBxChagas          = (CheckBox)                v.findViewById(R.id.CkBxChagas);
        ckBxMalaria         = (CheckBox)                v.findViewById(R.id.CkBxMalaria);
        ckBxDrogas          = (CheckBox)                v.findViewById(R.id.CkBxDrogas);
        fab                 = (FloatingActionButton)    v.findViewById(R.id.fab_meus_dados);
        formController = new FormController(v);
        daoUser = new DaoUsuario();

        txVwDtParto.setText(getString(R.string.data));
        txVwDtDoacao.setText(getString(R.string.data));
        txVwDtTatuagem.setText(getString(R.string.data));

        listTpSanguineos    = ArrayAdapter.createFromResource(v.getContext(), R.array.tipos_sanguineos, android.R.layout.simple_spinner_dropdown_item);
        listTpSanguineos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoSanguineo.setAdapter(listTpSanguineos);
        formController.setEnabled(false, ckBxNaoEstouGravida);
        formController.setEnabled(false, rdGrJaEsteveGravida, txVwGravida);
        formController.setEnabled(false, rdGrParto, txVwParto);
        formController.setEnabled(false, txVwDtParto, v.getContext());
        formController.setEnabled(false, ckBxPrimeiraDoacao);
        formController.setEnabled(false, txVwDtDoacao, v.getContext());
        formController.setEnabled(false, txVwDtTatuagem, v.getContext());
    }
}
