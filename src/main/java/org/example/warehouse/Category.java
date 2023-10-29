package org.example.warehouse;

import java.util.ArrayList;
import java.util.Objects;

public final class Category {
    String name;
    private static Category category;

    private Category() {
    }

    public static Category of(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        if (category == null || !category.name.equals(name)) {
            category = new Category();
            category.name = name.substring(0, 1).toUpperCase() + name.substring(1);;
        }
        return category;
    }

    public String getName() {
        return name;
    }

}
