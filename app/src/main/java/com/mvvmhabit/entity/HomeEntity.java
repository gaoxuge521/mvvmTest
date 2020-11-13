package com.mvvmhabit.entity;

import java.util.List;

public class HomeEntity {
  private String name;
  private String image;
  private int count;
  private int pageSize;
    private List<Product> product;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public static class Product{
      private String name;
      private boolean isLogin;
      private String sku;
      private String _id;
      private String image;
      private String share_image;
      private int qty;
      private int sale_num;
      private int month_sale_num;
      private String price;
      private String thumbnails_image;
        private String pack;
        private String attr_group_id;
        private String product_id;
        private String description;
        private String count;
        private String itemId;
      private List<String> category_id;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        private int in_promotion;

        public boolean isLogin() {
            return isLogin;
        }

        public void setLogin(boolean login) {
            isLogin = login;
        }

        public void setName(String name) {
          this.name = name;
      }
      public String getName() {
          return name;
      }

      public void setSku(String sku) {
          this.sku = sku;
      }
      public String getSku() {
          return sku;
      }

      public String get_id() {
          return _id;
      }

      public void set_id(String _id) {
          this._id = _id;
      }

      public String getImage() {
          return image;
      }

      public void setImage(String image) {
          this.image = image;
      }

      public String getShare_image() {
          return share_image;
      }

      public void setShare_image(String share_image) {
          this.share_image = share_image;
      }

      public int getQty() {
          return qty;
      }

      public void setQty(int qty) {
          this.qty = qty;
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

      public String getThumbnails_image() {
          return thumbnails_image;
      }

      public void setThumbnails_image(String thumbnails_image) {
          this.thumbnails_image = thumbnails_image;
      }

      public String getPack() {
          return pack;
      }

      public void setPack(String pack) {
          this.pack = pack;
      }

      public String getAttr_group_id() {
          return attr_group_id;
      }

      public void setAttr_group_id(String attr_group_id) {
          this.attr_group_id = attr_group_id;
      }

      public String getProduct_id() {
          return product_id;
      }

      public void setProduct_id(String product_id) {
          this.product_id = product_id;
      }

      public String getDescription() {
          return description;
      }

      public void setDescription(String description) {
          this.description = description;
      }

      public List<String> getCategory_id() {
          return category_id;
      }

      public void setCategory_id(List<String> category_id) {
          this.category_id = category_id;
      }

      public int getIn_promotion() {
          return in_promotion;
      }

      public void setIn_promotion(int in_promotion) {
          this.in_promotion = in_promotion;
      }
    }
}
