/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.dto;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class ProductDTO {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String imageAddress;
    private CategoryDTO categoryDTO;
    private Date fromDate;
    private Date toDate;
    private int quantityRent;
    private int quantityFree;
    private String carNumber;
    private int yearProduct;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getImageAddress() {
        return imageAddress;
    }
    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }
    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }
    public Date getFromDate() {
        return fromDate;
    }
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    public Date getToDate() {
        return toDate;
    }
    public int getQuantityRent() {
        return quantityRent;
    }

    public void setQuantityRent(int quantityRent) {
        this.quantityRent = quantityRent;
    }

    public void setQuantityFree(int quantityFree) {
        this.quantityFree = quantityFree;
    }

    public int getQuantityFree() {
        return quantityFree;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public int getYearProduct() {
        return yearProduct;
    }

    public void setYearProduct(int yearProduct) {
        this.yearProduct = yearProduct;
    }

    @Override
    public String toString() {
        return "ProductDTO{" + "id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", imageAddress=" + imageAddress + ", categoryDTO=" + categoryDTO + ", fromDate=" + fromDate + ", toDate=" + toDate + ", quantityRent=" + quantityRent + ", quantityFree=" + quantityFree + ", carNumber=" + carNumber + ", yearProduct=" + yearProduct + '}';
    }

    

  
    
   

    
    
    
}
