import com.magus.net.OPConnect;
import com.magus.net.OPHisData;
import com.magus.net.OPNetConst;
import com.magus.net.OPNode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author xqh
 * @date 2022/8/22  16:21:25
 * @apiNote
 */
public class DataFromMagusByAll {
    private static Long cnt = 0l;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
//    System.out.println("无参，默认使用昨日");
            String s_start = "2022-09-08 08:36:46";
            String s_end = "2022-09-08 08:41:46";
            getDataFromDates(s_start, s_end);
        }
    }

    /**
     * @param s_start
     * @param s_end
     */
    private static void getDataFromDates(String s_start, String s_end) throws Exception {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime date_s = LocalDateTime.parse(s_start, df);
        LocalDateTime date_e = LocalDateTime.parse(s_end, df);

        while (date_s.isBefore(date_e)) {
            LocalDateTime next_date = date_s.plusDays(1);
            getDataFromMagus(date_s, next_date.minusSeconds(1));
            date_s = next_date;
        }

    }

    /**
     * @param _s
     * @param _e
     */
    private static void getDataFromMagus(LocalDateTime _s, LocalDateTime _e) throws Exception {
        OPConnect conn = new OPConnect("10.191.177.6", 8200, 60000, "cypc_ds", "Jxkj@123");

//        Date from = Date.from(_s.toInstant(ZoneOffset.of("+8")));
//        Date from = Date.from(_s.atZone(ZoneId.systemDefault()).toInstant());
//        Date to = Date.from(_e.atZone(ZoneId.systemDefault()).toInstant());

        Date from = new Date(1662693822000L);
        Date to = new Date(1662695768000L);
        System.out.println(from+" " +to);


        OPNode[] allNodesByDBName = conn.getAllNodesByDBName("W3.XJB");
        int size = allNodesByDBName.length;
        for (int i = 0; i < size; i++) {
            OPNode opNode = allNodesByDBName[i];
            String gn = (String) opNode.getVar(1);
//            System.out.println(gn);
            if (!gn.startsWith("W3.XJB.03")) {
                System.out.println("---");
                continue;
            }

            OPHisData[] hisData = conn.getPointHistory(gn, from, to, OPNetConst.HISTORY_DATA_SAMPLE, 1);
            //原始数据 HISTORY_DATA_SAMPLE
            //跨度数据 HISTORY_DATA_SPAN
            cnt += hisData.length;
//            if (i%5000==0){
                System.out.println(i+"/ "+size+"  :"+cnt);
//            }

        }
        System.out.println(cnt);

    }
}
