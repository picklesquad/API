package com.pickle.Controller;

import com.pickle.Domain.*;
import com.pickle.Service.BankService;
import com.pickle.Service.TransaksiService;
import com.pickle.Service.UserService;
import com.pickle.Service.WithdrawService;
import com.pickle.Util.TokenGenerator;
import com.pickle.Util.UserProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.text.SimpleDateFormat;
import java.util.Random;

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

    @RequestMapping(path = "/login/isComplete", method = RequestMethod.POST)
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
        // check whether the email given exists or not
        UserEntity userResult = userService.validation(email);
        if (userResult == null) {
            return new Wrapper(200, "Login failed.", null);
        }
        // end of checking

        ModelMap model = new ModelMap();
        model.addAttribute("id", userResult.getId());
        model.addAttribute("nama", userResult.getNama());
        model.addAttribute("photo", userResult.getPhoto());
        int userLevel = UserProfileUtil.generateLevel(userResult.getExp());
        model.addAttribute("level", UserProfileUtil.getLevelName(userLevel));
        model.addAttribute("star", UserProfileUtil.generateStars(userResult.getExp(), userLevel));
        model.addAttribute("exp", userResult.getExp());
        model.addAttribute("memberSince", userResult.getMemberSince());
        model.addAttribute("saldo", userResult.getSaldo());
        UserProfileUtil.countSampahUser(userResult.getId(), model, transaksiService);

        return new Wrapper(200, "Sukses", model);
    }

    @RequestMapping(path = "/login/addUser", method = RequestMethod.POST)
    public Wrapper addUser(@RequestParam("nama")String nama,
                           @RequestParam("email")String email,
                           @RequestParam("phoneNumber")String phoneNumber,
                           @RequestParam("dateOfBirth")String dob,
                           @RequestParam("gender")String gender,
                           @RequestParam("alamat")String alamat,
                           @RequestParam("fbToken")String fbToken) {

        // check whether the phone number has been registered or not
        UserEntity test = userService.getUserByPhoneNumber(phoneNumber);
        if (test != null) {
            return new Wrapper(400, "Phone number already registered.", null);
        }
        // check whether the email has been registered or not
        test = userService.getUserByEmail(email);
        if (test != null) {
            return new Wrapper(400, "E-mail already registered.", null);
        }
        // end of checking

        UserEntity newUser = new UserEntity();

        newUser.setNama(nama);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setPhoto("http://xxxxxx.com/image/default.jpg");
        newUser.setAlamat(alamat);
        newUser.setExp(0);
        newUser.setSaldo(0);
        newUser.setApiToken(TokenGenerator.generateApiToken(phoneNumber));
        newUser.setFbToken(fbToken);
        newUser.setIsComplete(1);
        newUser.setMemberSince(UserProfileUtil.generateCurrentTime());

        // insert to DB
        newUser = userService.save(newUser);

        ModelMap model = new ModelMap();
        model.addAttribute("id", newUser.getId());
        model.addAttribute("nama", newUser.getNama());
        model.addAttribute("photo", newUser.getPhoto());
        model.addAttribute("level", "newbie");
        model.addAttribute("star", 1);
        model.addAttribute("exp", 0);
        model.addAttribute("memberSince", newUser.getMemberSince());
        model.addAttribute("sampahPlastik", 0);
        model.addAttribute("sampahKertas", 0);
        model.addAttribute("sampahBotol", 0);
        model.addAttribute("sampahBesi", 0);
        model.addAttribute("saldo", newUser.getSaldo());

        return new Wrapper(200, "success", model);
    }

    @RequestMapping(path = "/withdrawals", method = RequestMethod.GET)
    public Wrapper getWithdrawals(@RequestHeader(value="token")String token,
                                  @RequestHeader(value="idUser")int idUser) {

        // check whether logged user is authorized or not
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);
        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found", null);
        }
        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access", null);
        }
        // end of checking

        UserEntity user = userById;

        List<WithdrawEntity> withdrawals = withdrawService.getWithdrawByIdUser(user.getId());
        List<ModelMap> models = new LinkedList<>();

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

    @RequestMapping(path = "/withdrawal/{id}", method = RequestMethod.GET)
    public Wrapper getWithdrawalDetail(@PathVariable("id") int id,
                                       @RequestHeader(value="token")String token,
                                       @RequestHeader(value="idUser")int idUser) {

        // check whether logged user is authorized or not
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);
        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found.", null);
        }
        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access.", null);
        }
        WithdrawEntity withdrawResult = withdrawService.getWithdrawById(id);
        if (withdrawResult == null) {
            return new Wrapper(404, "Data not found.", null);
        }
        if(withdrawResult.getIdUser() != userById.getId()) {
            return new Wrapper(403,"Forbidden access.", null);
        }
        // end of checking

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
    }

    @RequestMapping(path = "/transaction", method = RequestMethod.GET)
    public Wrapper getTransactions(@RequestHeader("token")String token,
                                   @RequestHeader("idUser")int idUser) {

        // check whether logged user is authorized or not
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);
        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found", null);
        }
        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access", null);
        }
        // end of checking

        UserEntity user = userById;

        List<TransaksiEntity> transactions = transaksiService.getTransaksiByIdUser(user.getId());
        List<ModelMap> models = new LinkedList<>();

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

            String[] tanggalWaktu = UserProfileUtil.generateTanggalWaktu(t.getWaktu());
            model.addAttribute("tanggal", tanggalWaktu[0]);
            model.addAttribute("waktu", tanggalWaktu[1]);
            models.add(model);
        }
        return new Wrapper(200, "success", models);
    }

    @RequestMapping(path = "/transaction/{id}", method = RequestMethod.GET)
    public Wrapper getTransactionDetail(@PathVariable("id") int id,
                                        @RequestHeader(value="token")String token,
                                        @RequestHeader(value="idUser")int idUser){

        // check whether logged user is authorized or not
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);
        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found", null);
        }
        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access", null);
        }
        TransaksiEntity transactionResult = transaksiService.getTransaksiById(id);
        if (transactionResult == null) {
            return new Wrapper(404,"Data not found", null);
        }
        if (transactionResult.getIdUser() != userById.getId()) {
            return new Wrapper(403,"Forbidden access", null);
        }
        // end of checkig

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

        String[] tanggalWaktu = UserProfileUtil.generateTanggalWaktu(transactionResult.getWaktu());
        model.addAttribute("tanggal", tanggalWaktu[0]);
        model.addAttribute("waktu", tanggalWaktu[1]);

        return new Wrapper(200,"success", model);
    }

    @RequestMapping(path = "/requestWithdraw", method = RequestMethod.POST)
    public Wrapper requestWithdraw(@RequestParam("token")String token,
                                   @RequestParam("idUser")int idUser,
                                   @RequestParam("idBank")int idBank,
                                   @RequestParam("jumlah")int jumlah,
                                   @RequestParam("tanggal")String tanggal,
                                   @RequestParam("waktu")String waktu) {

        // check whether logged user is authorized
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);
        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found", null);
        }
        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access", null);
        }

        // check whether the bank exists or not
        BanksampahEntity bankSampah = bankService.findById(idBank);
        if (bankSampah == null) {
            return new Wrapper(404,"Bank sampah not found", null);
        }
        // end of checking

        WithdrawEntity newWithdraw = new WithdrawEntity();

        newWithdraw.setIdUser(idUser);
        newWithdraw.setIdBank(idBank);
        newWithdraw.setNominal(jumlah);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date;
        try {
            date = formatter.parse(tanggal + " " + waktu);
        } catch (ParseException e) {
            return new Wrapper(403,"Date is not valid", null);
        }
        long millis = date.getTime();
        newWithdraw.setWaktu(millis);

        newWithdraw = withdrawService.save(newWithdraw);

        ModelMap model = new ModelMap();
        model.addAttribute("id", newWithdraw.getId());

        model.addAttribute("namaBank", bankSampah.getNama());
        model.addAttribute("jumlah", newWithdraw.getNominal());
        model.addAttribute("status", UserProfileUtil.generateStatus(newWithdraw.getStatus()));

        String[] tanggalWaktu = UserProfileUtil.generateTanggalWaktu(newWithdraw.getWaktu());
        model.addAttribute("tanggal", tanggalWaktu[0]);
        model.addAttribute("waktu", tanggalWaktu[1]);

        return new Wrapper(201, "New withdraw request has been added!", model);
    }
}