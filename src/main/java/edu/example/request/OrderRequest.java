package edu.example.request;

import edu.example.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
        private Long restaurantId;
        private Address deliveryAddress;
}
