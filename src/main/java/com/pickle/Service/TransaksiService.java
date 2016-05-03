package com.pickle.Service;

import com.pickle.Domain.TransaksiEntity;
import com.pickle.Repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by andrikurniawan.id@gmail.com on 3/28/2016.
 */
@Service
public class TransaksiService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    public TransaksiEntity getTransaksiById(int id) {
        return transaksiRepository.findById(id);
    }

    public List<TransaksiEntity> getTransaksiByIdUser(int id) {
        return transaksiRepository.findByIdUser(id);
    }

    public List<TransaksiEntity> getTransaksiByIdBank(int idBank) {
        return transaksiRepository.findByIdBank(idBank);
    }

    public Double getTotalSampahPlastikBank(int idBank){
        Double hasil = transaksiRepository.getTotalSampahPlastikBank(idBank);
        if (hasil == null) hasil = 0.0;
        return hasil;
    }

    public int getTotalSampahBotolBank(int idBank){
        Integer hasil = transaksiRepository.getTotalSampahBotolBank(idBank);
        if (hasil == null) hasil = 0;
        return hasil;
    }

    public Double getTotalSampahBesiBank(int idBank){
        Double hasil = transaksiRepository.getTotalSampahBesiBank(idBank);
        if (hasil == null) hasil = 0.0;
        return hasil;
    }

    public Double getTotalSampahKertasBank(int idBank){
        Double hasil = transaksiRepository.getTotalSampahKertasBank(idBank);
        if (hasil == null) hasil = 0.0;
        return hasil;
    }

    public Double getTotalSampahPlastikUser(int idUser){
        Double hasil = transaksiRepository.getTotalSampahPlastikUser(idUser);
        if (hasil == null) hasil = 0.0;
        return hasil;
    }

    public Integer getTotalSampahBotolUser(int idUser){
        Integer hasil = transaksiRepository.getTotalSampahBotolUser(idUser);
        if (hasil == null) hasil = 0;
        return hasil;
    }

    public Double getTotalSampahBesiUser(int idUser){
        Double hasil = transaksiRepository.getTotalSampahBesiUser(idUser);
        if (hasil == null) hasil = 0.0;
        return hasil;
    }

    public Double getTotalSampahKertasUser(int idUser){
        Double hasil = transaksiRepository.getTotalSampahKertasUser(idUser);
        if (hasil == null) hasil = 0.0;
        return hasil;
    }

    public Double getTotalRating(int idBank){
        Double hasil = transaksiRepository.getTotalRating(idBank);
        if (hasil == null) hasil = 0.0;
        return hasil;
    }

    public Integer getSaldoByIdBank(int idBank, int idUser){
        Integer saldo = transaksiRepository.getSaldoByIdBank(idBank, idUser);
        if(saldo == null) saldo = 0;
        return saldo;
    }

    public List<Object[]> getBalancePerBank(int idUser) {
        return transaksiRepository.getBalancePerBank(idUser);
    }

    public TransaksiEntity saveUpdateStatus(TransaksiEntity transaction, int newStatus) {
        if (transaction != null) {
            transaction.setStatus(newStatus);
            transaksiRepository.save(transaction);
        }
        return transaction;
    }

    public TransaksiEntity save(TransaksiEntity transaksi){
        return transaksiRepository.save(transaksi);
    }
}
