package com.pickle.Util;

import com.pickle.Domain.Wrapper;
import com.pickle.Service.TransaksiService;
import org.springframework.ui.ModelMap;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Common utilities used across all classes
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
public class PickleUtil {

    private static final int[] LEVEL_THRESHOLD = {0, 100000, 250000, 500000, 2000000, 5000000, 10000000};
    private static final String[] LEVEL_NAMES = {"Newbie", "Novice", "Advanced", "Senior", "Master", "King", "Legend"};
    private static final long MILLIS_PER_DAY = 60 * 60 * 24 * 1000;
    private static final String stringPool = "abcdefghijklmnopqrstuvwxyz1234567890";
    private static final Random r = new Random();

    /**
     * generate token with format %phoneNumber%
     *
     * @param phoneNumber the user's phoneNumber
     * @return token generated
     */
    public static String generateApiToken(String phoneNumber){
        String token = "";
        int mid = r.nextInt(10);
        for (int i = 0; i < 10; i++) {
            token += stringPool.charAt(r.nextInt(stringPool.length()));
            if (i == mid) token += phoneNumber;
        }
        return token;
    }

    /**
     * Converts user exp to its corresponding level
     * @param exp the user exp
     * @return the corresponding level
     */
    public static int generateLevel(int exp) {
        for (int i = 0; i < LEVEL_NAMES.length - 1; i++) {
            if (exp <= LEVEL_THRESHOLD[i + 1]) {
                return i;
            }
        }
        return LEVEL_NAMES.length;
    }

    /**
     * Converts user level to its corresponding level name
     * @param userLevel the user level
     * @return the corresponding level name
     */
    public static String getLevelName(int userLevel) {
        return LEVEL_NAMES[userLevel];
    }

    /**
     * Calculate stars the user got with his exp within a level
     * @param exp the exp within the level
     * @param level the user level
     * @return number of stars
     */
    public static int generateStars(int exp, int level) {
        int thisLevelThreshold = LEVEL_THRESHOLD[level];
        int nextLevelThreshold = level == 7 ? -1 : LEVEL_THRESHOLD[level + 1];

        if (nextLevelThreshold == -1) {
            return 5;
        } else {
            double expPerStar = (nextLevelThreshold - thisLevelThreshold) / 5;
            System.out.println(exp + " " + expPerStar + " " + thisLevelThreshold);
            return (int) Math.ceil((exp - thisLevelThreshold) / expPerStar);
        }
    }

    /**
     * Converts withdrawal status to its corresponding status name
     * @param status the status
     * @return the status name
     */
    public static String generateStatus(int status) {
        switch (status) {
            case 2: return "accepted";
            case 3: return "rejected";
            case 4: return "completed";
            default: return "waiting";
        }
    }

    /**
     * Get current time
     * @return the current time in time millis format
     */
    public static long generateCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * Calculate user's trashes
     * @param idUser the user id
     * @param model the ModelMap to save the result
     * @param transaksiService the service
     */
    public static void countSampahUser(int idUser, ModelMap model, TransaksiService transaksiService){
        Double sampahPlastik = transaksiService.getTotalSampahPlastikUser(idUser);
        int sampahBotol = transaksiService.getTotalSampahBotolUser(idUser);
        Double sampahBesi = transaksiService.getTotalSampahBesiUser(idUser);
        Double sampahKertas = transaksiService.getTotalSampahKertasUser(idUser);
        model.addAttribute("sampahPlastik",sampahPlastik);
        model.addAttribute("sampahBotol",sampahBotol);
        model.addAttribute("sampahBesi",sampahBesi);
        model.addAttribute("sampahKertas",sampahKertas);
    }

    /**
     * Calculate bank's trashes
     * @param idBank the bank id
     * @param model the ModelMap to save the result
     * @param transaksiService the service
     */
    public static void countSampahBank(int idBank, ModelMap model, TransaksiService transaksiService){
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
     * Calculate days between two different time
     * @param then the older time
     * @param now the newer time
     * @return number of days separating those time
     */
    public static long countDays(long then, long now) {
        Date dateThen = new Date(then);
        Date dateNow = new Date(now);

        long diff = dateNow.getTime() - dateThen.getTime();
        return diff / MILLIS_PER_DAY;
    }

    public static String formatRupiah(int harga){
        DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("");
        dfs.setMonetaryDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        String hsl = "Rp. " + df.format(harga);
        return hsl;
    }

    public static String getExpFraction(int exp) {
        int i = 0;
        while (i < LEVEL_THRESHOLD.length && LEVEL_THRESHOLD[i] < exp) {
            i++;
        }
        return exp + "/" + LEVEL_THRESHOLD[i];
    }
}
