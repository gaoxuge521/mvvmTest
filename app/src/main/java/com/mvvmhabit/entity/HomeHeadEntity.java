package com.mvvmhabit.entity;

import java.util.List;

public class HomeHeadEntity  {
    private List<HomeAd> homeAd;
    private List<CategoryAd> categoryAd;
    private HeadAd headAd;
    private ActivityGroupAd activityGroupAd;
    private ActivityPreSaleAd activityPreSaleAd;

    public List<HomeAd> getHomeAd() {
        return homeAd;
    }

    public void setHomeAd(List<HomeAd> homeAd) {
        this.homeAd = homeAd;
    }

    public List<CategoryAd> getCategoryAd() {
        return categoryAd;
    }

    public void setCategoryAd(List<CategoryAd> categoryAd) {
        this.categoryAd = categoryAd;
    }

    public HeadAd getHeadAd() {
        return headAd;
    }

    public void setHeadAd(HeadAd headAd) {
        this.headAd = headAd;
    }

    public ActivityGroupAd getActivityGroupAd() {
        return activityGroupAd;
    }

    public void setActivityGroupAd(ActivityGroupAd activityGroupAd) {
        this.activityGroupAd = activityGroupAd;
    }

    public ActivityPreSaleAd getActivityPreSaleAd() {
        return activityPreSaleAd;
    }

    public void setActivityPreSaleAd(ActivityPreSaleAd activityPreSaleAd) {
        this.activityPreSaleAd = activityPreSaleAd;
    }
    public  class HeadAd{
        private String id;
        private String title;
        private String image;
        private String redirect_activity_type;
        private String redirect_activity_id;
        private String type;
        private String status;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRedirect_activity_type() {
            return redirect_activity_type;
        }

        public void setRedirect_activity_type(String redirect_activity_type) {
            this.redirect_activity_type = redirect_activity_type;
        }

        public String getRedirect_activity_id() {
            return redirect_activity_id;
        }

        public void setRedirect_activity_id(String redirect_activity_id) {
            this.redirect_activity_id = redirect_activity_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
    public class HomeAd{
        private String title;
        private String image;
        private String page_id;
        private String redirect_activity_type;
        private String redirect_activity_id;
        private int is_use;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPage_id() {
            return page_id;
        }

        public void setPage_id(String page_id) {
            this.page_id = page_id;
        }

        public String getRedirect_activity_type() {
            return redirect_activity_type;
        }

        public void setRedirect_activity_type(String redirect_activity_type) {
            this.redirect_activity_type = redirect_activity_type;
        }

        public String getRedirect_activity_id() {
            return redirect_activity_id;
        }

        public void setRedirect_activity_id(String redirect_activity_id) {
            this.redirect_activity_id = redirect_activity_id;
        }

        public int getIs_use() {
            return is_use;
        }

        public void setIs_use(int is_use) {
            this.is_use = is_use;
        }
    }
    public class CategoryAd{
        private String id;
        private String title;
        private String image;
        private String redirect_activity_type;
        private String redirect_activity_id;
        private String type;
        private String status;
        private String created_at;
        private String updated_at;
        private int is_use;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRedirect_activity_type() {
            return redirect_activity_type;
        }

        public void setRedirect_activity_type(String redirect_activity_type) {
            this.redirect_activity_type = redirect_activity_type;
        }

        public String getRedirect_activity_id() {
            return redirect_activity_id;
        }

        public void setRedirect_activity_id(String redirect_activity_id) {
            this.redirect_activity_id = redirect_activity_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getIs_use() {
            return is_use;
        }

        public void setIs_use(int is_use) {
            this.is_use = is_use;
        }
    }
    public class ActivityGroupAd{
        private String home_page_image;
        private String main_image;

        public String getHome_page_image() {
            return home_page_image;
        }

        public void setHome_page_image(String home_page_image) {
            this.home_page_image = home_page_image;
        }

        public String getMain_image() {
            return main_image;
        }

        public void setMain_image(String main_image) {
            this.main_image = main_image;
        }
    }
    public class ActivityPreSaleAd{
        private String home_page_image;
        private String main_image;

        public String getHome_page_image() {
            return home_page_image;
        }

        public void setHome_page_image(String home_page_image) {
            this.home_page_image = home_page_image;
        }

        public String getMain_image() {
            return main_image;
        }

        public void setMain_image(String main_image) {
            this.main_image = main_image;
        }
    }
}
