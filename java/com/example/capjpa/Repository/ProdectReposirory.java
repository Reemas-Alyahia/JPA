package com.example.capjpa.Repository;

import com.example.capjpa.Model.Prodect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdectReposirory extends JpaRepository<Prodect,Integer> {
}
