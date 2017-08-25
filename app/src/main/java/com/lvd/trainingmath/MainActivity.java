package com.lvd.trainingmath;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean[] oprArr = new boolean[6];
    CheckBox chBxAdd;
    CheckBox chBxSub;
    CheckBox chBxMlt;
    CheckBox chBxDiv;
    CheckBox chBxStep;
    CheckBox chBxLog;
    char [] oprChrArr = {'+', '-','*','/','^','L'};
    int oprNum=-1;

    int rights=0;
    int step=0;
    float results=0;
    TextView txtVwResults;

    EditText txtVwResult;

    TextView txtVwTask;
    long a;
    long b;
    long c;

    TextView txtVwAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chBxAdd = (CheckBox) findViewById(R.id.chBxAdd);
        chBxSub = (CheckBox) findViewById(R.id.chBxSub);
        chBxMlt= (CheckBox) findViewById(R.id.chBxMlt);
        chBxDiv = (CheckBox) findViewById(R.id.chBxDiv);
        chBxStep = (CheckBox) findViewById(R.id.chBxStep);
        chBxLog = (CheckBox) findViewById(R.id.chBxLog);

        txtVwResults = (TextView) findViewById(R.id.results);
        txtVwTask = (TextView) findViewById(R.id.task);
        txtVwAnswer = (TextView) findViewById(R.id.answer);
        txtVwResult = (EditText) findViewById(R.id.usrResult);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void onClickClear(View v) {
        results=0;
        step=0;
        txtVwAnswer.setText("");
        txtVwResult.setText("");
        txtVwTask.setText("");
        txtVwResults.setText("0");
    }

    public void onClickCheakBox(View v) {
        onClickStart(v);
    }

    public void onClickStart(View v) {

        boolean oprCh = false;

        oprArr[0] = chBxAdd.isChecked();
        oprArr[1] = chBxSub.isChecked();
        oprArr[2] = chBxMlt.isChecked();
        oprArr[3] = chBxDiv.isChecked();
        oprArr[4] = chBxStep.isChecked();
        oprArr[5] = chBxLog.isChecked();

        for(int i=0; i<6 && !oprCh; i++) {
            if(oprArr[i]) {
                oprCh = true;
            }
        }

        if(oprCh) {
            txtVwResult.setText("");
            a = Math.round(Math.random()*50);
            b = Math.round(Math.random()*50);
            long nomOper;
            oprNum=-1;

            while (oprNum==-1) {
                nomOper = Math.round(Math.random()*71) % 6;
                if (nomOper == 0 && oprArr[0]) {
                    c = a + b;
                    oprNum=0;
                } else if (nomOper == 1 && oprArr[1]) {
                    c = a - b;
                    oprNum=1;
                } else if (nomOper == 2 && oprArr[2]) {
                    c = a * b;
                    oprNum=2;
                } else if (nomOper == 3 && oprArr[3]){
                    a = a * b;
                    c = a / b;
                    oprNum=3;
                } else if (nomOper == 4 && oprArr[4]){ //возведение в степень
                    a = a % 20;
                    b = b % 10;
                    c = Math.round(Math.pow(a,b));
                    oprNum=4;
                } else if (nomOper == 5 && oprArr[5]){ //взятие логарифма log(a,b)=х, a^x=b
                    a = a % 20;
                    b = b % 10;
                    c = b;
                    b = Math.round(Math.pow(a,b));
                    oprNum=5;
                }
            }
            txtVwTask.setText(String.valueOf(a) + " " + oprChrArr[oprNum] + " " + String.valueOf(b) + " = ?");
        }
        else {
            txtVwTask.setText("НЕ ВЫБРАНА ОПЕРАЦИЯ !!!");
        }
    }

    public void onClickCheck(View v) {
        int x = 0;
        if(txtVwTask.getText().toString()!="НЕ ВЫБРАНА ОПЕРАЦИЯ !!!" && txtVwResult.getText().toString() != null) {
            if(txtVwResult.getText().toString().length()>0) {

                x = Integer.valueOf(txtVwResult.getText().toString());
                if (c == x) {
                    rights++;
                    txtVwAnswer.setText(txtVwResult.getText().toString() + " Верно! " + String.valueOf(a) + " "
                            + oprChrArr[oprNum] + " " + String.valueOf(b) + " =" + String.valueOf(c));
                }
                else {
                    txtVwAnswer.setText(txtVwResult.getText().toString() + " Не верно! " + String.valueOf(a) + " "
                            + oprChrArr[oprNum] + " " + String.valueOf(b) + " =" + String.valueOf(c));
                }

                step++;
                txtVwResults.setText("(" + String.valueOf(rights) + " из " + String.valueOf(step) + ")");
                txtVwResult.setText("");
                txtVwTask.setText("");
                a=-1;
                b=-1;
                c=-1;

                onClickStart(v);
            }
        }
        else {
            txtVwResult.setText("Ошибка");
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
