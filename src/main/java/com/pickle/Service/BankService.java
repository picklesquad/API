package com.pickle.Service;

import com.pickle.Domain.BanksampahEntity;
import com.pickle.Repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Services provided by BankRepository
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Service
public class BankService {
    @Autowired
    private BankRepository bankRepository;

    public BanksampahEntity validation(String phoneNumber, String password){
        BanksampahEntity bank = bankRepository.findByPhoneNumberAndPassword(phoneNumber, password);
        return bank;
    }

    public BanksampahEntity findById(int id) {
        return bankRepository.findById(id);
    }

    public BanksampahEntity findByIdAndToken(int id, String token) {
        return bankRepository.findByIdAndApiToken(id,token);
    }

    public List<BanksampahEntity> searchByLocation(String query) {
        return bankRepository.findByLocationNameContainingIgnoreCaseOrNamaContainingIgnoreCase(query, query);
    }

    public BanksampahEntity save(BanksampahEntity banksampahEntity){
        return bankRepository.save(banksampahEntity);
    }
}
