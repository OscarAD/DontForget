//USED TO BIND DATA

package com.COP4655.Odiaz053.DontForget.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.COP4655.Odiaz053.DontForget.Object.Task;
import com.COP4655.Odiaz053.DontForget.R;

import java.util.ArrayList;

/**
 * Created by Send on 6/6/2016.
 */


public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Task> tasks;

    LayoutInflater inflater;
    Task task;


    public CustomAdapter(Context c, ArrayList<Task> tasks) {
        this.c = c;
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.model, parent, false);
        }

        //BIND DATA
        MyViewHolder holder = new MyViewHolder(convertView);
        holder.nameTxt.setText(tasks.get(position).getName());


        //Handles LongClick
        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onItemLongClick() {

                task = (Task) getItem(position);

            }
        });

        return convertView;
    }


    //Gets ID of item to edit
    public int getSelectedID() {

        return task.getId();

    }

    //When editing, gets name initially stored
    public String getSelectedItemName() {
        return task.getName();

    }

}
