package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRecord {
    public UUID id;
    public Category category;

    public String name;

    public BigDecimal price;
    public boolean changed;

    public UUID uuid() {
        return id;
    }
    public BigDecimal price() {
        return price;
    }
    public Category category() {
        return category;
    }
}
