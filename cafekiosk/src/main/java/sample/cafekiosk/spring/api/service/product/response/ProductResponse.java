package sample.cafekiosk.spring.api.service.product.response;

import jakarta.persistence.*;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductResponse {

    private Long id;
    private ProductType type;
    private ProductSellingType sellingType;
    private String name;
    private int price;

    public static Product of(Product product) {
    }
}
