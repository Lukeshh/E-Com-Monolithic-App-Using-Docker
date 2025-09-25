package com.app.ecom_application.service;

import com.app.ecom_application.dto.OrderItemDto;
import com.app.ecom_application.dto.OrderResponseDto;
import com.app.ecom_application.model.entity.*;
import com.app.ecom_application.repository.CartItemRepository;
import com.app.ecom_application.repository.OrderItemRepository;
import com.app.ecom_application.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderItemRepository orderItemRepository;
    private final CartItemService cartItemService;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public OrderService(OrderItemRepository orderItemRepository, CartItemService cartItemService, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.orderItemRepository = orderItemRepository;
        this.cartItemService = cartItemService;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Optional<OrderResponseDto> createOrder(Long userId) {
//        validate cart items;
        //validate user
        //Calaculate total price
//        Create oder  then if the order is created clear the cart
        List<CartItemEntity> cartItemList = cartItemService.fetchCart(userId);

        if (cartItemList.isEmpty()) {
            throw new IllegalArgumentException("Cart item is empty");
            //Calc total price

        }
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        UserEntity user = userOpt.get();

        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (CartItemEntity cartItem : cartItemList) {
            totalPrice = totalPrice.add(cartItem.getPrice());
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(user);
        orderEntity.setTotalAmount(totalPrice);
        orderEntity.setStatus(OrderStatus.CONFIRMED);

        List<OrderItem> orderItemList = new ArrayList<>();

        for (CartItemEntity cartItem : cartItemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(cartItem.getId());
            orderItem.setProducts(cartItem.getProductsEntity());
            orderItem.setPrice(cartItem.getPrice().multiply( new BigDecimal(cartItem.getStockQuantity())));
            orderItem.setQuantity(cartItem.getStockQuantity());
            orderItem.setOrder(orderEntity);
            orderItemList.add(orderItem);
        }

        orderEntity.setItems(orderItemList);
        OrderEntity savedOrder = orderItemRepository.save(orderEntity);
        cartItemService.clearCart(userId);
       Optional<OrderResponseDto>  responseDto = Optional.ofNullable(mapOrderItemDtoToOrderResponseDto(savedOrder));
        return responseDto;
    }

    // mapping orderItemdto to OrderItem
    public OrderItemDto mapOrderItemToOrderItemDto(OrderItem orderItem) {
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setProductId(orderItem.getProducts().getId());
        itemDto.setPrice(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
        return itemDto;
    }

    public OrderResponseDto mapOrderItemDtoToOrderResponseDto(OrderEntity entity) {
        List<OrderItemDto> itemDtos = new ArrayList<>();
        for (OrderItem orderItem : entity.getItems()) {
            OrderItemDto orderItemDto = mapOrderItemToOrderItemDto(orderItem);
            itemDtos.add(orderItemDto);
        }

        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setTotalAmount(entity.getTotalAmount());
        responseDto.setStatus(entity.getStatus());
        responseDto.setItems(itemDtos);
        responseDto.setCreatedAt(entity.getCreationTime());
        return responseDto;
    }

}
