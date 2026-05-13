package com.doo.aqqle.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListDTO<T> {
    private List<T> items;

    public ListDTO() {
        items = new ArrayList<>();
    }

    public void addItem(T item) {
        items.add(item);
    }

    public T getItem(int index) {
        return items.get(index);
    }

    public int getSize() {
        return items.size();
    }

    public List<T> getItems() {
        return items;
    }
}
