package com.pickle.Domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Entity for User.
 *
 * <p>Describes persistence mappings for table "user"</p>
 *
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
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
    private int gender;
    private int exp;
    private int saldo;
    private String apiToken;
    private String fbToken;
    private int isComplete;
    private long memberSince;
    private String gcmId;

    /**
     * Returns the id of this user.
     * @return the {@code id} property
     */
    @Id
    @GenericGenerator(name="generator" , strategy="increment")
    @GeneratedValue(generator="generator")
    @Column(name = "id")
    public int getId() {
        return id;
    }

    /**
     * Sets the id of this user with the specified {@code id}.
     * @param id the {@code id} for this user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of this user.
     * @return the {@code nama} property
     */
    @Basic
    @Column(name = "nama")
    public String getNama() {
        return nama;
    }

    /**
     * Sets the name of this user with the specified {@code name}.
     * @param name the {@code nama} for this user
     */
    public void setNama(String name) {
        this.nama = name;
    }

    /**
     * Returns the email of this user.
     * @return the {@code email} property
     */
    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of this user with the specified {@code email}.
     * @param email the {@code email} for this user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of this user.
     * @return the {@code phoneNumber} property
     */
    @Basic
    @Column(name = "phonenumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of this user with the specified {@code phoneNumber}.
     * @param phoneNumber the {@code phoneNumber} for this user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the photo URL of this user.
     * @return the {@code photo} property
     */
    @Basic
    @Column(name = "photo")
    public String getPhoto() {
        return photo;
    }

    /**
     * Sets the photo URL of this user with the specified {@code photo}.
     * @param photo the {@code photo} URL for this user
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Returns the gender of this user.
     * @return the {@code gender} property, 0 is male, 1 is female
     */
    @Basic
    @Column(name = "gender")
    public int getGender() {
        return gender;
    }

    /**
     * Sets the gender of this user with the specified {@code address}.
     * @param gender the {@code alamat} for this user, 0 is male, 1 is female
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "alamat")
    public String getAlamat() {
        return alamat;
    }

    /**
     * Sets the address of this user with the specified {@code address}.
     * @param address the {@code alamat} for this user
     */
    public void setAlamat(String address) {
        this.alamat = address;
    }

    /**
     * Returns the experiences of this user.
     * @return the {@code exp} property
     */
    @Basic
    @Column(name = "exp")
    public int getExp() {
        return exp;
    }

    /**
     * Sets the experiences of this user with the specified {@code exp}.
     * @param exp the {@code exp} for this user
     */
    public void setExp(int exp) {
        this.exp = exp;
    }

    /**
     * Returns the balance of this user.
     * @return the {@code saldo} property
     */
    @Basic
    @Column(name = "saldo")
    public int getSaldo() {
        return saldo;
    }

    /**
     * Sets the balance of this user with the specified {@code balance}.
     * @param balance the {@code saldo} for this user
     */
    public void setSaldo(int balance) {
        this.saldo = balance;
    }

    /**
     * Returns the API token of this user.
     * @return the {@code apiToken} property
     */
    @Basic
    @Column(name = "apitoken")
    public String getApiToken() {
        return apiToken;
    }

    /**
     * Sets the API token of this user with the specified {@code token}.
     * @param token the {@code apiToken} for this user
     */
    public void setApiToken(String token) {
        this.apiToken = token;
    }

    /**
     * Returns the Facebook token of this user.
     * @return the {@code fbToken} property
     */
    @Basic
    @Column(name = "fbtoken")
    public String getFbToken() {
        return fbToken;
    }

    /**
     * Sets the Facebook token of this user with the specified {@code token}.
     * @param token the {@code fbToken} for this user
     */
    public void setFbToken(String token) {
        this.fbToken = token;
    }

    /**
     * Returns an integer representing whether this user has completed his/her data or not.
     * <p>1 means the data is complete, 0 means no.</p>
     * @return the {@code isComplete} property
     */
    @Basic
    @Column(name = "iscomplete")
    public int getIsComplete() {
        return isComplete;
    }

    /**
     * Sets the integer representing whether this user has completed his/her data or not with the specified {@code
     * isComplete}.
     * <p>1 means the data is complete, 0 means no.</p>
     * @param isComplete the integer representing completion of this user
     */
    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    /**
     * Returns the time millis representing the registration date of this user.
     * @return the {@code memberSince} property
     */
    @Basic
    @Column(name = "membersince")
    public long getMemberSince() {
        return memberSince;
    }

    /**
     * Sets the time millis representing the registration date of this user.
     * @param memberSince the time millis representing registration date of this user
     */
    public void setMemberSince(long memberSince) { this.memberSince = memberSince; }

    @Basic
    @Column(name = "gcmid")
    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

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
