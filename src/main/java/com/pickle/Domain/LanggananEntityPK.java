package com.pickle.Domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Entity for primary keys of Langganan.
 *
 * <p>Describes persistence mappings for multiple PK in table "langganan"</p>
 *
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
public class LanggananEntityPK implements Serializable {
    private int idbank;
    private int iduser;

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

    public void setIduser(int idUser) {
        this.iduser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanggananEntityPK that = (LanggananEntityPK) o;

        if (idbank != that.idbank) return false;
        if (iduser != that.iduser) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idbank;
        result = 31 * result + iduser;
        return result;
    }
}
