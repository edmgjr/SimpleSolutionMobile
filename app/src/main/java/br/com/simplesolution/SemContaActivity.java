package br.com.simplesolution;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SemContaActivity extends AppCompatActivity {

    ImageView buttonVolta;
    TextView semConta;
    int uni79 = 0x1F61F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_conta);


        String emoji79 = getEmoji(uni79);
        buttonVolta = findViewById(R.id.buttonVoltaSemConta);
        semConta = findViewById(R.id.textViewOlaSemConta);
        semConta.setText("Ah...\nque pena "+emoji79);

        buttonVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String getEmoji(int uni) {
        return new String(Character.toChars(uni));
    }
}
