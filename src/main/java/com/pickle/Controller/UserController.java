package com.pickle.Controller;

import com.pickle.Domain.*;
import com.pickle.Service.*;
import com.pickle.Util.PickleUtil;
import com.pickle.Util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.List;
import java.util.LinkedList;

/**
 * Created by andrikurniawan.id@gmail.com on 3/17/2016.
 */
@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private BankService bankService;

    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private LanggananService langgananService;

    /**
     * Check whether the user has completed his/her data or not
     * @param email the user's email
     * @return true if the user has completed his/her data, otherwise false
     */
    @RequestMapping(path = "/login/isComplete", method = RequestMethod.POST)
    public Wrapper isComplete(@RequestParam("email") String email) {
        // check whether the email given exists or not
        UserEntity userResult = userService.validation(email);

        if (userResult == null) {
            return new Wrapper(404, "Email not found", null);
        }
        // end of checking

        int isComplete = userService.getIsComplete(email);

        if (isComplete == 0) {
            return new Wrapper(200, "Success", false);
        } else {
            return new Wrapper(200, "Success", true);
        }
    }

    /**
     * Login for registered user
     * @param email the user's email
     * @return response body containing his/her profile
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Wrapper login(@RequestParam("email")String email){
        // check whether the email given exists or not
        UserEntity userResult = userService.validation(email);

        if (userResult == null) {
            return new Wrapper(404, "Login failed, email not found", null);
        }
        // end of checking

        ModelMap model = new ModelMap();
        model.addAttribute("id", userResult.getId());
        model.addAttribute("nama", userResult.getNama());
        model.addAttribute("photo", userResult.getPhoto());

        int userLevel = PickleUtil.generateLevel(userResult.getExp());
        model.addAttribute("level", PickleUtil.getLevelName(userLevel));
        model.addAttribute("stars", PickleUtil.generateStars(userResult.getExp(), userLevel));

        model.addAttribute("exp", userResult.getExp());
        model.addAttribute("memberSince", userResult.getMemberSince());
        model.addAttribute("saldo", userResult.getSaldo());
        PickleUtil.countSampahUser(userResult.getId(), model, transaksiService);

        return new Wrapper(200, "Success", model);
    }

    /**
     * Login for new user who just registered
     * @param nama the user's name
     * @param email the user's email
     * @param phoneNumber the user's phone number
     * @param dob the user's date of birth
     * @param gender the user's gender
     * @param alamat the user's address
     * @param fbToken the user's facebook token
     * @return response body containing his/her profile
     */
    @RequestMapping(path = "/login/addUser", method = RequestMethod.POST)
    public Wrapper addUser(@RequestParam("nama")String nama,
                           @RequestParam("email")String email,
                           @RequestParam("phoneNumber")String phoneNumber,
                           @RequestParam("dateOfBirth")String dob,
                           @RequestParam("facebookPhoto")String facebookPhoto,
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

        if (facebookPhoto != null && facebookPhoto.length() > 0) {
            newUser.setPhoto(facebookPhoto);
        } else {
            newUser.setPhoto("http://imgbox.com/YXCC1M4W");
        }
        newUser.setAlamat(alamat);
        newUser.setExp(0);
        newUser.setSaldo(0);
        newUser.setApiToken(TokenGenerator.generateApiToken(phoneNumber));
        newUser.setFbToken(fbToken);
        newUser.setIsComplete(1);
        newUser.setMemberSince(PickleUtil.generateCurrentTime());

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

        return new Wrapper(201, "Success", model);
    }

    /**
     * Get user's balance in a specified bank
     * @param idBank the bank id
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @return response body containing the data only if the user is authorized and the bank exists
     */
    @RequestMapping(path = "/balance/{id}", method = RequestMethod.GET)
    public Wrapper getBalancePerBank(@PathVariable("id") int idBank,
                                     @RequestHeader(value="token")String token,
                                     @RequestHeader(value="idUser")int idUser) {

        // check whether logged in user is authorized
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

        // check whether this user has subscribed to this bank or not
        LanggananEntity langgananEntity = langgananService.isSubscribedToThisBank(idBank, idUser);
        if (langgananEntity == null) {
            return new Wrapper(403,"You're not subscribed to this bank", null);
        }
        // end of checking

        Integer transactionsBalance = transaksiService.getSaldoByIdBank(idBank, idUser);
        List<WithdrawEntity> withdrawalsInThisBank = withdrawService.getWithdrawByIdUserAndIdBank(idUser, idBank);
        int withdrawalsBalance = 0;

        for (WithdrawEntity w : withdrawalsInThisBank) {
            if (w.getStatus() == 2 ) {
                withdrawalsBalance += w.getNominal();
            }
        }
        ModelMap model = new ModelMap();
        model.addAttribute("namaBank", bankSampah.getNama());
        model.addAttribute("balance", transactionsBalance - withdrawalsBalance);

        return new Wrapper(200, "Success", model);
    }

    /**
     * Get user's balance in all bank he/she subscribes to
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @return response body containing the data only if the user is authorized
     */
    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Wrapper getBalanceAnyBank(@RequestHeader(value="token")String token,
                                     @RequestHeader(value="idUser")int idUser) {

        // check whether logged in user is authorized
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);

        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found", null);
        }

        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access", null);
        }
        // end of checking

        List<Object[]> result = transaksiService.getBalancePerBank(idUser);
        List<ModelMap> models = new LinkedList<>();

        for (Object[] records : result) {
            ModelMap model = new ModelMap();

            BanksampahEntity thisBank = bankService.findById((int) records[0]);
            BigDecimal transcationsBalance = (BigDecimal) records[1];

            List<WithdrawEntity> withdrawalsInThisBank = withdrawService.getWithdrawByIdUserAndIdBank(idUser, thisBank.getId());
            int withdrawalsBalance = 0;

            for (WithdrawEntity w : withdrawalsInThisBank) {
                withdrawalsBalance += w.getNominal();
            }
            model.addAttribute("idBank", thisBank.getId());
            model.addAttribute("namaBank", thisBank.getNama());
            model.addAttribute("balance", transcationsBalance.subtract(new BigDecimal(withdrawalsBalance)));

            models.add(model);
        }
        return new Wrapper(200, "Success", models);
    }

    /**
     * Get user's withdrawals history in all banks he/she subscribed to
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @return response body containing the data only if the user is authorized
     */
    @RequestMapping(path = "/withdrawals", method = RequestMethod.GET)
    public Wrapper getWithdrawals(@RequestHeader(value="token")String token,
                                  @RequestHeader(value="idUser")int idUser) {

        // check whether logged in user is authorized or not
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);

        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found", null);
        }

        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access", null);
        }
        // end of checking

        List<WithdrawEntity> withdrawals = withdrawService.getWithdrawByIdUser(idUser);
        List<ModelMap> models = new LinkedList<>();

        for (WithdrawEntity w : withdrawals) {
            ModelMap model = new ModelMap();
            model.addAttribute("idWithdraw", w.getId());

            BanksampahEntity bankResult = bankService.findById(w.getIdBank());
            model.addAttribute("namaBank", bankResult.getNama());

            model.addAttribute("nominal", w.getNominal());
            model.addAttribute("status", w.getStatus());
            model.addAttribute("waktu", w.getWaktu());
            models.add(model);
        }
        return new Wrapper(200, "Success", models);
    }

    /**
     * Get user's withdrawal history with a specified id
     * @param id the withdrawal id
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @return response body containing the data only if the user is authorized and the withdrawal exists
     */
    @RequestMapping(path = "/withdrawal/{id}", method = RequestMethod.GET)
    public Wrapper getWithdrawalDetail(@PathVariable("id") int id,
                                       @RequestHeader(value="token")String token,
                                       @RequestHeader(value="idUser")int idUser) {

        // check whether logged in user is authorized or not
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);

        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found.", null);
        }

        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access.", null);
        }

        // check whether desired withdraw id exists or not
        WithdrawEntity withdrawResult = withdrawService.getWithdrawById(id);
        if (withdrawResult == null) {
            return new Wrapper(404, "Withdrawal not found.", null);
        }

        // verify this user is truly the one who did this withdrawal
        if(withdrawResult.getIdUser() != userById.getId()) {
            return new Wrapper(403,"Forbidden access.", null);
        }

        // check whether this user has subscribed to this bank or not
        LanggananEntity langgananEntity = langgananService.isSubscribedToThisBank(withdrawResult.getIdBank(), idUser);

        if (langgananEntity == null) {
            return new Wrapper(403,"You're not subscribed to this bank", null);
        }
        // end of checking

        ModelMap model = new ModelMap();
        model.addAttribute("idWithdraw", withdrawResult.getId());

        BanksampahEntity bankResult = bankService.findById(withdrawResult.getIdBank());
        model.addAttribute("namaBank", bankResult.getNama());

        model.addAttribute("jumlah", withdrawResult.getNominal());
        model.addAttribute("status", withdrawResult.getStatus());
        model.addAttribute("waktu", withdrawResult.getWaktu());
        return new Wrapper(200, "Success", model);
    }

    /**
     * Get user's transactions history in all banks he/she subscribed to
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @return response body containing the data only if the user is authorized
     */
    @RequestMapping(path = "/transaction", method = RequestMethod.GET)
    public Wrapper getTransactions(@RequestHeader("token")String token,
                                   @RequestHeader("idUser")int idUser) {

        // check whether logged in user is authorized or not
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);
        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found", null);
        }

        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access", null);
        }
        // end of checking

        List<TransaksiEntity> transactions = transaksiService.getTransaksiByIdUser(idUser);
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
            model.addAttribute("waktu", t.getWaktu());
            models.add(model);
        }
        return new Wrapper(200, "Success", models);
    }

    /**
     * Get user's transaction history with a specified id
     * @param id the transaction id
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @return response body containing the data only if the user is authorized and the transaction exists
     */
    @RequestMapping(path = "/transaction/{id}", method = RequestMethod.GET)
    public Wrapper getTransactionDetail(@PathVariable("id") int id,
                                        @RequestHeader(value="token")String token,
                                        @RequestHeader(value="idUser")int idUser){

        // check whether logged in user is authorized or not
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);

        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found", null);
        }

        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access", null);
        }

        // check whether the transaction exists or not
        TransaksiEntity transactionResult = transaksiService.getTransaksiById(id);

        if (transactionResult == null) {
            return new Wrapper(404,"Transaction not found", null);
        }

        // verify this user is truly the one who did this transaction
        if (transactionResult.getIdUser() != userById.getId()) {
            return new Wrapper(403,"Forbidden access", null);
        }

        // check whether this user has subscribed to this bank or not
        LanggananEntity langgananEntity = langgananService.isSubscribedToThisBank(transactionResult.getIdBank(), idUser);

        if (langgananEntity == null) {
            return new Wrapper(403,"You're not subscribed to this bank", null);
        }
        // end of checking

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
        model.addAttribute("waktu", transactionResult.getWaktu());

        return new Wrapper(200,"Success", model);
    }

    /**
     * Requesting new withdrawal in a specified bank, assuming 90 days has been passed
     * since the user's first transaction
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @param idBank the bank id
     * @param jumlah the withdrawal amount
     * @param waktu the withdrawal time, in java time millis
     * @return response body containing the withdrawal data only if the user is authorized and the bank exists
     */
    @RequestMapping(path = "/requestWithdraw", method = RequestMethod.POST)
    public Wrapper requestWithdraw(@RequestHeader("token")String token,
                                   @RequestHeader("idUser")int idUser,
                                   @RequestParam("idBank")int idBank,
                                   @RequestParam("jumlah")int jumlah,
                                   @RequestParam("waktu")long waktu) {

        // check whether logged in user is authorized
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

        // check whether this user has subscribed to this bank or not
        LanggananEntity langgananEntity = langgananService.isSubscribedToThisBank(idBank, idUser);

        if (langgananEntity == null) {
            return new Wrapper(403,"You're not subscribed to this bank", null);
        }

        // check whether this user has sufficient balance or not
        Integer transactionsBalance = transaksiService.getSaldoByIdBank(idBank, idUser);
        int withdrawalsBalance = 0;
        List<WithdrawEntity> withdrawalsInThisBank = withdrawService.getWithdrawByIdUserAndIdBank(idUser, idBank);

        for (WithdrawEntity w : withdrawalsInThisBank) {
            if (w.getStatus() == 2 ) {
                withdrawalsBalance += w.getNominal();
            }
        }
        int balance = transactionsBalance - withdrawalsBalance;

        if (balance < jumlah) {
            return new Wrapper(403,"Insufficient balance", null);
        }
        // end of checking

        WithdrawEntity newWithdraw = new WithdrawEntity();

        newWithdraw.setIdUser(idUser);
        newWithdraw.setIdBank(idBank);
        newWithdraw.setNominal(jumlah);
        newWithdraw.setWaktu(waktu);

        // insert into DB
        newWithdraw = withdrawService.save(newWithdraw);

        ModelMap model = new ModelMap();
        model.addAttribute("id", newWithdraw.getId());
        model.addAttribute("namaBank", bankSampah.getNama());
        model.addAttribute("jumlah", newWithdraw.getNominal());
        model.addAttribute("status", PickleUtil.generateStatus(newWithdraw.getStatus()));
        model.addAttribute("waktu", newWithdraw.getWaktu());

        return new Wrapper(201, "New withdraw request has been added", model);
    }

    /**
     * Condition must be satisfied so that user can request new withdrawal: 90 days has been passed
     * since his/her first transaction. This method returns the number of day and
     * a boolean true if more than 90 days has been passed, false otherwise.
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @param idBank the bank's id
     * @return response body containing the number of days only if the user is authorized and the bank exists
     */
    @RequestMapping(path = "/requestWithdraw/check", method = RequestMethod.POST)
    public Wrapper getDaysSubscribed(@RequestHeader("token") String token,
                                   @RequestHeader("idUser") int idUser,
                                   @RequestParam("idBank")int idBank) {

        // check whether logged in user is authorized
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

        // check whether this user has subscribed to this bank or not
        LanggananEntity langgananEntity = langgananService.isSubscribedToThisBank(idBank, idUser);

        if (langgananEntity == null) {
            return new Wrapper(403,"You're not subscribed to this bank", null);
        }
        // end of checking

        long firstTransactionTime = langgananEntity.getTransaksiPertama();
        long currentTime = System.currentTimeMillis();
        double numberOfDays = PickleUtil.countDays(firstTransactionTime, currentTime);

        ModelMap model = new ModelMap();

        // firstTransactionTime = -1 means no transaction has been made, therefore can't withdraw
        if (firstTransactionTime == -1) {
            model.addAttribute("jumlahHari", -1);
            model.addAttribute("boolean:", false);
            return new Wrapper(200, "Success", model);
        }
        model.addAttribute("jumlahHari", numberOfDays);

        if (numberOfDays >= 90) {
            model.addAttribute("boolean:", true);
            return new Wrapper(200, "Success", model);
        } else {
            model.addAttribute("boolean:", false);
            return new Wrapper(200, "Success", model);
        }
    }

    /**
     * Get a specified bank's profile
     * @param idBank the bank's id
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @return response body containing the data only if the user is authorized and the bank exists
     */
    @RequestMapping(path = "/bank/getDetail/{id}", method = RequestMethod.GET)
    public Wrapper getBankDetails(@PathVariable("id") int idBank,
                                 @RequestHeader("token") String token,
                                 @RequestHeader("idUser") int idUser) {

        // check whether logged in user is authorized
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

        ModelMap model = new ModelMap();

        model.addAttribute("namaBank", bankSampah.getNama());
        model.addAttribute("lokasi", bankSampah.getLocationName());
        model.addAttribute("lokasiDesc", bankSampah.getLocationDesc());
        model.addAttribute("lat", bankSampah.getLocationLat());
        model.addAttribute("lng", bankSampah.getLocationLng());
        model.addAttribute("deskripsi", bankSampah.getDescription());
        model.addAttribute("narahubung", bankSampah.getNarahubung());
        model.addAttribute("phoneNumber", bankSampah.getPhoneNumber());

        int numOfSubscriber = langgananService.countUserSubscribe(bankSampah.getId());
        model.addAttribute("jumlahNasabah", numOfSubscriber);

        // check whether this user has subscribed to this bank or not
        LanggananEntity langgananEntity = langgananService.isSubscribedToThisBank(idBank, idUser);

        if (langgananEntity == null) {
            model.addAttribute("isSubsribed", false);
        } else {
            model.addAttribute("isSubsribed", true);
        }

        return new Wrapper(200, "Success", model);
    }

    /**
     * Subscribe to a specified bank
     * @param idBank the bank's id
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @return response body containing the subscription data only if the user is authorized and the bank exists
     */
    @RequestMapping(path = "/bank/subscribe ", method = RequestMethod.POST)
    public Wrapper subscribe(@RequestParam("idBank") int idBank,
                             @RequestHeader("token") String token,
                             @RequestHeader("idUser") int idUser) {

        // check whether logged in user is authorized
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

        // check whether this user has subscribed to this bank or not
        LanggananEntity langgananEntity = langgananService.isSubscribedToThisBank(idBank, idUser);

        if (langgananEntity != null) {
            return new Wrapper(400,"You're already subscribed to this bank", null);
        }
        // end of checking

        LanggananEntity newLangganan = new LanggananEntity();
        newLangganan.setIdbank(idBank);
        newLangganan.setIduser(idUser);
        newLangganan.setLanggananSejak(System.currentTimeMillis());
        newLangganan.setTransaksiPertama(-1);

        // insert into DB
        newLangganan = langgananService.save(newLangganan);

        ModelMap model = new ModelMap();
        model.addAttribute("idBank", newLangganan.getIdbank());
        model.addAttribute("idUser", newLangganan.getIduser());
        model.addAttribute("langgananSejak", newLangganan.getLanggananSejak());
        model.addAttribute("transaksiPertama", newLangganan.getTransaksiPertama());

        return new Wrapper(201, "You're now subscribed to this bank", model);
    }

    /**
     * Search banks by location or bank name
     * @param query the query, may containing location or bank name, encoded in UTF-8 encoding scheme
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @return response body containing search results only if the user is authorized
     */
    @RequestMapping(path = "/search", method = RequestMethod.POST)
    public Wrapper searchByLocation(@RequestParam("query") String query,
                                    @RequestHeader("token") String token,
                                    @RequestHeader("idUser") int idUser) {

        // check whether logged in user is authorized
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);

        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found", null);
        }

        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access", null);
        }

        try {
            query = URLDecoder.decode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new Wrapper(400, e.getMessage(), null);
        }
        List<BanksampahEntity> bankResults = bankService.searchByLocation(query);
        List<ModelMap> models = new LinkedList<>();

        for (BanksampahEntity b : bankResults) {
            ModelMap model = new ModelMap();
            model.addAttribute("idBank", b.getId());
            model.addAttribute("namaBank", b.getNama());
            model.addAttribute("locationName", b.getLocationName());
            model.addAttribute("locationDesc", b.getLocationDesc());
            model.addAttribute("locationLat", b.getLocationLat());
            model.addAttribute("locationLng", b.getLocationLng());
            models.add(model);
        }
        return new Wrapper(200, "success", models);
    }

    /**
     * Update transaction status, possible actions: accept or reject
     * @param idTransaksi the transaction's id
     * @param status the status to be applied. 1 for accept, -1 for reject
     * @param token the user's token, for authentication
     * @param idUser the user's id, for authentication
     * @return response body containing the transaction after being updated only if the user is authorized
     */
    @RequestMapping(path = "/updateTransaction", method = RequestMethod.PUT)
    public Wrapper updateTransaction(@RequestHeader("idTransaksi") int idTransaksi,
                                     @RequestHeader("status") int status,
                                     @RequestHeader("token") String token,
                                     @RequestHeader("idUser") int idUser) {

        // check whether logged in user is authorized
        UserEntity userByToken = userService.getUserByApiToken(token);
        UserEntity userById = userService.getUserById(idUser);

        if (userByToken == null || userById == null) {
            return new Wrapper(404,"User not found", null);
        }

        if (!userByToken.equals(userById)) {
            return new Wrapper(403,"Forbidden access", null);
        }

        // check whether the transaction exists or not
        TransaksiEntity transaction = transaksiService.getTransaksiById(idTransaksi);

        if (transaction == null) {
            return new Wrapper(404,"Transaction not found", null);
        }

        // check whether the transaction was made in a bank he/she already subscribed to or not
        LanggananEntity subscription = langgananService.isSubscribedToThisBank(transaction.getIdBank(), idUser);

        if (subscription == null) {
            return new Wrapper(403,"You're not subscribed to this bank", null);
        }

        // verify this user is truly the one who did this transaction
        if (transaction.getIdUser() != userById.getId()) {
            return new Wrapper(403,"Forbidden access", null);
        }
        // end of checking

        // update the status
        transaction = transaksiService.saveUpdateStatus(transaction, status);

        // check whether this is the user's first transaction or not
        if (subscription.getTransaksiPertama() == -1) {
            subscription.setTransaksiPertama(PickleUtil.generateCurrentTime());
            langgananService.save(subscription);
        }
        return new Wrapper(200, "Success", transaction);
    }
}