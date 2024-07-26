package edu.example.controller;

import edu.example.model.IngredientCategory;
import edu.example.request.IngredientCategoryRequest;
import edu.example.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/ingredients")
public class IngredientController {
    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
            ) throws Exception {
        IngredientCategory item= ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

}
