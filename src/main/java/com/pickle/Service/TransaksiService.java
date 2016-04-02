package com.pickle.Service;

import com.pickle.Repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by andrikurniawan.id@gmail.com on 3/28/2016.
 */
@Service
public class TransaksiService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    public Double getTotalSampahPlastik(int idBank){
        Double hasil = transaksiRepository.getTotalSampahPlastik(idBank);
        return hasil;
    }

    public int getTotalSampahBotol(int idBank){
        int hasil = transaksiRepository.getTotalSampahBotol(idBank);
        return hasil;
    }

    public Double getTotalSampahBesi(int idBank){
        Double hasil = transaksiRepository.getTotalSampahBesi(idBank);
        return hasil;
    }

    public Double getTotalSampahKertas(int idBank){
        Double hasil = transaksiRepository.getTotalSampahKertas(idBank);
        return hasil;
    }

    public Double getTotalRating(int idBank){
        Double hasil = transaksiRepository.getTotalRating(idBank);
        return hasil;
    }
}
