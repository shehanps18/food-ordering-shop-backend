package edu.example.service;

import edu.example.dto.RestaurantDto;
import edu.example.model.Restaurant;
import edu.example.model.UserEntity;
import edu.example.request.CreateRestaurantRequest;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, UserEntity user);

    public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantByID(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId,UserEntity user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id)throws Exception;
}
