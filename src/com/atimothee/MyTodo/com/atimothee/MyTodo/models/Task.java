package com.atimothee.MyTodo.com.atimothee.MyTodo.models;

/**
 * Created by Timo on 4/20/14.
 */
public class Task {
    public Long id;
    public String name;
    public String description;

    public Task(Long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
