package com.yuriyk_israelb.ex1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStartGame;
    private Switch swtMusic;
    private TextView txvTitle;
    private Spinner spinLevel;
    SharedPreferences sp;
    int begin = 20, inter = 50, prof = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //used SaredPreferences to save the music switch state
        sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String[] level = new String[]{"Beginner", "Intermediate", "Professional"};

        txvTitle = findViewById(R.id.txvWelcomeID);
        btnStartGame = findViewById(R.id.btnNewGameID);
        swtMusic = findViewById(R.id.swchMusicID);
        spinLevel = findViewById(R.id.spinLevelID);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, level);
        spinLevel.setAdapter(adapter);
        btnStartGame.setOnClickListener(this);
        if(sp.getBoolean("isMusicOn", false)) {
            swtMusic.setChecked(true);
        }

        spinLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SharedPreferences.Editor editor = sp.edit();
                if(parentView.getItemAtPosition(position).toString().equals("Beginner"))
                    editor.putInt("level" ,begin);
                else if(parentView.getItemAtPosition(position).toString().equals("Intermediate"))
                    editor.putInt("level", inter);
                else
                    editor.putInt("level", prof);
                editor.apply();
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                parentView.getItemIdAtPosition(0);
            }

        });

        swtMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                SharedPreferences.Editor editor = sp.edit();
                if(isChecked) {
                    editor.putBoolean("isMusicOn", true);
                    editor.apply();
                }
                else {
                    editor.putBoolean("isMusicOn", false);
                    editor.apply();
                }
                editor.commit();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNewGameID) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuItem menuAbout = menu.add("About");
        MenuItem menuExit = menu.add("Exit");

        menuAbout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                showAboutDialog();
                return true;
            }
        });

        menuExit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                showExitDialog();
                return true;
            }
        });
        return true;
    }

    private void showAboutDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(R.drawable.document);
        alertDialog.setTitle("About Puzzle 15");
        alertDialog.setMessage("This game implements the Game Of Fifteen\n\nBy YURIY KOVALYOV & ISRAEL BEN MENACHEM (c)");
        alertDialog.show();
    }

    private void showExitDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(R.drawable.document);
        alertDialog.setTitle("Exit App");
        alertDialog.setMessage("Do you really want to exit?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();  // destroy this activity
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });
        alertDialog.show();
    }

}
