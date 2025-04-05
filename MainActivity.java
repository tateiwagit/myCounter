package com.example.mycounter;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mycounter.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateCounterBasedOnVolumeChange(int volumeChange) {
        // 1. NavHostFragment を取得
        //    activity_main.xml で定義した NavHostFragment の ID (nav_host_fragment_content_main) を指定
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);

        if (navHostFragment != null) {
            // 2. NavHostFragment 内で現在表示されているプライマリフラグメントを取得
            Fragment currentFragment = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();

            // 3. 現在のフラグメントが FirstFragment のインスタンスか確認
            if (currentFragment instanceof FirstFragment) {
                // 4. FirstFragment であれば、その updateCounter メソッドを呼び出して変化量を渡す
                ((FirstFragment) currentFragment).updateCounter(volumeChange);
            } else {
                // (任意) FirstFragment 以外が表示されている場合のログなど
                Log.d("MainActivity", "Current fragment is not FirstFragment.");
            }
        } else {
            // (任意) NavHostFragment が見つからない場合のエラーログなど
            Log.e("MainActivity", "NavHostFragment not found!");
        }
    }
}