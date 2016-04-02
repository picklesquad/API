package com.pickle.Repository;

import com.pickle.Domain.TransaksiEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by andrikurniawan.id@gmail.com on 3/28/2016.
 */
@Repository
public interface TransaksiRepository extends CrudRepository<TransaksiEntity, Integer> {

    @Query(value = "SELECT sum(sampahplastik) from transaksi where idbank = :idbank",
            nativeQuery = true)
        public Double getTotalSampahPlastik(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(sampahbotol) from transaksi where idbank = :idbank",
            nativeQuery = true)
    public int getTotalSampahBotol(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(sampahbesi) from transaksi where idbank = :idbank",
            nativeQuery = true)
    public Double getTotalSampahBesi(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(sampahkertas) from transaksi where idbank = :idbank",
            nativeQuery = true)
    public Double getTotalSampahKertas(@Param("idbank") int idbank);

    @Query(value = "SELECT avg(rating) from transaksi where idbank = :idbank",
            nativeQuery = true)
    public Double getTotalRating(@Param("idbank") int idbank);

}
