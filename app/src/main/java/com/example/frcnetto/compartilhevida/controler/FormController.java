package com.example.frcnetto.compartilhevida.controler;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frcnetto.compartilhevida.R;
import com.example.frcnetto.compartilhevida.fragment.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormController {
    private View v;

    public FormController() {}

    public FormController(View v) {
        this.v = v;
    }

    public boolean isNameValid(EditText text){
        if (text.getText().toString().isEmpty()){
            setError(true, "Campo obrigatório", text);
            return false;
        } else if(text.getText().toString().length() < 10){
            setError(true, "Nome completo!", text);
            return false;
        } else{
            return true;
        }
    }

    public boolean isIdadeValid(EditText idade){
        if (idade.getText().toString().isEmpty()){
            setError(true, "Campo obrigatório", idade);
            return false;
        } else if(Integer.parseInt(idade.getText().toString()) < 16){
            setError(true, "Você tem que ser maior de 16 anos", idade);
            return false;
        } else{
            return true;
        }
    }

    public boolean isCheckboxValid(CheckBox checkBox){
        if (checkBox.isEnabled() && !checkBox.isChecked()){
            setError(true, "Campo obrigatório", checkBox);
            return false;
        } else{
            return true;
        }
    }

    public boolean isRadiogroupValid(RadioGroup group, TextView text){
        if (group.isEnabled() && !isRadioGroupChecked(group)){
            setError(true, "Campo obrigatório", text);
            return false;
        } else{
            return true;
        }
    }

    public boolean isDataValid(TextView data){
        if (data.isEnabled() && data.getText().toString().equals("dia/mês/ano")){
            setError(true, "Campo obrigatório", data);
            return false;
        } else{
            return true;
        }
    }

    public boolean comparaData(TextView data, String condicao) throws ParseException {
        if (!data.getText().toString().equals("dia/mês/ano")){
            Calendar dataAtual = Calendar.getInstance();
            Calendar dataInformada = Calendar.getInstance();

            dataAtual.set(Calendar.MONTH, dataAtual.get(Calendar.MONTH) + 1);
            dataAtual.set(Calendar.HOUR, 0);
            dataAtual.set(Calendar.MINUTE, 0);
            dataAtual.set(Calendar.SECOND, 0);
            dataAtual.set(Calendar.MILLISECOND, 0);

            String separador = "/";
            String[] dataSeparada = data.getText().toString().split(separador);
            dataInformada.set(Integer.parseInt(dataSeparada[2]), Integer.parseInt(dataSeparada[1]), Integer.parseInt(dataSeparada[0]),0,0,0);

            if(condicao.equals("Normal")){
                dataAtual.set(Calendar.MONTH, dataAtual.get(Calendar.MONTH)-3);
                if (!(dataInformada.compareTo(dataAtual) == -1)){
                    Toast.makeText(v.getContext(), "São necessários 3 meses de espera para doar sangue.", Toast.LENGTH_LONG).show();
                    setError(true, "", data);
                    return false;
                }
            } else if(condicao.equals("Cesariana")){
                dataAtual.set(Calendar.MONTH, dataAtual.get(Calendar.MONTH)-6);
                if (!(dataInformada.compareTo(dataAtual) == -1)){
                    Toast.makeText(v.getContext(), "São necessários 6 meses de espera para doar sangue.", Toast.LENGTH_LONG).show();
                    setError(true, "", data);
                    return false;
                }
            } else if(condicao.equals("Masculino")){
                dataAtual.set(Calendar.MONTH, dataAtual.get(Calendar.MONTH)-3);
                if (!(dataInformada.compareTo(dataAtual) == -1)){
                    Toast.makeText(v.getContext(), "São necessários 3 meses de espera entre doações.", Toast.LENGTH_LONG).show();
                    setError(true, "", data);
                    return false;
                }
            } else if(condicao.equals("Feminino")){
                dataAtual.set(Calendar.MONTH, dataAtual.get(Calendar.MONTH)-4);
                if (!(dataInformada.compareTo(dataAtual) == -1)){
                    Toast.makeText(v.getContext(), "São necessários 4 meses de espera entre doações.", Toast.LENGTH_LONG).show();
                    setError(true, "", data);
                    return false;
                }
            } else if(condicao.equals("Sim")){
                dataAtual.set(Calendar.YEAR, dataAtual.get(Calendar.YEAR)-1);
                if (!(dataInformada.compareTo(dataAtual) == -1)){
                    Toast.makeText(v.getContext(), "É necessário 1 ano de espera para doar sangue.", Toast.LENGTH_LONG).show();
                    setError(true, "", data);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPesoValid(EditText peso){
        if (peso.getText().toString().isEmpty()){
            setError(true, "Campo obrigatório", peso);
            return false;
        } else if (Float.parseFloat(peso.getText().toString()) < 50){
            setError(true, "Você tem que ter mais de 50Kg", peso);
            return false;
        } else {
            return true;
        }
    }

    public String getCheckedRadioButtonText(RadioGroup radioGroup){
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) radioGroup.findViewById(id);
        return rb.getText().toString();
    }

    public boolean getCheckedRadioButton(RadioGroup radioGroup){
        RadioButton rb = (RadioButton) radioGroup.findViewById(
                radioGroup.getCheckedRadioButtonId());
        if (rb.getText().equals("Sim"))
            return true;
        return false;
    }

    public boolean isRadioGroupChecked(RadioGroup radioGroup){
        boolean retorno = false;
        for (int i = 0; i<radioGroup.getChildCount(); i++) {
            if (radioGroup.getCheckedRadioButtonId() != -1)
                retorno = true;
        }
        return retorno;
    }

    public void setEnabled(boolean enabled, RadioGroup radioGroup, TextView textView){
        if (radioGroup != null && textView != null) {
            if (!enabled) {
                setError(enabled, "", textView);
            }

            for (int i = 0; i < radioGroup.getChildCount(); i++)
                radioGroup.getChildAt(i).setEnabled(enabled);

            radioGroup.setEnabled(enabled);
        }
    }

    public void setEnabled(boolean enabled, TextView textView, Context context){
        if (textView != null) {
            textView.setEnabled(enabled);
            if (enabled) {
                textView.setTextColor(context.getResources().getColor(R.color.textEnabled));
                setError(!enabled, "", textView);
            } else {
                setError(enabled, "", textView);
                textView.setText(context.getResources().getText(R.string.data));
                textView.setTextColor(context.getResources().getColor(R.color.textDisabled));
            }
        }
    }

    public void setEnabled(boolean enable, CheckBox checkBox) {
        if (checkBox != null) {
            if (enable) {
                checkBox.setEnabled(enable);
            } else {
                checkBox.setChecked(false);
                setError(enable, "", checkBox);
                checkBox.setEnabled(false);
            }
        }
    }

    public void setError(boolean error, CharSequence text, TextView textView){
        if (error){
            textView.setError(text);
        } else{
            textView.setError("", null);
        }
    }

    public void showDatePickerDialog(View v, String tag, Activity activity) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(activity.getFragmentManager(), tag);
    }

    public void showDatePickerDialog(View v, String tag, Activity activity, String data) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence("data", data);
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(activity.getFragmentManager(), tag);
    }

    public boolean isEmailValid(TextView email){
        String e = email.getText().toString();
        if (!e.isEmpty()){
            if (e.contains("@") && e.contains(".com")){
                setError(false, "", email);
                return true;
            } else{
                setError(true, "Email inválido", email);
                return false;
            }
        } else {
            setError(true, "Campo obrigatório", email);
            return false;
        }
    }

    public boolean isPasswordValid(TextView password) {
        String p = password.getText().toString();
        if (!p.isEmpty()) {
            if (p.length() > 5) {
                setError(false, "", password);
                return true;
            } else {
                setError(true, "Senha deve conter mais de 5 caracteres", password);
                return false;
            }
        } else {
            setError(true, "Campo obrigatório", password);
            return false;
        }
    }

    public boolean isFieldsTextEqual(TextView a, TextView b){
        String y = a.getText().toString();
        String z = b.getText().toString();
        if (!y.isEmpty() && !z.isEmpty()) {
            if (y.equals(z)) {
                setError(false, "", b);
                return true;
            }
            else {
                setError(true, "As senhas não estão iguais", b);
                return false;
            }
        }
        else {
            setError(true, "Campo obrigatório", b);
            return false;
        }
    }

    public void setRadioButtonCheckedByName(RadioGroup group, String label) {
        for (int i = 0; i<group.getChildCount(); i++) {
            RadioButton rb = (RadioButton) group.getChildAt(i);
            if (rb.getText().equals(label)) {
                rb.setChecked(true);
                break;
            }
        }
    }

    public void setRadioButtonCheckedByName(RadioGroup group, boolean label) {
        for (int i = 0; i<group.getChildCount(); i++) {
            RadioButton rb = (RadioButton) group.getChildAt(i);
            if (rb.getText().equals("Sim") && label) {
                rb.setChecked(true);
                break;
            } else{
                rb = (RadioButton) group.getChildAt(i+1);
                rb.setChecked(true);
                break;
            }
        }
    }

    public boolean isCidadeValid(EditText cidade) {
        if (cidade.getText().toString().isEmpty()){
            setError(true, "Campo obrigatório", cidade);
            return false;
        } else {
            return true;
        }
    }
}
