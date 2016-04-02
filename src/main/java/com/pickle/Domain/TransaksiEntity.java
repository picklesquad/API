package com.pickle.Domain;

import javax.persistence.*;

/**
 * Created by andrikurniawan.id@gmail.com on 3/23/2016.
 */
@Entity
@Table(name = "transaksi", schema = "pickle")
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

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "iduser")
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "idbank")
    public int getIdBank() {
        return idBank;
    }

    public void setIdBank(int idBank) {
        this.idBank = idBank;
    }

    @Basic
    @Column(name = "waktu")
    public long getWaktu() {
        return waktu;
    }

    public void setWaktu(long waktu) {
        this.waktu = waktu;
    }

    @Basic
    @Column(name = "harga")
    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "rating")
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "sampahplastik")
    public String getSampahPlastik() {
        return sampahPlastik;
    }

    public void setSampahPlastik(String sampahPlastik) {
        this.sampahPlastik = sampahPlastik;
    }

    @Basic
    @Column(name = "sampahbotol")
    public String getSampahBotol() {
        return sampahBotol;
    }

    public void setSampahBotol(String sampahBotol) {
        this.sampahBotol = sampahBotol;
    }

    @Basic
    @Column(name = "sampahbesi")
    public String getSampahBesi() {
        return sampahBesi;
    }

    public void setSampahBesi(String sampahBesi) {
        this.sampahBesi = sampahBesi;
    }

    @Basic
    @Column(name = "sampahkertas")
    public String getSampahKertas() {
        return sampahKertas;
    }

    public void setSampahKertas(String sampahKertas) {
        this.sampahKertas = sampahKertas;
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
