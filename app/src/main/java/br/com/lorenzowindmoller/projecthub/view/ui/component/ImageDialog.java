package br.com.lorenzowindmoller.projecthub.view.ui.component;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.lorenzowindmoller.projecthub.R;

public class ImageDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private EditText url;

    private ImageView button_copy;

    private ImageDialogListener listener;

    ClipboardManager clipboardManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(
                R.layout.image_dialog, null);

        builder.setView(view)
                .setTitle("Insert an URL image")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyTextImageDialog(url.getText().toString());
                    }
                });

        url = view.findViewById(R.id.text_url_image);
        button_copy = view.findViewById(R.id.copy_text);

        button_copy.setOnClickListener(this);

        clipboardManager = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        return builder.create();
    }

    public void onClick(View v) {
        if (v == button_copy) {
            ClipData clipData = clipboardManager.getPrimaryClip();
            ClipData.Item item = clipData.getItemAt(0);

            url.setText(item.getText().toString());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ImageDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ImageDialogListener {
        void applyTextImageDialog(String url);
    }

}
