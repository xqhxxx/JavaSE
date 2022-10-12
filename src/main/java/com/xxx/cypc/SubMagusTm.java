package com.xxx.cypc;

import com.magus.net.OPConnect;
import com.magus.net.OPDynamicData;
import com.opencsv.CSVReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author xqh
 * @date 2022/9/15  13:47:09
 * @apiNote 取 magus 链路测点的实时数据
 *
 * java -cp .\JavaSE-1.0-SNAPSHOT-jar-with-dependencies.jar com.xxx.cypc.SubMagusTm
 */
public class SubMagusTm {

    static ArrayList<Integer> ids = new ArrayList<>();
    static HashMap<Integer, String> maps = new HashMap<>();

    static {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(System.getProperty("user.dir").concat("/code.csv")));
            CSVReader csvReader = new CSVReader(reader);
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                ids.add(Integer.valueOf(record[0]));
                maps.put(Integer.valueOf(record[0]), record[2]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        //todo 巡检初始化
//        StringBuffer buff = new StringBuffer();
//        buff.append("===巡检初始化====").append("\n")
//                .append(LocalDateTime.now()).append("\n")
//                .append("[鬼脸]");//表情
//        RealWarns.sendDingDing(buff);

        //todo
        OPConnect conn = new OPConnect("10.191.177.6", 8200, 60000, "cypc_ds", "Jxkj@123");
        Boolean flag = false;
        StringBuffer stringBuffer = new StringBuffer();

        while (true) {
            try {
                OPDynamicData[] pdd = conn.getPointDynamicDatasByIds(ids.stream().mapToInt(x -> x).toArray());
                // 输出获取到的数据
                long current_tm = System.currentTimeMillis();

                for (int i = 0; i < pdd.length; i++) {
                    OPDynamicData opd = pdd[i];
                    String av = String.valueOf(opd.getAV());
                    long tm = opd.getTime();
                    int id = ids.get(i);
                    if ("0.0".equals(av) || (Math.abs(tm - current_tm) > 1000 * 60)) {
                        String coent = maps.get(id);
                        stringBuffer.append(coent).append(" ：异常").append("\n");
                        flag = true;
                    }
                }

                if (flag) {//报警一次后暂定 30 min
                    stringBuffer.append(LocalDateTime.now()).append("\n").append("[吐]");//表情
                    RealWarns.sendDingDing(stringBuffer);
                    Thread.sleep(1000 * 60 * 30);
                }

                Thread.sleep(10 * 1000);
                System.out.println("10s  while  check");
                //清楚状态 重新检测
                stringBuffer.delete(0, stringBuffer.length());
                flag = false;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("fuck you err,");
                conn = new OPConnect("10.191.177.6", 8200, 60000, "cypc_ds", "Jxkj@123");
            }
        }
    }
}
