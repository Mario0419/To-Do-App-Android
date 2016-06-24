package com.mx3studios.to_do;

import com.mx3studios.to_do.TodoItem;

import java.util.ArrayList;
import java.util.List;

public interface TodoItemDao {

    public ArrayList<TodoItem> retreieveAll();

    public boolean delete(int id);

    public TodoItem save(TodoItem item);
}
