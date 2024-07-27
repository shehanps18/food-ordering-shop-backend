package edu.example.service.impl;

import edu.example.model.Cart;
import edu.example.model.CartItem;
import edu.example.model.Food;
import edu.example.model.UserEntity;
import edu.example.repository.CartItemRepository;
import edu.example.repository.CartRepository;
import edu.example.request.AddCartItemRequest;
import edu.example.service.CartService;
import edu.example.service.FoodService;
import edu.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req,String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);

        Food food = foodService.findFoodById(req.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem: cart.getItem()){
            if (cartItem.getFood().equals(food)){
                int newQuantity= cartItem.getQuantity()+ req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }
        CartItem newCartItem=new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity()* food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getItem().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()){
            throw new Exception("cart item not found");
        }
        CartItem item = cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);

        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        UserEntity user =userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional= cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("cart item not found");
        }
        CartItem cartItem = cartItemOptional.get();
        cart.getItem().remove(cartItem);
        return cart;
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        Long total=0L;
        for (CartItem cartItem:cart.getItem()){
            total+= cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart= cartRepository.findById(id);
        if (optionalCart.isEmpty()){
            throw new Exception("cart not found with id");
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
//        UserEntity user=userService.findUserByJwtToken(jwt);
        Cart cart= cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotal(cart));
        return cart;
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        UserEntity user= userService.findUserByJwtToken(jwt);
        Cart cart = findCartByUserId(user.getId());
        cart.getItem().clear();

        return cartRepository.save(cart);
    }
}
