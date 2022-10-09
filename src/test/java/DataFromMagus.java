import com.magus.net.OPConnect;
import com.magus.net.OPHisData;
import com.magus.net.OPNetConst;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author xqh
 * @date 2022/8/22  16:21:25
 * @apiNote
 */
public class DataFromMagus {
    public static void main(String[] args) throws Exception, IOException {


        if (args.length == 0) {
//    System.out.println("无参，默认使用昨日");
            String s_start = "2022-08-19 00:00:00";
            String s_end = "2022-08-21 00:00:00";
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

        OPConnect conn = new OPConnect("10.191.177.6", 8200, 6000, "cypc_ds", "Jxkj@123Jxkj@123");

//        Date from = Date.from(_s.toInstant(ZoneOffset.of("+8")));
        Date from = Date.from(_s.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(_e.atZone(ZoneId.systemDefault()).toInstant());

        OPHisData[] hisData = conn.getPointHistory("W3.GZB.01YY2CJA03ES261------BDIC1O",
                from, to, OPNetConst.HISTORY_DATA_SAMPLE, 1);
        //原始数据 HISTORY_DATA_SAMPLE
        //跨度数据 HISTORY_DATA_SPAN

        for (OPHisData data : hisData) {
//            System.out.println(data);
            double av = data.getAV();
            long time = data.getTime();
            System.out.println(av);
            System.out.println(time);

            break;
        }


    }
}
