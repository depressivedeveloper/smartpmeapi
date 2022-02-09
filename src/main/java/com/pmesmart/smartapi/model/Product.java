package com.pmesmart.smartapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "products")
public class Product implements Serializable{

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    
    @Column(name = "product_name")
    private String productName ;
    @Column(name = "product_description")
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

    public Product(){}

    public Product(String nome, String description,Category category){
        this.productName = nome;
        this.productDescription = description;
        this.category = category;
    }

    public Product(long id, String name, String description, Category category){
        this.productId = id;
        this.productName = name;
        this.productDescription = description;
        this.category = category;

    }

    // getters and setters

    public String getProductName(){
        return this.productName;
    }

    public void setProductName(String name){
        this.productName = name;
    }

    public String getProductDescription(){
        return this.productDescription;
    }

    public void setProductDescription(String description){
        this.productDescription = description;
    }


    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    // toString
    public String toString(){
        return "ID: "+this.productId+", ProductName: "+this.productName+
                ", ProductDescription: "+this.productDescription+
                ", Catgegory: "+category;

    }

}