package com.alaan.outn.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alaan.outn.R;
import com.alaan.outn.application.G;
import com.alaan.outn.interfac.CommentListener;
import com.alaan.outn.model.Comment;
import com.alaan.outn.utils.Preference;
import com.alaan.outn.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterComment extends RecyclerView.Adapter<AdapterComment.MyViewHolder> {

    private Context mContext;
    private List<CommentList> lists = new ArrayList<>();
    private CommentListener<CommentList> listener;
    private CommentListener<CommentList> replyLitener;
    private CommentListener<CommentList> loadMoreLitener;
    private CommentListener<CommentList> longPressListener;
    private int lastSelectedPosition;
    private boolean choosingMode;
    private boolean isReplay = false;
  //  private boolean isLogin = Preference.isLogin();

    public AdapterComment() {
    }

    public void setListener(CommentListener<CommentList> listener) {
        this.listener = listener;
    }

    public void setReplyLitener(CommentListener<CommentList> replyLitener) {
        this.replyLitener = replyLitener;
    }

    public void setLoadMoreLitener(CommentListener<CommentList> loadMoreLitener) {
        this.loadMoreLitener = loadMoreLitener;
    }

    public void setReplay(boolean replay) {
        isReplay = replay;
    }

    public void setLongPressListener(CommentListener<CommentList> longPressListener) {
        this.longPressListener = longPressListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_comment, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        CommentList item = lists.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public List<CommentList> getLists() {
        return lists;
    }

    public void clearAndPut(List<CommentList> items) {
        lists.clear();
        if (items != null) {
            lists.addAll(items);
        }

        notifyDataSetChanged();
    }

    public void put(Comment item) {
        if (item != null) {
            CommentList commentList = new CommentList();
            commentList.setComment(item);
            lists.add(commentList);
        }
        notifyDataSetChanged();
    }

    public void put(List<CommentList> items) {
        if (items != null) {
            lists.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void enableChoosingMode() {
        choosingMode = true;
    }

    public CommentList getItem(int position) {
        return lists.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        CommentList item;
        TextView txtName;
        TextView txt_comment;
        TextView txtTime;
        TextView txtReplay;
        TextView btnLoadMore;
        RecyclerView recyclerView;
        CircleImageView imgProfile;

        MyViewHolder(View view) {
            super(view);

            txtName = view.findViewById(R.id.txtName);
            txt_comment = view.findViewById(R.id.txt_comment);
            txtReplay = view.findViewById(R.id.txtReplay);
            btnLoadMore = view.findViewById(R.id.btnLoadMore);
            txtTime = view.findViewById(R.id.txtTime);
            recyclerView = view.findViewById(R.id.recyclerView);
            imgProfile = view.findViewById(R.id.imgProfile);
        }

        void bind(CommentList i) {
            item = i;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            if (item.getComment().getUser().getLname() != null || item.getComment().getUser().getFname() != null){
                String sourceString = "<b>"  +item.getComment().getUser().getFname()+ " "+item.getComment().getUser().getLname() + "</b>";
                txtName.setText(Html.fromHtml(sourceString));
            }

            txt_comment.setText(i.getComment().getText());
            txtTime.setText(i.getComment().getCreatedAt());
            txtReplay.setOnClickListener(this);
            txtReplay.setVisibility( View.VISIBLE);
            btnLoadMore.setOnClickListener(this);
            btnLoadMore.setText("Load " + (Math.abs(i.getComment().getHasReply() - i.getReplay().size())) + " Replies");
            btnLoadMore.setVisibility(i.getComment().getHasReply() > i.getReplay().size() ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(isReplay ? View.GONE : View.VISIBLE);


            Picasso.get().load(Utils.INSTANCE.getImagePicasso(i.getComment().getUser().getAvatar()))
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(imgProfile);


            if (!isReplay) {
                AdapterComment adapterComment = new AdapterComment();
                adapterComment.setReplay(true);
                adapterComment.setReplyLitener(replyLitener);
                adapterComment.setListener(listener);
                List<CommentList> commentLists = new ArrayList<>();
                for (Comment comment : i.getReplay()) {
                    CommentList commentList = new CommentList();
                    commentList.setComment(comment);
                    commentLists.add(commentList);
                }
                adapterComment.clearAndPut(commentLists);
                LinearLayoutManager linearLayout = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayout);
                recyclerView.setAdapter(adapterComment);
            }
        }

        @Override
        public void onClick(View view) {
            if (view == itemView) {
                if (listener != null) {
                    listener.onClick(isReplay, item);
                }
            } else if (view == txtReplay) {
                if (replyLitener != null) {
                    replyLitener.onClick(isReplay, item);
                }
            } else if (view == btnLoadMore) {
                if (loadMoreLitener != null) {
                    loadMoreLitener.onClick(isReplay, item);
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (longPressListener != null) {
                longPressListener.onClick(isReplay, item);
            }
            return false;
        }
    }

    public static class CommentList {
        Comment comment;
        List<Comment> replay = new ArrayList<>();

        public Comment getComment() {
            return comment;
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public List<Comment> getReplay() {
            return replay;
        }

        public void setReplay(List<Comment> replay) {
            this.replay = replay;
        }
    }

}