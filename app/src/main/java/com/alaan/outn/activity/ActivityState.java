package com.alaan.outn.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alaan.outn.R;
import com.alaan.outn.adapter.AdapterState;
import com.alaan.outn.interfac.ItemClickListener;
import com.alaan.outn.interfac.ListItem;
import com.alaan.outn.model.AreasItem;
import com.alaan.outn.model.CitiesItem;
import com.alaan.outn.model.ModelCountry;
import com.alaan.outn.model.StatesItem;
import com.alaan.outn.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityState extends Activity {



    SearchView searchView;
    RecyclerView recyclerView;
    ImageView btnBack;
    private AdapterState adapter;
    List<StatesItem> params = new ArrayList<>();
    List<StatesItem> searchItem = new ArrayList<>();
    private ItemClickListener<ListItem> listener;
    ProgressDialog progressDialog;



    protected void onCreate(Bundle savedInstanceState) {
        Utils.INSTANCE.setFillWindowAndTransparetStatusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        readView();
      //  functionView();

        Bundle extras = getIntent().getExtras();
        List<StatesItem> a = (List<StatesItem>) extras.getSerializable("params");

        initData(a);
        params.addAll(a);



    }


    public void readView() {


        searchView = (SearchView) findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.rv_filter);
        btnBack = findViewById(R.id.btnBack);


        searchView.setQueryHint("Search State");
        EditText searchEditText = (EditText) searchView.findViewById(R.id.search_src_text);
       // searchEditText.setTextColor(getResources().getColor(R.color.white));
      //  searchEditText.setHintTextColor(getResources().getColor(R.color.gray_btn_click));

    }


//    public void functionView() {
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setHasFixedSize(true);
//        adapter = new AdapterState();
//        adapter.setListener(new ItemClickListener<StatesItem>() {
//            @Override
//            public void area(@NotNull AreasItem item) {
//
//            }
//
//            @Override
//            public void city(@NotNull CitiesItem item) {
//
//            }
//
//            @Override
//            public void state(@NotNull StatesItem item) {
//
//                Intent intent = getIntent();
//                intent.putExtra("id",item.getId());
//                intent.putExtra("name",item.getName());
//                intent.putExtra("model", (Serializable) item.getCities());
//                setResult(RESULT_OK, intent);
//                finish();
//
//            }
//
//
//
//            @Override
//            public void onClick(@NotNull ModelCountry item) {
//
//
//            }
//        });
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(adapter);
//
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                if (newText.equals("")){
//
//                    adapter.clearAndPut(params);
//
//                }else {
//
//                    searchItem.clear();
//                    for (StatesItem row : params) {
//
//                        if (row.getName().toLowerCase().contains(newText.toLowerCase())) {
//                            searchItem.add(row);
//                        }
//                    }
//                    adapter.clearAndPut(searchItem);
//                }
//
//                return false;
//            }
//        });
//
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//    }

    public void initData(List<StatesItem> params){

        adapter.clearAndPut(params);
    }
}
