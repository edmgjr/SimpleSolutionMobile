package br.com.simplesolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.simplesolution.Fragment.AvaliacoesAlunoFragment;
import br.com.simplesolution.Fragment.AvaliacoesProfFragment;
import br.com.simplesolution.Fragment.FrequenciaAlunoFragment;
import br.com.simplesolution.Fragment.FrequenciaProfFragment;
import br.com.simplesolution.Fragment.IdiomasProfFragment;
import br.com.simplesolution.Fragment.UsersAdmFragment;

public class HomeProfActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_prof);


        bottomNavigationView = findViewById(R.id.bottomprof_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrequenciaProfFragment()).commit();
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.nav_avaliacoesprof:
                            selectedFragment = new AvaliacoesProfFragment();
                            break;

                        case R.id.nav_idiomasprof:
                            selectedFragment = new IdiomasProfFragment();
                            break;

                        case R.id.nav_frequenciaprof:
                            selectedFragment = new FrequenciaProfFragment();




                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }
                    return true;
                }
            };
}
