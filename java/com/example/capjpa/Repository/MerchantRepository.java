package com.example.capjpa.Repository;

import com.example.capjpa.Model.Merchant;
import jdk.jfr.Registered;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant,Integer> {
}
