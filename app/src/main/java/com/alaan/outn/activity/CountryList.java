package com.alaan.outn.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alaan.outn.R;
import com.alaan.outn.adapter.Adapter_Country;
import com.alaan.outn.api.Repository;
import com.alaan.outn.api.interfaces.CallBack;
import com.alaan.outn.interfac.ItemClickListener;
import com.alaan.outn.interfac.ListItem;
import com.alaan.outn.model.Api;
import com.alaan.outn.model.AreasItem;
import com.alaan.outn.model.CitiesItem;
import com.alaan.outn.model.ModelCountry;
import com.alaan.outn.model.StatesItem;
import com.alaan.outn.utils.Preference;
import com.alaan.outn.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CountryList extends Activity {

    SearchView searchView;
    RecyclerView recyclerView;
    ImageView btnBack;
    private Adapter_Country adapter;
    List<ModelCountry> list = new ArrayList<>();
    List<ModelCountry> searchItem = new ArrayList<>();
    List<CitiesItem> citiesItems = new ArrayList<>();
    List<StatesItem> statesItems = new ArrayList<>();
    private ItemClickListener<ListItem> listener;

     TextView txtTitle;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.INSTANCE.setFillWindowAndTransparetStatusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        readView();
        functionView();
        if (savedInstanceState == null)
            getCountry();
    }


    public void readView() {


        searchView = (SearchView) findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.rv_filter);
        btnBack = findViewById(R.id.btnBack);
        txtTitle = findViewById(R.id.txtTitle);

        if (Preference.INSTANCE.getLanguage().equals("en")){

            txtTitle.setText(getString(R.string.list_country));

        }else if (Preference.INSTANCE.getLanguage().equals("ar")) {
            txtTitle.setText("قائمة البلد");
        }else {
            txtTitle.setText("لیستەی وڵات");
        }



        searchView.setQueryHint("Search Country");
        EditText searchEditText = (EditText) searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.gray_btn_click));

    }

    public void functionView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        adapter = new Adapter_Country();
        adapter.setListener(new ItemClickListener<ModelCountry>() {

            @Override
            public void area(@NotNull AreasItem item) {

            }

            @Override
            public void city(@NotNull StatesItem item) {

            }

            @Override
            public void state(@NotNull StatesItem item) {

            }

            @Override
            public void onClick(ModelCountry item) {
                if (item != null){
//                    for (int i = 0; i <item.getStates().size() ; i++) {
//                        statesItems.addAll(item.getStates());
//                    }
                    for (int i = 0; i <item.getStates().size() ; i++) {

                        citiesItems.addAll(item.getStates().get(i).getCities());

                    }
                    Intent intent = getIntent();
                    intent.putExtra("city", (Serializable) citiesItems);
                    intent.putExtra("id",item.getId());
                    intent.putExtra("name",item.getName());
                    //intent.putExtra("state", (Serializable) item.getAlpha2Code());
                    setResult(RESULT_OK, intent);
                    finish();

                }

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

                    adapter.clearAndPut(list);

                }else {

                    searchItem.clear();
                    for (ModelCountry row : list) {

                        if (row.getName().toLowerCase().contains(newText.toLowerCase())) {
                            searchItem.add(row);
                        }
                    }
                    adapter.clearAndPut(searchItem);
                }

                return false;
            }
        });




        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getCountry() {


        showLoading();

        new Repository().getCountries(new CallBack<Api<List<ModelCountry>>>() {
            @Override
            public void onSuccess(Api<List<ModelCountry>> listApi) {
                super.onSuccess(listApi);
                hideLoading();
                if (listApi.getStatus().getCode() == 200){
                adapter.clearAndPut(listApi.getData());
                    put(listApi.getData());

                }
            }

            @Override
            public void onFail(@NotNull Exception e, int code) {
                super.onFail(e, code);
                hideLoading();

            }
        });

    }

    public void put(@Nullable List<ModelCountry> items) {
        if (items != null) {
            list.addAll(items);
        }

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

