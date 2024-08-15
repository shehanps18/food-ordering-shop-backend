package edu.example.controller;

import edu.example.model.Restaurant;
import edu.example.model.UserEntity;
import edu.example.request.CreateRestaurantRequest;
import edu.example.response.MessageResponse;
import edu.example.service.RestaurantService;
import edu.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("api/admin/restaurant")
public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant= restaurantService.createRestaurant(req, user);
        System.out.println("UserEntity: " + user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant
            (@RequestParam CreateRestaurantRequest req,
             @RequestHeader("Authorization")String jwt,
             @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant= restaurantService.updateRestaurant(id,req);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteRestaurant(

            @RequestHeader("Authorization")String jwt,
            @PathVariable Long id
    ) throws Exception {

        restaurantService.deleteRestaurant(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("restaurant delete successfully");
        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus (

            @RequestHeader("Authorization")String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant= restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantById (

            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant= restaurantService.findRestaurantByID(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
