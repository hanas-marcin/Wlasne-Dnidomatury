package hanas.dnidomatury.maturaListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

import hanas.dnidomatury.R;
import hanas.dnidomatury.selectActivity.SelectActivity;
import hanas.dnidomatury.settingsActivity.SettingsActivity;
import hanas.dnidomatury.matura.ListOfMatura;

public class MaturaListActivity extends AppCompatActivity {

    //ListOfMatura listOfMatura = new ListOfMatura();
    static int mainToSelectRequestCode=21;
    static public int mainToMaturaRequestCode=15;

    static public int getMainToSelectRequestCode() {
        return mainToSelectRequestCode;
    }

    static public int getMainToMaturaRequestCode() {
        return mainToMaturaRequestCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        Calendar date=Calendar.getInstance();;

        RecyclerView recyclerView = findViewById(R.id.selected_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        MaturaAdapter adapter = new MaturaAdapter(this, ListOfMatura.readFromFile(this, true));
        recyclerView.setAdapter(adapter);

        //zaczynamy powiadomienia
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) firstNotif();
    }


    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView recyclerView = findViewById(R.id.selected_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        MaturaAdapter adapter = new MaturaAdapter(this, ListOfMatura.readFromFile(this, true));
        recyclerView.setAdapter(adapter);

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
        if (id == R.id.action_edit) {
            Intent intent = new Intent(this, SelectActivity.class);
            startActivityForResult(intent, mainToSelectRequestCode);
            return true;
        }
        else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, mainToSelectRequestCode);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == mainToSelectRequestCode || requestCode == mainToMaturaRequestCode){
            if (resultCode == RESULT_OK){
                RecyclerView recyclerView = findViewById(R.id.selected_recycle_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

                MaturaAdapter adapter = new MaturaAdapter(this, ListOfMatura.readFromFile(this, true));
                recyclerView.setAdapter(adapter);
            }
        }
    }
}