package com.example.capjpa.Service;

import com.example.capjpa.Model.MerchantStock;
import com.example.capjpa.Model.Prodect;
import com.example.capjpa.Model.User;
import com.example.capjpa.Repository.MerchantStockRepositpry;
import com.example.capjpa.Repository.ProdectReposirory;
import com.example.capjpa.Repository.UserRepository;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepositroy;
    private final ProdectReposirory productRepository;
    private final MerchantStockRepositpry merchantStockRepository;

    private final Map<Integer, String[]> purchaseHistoryMap = new HashMap<>();
    private final Map<String, List<String>> wishList = new HashMap<>();


    public List<User> getUsers() {
        return userRepositroy.findAll();
    }

    public void addUser(User user) {
        userRepositroy.save(user);
    }

    public Boolean updateUser(Integer id, User user) {
        User oldUser = userRepositroy.findById(id).orElse(null);
        if (oldUser == null) {
            return false;
        }

        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setBalance(user.getBalance());
        oldUser.setRole(user.getRole());

        userRepositroy.save(oldUser);
        return true;
    }

    public Boolean deleteUser(Integer id) {
        User user = userRepositroy.findById(id).orElse(null);
        if (user == null) {
            return false;
        }
        userRepositroy.delete(user);
        return true;
    }

    /// ////
    public String buy(Integer userId, Integer productId, Integer merchantId) {
        User user = userRepositroy.findById(userId).orElse(null);
        if (user == null) {
            return "User is not found";
        }

        Prodect product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return "Cannot find the product";
        }

        MerchantStock stock = merchantStockRepository.findByMerchantIdAndProductId(merchantId, productId)
                .orElse(null);
        if (stock == null || stock.getStock() <= 0) {
            return "No stock available";
        }

        if (user.getBalance() < product.getPrice()) {
            return "Insufficient balance";
        }

        user.setBalance(user.getBalance() - product.getPrice());
        stock.setStock(stock.getStock() - 1);

        userRepositroy.save(user);
        merchantStockRepository.save(stock);

        String purchaseEntry = "Product: " + productId + ", Merchant: " + merchantId;
        addPurchaseHistory(userId, purchaseEntry);

        return "Purchase successful! Remaining balance: " + user.getBalance() + ", Remaining stock: " + stock.getStock();
    }

    private void addPurchaseHistory(Integer userId, String purchaseEntry) {
        String[] purchaseHistory = purchaseHistoryMap.getOrDefault(userId, new String[10]);
        for (int i = 0; i < purchaseHistory.length; i++) {
            if (purchaseHistory[i] == null) {
                purchaseHistory[i] = purchaseEntry;
                purchaseHistoryMap.put(userId, purchaseHistory);
                return;
            }
        }

        System.arraycopy(purchaseHistory, 1, purchaseHistory, 0, purchaseHistory.length - 1);
        purchaseHistory[purchaseHistory.length - 1] = purchaseEntry;

        purchaseHistoryMap.put(userId, purchaseHistory);
    }


    public String[] getPurchaseHistory(Integer userId) {
        return purchaseHistoryMap.getOrDefault(userId, new String[10]);
    }

    /// ////

    public Boolean hasPurchaseEntry(Integer userId, String purchaseEntry) {
        String[] purchaseHistory = purchaseHistoryMap.getOrDefault(userId, new String[10]);

        for (String entry : purchaseHistory) {
            if (entry != null && entry.equals(purchaseEntry)) {
                return true;
            }
        }
        return false;
    }

    public void removePurchaseEntry(Integer userId, String purchaseEntry) {
        String[] purchaseHistory = purchaseHistoryMap.get(userId);

        for (int i = 0; i < purchaseHistory.length; i++) {
            if (purchaseHistory[i] != null && purchaseHistory[i].equals(purchaseEntry)) {
                purchaseHistory[i] = null;
                break;
            }
        }

        purchaseHistory = Arrays.stream(purchaseHistory)
                .filter(entry -> entry != null)
                .toArray(String[]::new);

        String[] newHistory = new String[10];
        System.arraycopy(purchaseHistory, 0, newHistory, 0, purchaseHistory.length);
        purchaseHistoryMap.put(userId, newHistory);
    }

    /// return
    public String returnProduct(Integer userId, Integer productId, Integer merchantId) {
        User user = userRepositroy.findById(userId).orElse(null);
        if (user == null) {
            return "User is not found";
        }

        String purchaseEntry = "Product: " + productId + ", Merchant: " + merchantId;
        if (!hasPurchaseEntry(userId, purchaseEntry)) {
            return "Product was not purchased or wrong merchant.";
        }

        Prodect product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return "Cannot find the product";
        }

        MerchantStock stock = merchantStockRepository.findByMerchantIdAndProductId(merchantId, productId).orElse(null);
        if (stock == null) {
            return "Merchant stock not found for this product.";
        }

        user.setBalance(user.getBalance() + product.getPrice());
        stock.setStock(stock.getStock() + 1);

        removePurchaseEntry(userId, purchaseEntry);

        userRepositroy.save(user);
        merchantStockRepository.save(stock);

        return "Product returned successfully. Updated balance: " + user.getBalance() + ", Updated stock: " + stock.getStock();
    }
    /// /trasfer
    ///
    public Boolean transferBalance(Integer userId1, Integer userId2, double amount) {
        if (amount <= 0) {
            return false;
        }

        User sender = userRepositroy.findById(userId1).orElse(null);
        User receiver = userRepositroy.findById(userId2).orElse(null);

        if (sender == null || receiver == null) {
            return false;
        }

        if (sender.getBalance() < amount) {
            return false;
        }
        sender.setBalance((int)(sender.getBalance() - amount));
        receiver.setBalance((int)(receiver.getBalance() + amount));

        userRepositroy.save(sender);
        userRepositroy.save(receiver);

        return true;}

        /// //wishlist

    public String addProductToWishlist(Integer userId, Integer productId) {
        User user = userRepositroy.findById(userId).orElse(null);
        if (user == null) {
            return "User with ID: " + userId + " not found.";
        }

        Prodect product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return "Product with ID: " + productId + " not found.";
        }

        List<String> wishlist = wishList.getOrDefault(userId.toString(), new ArrayList<String >());

        if (wishlist.contains(productId.toString())) {
            return "Product is already in the wishlist.";
        }

        wishlist.add(productId.toString());
        wishList.put(userId.toString(), wishlist);

        return "Product added to wishlist successfully.";
    }

    public List<String> getWishlist(Integer userId) {
        return wishList.getOrDefault(userId.toString(), new ArrayList<String>());
    }


}