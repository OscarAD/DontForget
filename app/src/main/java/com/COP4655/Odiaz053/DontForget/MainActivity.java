package com.COP4655.Odiaz053.DontForget;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.COP4655.Odiaz053.DontForget.Data.DBAdapter;
import com.COP4655.Odiaz053.DontForget.Object.Task;
import com.COP4655.Odiaz053.DontForget.ListView.CustomAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    EditText nameEditText;
    Button saveBtn;
    final Boolean forUpdate = true;

    public ArrayList<Task> tasks = new ArrayList<>();
    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DELETE THIS TO HAVE NO APP TITTLE OR SETTINGS
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
//------------------------------------------------------------------

        //Finds the Listview on the screen by ID
        lv = (ListView) findViewById(R.id.lv);

        //Adapter used to populate Listview later
        adapter = new CustomAdapter(this, tasks);


        //pickImage();

        //Gets list of tasks when app launches
        this.getTasks();

        //Bottom right button to add tasks
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayDialog(false);

            }
        });
    }


    //Boolean to determine if updating
    public void displayDialog(Boolean forUpdate) {


        Dialog d = new Dialog(this);
        d.setTitle("SQLITE DATA");
        d.setContentView(R.layout.dialog_layout);


        nameEditText = (EditText) d.findViewById(R.id.nameEditTxt);
        saveBtn = (Button) d.findViewById((R.id.saveBtn));


        if (!forUpdate) {
            saveBtn.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    //If user enters nothing or enter the same thang!
                    if (nameEditText.getText().toString().trim().length() == 0) {

                        Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();

                    }

                    //If user enters task twice
                    else if (found(nameEditText.getText().toString().trim())) {


                        Toast.makeText(getApplicationContext(), "Task already added", Toast.LENGTH_SHORT).show();
                    }


                    //If user enters data
                    else {
                        save(nameEditText.getText().toString().trim());

                    }

                }
            });


        }
        //If USER IS UPDATING
        else {

            //SET SELECTED TEXT
            nameEditText.setText(adapter.getSelectedItemName());


            //Save button
            saveBtn.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    //User entered nothing
                    if (nameEditText.getText().toString().trim().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();

                    }

                    //If user enters task twice
                    else if (found(nameEditText.getText().toString().trim())) {

                        Toast.makeText(getApplicationContext(), "Task already added", Toast.LENGTH_SHORT).show();
                    }

                    //If task is valid
                    else {
                        update(nameEditText.getText().toString().trim());
                    }
                }
            });

        }

        //Show dialog
        d.show();

    }

    private boolean found(String name) {

        for (Task task : tasks) {
            if (task.getName() != null && task.getName().contains(nameEditText.getText().toString().trim())) {
                return true;
            }
        }

        return false;
    }


    //SAVE
    private void save(String name) {

        DBAdapter db = new DBAdapter(this);

        db.openDB();
        boolean saved = db.add(name);


        if (saved) {
            //nameEditText.setText("LOLWUT");
            getTasks();

        } else {
            Toast.makeText(this, "Not saved", Toast.LENGTH_SHORT).show();
        }

    }


    //RETRIEVE
    //Gets list of tasks when app launches.
    private void getTasks() {

        tasks.clear();


        DBAdapter db = new DBAdapter(this);
        db.openDB();
        //The retrieve method returns cursor with tasks
        Cursor c = db.retrieve();
        Task task = null;

        //Loops through cursor
        while (c.moveToNext()) {
            //Gets ID from position 0 and name from 1
            int id = c.getInt(0);
            String name = c.getString(1);

            //Passing to Data object
            task = new Task();
            task.setName(name);
            task.setId(id);


            tasks.add(task);

        }

        db.closeDB();
        lv.setAdapter(adapter);

    }


    //EDIT DATA
    private void update(String newName) {
        //Get ID from custom adapter
        int id = adapter.getSelectedID();

        //Update in DB (Passing context)
        DBAdapter db = new DBAdapter(this);

        db.openDB();


        boolean updated = db.update(newName, id);


        db.closeDB();

        if (updated) {

            nameEditText.setText(newName);
            getTasks();


        } else {

            Toast.makeText(this, "Not Updated", Toast.LENGTH_SHORT).show();
        }

    }

    //DELETE ALL
    public void deleteAll() {


        //DELETE FROM DB
        DBAdapter db = new DBAdapter(this);
        db.openDB();

        boolean deleted = db.deleteAll();

        db.closeDB();

        if (deleted) {

            getTasks();
        } else {
            Toast.makeText(this, "Unable To Delete", Toast.LENGTH_SHORT).show();
        }

    }


    //DELETE
    private void delete() {

        //Get ID
        int id = adapter.getSelectedID();

        //DELETE FROM DB
        DBAdapter db = new DBAdapter(this);
        db.openDB();

        boolean deleted = db.delete(id);

        db.closeDB();

        if (deleted) {

            getTasks();
        } else {
            Toast.makeText(this, "Unable To Delete", Toast.LENGTH_SHORT).show();
        }

    }


    // Depending of which action selected, new dialog is brought up.
    public boolean onContextItemSelected(MenuItem item) {

        CharSequence title = item.getTitle();

        if (title == "NEW") {
            displayDialog(!forUpdate);

        } else if (title == "EDIT") {

            displayDialog(forUpdate);

        } else if (title == "DELETE") {

            delete();
        } else if (title == "DELETE ALL") {

            deleteAll();

        }

        return super.onContextItemSelected(item);
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

       /*
    public void pickImage() {

        RelativeLayout RelLayout = (RelativeLayout) findViewById(R.id.back);
        Random rand = new Random();

        int n = rand.nextInt(5) + 1;

        if (n == 1) {
            RelLayout.setBackgroundResource(R.drawable.background);
        } else if (n == 2) {
            RelLayout.setBackgroundResource(R.drawable.background2);
        } else if (n == 3) {
            RelLayout.setBackgroundResource(R.drawable.background3);
        } else if (n == 4) {
            RelLayout.setBackgroundResource(R.drawable.background4);
        } else {
            RelLayout.setBackgroundResource(R.drawable.background5);


        }
    }


    */

}
