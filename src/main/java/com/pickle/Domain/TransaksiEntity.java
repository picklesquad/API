package com.pickle.Domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Entity for Transaksi.
 *
 * <p>Describes persistence mappings for table "transaksi"</p>
 *
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Entity
@Table(name = "transaksi", schema = "pickle_dev")
public class TransaksiEntity {
    private int id;
    private int idUser;
    private int idBank;
    private long waktu;
    private int harga;
    private int status;
    private Integer rating;
    private String sampahPlastik;
    private String sampahBotol;
    private String sampahBesi;
    private String sampahKertas;

    /**
     * Returns the id of this transaksi.
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
     * Sets the id of this transaksi with the specified {@code id}.
     * @param id the {@code id} for this transaksi
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user id of this transaksi.
     * @return the {@code idUser} property
     */
    @Basic
    @Column(name = "iduser")
    public int getIdUser() {
        return idUser;
    }

    /**
     * Sets the user id of this transaksi with the specified {@code idUser}.
     * @param idUser the {@code idUser} for this transaksi
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Returns the bank id of this transaksi.
     * @return the {@code idBank} property
     */
    @Basic
    @Column(name = "idbank")
    public int getIdBank() {
        return idBank;
    }

    /**
     * Sets the bank id of this transaksi with the specified {@code idBank}.
     * @param idBank the {@code idBank} for this transaksi
     */
    public void setIdBank(int idBank) {
        this.idBank = idBank;
    }

    /**
     * Returns the time millis representing the date when this transaksi is made.
     * @return the {@code waktu} property
     */
    @Basic
    @Column(name = "waktu")
    public long getWaktu() {
        return waktu;
    }

    /**
     * Sets the time millis representing the date when this transaksi is made with the specified {@code waktu}.
     * @param waktu the {@code waktu} for this transaksi
     */
    public void setWaktu(long waktu) {
        this.waktu = waktu;
    }

    /**
     * Returns the value (price) of this transaksi.
     * @return the {@code harga} property
     */
    @Basic
    @Column(name = "harga")
    public int getHarga() {
        return harga;
    }

    /**
     * Sets the value (price) of this transaksi with the specified {@code harga}.
     * @param harga the {@code harga} for this transaksi
     */
    public void setHarga(int harga) {
        this.harga = harga;
    }

    /**
     * Returns the status of this transaksi.
     *
     * <p>{@code status = 0} means still pending; {@code status = 1} means accepted; {@code status = -1} means
     * rejected.</p>
     * @return the {@code status} property
     */
    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of this transaksi with the specified {@code status}.
     * <p>{@code status = 0} means still pending; {@code status = 1} means accepted; {@code status = -1} means
     * rejected.</p>
     * @param status the {@code status} for this transaksi
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns the rating of this transaksi.
     * @return the {@code rating} property
     */
    @Basic
    @Column(name = "rating")
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets the rating of this transaksi with the specified {@code rating}.
     * @param rating the {@code rating} for this transaksi
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Returns the sampah plastik of this transaksi.
     * @return the {@code sampahPlastik} property
     */
    @Basic
    @Column(name = "sampahplastik")
    public String getSampahPlastik() {
        return sampahPlastik;
    }

    /**
     * Sets the value of sampah plastik of this transaksi with the specified {@code value}.
     * @param value the value of {@code sampahPlastik} for this transaksi
     */
    public void setSampahPlastik(String value) {
        this.sampahPlastik = value;
    }

    /**
     * Returns the sampah botol of this transaksi.
     * @return the {@code sampahBotol} property
     */
    @Basic
    @Column(name = "sampahbotol")
    public String getSampahBotol() {
        return sampahBotol;
    }

    /**
     * Sets the value of sampah botol of this transaksi with the specified {@code value}.
     * @param value the value of {@code sampahBotol} for this transaksi
     */
    public void setSampahBotol(String value) {
        this.sampahBotol = value;
    }

    /**
     * Returns the sampah besi of this transaksi.
     * @return the {@code sampahPlastik} property
     */
    @Basic
    @Column(name = "sampahbesi")
    public String getSampahBesi() {
        return sampahBesi;
    }

    /**
     * Sets the value of sampah besi of this transaksi with the specified {@code value}.
     * @param value the value of {@code sampahBesi} for this transaksi
     */
    public void setSampahBesi(String value) {
        this.sampahBesi = value;
    }

    /**
     * Returns the sampah kertas of this transaksi.
     * @return the {@code sampahKertas} property
     */
    @Basic
    @Column(name = "sampahkertas")
    public String getSampahKertas() {
        return sampahKertas;
    }

    /**
     * Sets the value of sampah kertas of this transaksi with the specified {@code value}.
     * @param value the value of {@code sampahKertas} for this transaksi
     */
    public void setSampahKertas(String value) {
        this.sampahKertas = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransaksiEntity that = (TransaksiEntity) o;

        if (id != that.id) return false;
        if (idUser != that.idUser) return false;
        if (idBank != that.idBank) return false;
        if (waktu != that.waktu) return false;
        if (harga != that.harga) return false;
        if (status != that.status) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (sampahPlastik != null ? !sampahPlastik.equals(that.sampahPlastik) : that.sampahPlastik != null)
            return false;
        if (sampahBotol != null ? !sampahBotol.equals(that.sampahBotol) : that.sampahBotol != null) return false;
        if (sampahBesi != null ? !sampahBesi.equals(that.sampahBesi) : that.sampahBesi != null) return false;
        if (sampahKertas != null ? !sampahKertas.equals(that.sampahKertas) : that.sampahKertas != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idUser;
        result = 31 * result + idBank;
        result = 31 * result + (int) (waktu ^ (waktu >>> 32));
        result = 31 * result + harga;
        result = 31 * result + status;
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (sampahPlastik != null ? sampahPlastik.hashCode() : 0);
        result = 31 * result + (sampahBotol != null ? sampahBotol.hashCode() : 0);
        result = 31 * result + (sampahBesi != null ? sampahBesi.hashCode() : 0);
        result = 31 * result + (sampahKertas != null ? sampahKertas.hashCode() : 0);
        return result;
    }
}
