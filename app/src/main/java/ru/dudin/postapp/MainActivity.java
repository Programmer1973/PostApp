package ru.dudin.postapp;

/**
 * Application for making posts in RecyclerView by using ORMLite and DAO.
 *
 * @created 12.03.2019
 * @author Andrey Dudin <programmer1973@mail.ru>
 * @version 0.1.0.2019.03.12
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.dudin.postapp.OrmDatabase.DatabaseHelper;
import ru.dudin.postapp.OrmDatabase.PostOrm;
import ru.dudin.postapp.PostDialogFragments.CreatePostDialogFragment;
import ru.dudin.postapp.PostDialogFragments.DeletePostDialogFragment;
import ru.dudin.postapp.PostDialogFragments.UpdatePostDialogFragment;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements CreatePostDialogFragment.Listener,
                                                               DeletePostDialogFragment.Listener,
                                                               UpdatePostDialogFragment.Listener {

    private FloatingActionButton mFab;
    private List<PostOrm> posts = new ArrayList<>();
    private PostListAdapter mPostListAdapter;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm", Locale.US);

    private DatabaseHelper mHelper = DatabaseHelper.getInstance();

    @Override
    protected void onCreate ( final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SelectPostTask().execute();

        mFab = findViewById(R.id.view_fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //              mFab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_clear_black_48dp));
                showCreatePostDialog();
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPostListAdapter = new PostListAdapter(this, posts);

        mPostListAdapter.registerListener(new PostListAdapter.AdapterDialogListener() {

            @Override
            public void showDeletePostDialog() {
                new DeletePostDialogFragment().show(getSupportFragmentManager(), "DeletePostDialogFragment");
            }

            @Override
            public void showUpdatePostDialog() {
                new UpdatePostDialogFragment().show(getSupportFragmentManager(), "UpdatePostDialogFragment");
            }
        });

        recyclerView.setAdapter(mPostListAdapter);
    }

    private void showCreatePostDialog () {
        FragmentManager fm = getSupportFragmentManager();
        CreatePostDialogFragment cpdf = new CreatePostDialogFragment();
        cpdf.show(fm, "CreatePostDialogFragment");
    }

    //=======================================
    @Override
    public void onCreatePost (CreatePostDialogFragment fragment, String postMessage){
        new InsertPostTask().execute(postMessage, String.valueOf(mDateFormat.format(Calendar.getInstance().getTime())));
        mPostListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeletePost(DeletePostDialogFragment fragment) {
        new DeletePostTask().execute(mPostListAdapter.getPostId());
        mPostListAdapter.notifyItemRemoved(mPostListAdapter.getAdapterPosition());
    }

    @Override
    public void onDeleteAllPosts(DeletePostDialogFragment fragment) {
        new DeleteAllPostsTask().execute(mPostListAdapter.getPostId());
        mPostListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdatePost(UpdatePostDialogFragment fragment, String postMessage) {
        new UpdatePostTask().execute(String.valueOf(mPostListAdapter.getPostId()),
                postMessage,
                String.valueOf(getString(R.string.updated) + ": "
                        + mDateFormat.format(Calendar.getInstance().getTime())));
        mPostListAdapter.notifyItemChanged(mPostListAdapter.getAdapterPosition());
    }

    //==================================================================
    private class InsertPostTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//           super.onPostExecute(aVoid);
            new SelectPostTask().execute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                final Dao<PostOrm, Integer> dao = mHelper.getDao(PostOrm.class);
                final PostOrm post = new PostOrm();
                post.setMessage(strings[0]);
                post.setCreatedTime(strings[1]);
                dao.create(post);

                return null;
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class UpdatePostTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
//            super.onPostExecute(aVoid);
            new SelectPostTask().execute();
        }

        @Override
        protected Void doInBackground(final String... strings) {

            try {
                final Dao<PostOrm, Integer> dao = mHelper.getDao(PostOrm.class);
                final UpdateBuilder<PostOrm, Integer> updateBuilder = dao.updateBuilder();
                updateBuilder.where().eq(PostOrm.COLUMN_NAME_ID, strings[0]);
                updateBuilder.updateColumnValue(PostOrm.COLUMN_NAME_MESSAGE, strings[1]);
                updateBuilder.updateColumnValue(PostOrm.COLUMN_NAME_CREATED_TIME, strings[2]);
                updateBuilder.update();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class DeletePostTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
//            super.onPostExecute(aVoid);
            new SelectPostTask().execute();
        }
        @Override
        protected Void doInBackground(final Integer... position) {

            try {
                Dao<PostOrm, Integer> dao = mHelper.getDao(PostOrm.class);
                DeleteBuilder<PostOrm, Integer> deleteBuilder = dao.deleteBuilder();
                deleteBuilder.where().eq(PostOrm.COLUMN_NAME_ID, position[0]);
                deleteBuilder.delete();

                return null;
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class DeleteAllPostsTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
//            super.onPostExecute(aVoid);
            new SelectPostTask().execute();
        }
        @Override
        protected Void doInBackground(final Integer... position) {

            try {
                Dao<PostOrm, Integer> dao = mHelper.getDao(PostOrm.class);
                dao.deleteBuilder().delete();

                return null;
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class SelectPostTask extends AsyncTask<Void, Void, List<PostOrm>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final List<PostOrm> posts) {
            // super.onPostExecute(users);
            mPostListAdapter.setData(posts);
        }

        @Override
        protected List<PostOrm> doInBackground(final Void... voids) {

            try {
                final Dao<PostOrm, Integer> dao = mHelper.getDao(PostOrm.class);
                return dao.queryForAll();
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}