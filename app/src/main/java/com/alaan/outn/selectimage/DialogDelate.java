package com.alaan.outn.selectimage;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.alaan.outn.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class DialogDelate extends BottomSheetDialog {

    Context context;
    ChangeData changeData;
    TextView txt_delete,txt_titele;


    public DialogDelate(@NonNull Context context, ChangeData changeData) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        this.changeData = changeData;
        init(context);
    }


    void init(final Context context) {

        View view = View.inflate(context, R.layout.dialog_delate, null);
        setContentView(view);
        txt_delete = view.findViewById(R.id.txt_delete);
        txt_titele = view.findViewById(R.id.txt_titele);



        txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeData.onDalate();
                dismiss();
            }
        });
    }
}
