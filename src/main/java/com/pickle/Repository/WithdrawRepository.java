package com.pickle.Repository;

import com.pickle.Domain.WithdrawEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository interface for table "withdraw"
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Repository
public interface WithdrawRepository extends CrudRepository<WithdrawEntity, Integer> {

    WithdrawEntity findById(int id);
    List<WithdrawEntity> findByIdUserOrderByStatusAscWaktuDesc(int idUser);
    List<WithdrawEntity> findByIdBankOrderByStatusAscWaktuDesc(int idBank);
    List<WithdrawEntity> findByIdUserAndIdBank(int idUser, int idBank);
}
