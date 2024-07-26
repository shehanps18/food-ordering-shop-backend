package edu.example.repository;

import edu.example.model.Cart;
import edu.example.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {


}
