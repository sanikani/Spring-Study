package sample.cafekiosk.spring.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductResponse {

    private Long id;
    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private String productNumber;
    private int price;

    @Builder
    public ProductResponse(Long id, ProductType type, ProductSellingStatus sellingStatus, String name, int price, String productNumber) {
        this.id = id;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
        this.productNumber = productNumber;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .type(product.getType())
                .sellingStatus(product.getSellingStatus())
                .name(product.getName())
                .price(product.getPrice())
                .productNumber(product.getProductNumber())
                .build();
    }
}
