/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ASUS
 */
public class CartDTO {

    private String idAccount;
    private LinkedHashMap<String, List<ProductDTO>> cart;
    private String paymentId;
    private String payerId;

    public CartDTO() {
        this.cart = new LinkedHashMap<>();
    }

    public CartDTO(String id) {
        this.idAccount = id;
        this.cart = new LinkedHashMap<>();
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public void setCart(LinkedHashMap<String, List<ProductDTO>> cart) {
        this.cart = cart;
    }

    public LinkedHashMap<String, List<ProductDTO>> getCart() {
        return cart;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public boolean addToCart(ProductDTO productDTO) {
        if (cart.containsKey(productDTO.getId())) {
            List<ProductDTO> listProductDTOs = cart.get(productDTO.getId());
            for (ProductDTO dto : listProductDTOs) {
                if (dto.getId().equals(productDTO.getId())) {
                    listProductDTOs.add(productDTO);
                    return true;
                }
            }
        } else {
            List<ProductDTO> listProductDTOs = new ArrayList<>();
            listProductDTOs.add(productDTO);
            cart.put(productDTO.getId(), listProductDTOs);
            return true;
        }
        return false;
    }

    public boolean remove(ProductDTO productDTO) {
        if (cart.containsKey(productDTO.getId())) {
            List<ProductDTO> listProductDTOs = cart.get(productDTO.getId());
            for (int i = 0; i < listProductDTOs.size(); i++) {
                if (listProductDTOs.get(i).getId().equals(productDTO.getId())) {
                    if (listProductDTOs.get(i).getFromDate().getTime() == productDTO.getFromDate().getTime() && listProductDTOs.get(i).getToDate().getTime() == productDTO.getToDate().getTime()) {
                        if(listProductDTOs.size()==1){
                            cart.remove(productDTO.getId());
                        }else{
                            listProductDTOs.remove(i);
                        }
                        
                        
                        return true;
                    }

                }
            }
        }
        return false;
    }

    public void update(String id, ProductDTO productDTO) {

    }

    public double total() {
        float total = 0;
        for (Map.Entry<String, List<ProductDTO>> entry : cart.entrySet()) {
            for (ProductDTO dto : entry.getValue()) {
                long date=dto.getToDate().getTime()-dto.getFromDate().getTime();
                long diff=TimeUnit.DAYS.convert(date, TimeUnit.MILLISECONDS);
                total += dto.getPrice()*diff;
            }
        }
        return total;
    }

    public List<ProductDTO> getItems() {
        List<ProductDTO> result = new ArrayList<>();
        for (List<ProductDTO> list : cart.values()) {
            for (ProductDTO dto : list) {
                result.add(dto);
            }
        }
        return result;
    }
}
