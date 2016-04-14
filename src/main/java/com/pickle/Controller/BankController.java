package com.pickle.Controller;

import com.pickle.Domain.BanksampahEntity;
import com.pickle.Domain.TransaksiEntity;
import com.pickle.Domain.UserEntity;
import com.pickle.Domain.Wrapper;
import com.pickle.Service.BankService;
import com.pickle.Service.LanggananService;
import com.pickle.Service.TransaksiService;
import com.pickle.Service.UserService;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by andrikurniawan.id@gmail.com on 3/17/2016.
 */
@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private LanggananService langgananService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Wrapper login(@RequestParam("phoneNumber")String phoneNumber, @RequestParam("password")String password){
        BanksampahEntity bankResult = bankService.validation(phoneNumber,password);
        if (bankResult != null){
            ModelMap model = new ModelMap();
            Double rating = transaksiService.getTotalRating(bankResult.getId());
            int nasabah = langgananService.countUserSubscribe( bankResult.getId());
            model.addAttribute("id",bankResult.getId());
            model.addAttribute("nama",bankResult.getNama());
            model.addAttribute("rating",rating);
            model.addAttribute("totalNasabah", nasabah);
            sampahBank(bankResult.getId(), model);
            return new Wrapper(200,"Login", model);
        }else{
            return new Wrapper(200,"Gagal", null);
        }
    }

    @RequestMapping(path = "/nasabah/{id}", method = RequestMethod.GET)
    public Wrapper getUserById(@PathVariable("id")int id){
        UserEntity user = userService.getUserById(id);
        ModelMap model = new ModelMap();
        model.addAttribute("id", user.getId());
        model.addAttribute("nama", user.getNama());
        model.addAttribute("phoneNumber", user.getPhoneNumber());
        model.addAttribute("alamat", user.getAlamat());
        model.addAttribute("saldo", user.getSaldo());
        sampahUser(id,model);
        return new Wrapper(200,"Success",model);
    }

    /**
     * Fungsi ini untuk menambahkan data tentang sampah dari user/ bank ke dalam model
     * @param idBank id bank nya
     * @param model
     */
    public void sampahBank(int idBank, ModelMap model){
        Double sampahPlastik = transaksiService.getTotalSampahPlastikBank(idBank);
        int sampahBotol = transaksiService.getTotalSampahBotolBank(idBank);
        Double sampahBesi = transaksiService.getTotalSampahBesiBank(idBank);
        Double sampahKertas = transaksiService.getTotalSampahKertasBank(idBank);
        model.addAttribute("sampahPlastik",sampahPlastik);
        model.addAttribute("sampahBotol",sampahBotol);
        model.addAttribute("sampahBesi",sampahBesi);
        model.addAttribute("sampahKertas",sampahKertas);
    }

    /**
     * Fungsi ini untuk menambahkan data tentang sampah dari user/ bank ke dalam model
     * @param idUser id user nya
     * @param model
     */
    public void sampahUser(int idUser, ModelMap model){
        Double sampahPlastik = transaksiService.getTotalSampahPlastikUser(idUser);
        int sampahBotol = transaksiService.getTotalSampahBotolUser(idUser);
        Double sampahBesi = transaksiService.getTotalSampahBesiUser(idUser);
        Double sampahKertas = transaksiService.getTotalSampahKertasUser(idUser);
        model.addAttribute("sampahPlastik",sampahPlastik);
        model.addAttribute("sampahBotol",sampahBotol);
        model.addAttribute("sampahBesi",sampahBesi);
        model.addAttribute("sampahKertas",sampahKertas);
    }

}