package com.alaan.outn.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alaan.outn.R;
import com.alaan.outn.adapter.AdapterComment;
import com.alaan.outn.api.Repository;
import com.alaan.outn.api.interfaces.CallBack;
import com.alaan.outn.interfac.CommentListener;
import com.alaan.outn.model.Api;
import com.alaan.outn.model.Comment;
import com.alaan.outn.model.ModelCountry;
import com.alaan.outn.model.ModelPlatform;
import com.alaan.outn.utils.EndlessRecyclerOnScrollListener;
import com.alaan.outn.utils.Preference;
import com.alaan.outn.utils.Utils;
import com.squareup.picasso.Picasso;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentComment extends Activity {
    RecyclerView recyclerViewDoctor;
    EditText edtComment;
    ImageView btnComment;
    TextView txtReply,text_post,txt_fullName_platform_post,txt_date_time,title_news,txt_des;
    LinearLayout layReply;
    LinearLayout layComment,lin_post;
    ImageView btnClose,btnBack;
    Toolbar toolbar;
    CircleImageView imgProfile;
    private AdapterComment adapterComment;
    SwipeRefreshLayout swipe;
    String homeId;
    String status;
    ModelPlatform post;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.INSTANCE.setFillWindowAndTransparetStatusBar(this);
        setContentView(R.layout.fragment_comment);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            homeId = String.valueOf((extras.getInt("homeId")));
            status = extras.getString("model_type");
            post = (ModelPlatform) extras.getSerializable("post");
        }
        readView();
        functionView();
        hideSoftInput(this);

        if (post != null){
            txt_fullName_platform_post.setText(post.getUser());
            txt_date_time.setText(post.getElapsed_time());
            txt_des.setText(post.getDescription());
            title_news.setText(post.getTitle());
        }else {
            lin_post.setVisibility(View.GONE);
        }

    }

    public void readView() {

        recyclerViewDoctor = findViewById(R.id.recyclerViewDoctor);
        edtComment = findViewById(R.id.edtComment);
        btnComment = findViewById(R.id.btnComment);
        imgProfile = findViewById(R.id.imgProfile);
        btnClose = findViewById(R.id.btnClose);
        txtReply = findViewById(R.id.txtReply);
        layReply = findViewById(R.id.layReply);
        swipe = findViewById(R.id.swipe);
        layComment = findViewById(R.id.layComment);
        btnBack = findViewById(R.id.btnBack);

        lin_post = findViewById(R.id.lin_post);

        txt_fullName_platform_post = findViewById(R.id.txt_fullName_platform_post);
        txt_date_time = findViewById(R.id.txt_date_time);
        title_news = findViewById(R.id.title_news);
        txt_des = findViewById(R.id.txt_des);
        txt_des.setMovementMethod(new ScrollingMovementMethod());

    }


    public void functionView() {

        layReply.setVisibility(View.GONE);
        layComment.setVisibility( View.VISIBLE);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layReply.setVisibility(View.GONE);
                idReplay = null;
            }
        });
        adapterComment = new AdapterComment();
        @SuppressLint("WrongConstant")
        LinearLayoutManager doctorManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewDoctor.setLayoutManager(doctorManager);
        recyclerViewDoctor.setAdapter(adapterComment);
        adapterComment.setReplyLitener(new CommentListener<AdapterComment.CommentList>() {
            @Override
            public void onClick(boolean isReply, AdapterComment.CommentList item) {
                idReplay = isReply ? item.getComment().getReply_to() + "" : item.getComment().getId() + "";
                String sourceString = "Reply To  " + "<b>" +item.getComment().getUser().getFname()+ " "+item.getComment().getUser().getLname() + "</b>  ";
                txtReply.setText(Html.fromHtml(sourceString));
                edtComment.requestFocus();
                layReply.setVisibility(View.VISIBLE);
            }
        });
        adapterComment.setLongPressListener(new CommentListener<AdapterComment.CommentList>() {
            @Override
            public void onClick(boolean isReply, AdapterComment.CommentList item) {

            }
        });
        adapterComment.setLoadMoreLitener(new CommentListener<AdapterComment.CommentList>() {
            @Override
            public void onClick(boolean isReply, AdapterComment.CommentList item) {
                getDataReply(item.getComment().getId());
            }
        });
        recyclerViewDoctor.addOnScrollListener(new EndlessRecyclerOnScrollListener(doctorManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getData(currentPage);
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                {
                   onBackPressed();
                }
            }
        });

        Picasso.get().load(Utils.INSTANCE.getImagePicasso(Preference.INSTANCE.getImage()))
                                .placeholder(R.drawable.profile)
                                .error(R.drawable.profile)
                                .into(imgProfile);

        getData(1);
    }


    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void getData(int page) {
        if (page == 1) {
            swipe.setEnabled(true);
            swipe.setRefreshing(true);
        }

     new Repository().getComments(homeId,50,page,status,new CallBack<Api<List<Comment>>>(){

         @Override
         public void onSuccess(Api<List<Comment>> listApi) {
             super.onSuccess(listApi);
             if (swipe != null) {
                 swipe.setRefreshing(false);
                 swipe.setEnabled(false);
             }

             if (adapterComment != null) {
                 if (listApi.getStatus().getCode() == 200) {
                     List<AdapterComment.CommentList> commentLists = new ArrayList<>();
                     for (Comment comment : listApi.getData()) {
                         AdapterComment.CommentList commentList = new AdapterComment.CommentList();
                         commentList.setComment(comment);
                         commentList.setReplay(new ArrayList<Comment>());
                         commentLists.add(commentList);
                     }
                     adapterComment.put(commentLists);
                 }

             }


         }

         @Override
         public void onFail(@NotNull Exception e, int code) {
             super.onFail(e, code);
             if (swipe != null) {
                 swipe.setRefreshing(false);
                 swipe.setEnabled(false);
             }

         }
     });
    }

    private void getDataReply(final int commentId) {
      new   Repository().getCommentReply(commentId + "", 2000,
                                                 1,status, new CallBack<Api<List<Comment>>>() {
                    @Override
                    public void onSuccess(Api<List<Comment>> listApi) {
                        super.onSuccess(listApi);
                        if (adapterComment != null) {
                            if (listApi.getStatus().getCode()==200) {
                                for (AdapterComment.CommentList list : adapterComment.getLists()) {
                                    if (list.getComment().getId() == commentId) {
                                        Set<Comment> comments = new ArraySet<>();
                                        comments.addAll(list.getReplay());
                                        comments.addAll(listApi.getData());
                                        list.getReplay().clear();
                                        list.getReplay().addAll(comments);
                                    }
                                }
                                adapterComment.notifyDataSetChanged();
                            }

                        }
                    }
                });
    }

    String idReplay;

    private void add() {
        if (edtComment.getText() == null || edtComment.getText().length() == 0) {
            return;
        }
        edtComment.setEnabled(false);
        btnComment.setEnabled(false);
        String text = edtComment.getText().toString();
       new Repository().addComment(homeId, text, idReplay,status, new CallBack<Api<Comment>>() {
            @Override
            public void onSuccess(Api<Comment> commentApi) {
                super.onSuccess(commentApi);
                if (commentApi.getStatus().getCode() == 200) {
                    if (commentApi.getData().getReply_to() == null || commentApi.getData().getReply_to().isEmpty()) {
                        adapterComment.put(commentApi.getData());
                    } else {
                        for (AdapterComment.CommentList list : adapterComment.getLists()) {
                            if ((list.getComment().getId() + "").equals(commentApi.getData().getReply_to())) {
                               // list.getComment().setHasReply(list.getComment().getHasReply() + 1);
                                list.getReplay().add(commentApi.getData());
                            }
                        }
                        adapterComment.notifyDataSetChanged();
                    }
                    idReplay = null;
                    edtComment.setText("");
                    btnClose.performClick();
                }

                edtComment.setEnabled(true);
                btnComment.setEnabled(true);
            }

            @Override
            public void onFail(@NotNull Exception e, int code) {
                super.onFail(e, code);
                edtComment.setEnabled(true);
                btnComment.setEnabled(true);

            }

        });
    }

    private void changeCommnetStatus(Comment comment) {
        new Repository().readComment(comment.getId() + "",status, new CallBack<Api<List<Void>>>() {
            @Override
            public void onSuccess(Api<List<Void>> listApi) {
                super.onSuccess(listApi);
            }

            @Override
            public void onFail(@NotNull Exception e, int code) {
                super.onFail(e, code);


            }

        });

    }

    private void dialogDelete(final boolean isReply, final Comment comment) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you want to Delete this?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        delete(isReply, comment);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    private void delete(final boolean isReply, Comment comment) {
        new Repository().deleteComments(comment.getId() + "", new CallBack<Api<List<Void>>>() {
            @Override
            public void onSuccess(Api<List<Void>> listApi) {
                super.onSuccess(listApi);
                if (!isReply) {
                } else {
                }
            }

            @Override
            public void onFail(@NotNull Exception e, int code) {
                super.onFail(e, code);

            }

        });

    }
}
