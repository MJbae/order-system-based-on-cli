package kr.co._29cm.homework;

import kr.co._29cm.homework.application.ItemService;
import kr.co._29cm.homework.application.OrderItemService;
import kr.co._29cm.homework.application.OrderService;
import kr.co._29cm.homework.domain.Item;
import kr.co._29cm.homework.domain.Order;
import kr.co._29cm.homework.domain.OrderItem;
import kr.co._29cm.homework.infra.OrderItemRepository;
import kr.co._29cm.homework.infra.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.List;

@DisplayName("OrderService")
@Transactional
class OrderTest {
    private OrderService orderService;
    private OrderItemService orderItemService;

    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private final OrderItemRepository orderItemRepository = mock(OrderItemRepository.class);
    private final ItemService itemService = mock(ItemService.class);
    private final Order orderMocking = mock(Order.class);
    private Item item;
    private OrderItem orderItem;
    private int orderCount;
    private BigDecimal itemPrice;
    private final Long itemId = 778422L;
    private final String itemName = "캠핑덕 우드롤테이블";
    private final int stockQuantity = 7;
    private final BigDecimal deliveryFee = BigDecimal.valueOf(2500);

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, orderItemRepository, itemService);
        orderItemService = new OrderItemService(orderItemRepository);
    }

    @Nested
    @DisplayName("order 메소드는")
    class Describe_order {

        @Nested
        @DisplayName("만약 주문금액이_5만원_이상이면")
        class Context_with_order_price_higher_than_or_equal_to_50000 {
            @BeforeEach
            void setUp() {
                orderCount = 2;
                itemPrice = BigDecimal.valueOf(45000);

                item = new Item(itemId, itemName, itemPrice, stockQuantity);
                orderItem = new OrderItem(orderMocking, item, orderCount);

                given(itemService.loadOne(any())).willReturn(item);
                given(orderItemRepository.findByOrder(any())).willReturn(List.of(orderItem));
            }

            @Test
            @DisplayName("주문 엔티티의 price에 배송료를 포함하지 않고 반환한다")
            void it_returns_price_not_including_delivery_fee() {
                Order order = orderService.order(itemId, orderCount);

                BigDecimal actualPrice = order.getPrice();
                BigDecimal expectedPrice = itemPrice.multiply(BigDecimal.valueOf(orderCount));

                assertThat(actualPrice).isEqualTo(expectedPrice);
            }
        }

        @Nested
        @DisplayName("만약 주문금액이_5만원_미만이면")
        class Context_with_order_price_lower_than_50000 {
            @BeforeEach
            void setUp() {
                orderCount = 1;
                itemPrice = BigDecimal.valueOf(45000);

                item = new Item(itemId, itemName, itemPrice, stockQuantity);
                orderItem = new OrderItem(orderMocking, item, orderCount);

                given(itemService.loadOne(any())).willReturn(item);
                given(orderItemRepository.findByOrder(any())).willReturn(List.of(orderItem));
            }

            @Test
            @DisplayName("주문 엔티티의 price에 배송료를 포함하여 반환한다")
            void it_returns_price_including_delivery_fee() {
                Order order = orderService.order(itemId, orderCount);

                BigDecimal actualPrice = order.getPrice();
                BigDecimal expectedPrice = itemPrice.multiply(BigDecimal.valueOf(orderCount)).add(deliveryFee);

                assertThat(actualPrice).isEqualTo(expectedPrice);
            }
        }
    }
}
