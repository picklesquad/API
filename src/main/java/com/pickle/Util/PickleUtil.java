package com.pickle.Util;

import com.pickle.Domain.Wrapper;
import com.pickle.Service.TransaksiService;
import org.springframework.ui.ModelMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Syukri Mullia Adil P on 4/25/2016.
 */
public class PickleUtil {

    private static final int[] LEVEL_THRESHOLD = {0, 100000, 250000, 500000, 2000000, 5000000, 10000000};
    private static final String[] LEVEL_NAMES = {"Newbie", "Novice", "Advanced", "Senior", "Master", "King", "Legend"};
    private static final long MILLIS_PER_DAY = 1000 * 60 * 60 * 24;

    /**
     * Converts date and time to time millis format
     * @param tanggal the date
     * @param waktu the time
     * @return the time millis format
     * @throws ParseException if the date or date given is not in supported format
     */
    public static long generateTimeMillis(String tanggal, String waktu) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = formatter.parse(tanggal + " " + waktu);
        return date.getTime();
    }

    /**
     * Converts time millis format to two strings: date and time
     * @param time the time millis
     * @return date and time saved in an array of strings
     */
    public static String[] generateTanggalWaktu(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return formatter.format(date).split(" ");
    }

    /**
     * Converts user exp to its corresponding level
     * @param exp the user exp
     * @return the corresponding level
     */
    public static int generateLevel(int exp) {
        for (int i = 1; i < LEVEL_NAMES.length - 1; i++) {
            if (exp < LEVEL_THRESHOLD[i]) {
                return i - 1;
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
     * Calculate number of days given two different time
     * @param then the older time
     * @param now the newer time
     * @return number of days separating those time
     */
    public static double countDays(long then, long now) {
        Date dateThen = new Date(then);
        Date dateNow = new Date(now);

        long diff = dateNow.getTime() - dateThen.getTime();
        return diff / MILLIS_PER_DAY;
    }
}
