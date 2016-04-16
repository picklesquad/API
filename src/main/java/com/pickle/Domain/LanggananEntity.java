package com.pickle.Domain;

import javax.persistence.*;

/**
 * Created by andrikurniawan.id@gmail.com on 3/23/2016.
 */
@Entity
@Table(name = "langganan", schema = "pickle_dev")
public class LanggananEntity {
    private BanksampahEntity banksampahEntity;
    private UserEntity userEntity;
    private long langgananSejak;
    private long transaksiPertama;

    @Id
    @ManyToOne
    @JoinColumn(name = "idbank")
    public BanksampahEntity getBank() {
        return banksampahEntity;
    }

    public void setBank(BanksampahEntity banksampahEntity) {
        this.banksampahEntity = banksampahEntity;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "iduser")
    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
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

}
