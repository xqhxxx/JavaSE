import com.magus.net.OPConnect;
import com.magus.net.OPHisData;
import com.magus.net.OPNetConst;
import org.apache.log4j.Logger;

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
public class DataFromMagusbyGzb24point {
    private static Logger log = Logger.getLogger(DataFromMagusbyGzb24point.class);

    public static void main(String[] args) throws Exception, IOException {

        if (args.length == 0) {
//    System.out.println("无参，默认使用昨日");
//            String s_start = "2022-01-01 00:00:00";
//            String s_end = "2022-08-30 00:00:00";
            String s_start = "2020-01-01 00:00:00";
            String s_end = "2020-12-31 00:00:00";
            getDataFromDates(s_start, s_end);
        }
    }

    /**
     * @param s_start
     * @param s_end
     */
    private static void getDataFromDates(String s_start, String s_end) throws Exception {
        OPConnect conn = new OPConnect("10.191.177.6", 8200, 60000, "cypc_ds", "Jxkj@123");
        String arr = "03011CFB40DL501------CAI1INV\n" +
                "03021CFB40DL501------CAI1INV\n" +
                "03031CFB40DL501------CAI1INV\n" +
                "03041CFB40DL501------CAI1INV\n" +
                "03052CFB40DL501------CAI1INV\n" +
                "03062CFB40DL501------CAI1INV\n" +
                "03072CFB40DL501------CAI1INV\n" +
                "03082CFB40DL501------CAI1INV\n" +
                "03031MKA05CY102------CAI1VPP\n" +
                "03031MKA05CY101------CAI1VPP\n" +
                "03031MKA06CY102------CAI1VPP\n" +
                "03031MKA06CY101------CAI1VPP\n" +
                "03031MEA05CY102------CAI1VPP\n" +
                "03031MEA05CY101------CAI1VPP\n" +
                "03052MEA10CP003-CFB10AAO1INV\n" +
                "03021MEA10CP001-MEA26AAI1INV\n" +
                "03011MEA10CP001-CFB10AAO1INV\n" +
                "03031MEA10CP001------CAI2INV\n" +
                "03021MEA10CP001-CFB10AAO1INV\n" +
                "03031MEA10CP001-CFB10AAO1INV\n" +
                "03082MEA10CP003-CFB10AAO1INV\n" +
                "03062MEA10CP003-CFB10AAO1INV\n" +
                "03072MEA10CP003-CFB10AAO1INV\n" +
                "03041MEA10CP001-CFB10AAO1INV";

        String[] ar = arr.split("\n");

        for (String s : ar) {
            String gn = "W3.XJB." + s;
            Boolean flag = true;
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime date_s = LocalDateTime.parse(s_start, df);
            LocalDateTime date_e = LocalDateTime.parse(s_end, df);

            while (!date_e.isBefore(date_s) && flag) {
                LocalDateTime next_date = date_e.minusDays(1);
                LocalDateTime _s = next_date;
                LocalDateTime _e = date_e.minusSeconds(1);

//        Date from = Date.from(_s.toInstant(ZoneOffset.of("+8")));
                Date from = Date.from(_s.atZone(ZoneId.systemDefault()).toInstant());
                Date to = Date.from(_e.atZone(ZoneId.systemDefault()).toInstant());

//                OPHisData[] hisData = conn.getPointHistory(gn,from, to, OPNetConst.HISTORY_DATA_SAMPLE, 1);
                try {
                    OPHisData[] hisData = conn.getPointHistory(gn, from, to, OPNetConst.HISTORY_DATA_SPAN, 3600);

                    //原始数据 HISTORY_DATA_SAMPLE
                    //跨度数据 HISTORY_DATA_SPAN

                    for (OPHisData data : hisData) {
//            System.out.println(data);
                        double av = data.getAV();
                        long time = data.getTime();
                        System.out.println(LocalDateTime.now()+" "+gn+" " +new Date(time)+" " +av);
                        if (av != 0.0) {
                            log.info(gn+" "+new Date(time)+" "+av);
                            flag = false;
                            break;
                        }
                    }

                } catch (Exception e) {
//                   log.error(e);
                    conn = new OPConnect("10.191.177.6", 8200, 60000, "cypc_ds", "Jxkj@123");
                }


                date_e = next_date;
//                Thread.sleep(200);
            }
        }
    }
}
