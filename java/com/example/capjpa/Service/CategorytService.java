package com.example.capjpa.Service;

import com.example.capjpa.Model.Category;
import com.example.capjpa.Model.Merchant;
import com.example.capjpa.Repository.CategoryRepositort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategorytService {
    private final CategoryRepositort categoryRepositort;



    public List<Category> getCategory(){
        return categoryRepositort.findAll();
    }


    public void addCategory(Category category){
        categoryRepositort.save(category);
    }

    public Boolean updateCategory(Integer id,Category category){
        Category oldcat=categoryRepositort.getById(id);
        if( oldcat==null){
            return false;
        }

        oldcat.setName(category.getName());

        categoryRepositort.save(oldcat);

        return true;
    }

    public Boolean deletCategory(Integer id){
        Category category=categoryRepositort.getById(id);
        if(category==null){
            return false;
        }
        categoryRepositort.delete(category);
        return true;
    }
}
