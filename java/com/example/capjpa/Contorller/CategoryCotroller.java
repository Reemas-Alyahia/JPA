package com.example.capjpa.Contorller;

import com.example.capjpa.ApiResponce.ApiResponse;
import com.example.capjpa.Model.Category;
import com.example.capjpa.Service.CategorytService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/catg")
@RequiredArgsConstructor
public class CategoryCotroller {
    private final CategorytService categorytService;

    @GetMapping("/get")
    public ResponseEntity getCategory(){
        return ResponseEntity.status(200).body(categorytService.getCategory());
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        categorytService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("Done Adding"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable Integer id,@RequestBody @Valid Category category,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdate= categorytService.updateCategory(id,category);
        if(isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Done Updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Cannot Found the id"));

    }
    @DeleteMapping("/delet/{id}")
    public ResponseEntity deletCategory(Integer id){
        boolean isDelet= categorytService.deletCategory(id);
        return ResponseEntity.status(200).body(new ApiResponse("Done Deleted"));

    }
}
