package com.xxx.cypc;

import com.magus.jdbc.net.OPSubscribe;
import com.magus.jdbc.net.SubscribeResultSet;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author xqh
 * @date 2022/9/15  13:47:09
 * @apiNote
 * 订阅麦杰实时数据
 */
public class SubMagus implements SubscribeResultSet {

    public static String className = "com.magus.jdbc.Driver";
    public static String url = "jdbc:openplant://10.191.177.6:8200/RTDB";
    public static String user = "cypc_ds";
    public static String password = "Jxkj@123";

    static ArrayList<Integer> ids = new ArrayList<Integer>();
    static HashMap<Integer, String> maps = new HashMap();

    static {

        try (Reader reader = Files.newBufferedReader(
                Paths.get("src/main/java/com/xxx/cypc/code.csv"));
             CSVReader csvReader = new CSVReader(reader)) {
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                ids.add(Integer.valueOf(record[0]));
                maps.put(Integer.valueOf(record[0]), record[2]);
            }
        } catch (IOException | CsvValidationException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName(className);
        Connection conn = DriverManager.getConnection(url, user, password);
        if (conn.getAutoCommit()) {
            conn.setAutoCommit(false);
        }
        try {
            OPSubscribe subscribe = new OPSubscribe(conn, "Realtime", ids, new SubMagus());
            Thread.sleep(1000 * 10);
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        }

    }

    public void onResponse(ResultSet result) throws SQLException {
        while (result.next()) {
            int ID = result.getInt(1);
            String GN = result.getString(2);
            Long TM = result.getDate(3).getTime();
            String DS = result.getString(4);
            String AV = result.getString(5);

            System.out.println(DS);

            if (!AV.equals("1")) {
                String coent = maps.get(ID);
                System.out.println(coent);

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(coent + " ：异常").append("\n")
                        .append(LocalDateTime.now()).append("\n")
                        .append("[吐]");//表情
                RealWarns.sendDingDing(stringBuffer);
            }
        }
    }
}
