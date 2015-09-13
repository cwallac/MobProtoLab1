package com.example.cwallace.grocerylist.clickListeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.cwallace.grocerylist.R;
import com.example.cwallace.grocerylist.services.DatabaseService;

import java.util.logging.Logger;

/**
 * Implementation of OnCLickListener that will add the contents of 
 * and EditText to the listView
 * Created by cwallace on 9/7/2015.
 */
public class AddToListListener implements View.OnClickListener {

    //Needed for creation of DatabaseService
    private Context context;
    private DatabaseService dbService;

    /**
     * Constructor for AddToListListener
     * @param context activity or context this is called from
     */
    public AddToListListener(Context context) {
        this.context = context;
        dbService = new DatabaseService(context);

    }

    /**
     * Onclick method that shoould handle reading text from the edit text, 
     * adding this text to the list view, and saving it to the database
     * @param v View that was clicked
     */
    @Override
    public void onClick(View v) {

        //Since the view is the button we need to find the rootView to find the editText
        View rootView = v.getRootView();
        EditText textToAdd = (EditText)rootView.findViewById(R.id.text_to_add);
        ListView groceryList = (ListView)rootView.findViewById(R.id.grocery_list);
        ArrayAdapter<String> groceryAdapter = (ArrayAdapter<String>)groceryList.getAdapter();

        String groceryItem = textToAdd.getText().toString();
        if (groceryItem.length() != 0 ) {
            groceryAdapter.add(groceryItem);
            //Save thsi to the database
            dbService.writeToDatabase(groceryItem, groceryAdapter.getCount());
            //Call this to notify the adapter that the list it references has changed
            groceryAdapter.notifyDataSetChanged();
            textToAdd.setText("");
        } else {
            //If we enter blank text load an Alert Dialog to warn the user
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder
                    .setTitle(context.getString(R.string.blank_grcoery_warning))
                    .setCancelable(false)
                    .setPositiveButton(context.getString(R.string.groceries_ok),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }
                    );
            ;

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();
        }

    }
}
