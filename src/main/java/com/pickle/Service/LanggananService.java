package com.pickle.Service;

import com.pickle.Domain.LanggananEntity;
import com.pickle.Repository.LanggananRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Services provided by LanggananRepository
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Service
public class LanggananService {
    @Autowired
    private LanggananRepository langgananRepository;

    public int countUserSubscribe(int idBank){
        int hasil = langgananRepository.countUserSubscribe(idBank);
        return hasil;
    }

    public List<LanggananEntity> getLanggananByIdbank(int idBank){
        return langgananRepository.findByIdbank(idBank);
    }

    public List<LanggananEntity> getLanggananByIdUser(int idUser) {
        return langgananRepository.findByIduser(idUser);
    }
    public LanggananEntity isSubscribedToThisBank(int idBank, int idUser) {
        return langgananRepository.findByIdbankAndIduser(idBank, idUser);
    }

    public LanggananEntity save(LanggananEntity newLangganan) {
        return langgananRepository.save(newLangganan);
    }
}
