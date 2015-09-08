package com.hhansson.notepad;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static Activity activity;
    private ArrayList<String> currentList = new ArrayList<>();
    private int listPosition;
    public boolean isListChanged;

    @Bind(R.id.add_item_edit)
    EditText newItemEditText;

    @Bind(R.id.shopping_list)
    ListView shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        activity = this;
        int position = 0;
        currentList.clear();
        while (shoppingList.getItemAtPosition(position) != null){
            currentList.add(shoppingList.getItemAtPosition(position).toString());
            position += 1;
        }

        shoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPosition = position;
                new EditDialog();
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

    @OnClick(R.id.add_button)
    public void onAddClick() {
        currentList.add(newItemEditText.getText().toString());
        ArrayAdapter myListAdapter = new ArrayAdapter(getApplicationContext(), R.layout.list_item, currentList);
        shoppingList.setAdapter(myListAdapter);
        newItemEditText.setText("");
    }

    public void changeText(String newText) {
        int position = 0;
        currentList.clear();
        while (shoppingList.getItemAtPosition(position) != null){
            if (position == listPosition) {
                currentList.add(newText);
            } else {
                currentList.add(shoppingList.getItemAtPosition(position).toString());
            }
        }

    }
}
