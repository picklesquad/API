package com.pickle.Util;

import com.pickle.Service.TransaksiService;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Syukri Mullia Adil P on 4/25/2016.
 */
public class UserProfileUtil {

    private static final int[] LEVEL_THRESHOLD = {0, 100000, 250000, 500000, 2000000, 5000000, 10000000};
    private static final String[] LEVEL_NAMES = {"Newbie", "Novice", "Advanced", "Senior", "Master", "King", "Legend"};

    // generate date and time
    public static String[] generateTanggalWaktu(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return formatter.format(date).split(" ");
    }

    // generate user level given their exp
    public static int generateLevel(int exp) {
        for (int i = 1; i < LEVEL_NAMES.length - 1; i++) {
            if (exp < LEVEL_THRESHOLD[i]) {
                return i - 1;
            }
        }
        return LEVEL_NAMES.length;
    }

    // get level name given the level number
    public static String getLevelName(int userLevel) {
        return LEVEL_NAMES[userLevel];
    }

    // generate star given their exp end level
    public static int generateStars(int exp, int level) {
        int thisLevelThreshold = LEVEL_THRESHOLD[level];
        int nextLevelThreshold = level == 7 ? -1 : LEVEL_THRESHOLD[level + 1];

        if (nextLevelThreshold == -1) return 5;
        else {
            double expPerStar = (nextLevelThreshold - thisLevelThreshold) / 5;
            System.out.println(exp + " " + expPerStar + " " + thisLevelThreshold);
            return (int) Math.ceil((exp - thisLevelThreshold) / expPerStar);
        }
    }

    // generate withdrawal status given its status number
    public static String generateStatus(int status) {
        switch (status) {
            case 2: return "accepted";
            case 3: return "rejected";
            case 4: return "completed";
            default: return "waiting";
        }
    }

    // get current time
    public static long generateCurrentTime() {
        return System.currentTimeMillis();
    }

    // count total sampah
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
}
