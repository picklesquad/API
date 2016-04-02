package com.pickle.Domain;

import javax.persistence.*;

/**
 * Created by andrikurniawan.id@gmail.com on 3/23/2016.
 */
@Entity
@Table(name = "withdraw", schema = "pickle", catalog = "")
public class WithdrawEntity {
    private int id;
    private int idUser;
    private int idBank;
    private long waktu;
    private int nominal;
    private int status;

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
    @Column(name = "nominal")
    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WithdrawEntity that = (WithdrawEntity) o;

        if (id != that.id) return false;
        if (idUser != that.idUser) return false;
        if (idBank != that.idBank) return false;
        if (waktu != that.waktu) return false;
        if (nominal != that.nominal) return false;
        if (status != that.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idUser;
        result = 31 * result + idBank;
        result = 31 * result + (int) (waktu ^ (waktu >>> 32));
        result = 31 * result + nominal;
        result = 31 * result + status;
        return result;
    }
}
