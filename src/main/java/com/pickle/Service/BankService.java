package com.pickle.Service;

import com.pickle.Domain.BanksampahEntity;
import com.pickle.Repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by andrikurniawan.id@gmail.com on 3/17/2016.
 */
@Service
public class BankService {
    @Autowired
    private BankRepository bankRepository;

    public BanksampahEntity validation(String phoneNumber, String password){
        BanksampahEntity bank = bankRepository.findByPhoneNumberAndPassword(phoneNumber, password);
        return bank;
    }
}
