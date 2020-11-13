package com.mvvmhabit.entity;

import java.util.List;

public class GoodDetailEntity {
    private String currentSpuAttr;
    private String reviewCount;
    private String reviewFirst;
    private int favoriteFlag;
    private Product product;

    public String getCurrentSpuAttr() {
        return currentSpuAttr;
    }

    public void setCurrentSpuAttr(String currentSpuAttr) {
        this.currentSpuAttr = currentSpuAttr;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getReviewFirst() {
        return reviewFirst;
    }

    public void setReviewFirst(String reviewFirst) {
        this.reviewFirst = reviewFirst;
    }

    public int getFavoriteFlag() {
        return favoriteFlag;
    }

    public void setFavoriteFlag(int favoriteFlag) {
        this.favoriteFlag = favoriteFlag;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public class Product {
        private String _id;
        private String name;
        private String spu;
        private String sku;
        private int status;
        private int qty;
        private int min_sales_qty;
        private int is_in_stock;
        private List<String> category_id;
        private String description;
        private String server_description;
        private int created_at;
        private int updated_at;
        private String box_code;
        private String bar_code;
        private String attr_group_id;
        private String supplier_id;
        private String main_image;
        private List<String> detail_image;
        private List<String> thumbnails_image;
        private int sale_num;
        private int month_sale_num;
        private String price;
        private String box_width;
        private String box_height;
        private String box_depth;
        private String product_info;
        private String product_pack;
        private String sort;
        private String minimun_spec_product_id;
        private String has_minimum_product;
        private String master_spec_product_id;
        private String is_minimum_product;
        private int enable_in_app;
        private int promoter_price;
        private int profit_price;
        private String weight;
        private String save;
        private String itemId;
        private List<Groupattrarr> groupAttrArr;
        private int in_promotion;

        public String get_id() {
            return _id;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public int getMin_sales_qty() {
            return min_sales_qty;
        }

        public void setMin_sales_qty(int min_sales_qty) {
            this.min_sales_qty = min_sales_qty;
        }

        public int getIs_in_stock() {
            return is_in_stock;
        }

        public void setIs_in_stock(int is_in_stock) {
            this.is_in_stock = is_in_stock;
        }

        public List<String> getCategory_id() {
            return category_id;
        }

        public void setCategory_id(List<String> category_id) {
            this.category_id = category_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getServer_description() {
            return server_description;
        }

        public void setServer_description(String server_description) {
            this.server_description = server_description;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public String getBox_code() {
            return box_code;
        }

        public void setBox_code(String box_code) {
            this.box_code = box_code;
        }

        public String getBar_code() {
            return bar_code;
        }

        public void setBar_code(String bar_code) {
            this.bar_code = bar_code;
        }

        public String getAttr_group_id() {
            return attr_group_id;
        }

        public void setAttr_group_id(String attr_group_id) {
            this.attr_group_id = attr_group_id;
        }

        public String getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(String supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getMain_image() {
            return main_image;
        }

        public void setMain_image(String main_image) {
            this.main_image = main_image;
        }

        public List<String> getDetail_image() {
            return detail_image;
        }

        public void setDetail_image(List<String> detail_image) {
            this.detail_image = detail_image;
        }

        public List<String> getThumbnails_image() {
            return thumbnails_image;
        }

        public void setThumbnails_image(List<String> thumbnails_image) {
            this.thumbnails_image = thumbnails_image;
        }

        public int getSale_num() {
            return sale_num;
        }

        public void setSale_num(int sale_num) {
            this.sale_num = sale_num;
        }

        public int getMonth_sale_num() {
            return month_sale_num;
        }

        public void setMonth_sale_num(int month_sale_num) {
            this.month_sale_num = month_sale_num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBox_width() {
            return box_width;
        }

        public void setBox_width(String box_width) {
            this.box_width = box_width;
        }

        public String getBox_height() {
            return box_height;
        }

        public void setBox_height(String box_height) {
            this.box_height = box_height;
        }

        public String getBox_depth() {
            return box_depth;
        }

        public void setBox_depth(String box_depth) {
            this.box_depth = box_depth;
        }

        public String getProduct_info() {
            return product_info;
        }

        public void setProduct_info(String product_info) {
            this.product_info = product_info;
        }

        public String getProduct_pack() {
            return product_pack;
        }

        public void setProduct_pack(String product_pack) {
            this.product_pack = product_pack;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getMinimun_spec_product_id() {
            return minimun_spec_product_id;
        }

        public void setMinimun_spec_product_id(String minimun_spec_product_id) {
            this.minimun_spec_product_id = minimun_spec_product_id;
        }

        public String getHas_minimum_product() {
            return has_minimum_product;
        }

        public void setHas_minimum_product(String has_minimum_product) {
            this.has_minimum_product = has_minimum_product;
        }

        public String getMaster_spec_product_id() {
            return master_spec_product_id;
        }

        public void setMaster_spec_product_id(String master_spec_product_id) {
            this.master_spec_product_id = master_spec_product_id;
        }

        public String getIs_minimum_product() {
            return is_minimum_product;
        }

        public void setIs_minimum_product(String is_minimum_product) {
            this.is_minimum_product = is_minimum_product;
        }

        public int getEnable_in_app() {
            return enable_in_app;
        }

        public void setEnable_in_app(int enable_in_app) {
            this.enable_in_app = enable_in_app;
        }

        public int getPromoter_price() {
            return promoter_price;
        }

        public void setPromoter_price(int promoter_price) {
            this.promoter_price = promoter_price;
        }

        public int getProfit_price() {
            return profit_price;
        }

        public void setProfit_price(int profit_price) {
            this.profit_price = profit_price;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getSave() {
            return save;
        }

        public void setSave(String save) {
            this.save = save;
        }

        public List<Groupattrarr> getGroupAttrArr() {
            return groupAttrArr;
        }

        public void setGroupAttrArr(List<Groupattrarr> groupAttrArr) {
            this.groupAttrArr = groupAttrArr;
        }

        public int getIn_promotion() {
            return in_promotion;
        }

        public void setIn_promotion(int in_promotion) {
            this.in_promotion = in_promotion;
        }
    }


    public class Groupattrarr{
        private String key;
        private String value;
        public void setKey(String key) {
            this.key = key;
        }
        public String getKey() {
            return key;
        }

        public void setValue(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
}
