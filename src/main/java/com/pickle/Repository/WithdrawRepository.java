package com.pickle.Repository;

import com.pickle.Domain.WithdrawEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Syukri Mullia Adil P on 4/17/2016.
 */
@Repository
public interface WithdrawRepository extends CrudRepository<WithdrawEntity, Integer> {

    WithdrawEntity findById(int id);
    List<WithdrawEntity> findByIdUserAndIdBank(int idUser, int idBank);
    List<WithdrawEntity> findByIdUser(int idUser);
    List<WithdrawEntity> findByIdBank(int idBank);
}
