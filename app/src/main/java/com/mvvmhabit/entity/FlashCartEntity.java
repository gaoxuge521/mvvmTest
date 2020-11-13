package com.mvvmhabit.entity;

import java.util.HashMap;

public class FlashCartEntity {
    private Cart cart;
    private HashMap<String ,CartItem > items;
    public class Cart{
        private String cartId;
        private String items_count;

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getItems_count() {
            return items_count;
        }

        public void setItems_count(String items_count) {
            this.items_count = items_count;
        }
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public HashMap<String, CartItem> getItems() {
        return items;
    }

    public void setItems(HashMap<String, CartItem> items) {
        this.items = items;
    }

    public class CartItem{
        private String productId;
        private String qty;
        private String itemId;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }
    }
}
