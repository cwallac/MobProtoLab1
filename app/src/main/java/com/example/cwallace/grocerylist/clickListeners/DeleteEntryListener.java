package com.example.cwallace.grocerylist.clickListeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cwallace.grocerylist.R;
import com.example.cwallace.grocerylist.services.DatabaseService;

import java.util.List;

/**
 * Onclick listener implementation that will delete items from the database
 * Created by cwallace on 9/7/2015.
 */
public class DeleteEntryListener implements AdapterView.OnItemClickListener {

    Context context;
    List<String> groceryList;
    DatabaseService databaseService;

    public DeleteEntryListener(Context context, List<String> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
        databaseService = new DatabaseService(context);
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setTitle(context.getString(R.string.delete_from_list))
                .setCancelable(true)
                .setPositiveButton(context.getString(R.string.delete_keyword),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                groceryList.remove(position);
                                databaseService.deleteGroceries(position);
                                Log.i("DELETE ENTRY", String.valueOf(groceryList.size()));
                                ArrayAdapter groceryAdapter = (ArrayAdapter)parent.getAdapter();
                                groceryAdapter.notifyDataSetChanged();
                            }
                        }
                )
                .setNegativeButton(context.getString(R.string.cancel_dialog), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        ;

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }
}
