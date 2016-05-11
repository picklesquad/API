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

    /**
     * Login for registered bank
     *
     * <p>Authentication phase :</p>
     * <ol>
     *     <li>Check whether the phone number given exists or not</li>
     * </ol>
     *
     * @param phoneNumber the bank's phone number
     * @param password the bank's password for authentation
     * @return response body containing bank profile
     */
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
            return new Wrapper(200, "Login", model);
        } else {
            return new Wrapper(200, "Gagal", null);
        }
    }

    /**
     * Get detail a nasabah based their id
     *
     * @param id the nasabah id that their data want to get
     * @return response body containing the data of a nasabah
     */
    @RequestMapping(path = "/nasabah/{id}", method = RequestMethod.GET)
    public Wrapper getUserById(@PathVariable("id") int id) {
        UserEntity user = userService.getUserById(id);
        ModelMap model = new ModelMap();
        model.addAttribute("id", user.getId());
        model.addAttribute("nama", user.getNama());
        model.addAttribute("phoneNumber", user.getPhoneNumber());
        model.addAttribute("alamat", user.getAlamat());
        model.addAttribute("saldo", user.getSaldo());
        PickleUtil.countSampahUser(id, model, transaksiService);
        return new Wrapper(200, "Success", model);
    }

    /**
     * Get list of nasabah that subscribed to a bank
     *
     * @param idbank id from bank which login, as header param
     * @return response body containing data of all nasabah as a list
     */
    @RequestMapping(path = "/nasabah/getAll", method = RequestMethod.GET)
    public Wrapper getListAllNasabah(@RequestHeader(value = "idBank") int idbank) {
        List<LanggananEntity> langganan = langgananService.getLanggananByIdbank(idbank);
        if (langganan == null) {
            return new Wrapper(404, "Data Not Found", null);
        }
        List<ModelMap> result = new LinkedList<ModelMap>();

        for (LanggananEntity n : langganan) {
            ModelMap model = new ModelMap();

            UserEntity user = userService.getUserById(n.getIduser());
            model.addAttribute("id", user.getId());
            model.addAttribute("nama", user.getNama());
            model.addAttribute("photo", user.getPhoto());
            int saldo = transaksiService.getUserBalanceByIdBank(idbank, user.getId());
            model.addAttribute("saldo", saldo);
            result.add(model);
        }

        return new Wrapper(200, "Success", result);
    }

    /**
     * Get list of all transaction from a bank
     *
     * @param idBank id from bank which login, as header param
     * @return response body containing data of all transaction as a list
     */
    @RequestMapping(path = "/transaction", method = RequestMethod.GET)
    public Wrapper getListAllTransaction(@RequestHeader(value = "idBank") int idBank) {
        List<TransaksiEntity> transaction = transaksiService.getTransaksiByIdBank(idBank);
        if (transaction == null) {
            return new Wrapper(404, "Data Not Found", null);
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


    /**
     * Create a new transaction
     *
     * @param phoneNumber the nasabah's phone number
     * @param plastik total trash of plastik that nasabah given
     * @param logam total trash of logam that nasabah given
     * @param kertas total trash of kertas that nasabah given
     * @param botol total trash of botol that nasabah given
     * @param totalHarga total value that nasabah got from this transaction
     * @param idBank id from bank which login, as header param
     * @return response body with message that inform that transcation success or not
     */
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
        transaksi.setHarga(0);
        transaksi.setRating(null);
        transaksi.setSampahPlastik(plastik);
        transaksi.setSampahBesi(logam);
        transaksi.setSampahBotol(botol);
        transaksi.setSampahKertas(kertas);

        TransaksiEntity hasil = transaksiService.save(transaksi);
        LanggananEntity langganan = langgananService.isSubscribedToThisBank(idBank, iduser);

        if(langganan == null) return new Wrapper(200, "Nasabah belum berlangganan", null);
        if(hasil == null) return new Wrapper(200, "gagal menyimpan data", null);
        else {
            if (langganan.getTransaksiPertama() == -1) {
                langganan.setTransaksiPertama(PickleUtil.generateCurrentTime());
                langgananService.save(langganan);
            }
        }

        return new Wrapper(201, "Transaksi berhasil dibuat", null);
    }


    /**
     * Get list of all completed withdrawal process
     *
     * @param idBank id from bank which login, as header param
     * @return response body containing data of all completed withdrawal process as a list
     */
    @RequestMapping(path = "/withdraw", method = RequestMethod.GET)
    public Wrapper getListAllWithdraw(@RequestHeader(value = "idBank") int idBank) {
        List<WithdrawEntity> withdrawals = withdrawService.getWithdrawByIdBank(idBank);
        if (withdrawals == null) {
            return new Wrapper(404, "Data Not Found", null);
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


    /**
     * get detail data of withdrawal with spesific id of transaction
     *
     * @param id the id's transaction
     * @return response body containing data of a withdrawal
     */
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


    /**
     * Update status a not response withdrawal to be accept
     *
     * @param id the id's transaction
     * @return response body containing data of this withdrawal process
     */
    @RequestMapping(path = "/withdraw/updateStatus/accept", method = RequestMethod.PUT)
    public Wrapper updateStatusWithdrawAccept(@RequestHeader(value = "id") int id) {
        WithdrawEntity data = withdrawService.getWithdrawById(id);
        WithdrawEntity withdraw = withdrawService.saveUpdateStatus(id, data, 1);
        return new Wrapper(200, "Success", withdraw);
    }


    /**
     * Update status a not response withdrawal to be reject
     *
     * @param id the id's transaction
     * @return response body containing data of this withdrawal process
     */
    @RequestMapping(path = "/withdraw/updateStatus/reject", method = RequestMethod.PUT)
    public Wrapper updateStatusWithdrawReject(@RequestHeader(value = "id") int id) {
        WithdrawEntity data = withdrawService.getWithdrawById(id);
        WithdrawEntity withdraw = withdrawService.saveUpdateStatus(id, data, -1);
        return new Wrapper(200, "Success", withdraw);
    }

    /**
     * Update status a not complete withdrawal to be complete
     *
     * @param id the id's transaction
     * @return response body containing data of this withdrawal process
     */
    @RequestMapping(path = "/withdraw/updateStatus/complete", method = RequestMethod.PUT)
    public Wrapper updateStatusWithdrawComplete(@RequestHeader(value = "id") int id) {
        WithdrawEntity data = withdrawService.getWithdrawById(id);
        WithdrawEntity withdraw = withdrawService.saveUpdateStatus(id, data, 2);
        return new Wrapper(200, "Success", withdraw);
    }


    /**
     * Get list of all request withdrawal from nasabah that not response
     *
     * @param idBank id from bank which login, as header param
     * @return response body containing data of all request withdrawal as a list
     */
    @RequestMapping(path = "/notification", method = RequestMethod.GET)
    public Wrapper getListNotification(@RequestHeader(value = "idBank") int idBank) {

        List<WithdrawEntity> withdraw = withdrawService.getWithdrawByIdBank(idBank);
        if (withdraw == null) return new Wrapper(200, "Data Not Found", null);

        List<ModelMap> result = new LinkedList<ModelMap>();
        for (WithdrawEntity w : withdraw) {
            if (w.getStatus() != 2) {
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
}