package com.example.frcnetto.compartilhevida.controler;

import android.content.Context;

public class ExceptionController {
    private Context context;
    private Exception exception;

    public ExceptionController(Context context, Exception exception) {
        this.context = context;
        this.exception = exception;
    }
}
