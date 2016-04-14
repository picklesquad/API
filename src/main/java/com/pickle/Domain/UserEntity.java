package com.pickle.Domain;

import javax.persistence.*;

/**
 * Created by andrikurniawan.id@gmail.com on 3/23/2016.
 */
@Entity
@Table(name = "user", schema = "pickle_dev", catalog = "")
public class UserEntity {
    private int id;
    private String nama;
    private String email;
    private String phoneNumber;
    private String photo;
    private String alamat;
    private int exp;
    private int saldo;
    private String apiToken;
    private String fbToken;
    private int isComplete;
    private long memberSince;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nama")
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phonenumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "photo")
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Basic
    @Column(name = "alamat")
    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Basic
    @Column(name = "exp")
    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Basic
    @Column(name = "saldo")
    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    @Basic
    @Column(name = "apitoken")
    public String getAPIToken() {
        return apiToken;
    }

    public void setAPIToken(String apiToken) {
        this.apiToken = apiToken;
    }

    @Basic
    @Column(name = "fbtoken")
    public String getFacebookToken() {
        return fbToken;
    }

    public void setFacebookToken(String fbToken) {
        this.fbToken = fbToken;
    }

    @Basic
    @Column(name = "iscomplete")
    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    @Basic
    @Column(name = "membersince")
    public long getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(long memberSince) { this.memberSince = memberSince; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (exp != that.exp) return false;
        if (saldo != that.saldo) return false;
        if (nama != null ? !nama.equals(that.nama) : that.nama != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;
        if (alamat != null ? !alamat.equals(that.alamat) : that.alamat != null) return false;
        if (apiToken != null ? !apiToken.equals(that.apiToken) : that.apiToken != null) return false;
        if (fbToken != null ? !fbToken.equals(that.fbToken) : that.fbToken != null) return false;
        if (isComplete != that.isComplete) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nama != null ? nama.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (alamat != null ? alamat.hashCode() : 0);
        result = 31 * result + exp;
        result = 31 * result + saldo;
        result = 31 * result + (apiToken != null ? apiToken.hashCode() : 0);
        result = 31 * result + (fbToken != null ? fbToken.hashCode() : 0);
        result = 31 * result + isComplete;
        return result;
    }
}
