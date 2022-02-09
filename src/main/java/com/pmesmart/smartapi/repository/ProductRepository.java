package com.pmesmart.smartapi.repository;

import com.pmesmart.smartapi.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// interface que garante o cumprimento da clausula da conexao DAO
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
