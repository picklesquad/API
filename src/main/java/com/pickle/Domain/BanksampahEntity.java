package com.pickle.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Entity for BankSampah.
 *
 * <p>Describes persistence mappings for table "banksampah".</p>
 *
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Entity
@Table(name = "banksampah", schema = "pickle_dev")
public class BanksampahEntity {
    private int id;
    private String nama;
    private String description;
    private String narahubung;
    private String phoneNumber;
    private String locationLat;
    private String locationLng;
    private String locationName;
    private String locationDesc;
    private String password;

    /**
     * Returns the id of this bank
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
     * Sets the bank id with the specified {@code id}.
     * @param id the id for this bank
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of this bank.
     * @return the {@code nama} property
     */
    @Basic
    @Column(name = "nama")
    public String getNama() {
        return nama;
    }

    /**
     * Sets the bank name with the specified {@code name}.
     * @param name the name for this bank
     */
    public void setNama(String name) {
        this.nama = name;
    }

    /**
     * Returns the description of this bank.
     * @return the {@code description} property
     */
    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    /**
     * Sets the bank description with the specified {@code description}.
     * @param description the description for this bank
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the contact person ({@code narahubung}) of this bank.
     * @return the {@code narahubung} property
     */
    @Basic
    @Column(name = "narahubung")
    public String getNarahubung() {
        return narahubung;
    }

    /**
     * Sets the bank contact person with the specified {@code contactPerson}.
     * @param contactPerson the contact person ({@code narahubung}) for this bank
     */
    public void setNarahubung(String contactPerson) {
        this.narahubung = contactPerson;
    }

    /**
     * Returns the phone number of this bank.
     * @return the {@code phoneNumber} property
     */
    @Basic
    @Column(name = "phonenumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the bank phone number with the specified {@code phoneNumber}.
     * @param phoneNumber the phone number for this bank
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the {@code locationLat} of this bank.
     * @return the {@code locationLat} property
     */
    @Basic
    @Column(name = "locationlat")
    public String getLocationLat() {
        return locationLat;
    }

    /**
     * Sets the bank {@code locationLat} with the specified {@code locationLat}.
     * @param locationLat the {@code locationLat} for this bank
     */
    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    /**
     * Returns the {@code locationLng} of this bank.
     * @return the {@code locationLng} property
     */
    @Basic
    @Column(name = "locationlng")
    public String getLocationLng() {
        return locationLng;
    }

    /**
     * Sets the bank {@code locationLng} with the specified {@code locationLng}.
     * @param locationLng the {@code locationLng} for this bank
     */
    public void setLocationLng(String locationLng) {
        this.locationLng = locationLng;
    }

    /**
     * Returns the location name of this bank.
     * @return the {@code locationName} property
     */
    @Basic
    @Column(name = "locationname")
    public String getLocationName() {
        return locationName;
    }

    /**
     * Sets the bank location name with the specified {@code locationName}.
     * @param locationName the location name for this bank
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * Returns the location description of this bank.
     * @return the {@code locationDesc} property
     */
    @Basic
    @Column(name = "locationdesc")
    public String getLocationDesc() {
        return locationDesc;
    }

    /**
     * Sets the bank location description with the specified {@code locationName}.
     * @param locationDesc the location description for this bank
     */
    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    /**
     * Returns the password of this bank account.
     * @return the {@code password} property
     */
    @Basic
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password with the specified {@code password}.
     * @param password the password for this bank
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BanksampahEntity that = (BanksampahEntity) o;

        if (id != that.id) return false;
        if (nama != null ? !nama.equals(that.nama) : that.nama != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (locationLat != null ? !locationLat.equals(that.locationLat) : that.locationLat != null) return false;
        if (locationLng != null ? !locationLng.equals(that.locationLng) : that.locationLng != null) return false;
        if (locationName != null ? !locationName.equals(that.locationName) : that.locationName != null) return false;
        if (locationDesc != null ? !locationDesc.equals(that.locationDesc) : that.locationDesc != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nama != null ? nama.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (locationLat != null ? locationLat.hashCode() : 0);
        result = 31 * result + (locationLng != null ? locationLng.hashCode() : 0);
        result = 31 * result + (locationName != null ? locationName.hashCode() : 0);
        result = 31 * result + (locationDesc != null ? locationDesc.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

}
