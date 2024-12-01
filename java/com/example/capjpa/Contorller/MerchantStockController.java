package com.example.capjpa.Contorller;

import com.example.capjpa.ApiResponce.ApiResponse;
import com.example.capjpa.Model.MerchantStock;
import com.example.capjpa.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchStock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;


    @GetMapping("/get")
    public ResponseEntity getMerchantStock(){
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStock());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantStockService.addMerchantStock(merchantStock);
        return ResponseEntity.status(200).body(new ApiResponse("Done Adding"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable Integer id,@RequestBody @Valid MerchantStock merchantStock,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdate= merchantStockService.updateMer(merchantStock,id);
        if(isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Done Updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Cannot Found the id"));

    }
    @DeleteMapping("/delet/{id}")
    public ResponseEntity deletMerchantStock(@PathVariable Integer id){
        boolean isDelet= merchantStockService.deletMer(id);
        return ResponseEntity.status(200).body(new ApiResponse("Done Deleted"));

    }
    /// /
    @PutMapping("/addStocks/{mid}/{pid}/{amount}")
    public ResponseEntity addStocks(@PathVariable Integer mid,@PathVariable Integer pid,@PathVariable int amount){
        String result=merchantStockService.addStock(mid,pid,amount);
        return ResponseEntity.status(200).body(result);
    }



}
