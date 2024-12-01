package com.example.capjpa.Repository;

import com.example.capjpa.Model.MerchantStock;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantStockRepositpry extends JpaRepository<MerchantStock,Integer> {
   Optional<MerchantStock> findByMerchantIdAndProductId(Integer merchantId, Integer productId);
}
