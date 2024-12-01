package com.example.capjpa.Service;


import com.example.capjpa.Model.Merchant;
import com.example.capjpa.Model.MerchantStock;
import com.example.capjpa.Model.Prodect;
import com.example.capjpa.Model.User;
import com.example.capjpa.Repository.MerchantRepository;
import com.example.capjpa.Repository.MerchantStockRepositpry;
import com.example.capjpa.Repository.ProdectReposirory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    private final MerchantStockRepositpry merchantStockRepositpry;
    private final MerchantRepository merchantRepository;
    private final ProdectReposirory prodectReposirory;


    public List<MerchantStock>getMerchantStock(){
        return merchantStockRepositpry.findAll();
    }
    public void addMerchantStock(MerchantStock merchantStock){
        merchantStockRepositpry.save(merchantStock);
    }

    public Boolean updateMer(MerchantStock merchantStock,Integer id){
        MerchantStock oldmer=merchantStockRepositpry.getById(id);
        if(oldmer==null){
            return false;
        }
        oldmer.setStock(merchantStock.getStock());

        merchantStockRepositpry.save(oldmer);
        return true;
    }

    public Boolean deletMer(Integer id){
        MerchantStock merchantStock=merchantStockRepositpry.getById(id);
        if(merchantStock==null){
            return false;
        }

        merchantStockRepositpry.delete(merchantStock);
        return true;
    }

    ///
    public MerchantStock findStock(Integer merchantId, Integer productId) {
        return merchantStockRepositpry.findByMerchantIdAndProductId(merchantId, productId)
                .orElse(null);
    }

    public String addStock(Integer merchantId, Integer productId, int quantity) {
       Merchant merchant = merchantRepository.findById(merchantId).orElse(null);
        if (merchant == null) {
            return "Sorry, no merchant found with ID: " + merchantId;
        }

       Prodect product = prodectReposirory.findById(productId).orElse(null);
        if (product == null) {
            return "Sorry, no product found with ID: " + productId;
        }

        MerchantStock existingStock =merchantStockRepositpry.findByMerchantIdAndProductId(merchantId, productId).orElse(null);

        if (existingStock != null) {
            existingStock.setStock(existingStock.getStock() + quantity);
            merchantStockRepositpry.save(existingStock);
            return "Stock updated successfully! New stock: " + existingStock.getStock();
        }
        else {
            MerchantStock newStock = new MerchantStock();
            newStock.setMerchantid(merchantId);
            newStock.setProductid(productId);
            newStock.setStock(quantity);
            merchantStockRepositpry.save(newStock);
            return "Stock added successfully! Current stock: " + quantity;
        }
    }

}
