package edu.example.controller;

import edu.example.dto.RestaurantDto;
import edu.example.model.Restaurant;
import edu.example.model.UserEntity;
import edu.example.request.CreateRestaurantRequest;
import edu.example.service.RestaurantService;
import edu.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization")String jwt,
            @RequestParam String keyword
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);

        List<Restaurant> restaurant= restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);

        List<Restaurant> restaurant= restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity <Restaurant> findRestaurantById(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant= restaurantService.findRestaurantByID(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}/add-favorite")
    public ResponseEntity <RestaurantDto> addFavourites(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);

        RestaurantDto restaurant= restaurantService.addToFavorites(id, user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
