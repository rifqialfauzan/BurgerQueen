package com.zangesterra.burgerQueen.repository;

import com.zangesterra.burgerQueen.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE from cart_item WHERE product_id = ?1 AND shopping_id = ?2", nativeQuery = true)
    void deleteCartItem(Long productId, Long shoppingCartId);

}
