package com.pickle.Service;

import com.pickle.Domain.WithdrawEntity;
import com.pickle.Repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Syukri Mullia Adil P on 4/17/2016.
 */
@Service
public class WithdrawService {
    @Autowired
    private WithdrawRepository withdrawRepository;

    public WithdrawEntity getWithdrawById(int id) {
        return withdrawRepository.findById(id);
    }

    public List<WithdrawEntity> getWithdrawByIdUser(int id) {
        return withdrawRepository.findByIdUser(id);
    }

    public List<WithdrawEntity> getWithdrawByIdBank(int idBank){
        return withdrawRepository.findByIdBank(idBank);
    }

    public WithdrawEntity save(WithdrawEntity newWithdraw) {
        return withdrawRepository.save(newWithdraw);
    }

    public WithdrawEntity saveUpdateStatus(int id, WithdrawEntity data, int type){
        if(data != null){
            if(type == 1) data.setStatus(1);
            else if(type == -1) data.setStatus(-1);
            else data.setStatus(2);
            withdrawRepository.save(data);
        }
        return data;
    }
}
