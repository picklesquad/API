package com.pickle.Domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Entity for Withdrawal.
 *
 * <p>Describes persistence mappings for table "withdraw"</p>
 *
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Entity
@Table(name = "withdraw", schema = "pickle_dev", catalog = "")
public class WithdrawEntity {
    private int id;
    private int idUser;
    private int idBank;
    private long waktu;
    private int nominal;
    private int status;

    /**
     * Returns the id of this withdrawal.
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
     * Sets the id of this withdrawal with the specified {@code id}.
     * @param id the {@code id} for this withdrawal
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user id of this withdrawal.
     * @return the {@code idUser} property
     */
    @Basic
    @Column(name = "iduser")
    public int getIdUser() {
        return idUser;
    }

    /**
     * Sets the user id of this withdrawal with the specified {@code idUser}.
     * @param idUser the {@code idUser} for this withdrawal
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Returns the bank id of this withdrawal.
     * @return the {@code idBank} property
     */
    @Basic
    @Column(name = "idbank")
    public int getIdBank() {
        return idBank;
    }

    /**
     * Sets the bank id of this withdrawal with the specified {@code idBank}.
     * @param idBank the {@code idBank} for this withdrawal
     */
    public void setIdBank(int idBank) {
        this.idBank = idBank;
    }

    /**
     * Returns the time of this withdrawal.
     * @return the {@code waktu} property
     */
    @Basic
    @Column(name = "waktu")
    public long getWaktu() {
        return waktu;
    }

    /**
     * Sets the time id of this withdrawal with the specified {@code time}.
     * @param time the {@code time} for this withdrawal
     */
    public void setWaktu(long time) {
        this.waktu = time;
    }

    /**
     * Returns the value of this withdrawal.
     * @return the {@code nominal} property
     */
    @Basic
    @Column(name = "nominal")
    public int getNominal() {
        return nominal;
    }

    /**
     * Sets the value of this withdrawal with the specified {@code value}.
     * @param value the {@code nominal} for this withdrawal
     */
    public void setNominal(int value) {
        this.nominal = value;
    }

    /**
     * Returns the status of this withdrawal.
     * <p>{@code status = 0} means still pending; {@code status = 1} means accepted but uncompleted; {@code status = 2}
     * means complete; {@code status = -1} means rejected.</p>
     * @return the {@code status} property
     */
    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of this withdrawal with the specified {@code status}.
     * <p>{@code status = 0} means still pending; {@code status = 1} means accepted but uncompleted; {@code status = 2}
     * @param status the {@code status} for this withdrawal
     */
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
