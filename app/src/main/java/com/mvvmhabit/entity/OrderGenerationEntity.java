package com.mvvmhabit.entity;

import java.util.List;

public class OrderGenerationEntity {
    private String cartTotalNum;
    private String cartTotalPrice;
    private String discountTotal;
    private String addressId;
    private String deliveryNote;
    private String shippingTotal;
    private String grandTotal;
    private String checkMoney;
    private String allMoney;
    private  boolean paymentMethod;
    private AddressEntity address;
    private CartActivityInfo  cartActivityInfo;
    private List<NoProductsManjian> noProductsManjian;
    private List<NoProductsManjian> products;

    public boolean isPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(boolean paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCartTotalNum() {
        return cartTotalNum;
    }

    public void setCartTotalNum(String cartTotalNum) {
        this.cartTotalNum = cartTotalNum;
    }

    public String getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(String cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public String getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(String discountTotal) {
        this.discountTotal = discountTotal;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(String shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getCheckMoney() {
        return checkMoney;
    }

    public void setCheckMoney(String checkMoney) {
        this.checkMoney = checkMoney;
    }

    public String getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(String allMoney) {
        this.allMoney = allMoney;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public CartActivityInfo getCartActivityInfo() {
        return cartActivityInfo;
    }

    public void setCartActivityInfo(CartActivityInfo cartActivityInfo) {
        this.cartActivityInfo = cartActivityInfo;
    }

    public List<NoProductsManjian> getNoProductsManjian() {
        return noProductsManjian;
    }

    public void setNoProductsManjian(List<NoProductsManjian> noProductsManjian) {
        this.noProductsManjian = noProductsManjian;
    }

    public List<NoProductsManjian> getProducts() {
        return products;
    }

    public void setProducts(List<NoProductsManjian> products) {
        this.products = products;
    }

    public class NoProductsManjian{
        private String itemId;
        private int active;
        private String product_id;
        private String spu;
        private String sku;
        private String name;
        private int num;
        private String brand;
        private String category;
        private String price;
        private String totalPrice;
        private String productDiscount;
        private String main_image;
        private String spuAttrStr;
        private String weight;
        private String qty;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public int getActive() {
            return active;
        }

        public void setActive(int active) {
            this.active = active;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getSpu() {
            return spu;
        }

        public void setSpu(String spu) {
            this.spu = spu;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getProductDiscount() {
            return productDiscount;
        }

        public void setProductDiscount(String productDiscount) {
            this.productDiscount = productDiscount;
        }

        public String getMain_image() {
            return main_image;
        }

        public void setMain_image(String main_image) {
            this.main_image = main_image;
        }

        public String getSpuAttrStr() {
            return spuAttrStr;
        }

        public void setSpuAttrStr(String spuAttrStr) {
            this.spuAttrStr = spuAttrStr;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }
    }
    public class CartActivityInfo{

    }

}
