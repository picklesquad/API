package com.pickle.Domain;

import javax.persistence.*;

/**
 * Created by andrikurniawan.id@gmail.com on 3/23/2016.
 */
@Entity
@Table(name = "langganan", schema = "pickle", catalog = "")
@IdClass(LanggananEntityPK.class)
public class LanggananEntity {
    private int idbank;
    private int iduser;
    private long langgananSejak;
    private long transaksiPertama;

    @Id
    @Column(name = "idbank")
    public int getIdbank() {
        return idbank;
    }

    public void setIdbank(int idbank) {
        this.idbank = idbank;
    }

    @Id
    @Column(name = "iduser")
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Basic
    @Column(name = "langganansejak")
    public long getLanggananSejak() {
        return langgananSejak;
    }

    public void setLanggananSejak(long langgananSejak) {
        this.langgananSejak = langgananSejak;
    }

    @Basic
    @Column(name = "transaksipertama")
    public long getTransaksiPertama() {
        return transaksiPertama;
    }

    public void setTransaksiPertama(long transaksiPertama) {
        this.transaksiPertama = transaksiPertama;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanggananEntity that = (LanggananEntity) o;

        if (idbank != that.idbank) return false;
        if (iduser != that.iduser) return false;
        if (langgananSejak != that.langgananSejak) return false;
        if (transaksiPertama != that.transaksiPertama) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idbank;
        result = 31 * result + iduser;
        result = 31 * result + (int) (langgananSejak ^ (langgananSejak >>> 32));
        result = 31 * result + (int) (transaksiPertama ^ (transaksiPertama >>> 32));
        return result;
    }
}
