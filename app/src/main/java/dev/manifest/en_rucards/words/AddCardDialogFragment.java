package dev.manifest.en_rucards.words;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import dev.manifest.en_rucards.R;

public class AddCardDialogFragment extends DialogFragment {

    public static final String EXTRA_WORD = "word";

    public static AddCardDialogFragment newInstance() {
        return new AddCardDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_card, null);
        final EditText editText = view.findViewById(R.id.et_new_card);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String newWord = editText.getText().toString();
                        if (!newWord.isEmpty()) {
                            sendResult(Activity.RESULT_OK, newWord);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    private void sendResult(int resultCode, String newWord) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_WORD, newWord);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
