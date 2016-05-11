package com.pickle.Domain;

import javax.persistence.*;

/**
 * Entity for Langganan.
 *
 * <p>Describes persistence mappings for table "langganan".</p>
 *
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Entity
@Table(name = "langganan", schema = "pickle_dev", catalog = "")
@IdClass(LanggananEntityPK.class)
public class LanggananEntity {
    private int idbank;
    private int iduser;
    private long langgananSejak;
    private long transaksiPertama;

    /**
     * Returns the bank id of this langganan.
     * @return the {@code idBank} property
     */
    @Id
    @Column(name = "idbank")
    public int getIdbank() {
        return idbank;
    }

    /**
     * Sets the bank id of this langganan with the specified {@code idBank}.
     * @param idbank the {@code idBank} for this langganan
     */
    public void setIdbank(int idbank) {
        this.idbank = idbank;
    }

    /**
     * Returns the user id of this langganan.
     * @return the {@code idUser} property
     */
    @Id
    @Column(name = "iduser")
    public int getIduser() {
        return iduser;
    }

    /**
     * Sets the user id of this langganan with the specified {@code idUser}.
     * @param iduser the {@code idUser} for this langganan
     */
    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    /**
     * Returns the time millis represents the date when the subscription is applied.
     * @return the {@code langgananSejak} property
     */
    @Basic
    @Column(name = "langganansejak")
    public long getLanggananSejak() {
        return langgananSejak;
    }

    /**
     * Sets the time millis represents the date when the subscription is applied with the
     * specified {@code langgananSejak}.
     * @param langgananSejak the {@code langgananSejak} for this langganan
     */
    public void setLanggananSejak(long langgananSejak) {
        this.langgananSejak = langgananSejak;
    }

    /**
     * Returns the time millis represents the date when the user makes his/her first transaction.
     * @return the {@code transaksiPertama} property
     */
    @Basic
    @Column(name = "transaksipertama")
    public long getTransaksiPertama() {
        return transaksiPertama;
    }

    /**
     * Sets the time millis represents the date when the user makes his/her first transaction with the
     * specified {@code transaksiPertama}.
     * @param transaksiPertama the {@code transaksiPertama} for this langganan
     */
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
