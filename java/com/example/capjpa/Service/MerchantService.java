package com.example.capjpa.Service;

import com.example.capjpa.Model.Merchant;
import com.example.capjpa.Model.Prodect;
import com.example.capjpa.Model.User;
import com.example.capjpa.Repository.MerchantRepository;
import com.example.capjpa.Repository.ProdectReposirory;
import com.example.capjpa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchantRepository;
    private final UserRepository userRepository;
    private final ProdectReposirory prodectReposirory;

    public List<Merchant> getMerchant(){
        return merchantRepository.findAll();
    }



    public void addMer(Merchant merchant){
        merchantRepository.save(merchant);
    }

    public Boolean updateMer(Integer id,Merchant merchant){
        Merchant oldMer=merchantRepository.getById(id);
        if(oldMer==null){
            return false;
        }

        oldMer.setName(merchant.getName());


        merchantRepository.save(oldMer);

        return true;
    }

    public Boolean deletMer(Integer id){
        Merchant merchant=merchantRepository.getById(id);
        if(merchant==null){
            return false;
        }
        merchantRepository.delete(merchant);
        return true;
    }
    /// ////////
    public String addOffer(Integer adminId, Integer productId, double discount) {

        User admin = userRepository.findById(adminId).orElse(null);
        if (admin == null || !admin.getRole().equalsIgnoreCase("Admin")) {
            return "Sorry, only Admin can apply offers.";
        }

        if (discount < 1 || discount > 50) {
            return "Discount percentage must be between 1% and 50%.";
        }

        Prodect product = prodectReposirory.findById(productId).orElse(null);
        if (product == null) {
            return "Sorry, no product found with the provided ID.";
        }
        double discountedPrice = product.getPrice() - (product.getPrice() * discount/ 100);
        if (discountedPrice <= 0) {
            return "Discount is too high, resulting in an invalid price.";
        }

        product.setPrice((int) discountedPrice);
        product.setHasDiscount(true);
        prodectReposirory.save(product);

        return "Discount applied successfully! New price for product ID " + productId + " is: " + discountedPrice;
    }
    /// /////////

    public String giveGiftTo(Integer userId, Integer merchantId, int amount) {
        Merchant foundMerchant = merchantRepository.findById(merchantId).orElse(null);
        if (foundMerchant == null) {
            return "Sorry, but there is no Merchant with this ID.";
        }

        User foundUser = userRepository.findById(userId).orElse(null);
        if (foundUser == null) {
            return "Sorry, there is no user with this ID.";
        }
        foundUser.setBalance(foundUser.getBalance() + amount);

        userRepository.save(foundUser);
        return "Gift card: " + amount + " added successfully! For user: " + userId + " and new balance is: " + foundUser.getBalance();
    }

}
