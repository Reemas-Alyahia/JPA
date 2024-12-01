package com.example.capjpa.Repository;

import com.example.capjpa.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositort extends JpaRepository<Category,Integer> {
}
