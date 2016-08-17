

package com.COP4655.Odiaz053.DontForget.ListView;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;


import com.COP4655.Odiaz053.DontForget.R;

/**
 * Created by Send on 6/6/2016.
 */
public class MyViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener {

    TextView nameTxt;
    MyLongClickListener longClickListener;

    public MyViewHolder(View v) {

        nameTxt = (TextView) v.findViewById(R.id.nameTxt);


        v.setOnLongClickListener(this);
        v.setOnCreateContextMenuListener(this);
    }


    @Override
    public boolean onLongClick(View v) {
        this.longClickListener.onItemLongClick();
        return false;
    }

    //Allows to call Long click Listener from the adapter
    public void setLongClickListener(MyLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


    //ON LONG CLICK THIS IS THE MENU DISPLAYED
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderIcon(android.R.drawable.sym_def_app_icon);
        menu.setHeaderTitle("Would you like to...");


        menu.add(0, 0, 0, "NEW");
        menu.add(0, 1, 0, "EDIT");
        menu.add(0, 2, 0, "DELETE");
        menu.add(0, 3, 0, "DELETE ALL");

    }
}
