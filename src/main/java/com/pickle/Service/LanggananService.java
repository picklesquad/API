package com.pickle.Service;

import com.pickle.Repository.LanggananRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by andrikurniawan.id@gmail.com on 3/28/2016.
 */
@Service
public class LanggananService {
    @Autowired
    private LanggananRepository langgananRepository;

    public int countUserSubscribe(int idBank){
        int hasil = langgananRepository.countUserSubscribe(idBank);
        return hasil;
    }
}
