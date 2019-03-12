package ru.dudin.postapp;

/**
 * @created 12.03.2019
 * @author Andrey Dudin <programmer1973@mail.ru>
 * @version 0.1.0.2019.03.12
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.dudin.postapp.OrmDatabase.PostOrm;

import java.util.List;


class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    private AdapterDialogListener mAdapterDialogListener;
    private final LayoutInflater mInflater;
    private List<PostOrm> mPosts;
    private int mAdapterPosition;
    private int mPostId;


    public PostListAdapter(final Context context, final List<PostOrm> mPosts) {
        mInflater = LayoutInflater.from(context);
        this.mPosts = mPosts;
    }

    public void registerListener(AdapterDialogListener mAdapterDialogListener){
        this.mAdapterDialogListener = mAdapterDialogListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.view_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textViewPost.setText(mPosts.get(position).getMessage());
        holder.textViewPostDateTime.setText(mPosts.get(position).getCreatedTime());
    }

    @Override
    public int getItemCount() {
        return mPosts == null ? 0 : mPosts.size();
    }

    public void setData (final List<PostOrm> posts) {
        mPosts = posts;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewPost;
        public TextView textViewPostDateTime;
        public TextView textViewXDeletePost;

        public ViewHolder(final View itemView) {
            super(itemView);

            textViewPost = itemView.findViewById(R.id.text_view_post);
            textViewPostDateTime = itemView.findViewById(R.id.text_view_post_date_time);
            textViewXDeletePost = itemView.findViewById(R.id.text_view_delete_post);

            textViewXDeletePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    invocationShowDeletePostDialog();
                }
            });

            textViewPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    invocationShowUpdatePostDialog();
                }
            });

            textViewPostDateTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    invocationShowUpdatePostDialog();
                }
            });
        }

        private void invocationShowDeletePostDialog() {
            adapterPosition();
            mAdapterDialogListener.showDeletePostDialog();
        }

        private void invocationShowUpdatePostDialog() {
            adapterPosition();
            mAdapterDialogListener.showUpdatePostDialog();
        }

        private void adapterPosition() {
            mAdapterPosition = getAdapterPosition();
            mPostId = mPosts.get(mAdapterPosition).getId();
        }
    }

    public int getAdapterPosition() {
        return mAdapterPosition;
    }

    public int getPostId() {
        return mPostId;
    }

    interface AdapterDialogListener {
        void showDeletePostDialog();
        void showUpdatePostDialog();
    }
}