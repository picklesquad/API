package com.pickle.Repository;

import com.pickle.Domain.WithdrawEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by Syukri Mullia Adil P on 4/17/2016.
 */
@Repository
public interface WithdrawRepository extends CrudRepository<WithdrawEntity, Integer> {

    public WithdrawEntity findById(int id);
    public List<WithdrawEntity> findByIdUser(int idUser);
    public List<WithdrawEntity> findByIdBank(int idBank);
}
