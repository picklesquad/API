package com.pickle.Repository;

import com.pickle.Domain.LanggananEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by andrikurniawan.id@gmail.com on 3/28/2016.
 */
@Repository
public interface LanggananRepository extends CrudRepository<LanggananEntity, Integer>{

    @Query(value = "SELECT count(DISTINCT iduser) as jumlahLangganan from langganan where idbank = :idbank",
            nativeQuery = true)
    public int countUserSubscribe(@Param("idbank")int idbank);

    public List<LanggananEntity> findByIdbank(int idbank);
}
