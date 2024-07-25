package edu.example.controller;

import edu.example.model.Food;
import edu.example.model.Restaurant;
import edu.example.model.UserEntity;
import edu.example.request.CreateFoodRequest;
import edu.example.response.MessageResponse;
import edu.example.service.FoodService;
import edu.example.service.RestaurantService;
import edu.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantByID(req.getRestaurantId());
        Food food = foodService.createFood(req,req.getCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("food deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);

        Food food = foodService.foodAvailabilityStatus(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("food deleted successfully");
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }


}
