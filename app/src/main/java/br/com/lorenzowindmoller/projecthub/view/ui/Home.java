package br.com.lorenzowindmoller.projecthub.view.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import br.com.lorenzowindmoller.projecthub.R;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private ImageView button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        button_back = findViewById(R.id.button_back_home);

        button_back.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == button_back) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }

}
