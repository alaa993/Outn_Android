package com.alaan.outn.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alaan.outn.R;
import com.alaan.outn.adapter.AdapterCity;
import com.alaan.outn.api.Repository;
import com.alaan.outn.api.interfaces.CallBack;
import com.alaan.outn.interfac.ItemClickListener;
import com.alaan.outn.interfac.ListItem;
import com.alaan.outn.model.Api;
import com.alaan.outn.model.AreasItem;
import com.alaan.outn.model.ModelCountry;
import com.alaan.outn.model.StatesItem;
import com.alaan.outn.utils.Preference;
import com.alaan.outn.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ActivityCity extends Activity {



    SearchView searchView;
    RecyclerView recyclerView;
    ImageView btnBack;
    private AdapterCity adapter;
    List<StatesItem> params = new ArrayList<>();
    List<StatesItem> searchItem = new ArrayList<>();
    private ItemClickListener<ListItem> listener;
    ProgressDialog progressDialog;
    TextView txtTitle;
       String id;

    protected void onCreate(Bundle savedInstanceState) {
        Utils.INSTANCE.setFillWindowAndTransparetStatusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        readView();
        functionView();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("params");
        }
        getCity(Integer.parseInt(id));
    }


    public void readView() {


        searchView = (SearchView) findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.rv_filter);
        btnBack = findViewById(R.id.btnBack);

        txtTitle = findViewById(R.id.txtTitle);

        if (Preference.INSTANCE.getLanguage().equals("en")){

            txtTitle.setText(getString(R.string.city));

        }else if (Preference.INSTANCE.getLanguage().equals("ar")) {
            txtTitle.setText(" المدينة");
        }else {
            txtTitle.setText("شار");
        }

        searchView.setQueryHint("Search State");
        EditText searchEditText = (EditText) searchView.findViewById(R.id.search_src_text);
        // searchEditText.setTextColor(getResources().getColor(R.color.white));
        //  searchEditText.setHintTextColor(getResources().getColor(R.color.gray_btn_click));

    }


    public void functionView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        adapter = new AdapterCity();
        adapter.setListener(new ItemClickListener<StatesItem>() {
            @Override
            public void area(@NotNull AreasItem item) {

            }

            @Override
            public void city(@NotNull StatesItem item) {

                Intent intent = getIntent();
                intent.putExtra("id",item.getId());
                intent.putExtra("name",item.getName());
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void state(@NotNull StatesItem item) {



            }



            @Override
            public void onClick(@NotNull ModelCountry item) {


            }
        });

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("")){

                    adapter.clearAndPut(params);

                }else {

                    searchItem.clear();
                    for (StatesItem row : params) {

                        if (row.getName().toLowerCase().contains(newText.toLowerCase())) {
                            searchItem.add(row);
                        }
                    }
                    adapter.clearAndPut(searchItem);
                }

                return false;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void initData(List<StatesItem> params){

        adapter.clearAndPut(params);
    }

    private void getCity(int id){

        showLoading();
        new  Repository().getCity(id, new CallBack<Api<ModelCountry>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(Api<ModelCountry> modelCountryApi) {
                super.onSuccess(modelCountryApi);
                hideLoading();
                if (modelCountryApi.getData().getAlpha2Code() != null ){
                    if (modelCountryApi.getData().getStates().size() > 0) {
                        initData(modelCountryApi.getData().getStates());
                        params.addAll(modelCountryApi.getData().getStates());
                    }
                }

            }

            @Override
            public void onFail(@NotNull Exception e, int code) {
                super.onFail(e, code);
                hideLoading();
            }
        });
    }

    private void showLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.pls_wait));
        progressDialog.show();
    }

    private void hideLoading() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}

