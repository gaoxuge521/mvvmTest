package com.mvvmhabit.entity;

public class UserInfoEntity {
    private String name;
    private String head_portrait;
    private String save_head_portrait;
    private String gender;
    private String is_perfect;
    private String is_login;
    private String mobile;
    private String error;
    private String nickname;
    private String is_identification;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_portrait() {
        return head_portrait;
    }

    public void setHead_portrait(String head_portrait) {
        this.head_portrait = head_portrait;
    }

    public String getSave_head_portrait() {
        return save_head_portrait;
    }

    public void setSave_head_portrait(String save_head_portrait) {
        this.save_head_portrait = save_head_portrait;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIs_perfect() {
        return is_perfect;
    }

    public void setIs_perfect(String is_perfect) {
        this.is_perfect = is_perfect;
    }

    public String getIs_login() {
        return is_login;
    }

    public void setIs_login(String is_login) {
        this.is_login = is_login;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIs_identification() {
        return is_identification;
    }

    public void setIs_identification(String is_identification) {
        this.is_identification = is_identification;
    }
}
