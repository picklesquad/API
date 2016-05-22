package com.pickle.Repository;

import com.pickle.Domain.BanksampahEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository interface for table "banksampah"
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Repository
public interface BankRepository extends CrudRepository<BanksampahEntity, Integer> {

    BanksampahEntity findByPhoneNumberAndPassword(String phoneNumber, String password);
    BanksampahEntity findById(int id);
    BanksampahEntity findByIdAndApiToken(int id, String apiToken);
    List<BanksampahEntity> findByLocationNameContainingIgnoreCaseOrNamaContainingIgnoreCase(String locationName, String nama);
}
