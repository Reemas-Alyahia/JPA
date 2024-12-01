package com.example.capjpa.Service;

import com.example.capjpa.Model.Category;
import com.example.capjpa.Model.Prodect;
import com.example.capjpa.Repository.CategoryRepositort;
import com.example.capjpa.Repository.ProdectReposirory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdectSevice {
    private final ProdectReposirory prodectReposirory;
    private final CategoryRepositort categoryRepositort;

    public List<Prodect> getProdect(){
        return prodectReposirory.findAll();
    }


    public void addProdect(Prodect prodect){
        for (int i = 0; i < categoryRepositort.findAll().size(); i++) {
            if(prodect.getCategoryID().equals(categoryRepositort.findAll().get(i).getId())){
                prodectReposirory.save(prodect);

            }

        }
    }

    public Boolean updateProdect(Integer id,Prodect prodect){
        Prodect oldpr=prodectReposirory.getById(id);
        if( oldpr==null){
            return false;
        }

        oldpr.setName(prodect.getName());
        oldpr.setPrice(prodect.getPrice());
        for (int i = 0; i < categoryRepositort.findAll().size(); i++) {
            if(prodect.getCategoryID().equals(categoryRepositort.findAll().get(i).getId())){
                oldpr.setCategoryID(prodect.getCategoryID());

            }

        }

        prodectReposirory.save(oldpr);

        return true;
    }

    public Boolean deletProdect(Integer id){
        Prodect prodect=prodectReposirory.getById(id);
        if(prodect==null){
            return false;
        }
        prodectReposirory.delete(prodect);
        return true;
    }
}
