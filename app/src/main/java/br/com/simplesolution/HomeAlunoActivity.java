package br.com.simplesolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.simplesolution.Fragment.AvaliacoesAlunoFragment;
import br.com.simplesolution.Fragment.FrequenciaAlunoFragment;
import br.com.simplesolution.Fragment.FrequenciaProfFragment;
import br.com.simplesolution.Fragment.UsersAdmFragment;

public class HomeAlunoActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_aluno);

        bottomNavigationView = findViewById(R.id.bottomaluno_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrequenciaAlunoFragment()).commit();
    }





    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.nav_avaliacoesaluno:
                            selectedFragment = new AvaliacoesAlunoFragment();
                            break;

                        case R.id.nav_frequenciaaluno:
                            selectedFragment = new FrequenciaAlunoFragment();
                            break;




                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }
                    return true;
                }
            };
}
