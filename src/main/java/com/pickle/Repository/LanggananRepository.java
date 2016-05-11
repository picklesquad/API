package com.pickle.Repository;

import com.pickle.Domain.LanggananEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository interface for table "langganan"
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Repository
public interface LanggananRepository extends CrudRepository<LanggananEntity, Integer>{

    List<LanggananEntity> findByIdbank(int idbank);
    LanggananEntity findByIdbankAndIduser(int idbank, int iduser);

    @Query(value = "SELECT count(DISTINCT iduser) as jumlahLangganan from langganan where idbank = :idbank",
            nativeQuery = true)
    int countUserSubscribe(@Param("idbank")int idbank);
}
