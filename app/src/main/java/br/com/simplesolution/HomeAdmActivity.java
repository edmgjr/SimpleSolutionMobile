package br.com.simplesolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.UserHandle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.simplesolution.Fragment.AtividadesAdmFragment;
import br.com.simplesolution.Fragment.IdiomasAdmFragment;
import br.com.simplesolution.Fragment.TurmasAdmFragment;
import br.com.simplesolution.Fragment.UsersAdmFragment;

public class HomeAdmActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_adm);


        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UsersAdmFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.nav_users:
                            selectedFragment = new UsersAdmFragment();
                            break;
                        case R.id.nav_turmas:
                            selectedFragment = new TurmasAdmFragment();
                            break;
                        case R.id.nav_atividades:
                            selectedFragment = new AtividadesAdmFragment();
                            break;
                        case R.id.nav_idiomas:
                            selectedFragment = new IdiomasAdmFragment();
                            break;
                    }

                    if (selectedFragment != null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }
                    return true;
                }
            };
}
