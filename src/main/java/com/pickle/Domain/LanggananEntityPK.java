package com.pickle.Domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by andrikurniawan.id@gmail.com on 3/23/2016.
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
