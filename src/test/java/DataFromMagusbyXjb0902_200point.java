import com.magus.net.OPConnect;
import com.magus.net.OPHisData;
import com.magus.net.OPNetConst;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

/**
 * @author xqh
 * @date 2022/8/22  16:21:25
 * @apiNote
 */
public class DataFromMagusbyXjb0902_200point {
    private static Logger log = Logger.getLogger(DataFromMagusbyXjb0902_200point.class);

    public static void main(String[] args) throws Exception {
        String arr = "03YY2ACA13CE701A------CAIC1ALM\n" +
                "03YY2ACB02CE703A------CAIC1ALM\n" +
                "03YY2ACC01CE701A------CAIC1ALM\n" +
                "03YY2ACB01CE703A------CAIC1ALM\n" +
                "03YY2ACA21CE701A------CAIC1ALM\n" +
                "03YY2ACB01CE702A------CAIC1ALM\n" +
                "03YY2ACA23CE701A------CAIC1ALM\n" +
                "03YY2ACB02CE702A------CAIC1ALM\n" +
                "03YY2ACC02CE701A------CAIC1ALM\n" +
                "03YY2ACB02CE701A------CAIC1ALM\n" +
                "03YY2ACA31CE701A------CAIC1ALM\n" +
                "03YY2ACB01CE701A------CAIC1ALM\n" +
                "03YY2ACA13CE701A------CAIC1F\n" +
                "03YY2ACB02CE703A------CAIC1F\n" +
                "03YY2ACC01CE701A------CAIC1F\n" +
                "03YY2ACB01CE703A------CAIC1F\n" +
                "03YY2ACA21CE701A------CAIC1F\n" +
                "03YY2ACB01CE702A------CAIC1F\n" +
                "03YY2ACA23CE701A------CAIC1F\n" +
                "03YY2ACB02CE702A------CAIC1F\n" +
                "03YY2ACC02CE701A------CAIC1F\n" +
                "03YY2ACB02CE701A------CAIC1F\n" +
                "03YY2ACA31CE701A------CAIC1F\n" +
                "03YY2ACB01CE701A------CAIC1F\n" +
                "03YY2ACA13CE701A------CAIC1AVG\n" +
                "03YY2ACB02CE703A------CAIC1AVG\n" +
                "03YY2ACC01CE701A------CAIC1AVG\n" +
                "03YY2ACB01CE703A------CAIC1AVG\n" +
                "03YY2ACA21CE701A------CAIC1AVG\n" +
                "03YY2ACB01CE702A------CAIC1AVG\n" +
                "03YY2ACA23CE701A------CAIC1AVG\n" +
                "03YY2ACB02CE702A------CAIC1AVG\n" +
                "03YY2ACC02CE701A------CAIC1AVG\n" +
                "03YY2ACB02CE701A------CAIC1AVG\n" +
                "03YY2ACA31CE701A------CAIC1AVG\n" +
                "03YY2ACB01CE701A------CAIC1AVG\n" +
                "03YY2ACA13CE701A------CAIC1MAX\n" +
                "03YY2ACB02CE703A------CAIC1MAX\n" +
                "03YY2ACC01CE701A------CAIC1MAX\n" +
                "03YY2ACB01CE703A------CAIC1MAX\n" +
                "03YY2ACA21CE701A------CAIC1MAX\n" +
                "03YY2ACB01CE702A------CAIC1MAX\n" +
                "03YY2ACA23CE701A------CAIC1MAX\n" +
                "03YY2ACB02CE702A------CAIC1MAX\n" +
                "03YY2ACC02CE701A------CAIC1MAX\n" +
                "03YY2ACB02CE701A------CAIC1MAX\n" +
                "03YY2ACA31CE701A------CAIC1MAX\n" +
                "03YY2ACB01CE701A------CAIC1MAX\n" +
                "03YY2ACA13CE701A------CAIC2ALM\n" +
                "03YY2ACB02CE703A------CAIC2ALM\n" +
                "03YY2ACC01CE701A------CAIC2ALM\n" +
                "03YY2ACB01CE703A------CAIC2ALM\n" +
                "03YY2ACA21CE701A------CAIC2ALM\n" +
                "03YY2ACB01CE702A------CAIC2ALM\n" +
                "03YY2ACA23CE701A------CAIC2ALM\n" +
                "03YY2ACB02CE702A------CAIC2ALM\n" +
                "03YY2ACC02CE701A------CAIC2ALM\n" +
                "03YY2ACB02CE701A------CAIC2ALM\n" +
                "03YY2ACA31CE701A------CAIC2ALM\n" +
                "03YY2ACB01CE701A------CAIC2ALM\n" +
                "03YY2ACA13CE701A------CDIC1COMN\n" +
                "03YY2ACB02CE703A------CDIC1COMN\n" +
                "03YY2ACC01CE701A------CDIC1COMN\n" +
                "03YY2ACB01CE703A------CDIC1COMN\n" +
                "03YY2ACA21CE701A------CDIC1COMN\n" +
                "03YY2ACB01CE702A------CDIC1COMN\n" +
                "03YY2ACA23CE701A------CDIC1COMN\n" +
                "03YY2ACB02CE702A------CDIC1COMN\n" +
                "03YY2ACC02CE701A------CDIC1COMN\n" +
                "03YY2ACB02CE701A------CDIC1COMN\n" +
                "03YY2ACA31CE701A------CDIC1COMN\n" +
                "03YY2ACB01CE701A------CDIC1COMN\n" +
                "03YY2ACA13CE701A------CDIC1ALM\n" +
                "03YY2ACB02CE703A------CDIC1ALM\n" +
                "03YY2ACC01CE701A------CDIC1ALM\n" +
                "03YY2ACB01CE703A------CDIC1ALM\n" +
                "03YY2ACA21CE701A------CDIC1ALM\n" +
                "03YY2ACB01CE702A------CDIC1ALM\n" +
                "03YY2ACA23CE701A------CDIC1ALM\n" +
                "03YY2ACB02CE702A------CDIC1ALM\n" +
                "03YY2ACC02CE701A------CDIC1ALM\n" +
                "03YY2ACB02CE701A------CDIC1ALM\n" +
                "03YY2ACA31CE701A------CDIC1ALM\n" +
                "03YY2ACB01CE701A------CDIC1ALM\n" +
                "03YY2ACA13CE701B------CAIC1ALM\n" +
                "03YY2ACB02CE703B------CAIC1ALM\n" +
                "03YY2ACC01CE701B------CAIC1ALM\n" +
                "03YY2ACB01CE703B------CAIC1ALM\n" +
                "03YY2ACA21CE701B------CAIC1ALM\n" +
                "03YY2ACB01CE702B------CAIC1ALM\n" +
                "03YY2ACA23CE701B------CAIC1ALM\n" +
                "03YY2ACB02CE702B------CAIC1ALM\n" +
                "03YY2ACC02CE701B------CAIC1ALM\n" +
                "03YY2ACB02CE701B------CAIC1ALM\n" +
                "03YY2ACA31CE701B------CAIC1ALM\n" +
                "03YY2ACB01CE701B------CAIC1ALM\n" +
                "03YY2ACA13CE701B------CAIC1F\n" +
                "03YY2ACB02CE703B------CAIC1F\n" +
                "03YY2ACC01CE701B------CAIC1F\n" +
                "03YY2ACB01CE703B------CAIC1F\n" +
                "03YY2ACA21CE701B------CAIC1F\n" +
                "03YY2ACB01CE702B------CAIC1F\n" +
                "03YY2ACA23CE701B------CAIC1F\n" +
                "03YY2ACB02CE702B------CAIC1F\n" +
                "03YY2ACC02CE701B------CAIC1F\n" +
                "03YY2ACB02CE701B------CAIC1F\n" +
                "03YY2ACA31CE701B------CAIC1F\n" +
                "03YY2ACB01CE701B------CAIC1F\n" +
                "03YY2ACA13CE701B------CAIC1AVG\n" +
                "03YY2ACB02CE703B------CAIC1AVG\n" +
                "03YY2ACC01CE701B------CAIC1AVG\n" +
                "03YY2ACB01CE703B------CAIC1AVG\n" +
                "03YY2ACA21CE701B------CAIC1AVG\n" +
                "03YY2ACB01CE702B------CAIC1AVG\n" +
                "03YY2ACA23CE701B------CAIC1AVG\n" +
                "03YY2ACB02CE702B------CAIC1AVG\n" +
                "03YY2ACC02CE701B------CAIC1AVG\n" +
                "03YY2ACB02CE701B------CAIC1AVG\n" +
                "03YY2ACA31CE701B------CAIC1AVG\n" +
                "03YY2ACB01CE701B------CAIC1AVG\n" +
                "03YY2ACA13CE701B------CAIC1MAX\n" +
                "03YY2ACB02CE703B------CAIC1MAX\n" +
                "03YY2ACC01CE701B------CAIC1MAX\n" +
                "03YY2ACB01CE703B------CAIC1MAX\n" +
                "03YY2ACA21CE701B------CAIC1MAX\n" +
                "03YY2ACB01CE702B------CAIC1MAX\n" +
                "03YY2ACA23CE701B------CAIC1MAX\n" +
                "03YY2ACB02CE702B------CAIC1MAX\n" +
                "03YY2ACC02CE701B------CAIC1MAX\n" +
                "03YY2ACB02CE701B------CAIC1MAX\n" +
                "03YY2ACA31CE701B------CAIC1MAX\n" +
                "03YY2ACB01CE701B------CAIC1MAX\n" +
                "03YY2ACA13CE701B------CAIC2ALM\n" +
                "03YY2ACB02CE703B------CAIC2ALM\n" +
                "03YY2ACC01CE701B------CAIC2ALM\n" +
                "03YY2ACB01CE703B------CAIC2ALM\n" +
                "03YY2ACA21CE701B------CAIC2ALM\n" +
                "03YY2ACB01CE702B------CAIC2ALM\n" +
                "03YY2ACA23CE701B------CAIC2ALM\n" +
                "03YY2ACB02CE702B------CAIC2ALM\n" +
                "03YY2ACC02CE701B------CAIC2ALM\n" +
                "03YY2ACB02CE701B------CAIC2ALM\n" +
                "03YY2ACA31CE701B------CAIC2ALM\n" +
                "03YY2ACB01CE701B------CAIC2ALM\n" +
                "03YY2ACA13CE701B------CDIC1COMN\n" +
                "03YY2ACB02CE703B------CDIC1COMN\n" +
                "03YY2ACC01CE701B------CDIC1COMN\n" +
                "03YY2ACB01CE703B------CDIC1COMN\n" +
                "03YY2ACA21CE701B------CDIC1COMN\n" +
                "03YY2ACB01CE702B------CDIC1COMN\n" +
                "03YY2ACA23CE701B------CDIC1COMN\n" +
                "03YY2ACB02CE702B------CDIC1COMN\n" +
                "03YY2ACC02CE701B------CDIC1COMN\n" +
                "03YY2ACB02CE701B------CDIC1COMN\n" +
                "03YY2ACA31CE701B------CDIC1COMN\n" +
                "03YY2ACB01CE701B------CDIC1COMN\n" +
                "03YY2ACA13CE701B------CDIC1ALM\n" +
                "03YY2ACB02CE703B------CDIC1ALM\n" +
                "03YY2ACC01CE701B------CDIC1ALM\n" +
                "03YY2ACB01CE703B------CDIC1ALM\n" +
                "03YY2ACA21CE701B------CDIC1ALM\n" +
                "03YY2ACB01CE702B------CDIC1ALM\n" +
                "03YY2ACA23CE701B------CDIC1ALM\n" +
                "03YY2ACB02CE702B------CDIC1ALM\n" +
                "03YY2ACC02CE701B------CDIC1ALM\n" +
                "03YY2ACB02CE701B------CDIC1ALM\n" +
                "03YY2ACA31CE701B------CDIC1ALM\n" +
                "03YY2ACB01CE701B------CDIC1ALM\n" +
                "03YY2ACA13CE701C------CAIC1ALM\n" +
                "03YY2ACB02CE703C------CAIC1ALM\n" +
                "03YY2ACC01CE701C------CAIC1ALM\n" +
                "03YY2ACB01CE703C------CAIC1ALM\n" +
                "03YY2ACA21CE701C------CAIC1ALM\n" +
                "03YY2ACB01CE702C------CAIC1ALM\n" +
                "03YY2ACA23CE701C------CAIC1ALM\n" +
                "03YY2ACB02CE702C------CAIC1ALM\n" +
                "03YY2ACC02CE701C------CAIC1ALM\n" +
                "03YY2ACB02CE701C------CAIC1ALM\n" +
                "03YY2ACA31CE701C------CAIC1ALM\n" +
                "03YY2ACB01CE701C------CAIC1ALM\n" +
                "03YY2ACA13CE701C------CAIC1F\n" +
                "03YY2ACB02CE703C------CAIC1F\n" +
                "03YY2ACC01CE701C------CAIC1F\n" +
                "03YY2ACB01CE703C------CAIC1F\n" +
                "03YY2ACA21CE701C------CAIC1F\n" +
                "03YY2ACB01CE702C------CAIC1F\n" +
                "03YY2ACA23CE701C------CAIC1F\n" +
                "03YY2ACB02CE702C------CAIC1F\n" +
                "03YY2ACC02CE701C------CAIC1F\n" +
                "03YY2ACB02CE701C------CAIC1F\n" +
                "03YY2ACA31CE701C------CAIC1F\n" +
                "03YY2ACB01CE701C------CAIC1F\n" +
                "03YY2ACA13CE701C------CAIC1AVG\n" +
                "03YY2ACB02CE703C------CAIC1AVG\n" +
                "03YY2ACC01CE701C------CAIC1AVG\n" +
                "03YY2ACB01CE703C------CAIC1AVG\n" +
                "03YY2ACA21CE701C------CAIC1AVG\n" +
                "03YY2ACB01CE702C------CAIC1AVG\n" +
                "03YY2ACA23CE701C------CAIC1AVG\n" +
                "03YY2ACB02CE702C------CAIC1AVG\n" +
                "03YY2ACC02CE701C------CAIC1AVG\n" +
                "03YY2ACB02CE701C------CAIC1AVG\n" +
                "03YY2ACA31CE701C------CAIC1AVG\n" +
                "03YY2ACB01CE701C------CAIC1AVG\n" +
                "03YY2ACA13CE701C------CAIC1MAX\n" +
                "03YY2ACB02CE703C------CAIC1MAX\n" +
                "03YY2ACC01CE701C------CAIC1MAX\n" +
                "03YY2ACB01CE703C------CAIC1MAX\n" +
                "03YY2ACA21CE701C------CAIC1MAX\n" +
                "03YY2ACB01CE702C------CAIC1MAX\n" +
                "03YY2ACA23CE701C------CAIC1MAX\n" +
                "03YY2ACB02CE702C------CAIC1MAX\n" +
                "03YY2ACC02CE701C------CAIC1MAX\n" +
                "03YY2ACB02CE701C------CAIC1MAX\n" +
                "03YY2ACA31CE701C------CAIC1MAX\n" +
                "03YY2ACB01CE701C------CAIC1MAX\n" +
                "03YY2ACA13CE701C------CAIC2ALM\n" +
                "03YY2ACB02CE703C------CAIC2ALM\n" +
                "03YY2ACC01CE701C------CAIC2ALM\n" +
                "03YY2ACB01CE703C------CAIC2ALM\n" +
                "03YY2ACA21CE701C------CAIC2ALM\n" +
                "03YY2ACB01CE702C------CAIC2ALM\n" +
                "03YY2ACA23CE701C------CAIC2ALM\n" +
                "03YY2ACB02CE702C------CAIC2ALM\n" +
                "03YY2ACC02CE701C------CAIC2ALM\n" +
                "03YY2ACB02CE701C------CAIC2ALM\n" +
                "03YY2ACA31CE701C------CAIC2ALM\n" +
                "03YY2ACB01CE701C------CAIC2ALM\n" +
                "03YY2ACA13CE701C------CDIC1COMN\n" +
                "03YY2ACB02CE703C------CDIC1COMN\n" +
                "03YY2ACC01CE701C------CDIC1COMN\n" +
                "03YY2ACB01CE703C------CDIC1COMN\n" +
                "03YY2ACA21CE701C------CDIC1COMN\n" +
                "03YY2ACB01CE702C------CDIC1COMN\n" +
                "03YY2ACA23CE701C------CDIC1COMN\n" +
                "03YY2ACB02CE702C------CDIC1COMN\n" +
                "03YY2ACC02CE701C------CDIC1COMN\n" +
                "03YY2ACB02CE701C------CDIC1COMN\n" +
                "03YY2ACA31CE701C------CDIC1COMN\n" +
                "03YY2ACB01CE701C------CDIC1COMN\n" +
                "03YY2ACA13CE701C------CDIC1ALM\n" +
                "03YY2ACB02CE703C------CDIC1ALM\n" +
                "03YY2ACC01CE701C------CDIC1ALM\n" +
                "03YY2ACB01CE703C------CDIC1ALM\n" +
                "03YY2ACA21CE701C------CDIC1ALM\n" +
                "03YY2ACB01CE702C------CDIC1ALM\n" +
                "03YY2ACA23CE701C------CDIC1ALM\n" +
                "03YY2ACB02CE702C------CDIC1ALM\n" +
                "03YY2ACC02CE701C------CDIC1ALM\n" +
                "03YY2ACB02CE701C------CDIC1ALM\n" +
                "03YY2ACA31CE701C------CDIC1ALM\n" +
                "03YY2ACB01CE701C------CDIC1ALM\n" +
                "03YY2CFD21GQ601------CDIC1CHAS\n" +
                "03YY2CFD21GQ601------CDIC2CHAS\n" +
                "03YY2CFD21GQ601------CDIC1ALM\n" +
                "03YY2CFD21GQ601------CDIC1UNOR\n" +
                "03YY2CFD21GQ601------CAIC1RUN";

        if (args.length == 0) {
            String s_start = "2022-08-29 00:00:00";
            String s_end = "2022-09-02 00:00:00";
            getDataFromDates(arr,s_start, s_end);
        }
    }

    /**
     * @param s_start
     * @param s_end
     */
    private static void getDataFromDates( String arrdata ,String s_start, String s_end) throws Exception {
        OPConnect conn = new OPConnect("10.191.177.6", 8200, 60000, "cypc_ds", "Jxkj@123");
        String[] ar = arrdata.split("\n");

        int index=0;
        HashMap<String, String> map_have_0 = new HashMap<>();
        HashMap<String, String> map_all_0 = new HashMap<>();
        for (String s : ar) {
            String gn = "W3.XJB." + s;
            Boolean flag = true;
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime date_s = LocalDateTime.parse(s_start, df);
            LocalDateTime date_e = LocalDateTime.parse(s_end, df);

            while (date_e.isAfter(date_s) && flag) {
                LocalDateTime next_date = date_e.minusDays(1);
                LocalDateTime _s = next_date;
                LocalDateTime _e = date_e.minusSeconds(1);

//        Date from = Date.from(_s.toInstant(ZoneOffset.of("+8")));
                Date from = Date.from(_s.atZone(ZoneId.systemDefault()).toInstant());
                Date to = Date.from(_e.atZone(ZoneId.systemDefault()).toInstant());


//
                try {
                    OPHisData[] hisData = conn.getPointHistory(gn,from, to, OPNetConst.HISTORY_DATA_SAMPLE, 1);
//                    OPHisData[] hisData = conn.getPointHistory(gn, from, to, OPNetConst.HISTORY_DATA_SPAN, 3600);

                    //原始数据 HISTORY_DATA_SAMPLE
                    //跨度数据 HISTORY_DATA_SPAN

                    for (OPHisData data : hisData) {
//            System.out.println(data);
                        double av = data.getAV();
                        long time = data.getTime();
//                        System.out.println(LocalDateTime.now()+" "+gn+" " +new Date(time)+" " +av);
                        index++;

                        if (av==0.0){
                            String s1 = df.format(LocalDateTime.ofInstant(new Date(time).toInstant(), ZoneId.systemDefault()));

                            map_have_0.put(gn.substring(7),  s1);
                            map_all_0.put(gn.substring(7),  s1);
//                            System.out.println(LocalDateTime.now()+" "+gn+" " +new Date(time)+" " +av);
                            break;
                        }
                    }

                } catch (Exception e) {
                   log.error(e);
                    conn = new OPConnect("10.191.177.6", 8200, 60000, "cypc_ds", "Jxkj@123");
                }



                date_e = next_date;
//                Thread.sleep(200);
            }
            System.out.println(index);
        }

        map_have_0.forEach((x,y)-> System.out.println(x+" "+y));
        System.out.println(map_have_0.size());
        System.out.println("*********************");
        map_all_0.forEach((x,y)-> System.out.println(x+" "+y));
        System.out.println(map_all_0.size());
    }
}
