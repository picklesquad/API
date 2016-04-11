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

    public Double getTotalSampahPlastikBank(int idBank){
        Double hasil = transaksiRepository.getTotalSampahPlastikBank(idBank);
        return hasil;
    }

    public int getTotalSampahBotolBank(int idBank){
        int hasil = transaksiRepository.getTotalSampahBotolBank(idBank);
        return hasil;
    }

    public Double getTotalSampahBesiBank(int idBank){
        Double hasil = transaksiRepository.getTotalSampahBesiBank(idBank);
        return hasil;
    }

    public Double getTotalSampahKertasBank(int idBank){
        Double hasil = transaksiRepository.getTotalSampahKertasBank(idBank);
        return hasil;
    }

    public Double getTotalSampahPlastikUser(int idUser){
        Double hasil = transaksiRepository.getTotalSampahPlastikUser(idUser);
        return hasil;
    }

    public int getTotalSampahBotolUser(int idUser){
        int hasil = transaksiRepository.getTotalSampahBotolUser(idUser);
        return hasil;
    }

    public Double getTotalSampahBesiUser(int idUser){
        Double hasil = transaksiRepository.getTotalSampahBesiUser(idUser);
        return hasil;
    }

    public Double getTotalSampahKertasUser(int idUser){
        Double hasil = transaksiRepository.getTotalSampahKertasUser(idUser);
        return hasil;
    }

    public Double getTotalRating(int idBank){
        Double hasil = transaksiRepository.getTotalRating(idBank);
        return hasil;
    }
}
