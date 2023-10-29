package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class Warehouse {
    private Warehouse() {
    }

    private static Warehouse warehouse;
    private String name;

    private static ArrayList<ProductRecord> products;

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public ProductRecord getProducts(UUID id, String name, Category category, BigDecimal price) {
        return addProduct(id, name, category, price);
    }

    public static Warehouse getInstance() {
        warehouse = new Warehouse();
        products = new ArrayList<>();
        warehouse.name = new String();
        return warehouse;
    }

    public static Warehouse getInstance(String name) {
        Warehouse warehouse = new Warehouse();
        warehouse.name = name;

        products = new ArrayList<>();
        return warehouse;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public ProductRecord addProduct(UUID id, String name, Category category, BigDecimal price) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (Objects.isNull(category)) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        if (products.stream().anyMatch(product -> product.id == id)) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        ProductRecord product = new ProductRecord();
        if (Objects.isNull(id)) {
            product.id = UUID.randomUUID();
        } else {
            product.id = id;
        }
        if (Objects.isNull(price)) {
            product.price = BigDecimal.ZERO;
        } else {
            product.price = price;
        }
        product.name = name;
        product.category = category;
        product.changed = false;
        products.add(product);
        return product;
    }

    public List<ProductRecord> getProductById(UUID uuid) {
        return products.stream().filter(product -> product.id == uuid).collect(Collectors.toList());

    }


    public void updateProductPrice(UUID uuid, BigDecimal price) {
        Optional<ProductRecord> optionalProductRecord =
                products.stream().filter(product -> product.id == uuid).findFirst();
        if (optionalProductRecord.isPresent()) {
            ProductRecord productRecord = optionalProductRecord.get();
            productRecord.price = price;
            productRecord.changed = true;
        } else {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }
    }

    public List<ProductRecord> getChangedProducts() {
        return products.stream().filter(product -> product.changed).collect(Collectors.toList());
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        Map<Category, List<ProductRecord>> map = products.stream().collect(groupingBy(ProductRecord::category));
        return map;
    }
    public List<ProductRecord> getProductsBy(Category category) {
        return products.stream().filter(product -> product.category.name.equals(category.name)).collect(Collectors.toList());
    }
}
