package sample.cafekiosk.spring.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;

class OrderTest {

    @Test
    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다")
    void calculateTotalPrice() {
        // given
        List<Product> products = List.of(createProduct("001", 1000),
                createProduct("002", 3000));
        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        Assertions.assertThat(order.getTotalPrice()).isEqualTo(4000);
    }

    @Test
    @DisplayName("주문 생성 시 주문 상태는 INIT이다")
    void init() {
        // given
        List<Product> products = List.of(createProduct("001", 1000),
                createProduct("002", 3000));
        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        Assertions.assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    @Test
    @DisplayName("주문 생성 시 등록 시간을 기록한다")
    void registeredDateTime() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        List<Product> products = List.of(createProduct("001", 1000),
                createProduct("002", 3000));
        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        Assertions.assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .type(ProductType.HANDMADE)
                .productNumber(productNumber)
                .sellingStatus(SELLING)
                .name("메뉴")
                .price(price)
                .build();
    }
}