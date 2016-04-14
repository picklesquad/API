package com.pickle.Controller;

import com.pickle.Domain.UserEntity;
import com.pickle.Domain.Wrapper;
import com.pickle.Service.TransaksiService;
import com.pickle.Service.UserService;
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
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;
    
    @Autowired
    private TransaksiService transaksiService;

    @RequestMapping(path = "/complete", method = RequestMethod.POST)
    public boolean isComplete(@RequestParam("email") String email) {
        int isComplete = userService.getIsComplete(email);
        if (isComplete == 0) {
            return false;
        } else {
            return true;
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Wrapper login(@RequestParam("email")String email){
        UserEntity userResult = userService.validation(email);
        if(userResult != null){
            ModelMap model = new ModelMap();
            model.addAttribute("id", userResult.getId());
            model.addAttribute("nama", userResult.getNama());
            model.addAttribute("photo", userResult.getPhoto());
            model.addAttribute("exp", userResult.getExp());
            model.addAttribute("memberSince", userResult.getMemberSince());
            model.addAttribute("saldo", userResult.getSaldo());
            countSampah(userResult.getId(), model);
            return new Wrapper(200, "Sukses", model);
        }else{
            return new Wrapper(200, "Gagal", null);
        }
    }
    /**
     * Fungsi ini untuk menambahkan data tentang sampah dari user/ bank ke dalam model
     * @param id
     * @param model
     */
    public void countSampah(int id, ModelMap model){
        Double sampahPlastik = transaksiService.getTotalSampahPlastikUser(id);
        int sampahBotol = transaksiService.getTotalSampahBotolUser(id);
        Double sampahBesi = transaksiService.getTotalSampahBesiUser(id);
        Double sampahKertas = transaksiService.getTotalSampahKertasUser(id);
        model.addAttribute("sampahPlastik",sampahPlastik);
        model.addAttribute("sampahBotol",sampahBotol);
        model.addAttribute("sampahBesi",sampahBesi);
        model.addAttribute("sampahKertas",sampahKertas);
    }
}
