package com.example.capjpa.Contorller;

import com.example.capjpa.ApiResponce.ApiResponse;
import com.example.capjpa.Model.Merchant;
import com.example.capjpa.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mer")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;
    @GetMapping("/get")
    public ResponseEntity getMerchant(){
        return ResponseEntity.status(200).body(merchantService.getMerchant());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantService.addMer(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Done Adding"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable Integer id,@RequestBody @Valid Merchant merchant,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdate=merchantService.updateMer(id,merchant);
        if(isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Done Updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Cannot Found the id"));

    }
    @DeleteMapping("/delet/{id}")
    public ResponseEntity deletMerchant(@PathVariable Integer id){
        boolean isDelet= merchantService.deletMer(id);
        return ResponseEntity.status(200).body(new ApiResponse("Done Deleted"));

    }

    /// ///////6
    @PutMapping("/gift/{iduser}/{mid}/{amount}")
    public ResponseEntity givto(@PathVariable Integer iduser,@PathVariable Integer mid,@PathVariable int amount){
        String result=merchantService.giveGiftTo(iduser,mid,amount);
        return ResponseEntity.status(200).body(result);
    }
    /// //////////////
    @PostMapping("/add-offer/{mid}/{productId}/{discount}")
    public ResponseEntity addOffer(@PathVariable Integer mid, @PathVariable Integer  productId,@PathVariable double discount) {
        String message = merchantService.addOffer(mid, productId,discount);
        return ResponseEntity.status(200).body(new ApiResponse(message));
    }
}
