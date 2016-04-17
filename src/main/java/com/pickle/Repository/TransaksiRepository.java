package com.pickle.Repository;

import com.pickle.Domain.TransaksiEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by andrikurniawan.id@gmail.com on 3/28/2016.
 */
@Repository
public interface TransaksiRepository extends CrudRepository<TransaksiEntity, Integer> {

    public TransaksiEntity findById(int id);
    public List<TransaksiEntity> findByIdUser(int idUser);

    @Query(value = "SELECT sum(sampahplastik) from transaksi where idbank = :idbank",
            nativeQuery = true)
        public Double getTotalSampahPlastikBank(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(sampahbotol) from transaksi where idbank = :idbank",
            nativeQuery = true)
    public int getTotalSampahBotolBank(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(sampahbesi) from transaksi where idbank = :idbank",
            nativeQuery = true)
    public Double getTotalSampahBesiBank(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(sampahkertas) from transaksi where idbank = :idbank",
            nativeQuery = true)
    public Double getTotalSampahKertasBank(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(sampahplastik) from transaksi where iduser = :iduser",
            nativeQuery = true)
    public Double getTotalSampahPlastikUser(@Param("iduser") int iduser);

    @Query(value = "SELECT sum(sampahbotol) from transaksi where iduser = :iduser",
            nativeQuery = true)
    public int getTotalSampahBotolUser(@Param("iduser") int iduser);

    @Query(value = "SELECT sum(sampahbesi) from transaksi where iduser = :iduser",
            nativeQuery = true)
    public Double getTotalSampahBesiUser(@Param("iduser") int iduser);

    @Query(value = "SELECT sum(sampahkertas) from transaksi where iduser = :iduser",
            nativeQuery = true)
    public Double getTotalSampahKertasUser(@Param("iduser") int iduser);

    @Query(value = "SELECT avg(rating) from transaksi where idbank = :idbank",
            nativeQuery = true)
    public Double getTotalRating(@Param("idbank") int idbank);

}
