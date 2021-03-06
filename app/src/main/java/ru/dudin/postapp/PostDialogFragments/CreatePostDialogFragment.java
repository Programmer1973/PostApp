package ru.dudin.postapp.PostDialogFragments;

/**
 * @created 12.03.2019
 * @author Andrey Dudin <programmer1973@mail.ru>
 * @version 0.1.0.2019.03.12
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;

import ru.dudin.postapp.R;

public class CreatePostDialogFragment extends DialogFragment {

    private Listener mListener;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListener = (Listener) getActivity();
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setTitle(R.string.create_post)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {

                    @SuppressWarnings("ConstantConditions")
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        final TextInputEditText postTextInputEditText = getDialog().findViewById(R.id.view_post);
                        final String postMessage = postTextInputEditText.getText().toString();
                        if (TextUtils.isEmpty(postMessage)) {
                            postTextInputEditText.setError(getString(R.string.error_no_text));
                        } else {
                            mListener.onCreatePost(CreatePostDialogFragment.this, postMessage);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.fragment_post_dialog);
        } else {
            builder.setView(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_post_dialog, null, false));
        }

        return builder.create();
    }

    public interface Listener {

        void onCreatePost(CreatePostDialogFragment fragment, String text);
    }
}