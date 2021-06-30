package com.alaan.outn.selectimage;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alaan.outn.R;

import com.google.android.material.bottomsheet.BottomSheetDialog;


public class DialogEntkabeImag extends BottomSheetDialog {
    Context context;
    TextView  txt_gallery, txt_camra;
    EntkabhImag entkabhImag;
    boolean delete;


    public DialogEntkabeImag(@NonNull Context context, EntkabhImag entkabhImag, boolean delete) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        this.context = context;
        this.delete = delete;
        this.entkabhImag = entkabhImag;
        init(context);
    }


    void init(Context context) {
        View view = View.inflate(context, R.layout.dialog_entkabe_imag, null);
        setContentView(view);


        txt_camra = view.findViewById(R.id.txt_camra);


        txt_gallery = view.findViewById(R.id.txt_gallery);



        txt_camra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entkabhImag.entkabhImagCamra();
                //entkabhImag.entkabhImagCamra();
                dismiss();
            }
        });
        txt_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entkabhImag.entkabhImagGallery();
                dismiss();

            }
        });




    }
}
