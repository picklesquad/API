package com.pickle.Controller;

import com.pickle.Domain.*;
import com.pickle.Service.BankService;
import com.pickle.Service.TransaksiService;
import com.pickle.Service.UserService;
import com.pickle.Service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.text.SimpleDateFormat;

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

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private BankService bankService;

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

    @RequestMapping(path = "/history/withdrawals", method = RequestMethod.POST)
    public Wrapper getTransactions(@RequestParam("token")String token) {
        UserEntity user = userService.getUserByApiToken(token);

        if (user == null) {
            return new Wrapper(403,"Forbidden access", null);
        }

        List<WithdrawEntity> withdrawals = withdrawService.getWithdrawByIdUser(user.getId());
        List<ModelMap> models = new LinkedList<ModelMap>();

        for (WithdrawEntity w : withdrawals) {
            ModelMap model = new ModelMap();

            BanksampahEntity bankResult = bankService.findById(w.getIdBank());
            model.addAttribute("namaBank", bankResult.getNama());

            model.addAttribute("nominal", w.getNominal());
            model.addAttribute("status", w.getStatus());

            long timestamp = w.getWaktu();
            Date date = new Date(timestamp);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String[] tanggalWaktu = formatter.format(date).split(" ");
            model.addAttribute("tanggal", tanggalWaktu[0]);
            model.addAttribute("waktu", tanggalWaktu[1]);
            models.add(model);
        }
        return new Wrapper(200, "success", models);
    }

    @RequestMapping(path = "/history/withdrawal/{id}", method = RequestMethod.POST)
    public Wrapper getWithdrawalDetail(@PathVariable("id") int id) {
        WithdrawEntity withdrawResult = withdrawService.getWithdrawById(id);

        if (withdrawResult != null) {
            ModelMap model = new ModelMap();
            model.addAttribute("idWithdraw", withdrawResult.getId());

            BanksampahEntity bankResult = bankService.findById(withdrawResult.getIdBank());
            model.addAttribute("namaBank", bankResult.getNama());

            model.addAttribute("jumlah", withdrawResult.getNominal());
            model.addAttribute("status", withdrawResult.getStatus());

            long timestamp = withdrawResult.getWaktu();
            Date date = new Date(timestamp);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String[] tanggalWaktu = formatter.format(date).split(" ");
            model.addAttribute("tanggal", tanggalWaktu[0]);
            model.addAttribute("waktu", tanggalWaktu[1]);

            return new Wrapper(200, "success", model);
        } else {
            return new Wrapper(200, "success", null);
        }
    }

    @RequestMapping(path = "/history/transactions", method = RequestMethod.POST)
    public Wrapper getWithdrawals(@RequestParam("token")String token) {
        UserEntity user = userService.getUserByApiToken(token);

        if (user == null) {
            return new Wrapper(403,"Forbidden access", null);
        }

        List<TransaksiEntity> transactions = transaksiService.getTransaksiByIdUser(user.getId());
        List<ModelMap> models = new LinkedList<ModelMap>();

        for (TransaksiEntity t : transactions) {
            ModelMap model = new ModelMap();
            model.addAttribute("idTransaksi", t.getId());

            BanksampahEntity bankResult = bankService.findById(t.getIdBank());
            model.addAttribute("namaBank", bankResult.getNama());

            model.addAttribute("harga", t.getHarga());

            Double totalSampah = Double.parseDouble(t.getSampahBesi());
            totalSampah += Double.parseDouble(t.getSampahBotol());
            totalSampah += Double.parseDouble(t.getSampahKertas());
            totalSampah += Double.parseDouble(t.getSampahPlastik());
            model.addAttribute("totalSampah", totalSampah + " kg");

            long timestamp = t.getWaktu();
            Date date = new Date(timestamp);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String[] tanggalWaktu = formatter.format(date).split(" ");
            model.addAttribute("tanggal", tanggalWaktu[0]);
            model.addAttribute("waktu", tanggalWaktu[1]);
            models.add(model);
        }
        return new Wrapper(200, "success", models);
    }

    @RequestMapping(path = "/history/transaction/{id}", method = RequestMethod.POST)
    public Wrapper getTransactionDetail(@PathVariable("id") int id){
        TransaksiEntity transactionResult = transaksiService.getTransaksiById(id);
        if (transactionResult != null){
            ModelMap model = new ModelMap();
            model.addAttribute("idTransaksi", transactionResult.getId());

            BanksampahEntity bankResult = bankService.findById(transactionResult.getIdBank());
            model.addAttribute("namaBank", bankResult.getNama());

            model.addAttribute("harga", transactionResult.getHarga());

            Double totalSampah = Double.parseDouble(transactionResult.getSampahBesi());
            totalSampah += Double.parseDouble(transactionResult.getSampahBotol());
            totalSampah += Double.parseDouble(transactionResult.getSampahKertas());
            totalSampah += Double.parseDouble(transactionResult.getSampahPlastik());
            model.addAttribute("totalSampah", totalSampah + " kg");

            long timestamp = transactionResult.getWaktu();
            Date date = new Date(timestamp);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String[] tanggalWaktu = formatter.format(date).split(" ");
            model.addAttribute("tanggal", tanggalWaktu[0]);
            model.addAttribute("waktu", tanggalWaktu[1]);
            return new Wrapper(200,"success", model);
        }else{
            return new Wrapper(200,"success", null);
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
