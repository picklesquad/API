package com.pickle.Repository;

import com.pickle.Domain.BanksampahEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by andrikurniawan.id@gmail.com on 3/17/2016.
 */
@Repository
public interface BankRepository extends CrudRepository<BanksampahEntity, Integer> {
    public BanksampahEntity findByPhoneNumberAndPassword(String phoneNumber, String password);
    public BanksampahEntity findById(int id);
}
