package com.pickle.Controller;

import com.pickle.Domain.BanksampahEntity;
import com.pickle.Domain.TransaksiEntity;
import com.pickle.Domain.Wrapper;
import com.pickle.Service.BankService;
import com.pickle.Service.LanggananService;
import com.pickle.Service.TransaksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Wrapper login(@RequestParam("phoneNumber")String phoneNumber, @RequestParam("password")String password){
        BanksampahEntity bankResult = bankService.validation(phoneNumber,password);
        if (bankResult != null){
            ModelMap model = new ModelMap();
            Double rating = transaksiService.getTotalRating(bankResult.getId());
            Double sampahPlastik = transaksiService.getTotalSampahPlastik(bankResult.getId());
            int sampahBotol = transaksiService.getTotalSampahBotol(bankResult.getId());
            Double sampahBesi = transaksiService.getTotalSampahBesi(bankResult.getId());
            Double sampahKertas = transaksiService.getTotalSampahKertas(bankResult.getId());
            int nasabah = langgananService.countUserSubscribe( bankResult.getId());
            model.addAttribute("id",bankResult.getId());
            model.addAttribute("nama",bankResult.getNama());
            model.addAttribute("rating",rating);
            model.addAttribute("totalNasabah", nasabah);
            model.addAttribute("sampahPlastik",sampahPlastik);
            model.addAttribute("sampahBotol",sampahBotol);
            model.addAttribute("sampahBesi",sampahBesi);
            model.addAttribute("sampahKertas",sampahKertas);
            return new Wrapper(200,"Login", model);
        }else{
            return new Wrapper(200,"Gagal", null);
        }
    }
}