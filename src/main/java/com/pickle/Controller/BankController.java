package com.pickle.Controller;

import com.pickle.Domain.*;
import com.pickle.Service.*;
import com.pickle.Util.PickleUtil;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * API Controller - Bank App.
 * <p>Determines RESTful API used by Pickle Bank App.</p>
 *
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
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

    @Autowired
    private WithdrawService withdrawService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Wrapper login(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password) {
        BanksampahEntity bankResult = bankService.validation(phoneNumber, password);
        if (bankResult != null) {
            ModelMap model = new ModelMap();
            Double rating = transaksiService.getTotalRating(bankResult.getId());
            int nasabah = langgananService.countUserSubscribe(bankResult.getId());
            model.addAttribute("id", bankResult.getId());
            model.addAttribute("nama", bankResult.getNama());
            model.addAttribute("rating", rating);
            model.addAttribute("totalNasabah", nasabah);
            PickleUtil.countSampahBank(bankResult.getId(), model, transaksiService);
            return new Wrapper(200, "Login berhasil", model);
        } else {
            return new Wrapper(200, "Login gagal", null);
        }
    }

    @RequestMapping(path = "/nasabah/{id}", method = RequestMethod.GET)
    public Wrapper getUserById(@PathVariable("id") int id) {
        UserEntity user = userService.getUserById(id);
        ModelMap model = new ModelMap();
        model.addAttribute("id", user.getId());
        model.addAttribute("nama", user.getNama());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("phoneNumber", user.getPhoneNumber());
        model.addAttribute("alamat", user.getAlamat());
        model.addAttribute("saldo", user.getSaldo());
        PickleUtil.countSampahUser(id, model, transaksiService);
        return new Wrapper(200, "Success", model);
    }

    @RequestMapping(path = "/nasabah/getAll", method = RequestMethod.GET)
    public Wrapper getListAllNasabah(@RequestHeader(value = "idBank") int idbank) {
        List<LanggananEntity> langganan = langgananService.getLanggananByIdbank(idbank);
        if (langganan == null) {
            return new Wrapper(404, "Data tidak ditemukan", null);
        }
        List<ModelMap> result = new LinkedList<ModelMap>();

        for (LanggananEntity n : langganan) {
            ModelMap model = new ModelMap();

            UserEntity user = userService.getUserById(n.getIduser());
            model.addAttribute("id", user.getId());
            model.addAttribute("nama", user.getNama());
            model.addAttribute("photo", user.getPhoto());
            model.addAttribute("memberSince", n.getLanggananSejak());
            model.addAttribute("saldo", user.getSaldo());
            result.add(model);
        }

        return new Wrapper(200, "Success", result);
    }

    @RequestMapping(path = "/transaction", method = RequestMethod.GET)
    public Wrapper getListAllTransaction(@RequestHeader(value = "idBank") int idBank) {
        List<TransaksiEntity> transaction = transaksiService.getTransaksiByIdBank(idBank);
        if (transaction == null) {
            return new Wrapper(404, "Data tidak ditemukan", null);
        }
        List<ModelMap> result = new LinkedList<ModelMap>();
        for (TransaksiEntity t : transaction) {
            ModelMap model = new ModelMap();
            model.addAttribute("id", t.getId());
            UserEntity user = userService.getUserById(t.getIdUser());
            model.addAttribute("nama", user.getNama());
            model.addAttribute("harga", t.getHarga());
            model.addAttribute("waktu", t.getWaktu());
            result.add(model);
        }

        return new Wrapper(200, "Success", result);

    }

//    @RequestMapping(path = "/transaction/{id}", method = RequestMethod.GET)
//    public Wrapper getDetailTransaction(@PathVariable("id")int id){
//        TransaksiEntity transaction = transaksiService.getTransaksiById(id);
//        ModelMap model = new ModelMap();
//        model.addAttribute("");
//
//    }

    @RequestMapping(path = "/withdraw", method = RequestMethod.GET)
    public Wrapper getListAllWithdraw(@RequestHeader(value = "idBank") int idBank) {
        List<WithdrawEntity> withdrawals = withdrawService.getWithdrawByIdBank(idBank);
        if (withdrawals == null) {
            return new Wrapper(404, "Data tidak ditemukan", null);
        }
        List<ModelMap> result = new LinkedList<ModelMap>();
        for (WithdrawEntity w : withdrawals) {
            if (w.getStatus() == 2) {
                ModelMap model = new ModelMap();
                model.addAttribute("id", w.getId());
                UserEntity user = userService.getUserById(w.getIdUser());
                model.addAttribute("nama", user.getNama());
                model.addAttribute("waktu", w.getWaktu());
                model.addAttribute("harga", w.getNominal());
                result.add(model);
            }
        }

        return new Wrapper(200, "Success", result);
    }

    @RequestMapping(path = "/withdraw/{id}", method = RequestMethod.GET)
    public Wrapper getListDetailWithdraw(@PathVariable(value = "id") int id) {
        WithdrawEntity withdrawals = withdrawService.getWithdrawById(id);
        if (withdrawals == null) return new Wrapper(200, "Data Not Found", null);
        UserEntity user = userService.getUserById(withdrawals.getIdUser());
        if (user == null) return new Wrapper(200, "Data Not Found", null);
        ModelMap model = new ModelMap();
        model.addAttribute("nama", user.getNama());
        model.addAttribute("saldo", withdrawals.getNominal());
        model.addAttribute("waktu", withdrawals.getWaktu());
        model.addAttribute("status", withdrawals.getStatus());
        return new Wrapper(200, "Success", model);
    }

    @RequestMapping(path = "/withdraw/updateStatus/accept", method = RequestMethod.PUT)
    public Wrapper updateStatusWithdrawAccept(@RequestHeader(value = "id") int id) {
        WithdrawEntity data = withdrawService.getWithdrawById(id);

        // cannot update updated withdrawal
        if (data.getStatus() != 0) {
            return new Wrapper(400, "Permintaan sudah direspon", null);
        }
        WithdrawEntity withdraw = withdrawService.saveUpdateStatus(id, data, 1);
        return new Wrapper(200, "Success", withdraw);
    }

    @RequestMapping(path = "/withdraw/updateStatus/reject", method = RequestMethod.PUT)
    public Wrapper updateStatusWithdrawReject(@RequestHeader(value = "id") int id) {
        WithdrawEntity data = withdrawService.getWithdrawById(id);

        // cannot update updated withdrawal
        if (data.getStatus() != 0) {
            return new Wrapper(400, "Permintaan sudah direspon", null);
        }
        WithdrawEntity withdraw = withdrawService.saveUpdateStatus(id, data, -1);
        return new Wrapper(200, "Success", withdraw);
    }

    @RequestMapping(path = "/withdraw/updateStatus/complete", method = RequestMethod.PUT)
    public Wrapper updateStatusWithdrawComplete(@RequestHeader(value = "id") int id) {
        WithdrawEntity data = withdrawService.getWithdrawById(id);

        // cannot update updated withdrawal or rejected withdrawal
        if (data.getStatus() != 1) {
            return new Wrapper(400, "Permintaan sudah direspon", null);
        }
        WithdrawEntity withdraw = withdrawService.saveUpdateStatus(id, data, 2);

        // update user's balance
        UserEntity theUser = userService.getUserById(withdraw.getIdUser());
        theUser.setSaldo(theUser.getSaldo() - withdraw.getNominal());
        userService.save(theUser);
        return new Wrapper(200, "Success", withdraw);
    }

    @RequestMapping(path = "/transaction/addNew", method = RequestMethod.POST)
    public Wrapper newTransaction(@RequestParam("phoneNumber") String phoneNumber,
                                  @RequestParam("plastik") String plastik,
                                  @RequestParam("logam") String logam,
                                  @RequestParam("kertas") String kertas,
                                  @RequestParam("botol") String botol,
                                  @RequestParam("totalHarga") int totalHarga,
                                  @RequestHeader("idBank")int idBank) {

        TransaksiEntity transaksi = new TransaksiEntity();
        UserEntity user = userService.getUserByPhoneNumber(phoneNumber);
        if(user == null) return new Wrapper(200,"Nasabah tidak ditemukan", null);
        int iduser = user.getId();
        transaksi.setIdUser(iduser);
        transaksi.setIdBank(idBank);
        transaksi.setWaktu(PickleUtil.generateCurrentTime());
        transaksi.setHarga(totalHarga);
        transaksi.setRating(0);
        transaksi.setSampahPlastik(plastik);
        transaksi.setSampahBesi(logam);
        transaksi.setSampahBotol(botol);
        transaksi.setSampahKertas(kertas);

        TransaksiEntity hasil = transaksiService.save(transaksi);
        LanggananEntity langganan = langgananService.isSubscribedToThisBank(idBank, iduser);

        if(langganan == null) {
            return new Wrapper(200, "Nasabah belum berlangganan", null);
        }

        if(hasil == null) {
            return new Wrapper(200, "Gagal menyimpan data", null);
        }
        return new Wrapper(201, "Transaksi berhasil dibuat", transaksi);

    }

    @RequestMapping(path = "/notification", method = RequestMethod.GET)
    public Wrapper getListNotification(@RequestHeader(value = "idBank") int idBank) {

        List<WithdrawEntity> withdraw = withdrawService.getWithdrawByIdBank(idBank);
        if (withdraw == null) return new Wrapper(200, "Data Not Found", null);

        List<ModelMap> result = new LinkedList<ModelMap>();
        for (WithdrawEntity w : withdraw) {
            if (w.getStatus() != 2 || w.getStatus() != -1) {
                ModelMap model = new ModelMap();
                model.addAttribute("id", w.getId());
                UserEntity user = userService.getUserById(w.getIdUser());
                model.addAttribute("nama", user.getNama());
                model.addAttribute("waktu", w.getWaktu());
                model.addAttribute("harga", w.getNominal());
                model.addAttribute("status", w.getStatus());
                result.add(model);
            }
        }

        return new Wrapper(200, "Success", result);
    }
}