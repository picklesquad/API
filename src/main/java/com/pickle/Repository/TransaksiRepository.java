package com.pickle.Repository;

import com.pickle.Domain.TransaksiEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository interface for table "transaksi"
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Repository
public interface TransaksiRepository extends CrudRepository<TransaksiEntity, Integer> {

    TransaksiEntity findById(int id);
    List<TransaksiEntity> findByIdUser(int idUser);
    List<TransaksiEntity> findByIdBank(int idBank);
    List<TransaksiEntity> findByIdBankAndIdUser(int idBank, int idUser);

    @Query(value = "SELECT sum(sampahplastik) from transaksi where idbank = :idbank and status = 1",
            nativeQuery = true)
    Double getTotalSampahPlastikBank(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(sampahbotol) from transaksi where idbank = :idbank and status = 1",
            nativeQuery = true)
    Integer getTotalSampahBotolBank(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(sampahbesi) from transaksi where idbank = :idbank and status = 1",
            nativeQuery = true)
    Double getTotalSampahBesiBank(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(sampahkertas) from transaksi where idbank = :idbank and status = 1",
            nativeQuery = true)
    Double getTotalSampahKertasBank(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(sampahplastik) from transaksi where iduser = :iduser and status = 1",
            nativeQuery = true)
    Double getTotalSampahPlastikUser(@Param("iduser") int iduser);

    @Query(value = "SELECT sum(sampahbotol) from transaksi where iduser = :iduser and status = 1",
            nativeQuery = true)
    Integer getTotalSampahBotolUser(@Param("iduser") int iduser);

    @Query(value = "SELECT sum(sampahbesi) from transaksi where iduser = :iduser and status = 1",
            nativeQuery = true)
    Double getTotalSampahBesiUser(@Param("iduser") int iduser);

    @Query(value = "SELECT sum(sampahkertas) from transaksi where iduser = :iduser and status = 1",
            nativeQuery = true)
    Double getTotalSampahKertasUser(@Param("iduser") int iduser);

    @Query(value = "SELECT avg(rating) from transaksi where idbank = :idbank and status = 1",
            nativeQuery = true)
    Double getTotalRating(@Param("idbank") int idbank);

    @Query(value = "SELECT sum(harga) from transaksi where idbank = :idbank and iduser = :iduser and status = 1",
            nativeQuery = true)
    Integer getSaldoByIdBank(@Param("idbank") int idbank, @Param("iduser") int iduser);

    @Query(value = "SELECT idbank, SUM(harga) FROM transaksi WHERE iduser = :iduser and status = 1 GROUP BY idbank",
            nativeQuery = true)
    List<Object[]> getBalancePerBank(@Param("iduser") int iduser);
}
