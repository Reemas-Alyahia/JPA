package com.example.capjpa.Contorller;

import com.example.capjpa.ApiResponce.ApiResponse;
import com.example.capjpa.Model.User;
import com.example.capjpa.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserContorller {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUser(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("Done Adding"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id,@RequestBody @Valid User user,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdate=userService.updateUser(id,user);
        if(isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Done Updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Cannot Found the id"));

    }
    @DeleteMapping("/delet/{id}")
    public ResponseEntity deletUser(@PathVariable Integer id){
        boolean isDelet= userService.deleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("Done Deleted"));

    }

    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity buyProduct(@PathVariable Integer userId, @PathVariable Integer productId, @PathVariable Integer merchantId) {
        String result = userService.buy(userId, productId, merchantId);
        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/return/{userId}/{productId}/{merchantId}")
    public ResponseEntity returnProduct(@PathVariable Integer userId, @PathVariable  Integer productId, @PathVariable Integer merchantId) {
        String message = userService.returnProduct(userId, productId, merchantId);
        return ResponseEntity.status(200).body(message);

    }

    @PostMapping("/transfer/{fromUserId}/{toUserId}/{amount}")
    public ResponseEntity<String> transferBalance(@PathVariable Integer fromUserId, @PathVariable Integer toUserId, @PathVariable double amount) {
        boolean success = userService.transferBalance(fromUserId, toUserId, amount);
        if (success) {
            return ResponseEntity.ok("Balance transferred successfully!");
        } else {
            return ResponseEntity.badRequest().body("Transfer failed! Check user IDs or balance.");
        }
    }


    @PostMapping("/wishlist/{userId}/{productId}")
    public ResponseEntity addProductToWishlist(@PathVariable Integer userId, @PathVariable Integer productId) {
        String message = userService.addProductToWishlist(userId, productId);
        return ResponseEntity.status(200).body(new ApiResponse(message));
    }

    @GetMapping("/wlist/{userId}")
    public ResponseEntity getWishlist(@PathVariable Integer userId) {
        List wishlist = (ArrayList) userService.getWishlist(userId);
        if (wishlist == null || wishlist.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("Wishlist is empty or user not found."));
        }
        return ResponseEntity.status(200).body(wishlist);
    }
}
