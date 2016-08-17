package com.COP4655.Odiaz053.DontForget.Object;

import java.io.Serializable;

/**
 * Created by Send on 6/6/2016.
 */
public class Task implements Serializable {

    String name;
    int id;

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
