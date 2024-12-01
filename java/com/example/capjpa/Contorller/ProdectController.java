package com.example.capjpa.Contorller;

import com.example.capjpa.ApiResponce.ApiResponse;
import com.example.capjpa.Model.Prodect;
import com.example.capjpa.Service.ProdectSevice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/prodect")
@RequiredArgsConstructor
public class ProdectController {
    private final ProdectSevice prodectServi;

    @GetMapping("/get")
    public ResponseEntity getProdect() {
        return ResponseEntity.status(200).body(prodectServi.getProdect());
    }
    @PostMapping("/add")
    public ResponseEntity addProdect(@RequestBody @Valid Prodect product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        prodectServi.addProdect(product);
        return ResponseEntity.status(200).body(new ApiResponse("Done Adding"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updatProduct(@PathVariable Integer id,@RequestBody @Valid Prodect product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdate=prodectServi.updateProdect(id,product);
        if(isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Done Updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Cannot Found the id"));

    }
    @DeleteMapping("/delet/{id}")
    public ResponseEntity deletProduct(@PathVariable Integer id){
        boolean isDelet= prodectServi.deletProdect(id);
        return ResponseEntity.status(200).body(new ApiResponse("Done Deleted"));

    }
}
