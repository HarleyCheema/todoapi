/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hsc.todoapi.data;

/**
 *
 * @author harle
 */
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import hsc.todoapi.models.ToDo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Repository
@Profile("memory")
@Component
public class ToDoInMemoryDao implements ToDoDao {

    private static final List<ToDo> todos = new ArrayList<>();

    @Override
    public ToDo add(ToDo todo) {

        int nextId = todos.stream()
                .mapToInt(i -> i.getId())
                .max()
                .orElse(0) + 1;

        todo.setId(nextId);
        todos.add(todo);
        return todo;
    }

    @Override
    public List<ToDo> getAll() {
        return new ArrayList<>(todos);
    }

    @Override
    public ToDo findById(int id) {
        return todos.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(ToDo todo) {

        int index = 0;
        while (index < todos.size()
                && todos.get(index).getId() != todo.getId()) {
            index++;
        }

        if (index < todos.size()) {
            todos.set(index, todo);
        }
        return index < todos.size();
    }

    @Override
    public boolean deleteById(int id) {
        return todos.removeIf(i -> i.getId() == id);
    }

}
