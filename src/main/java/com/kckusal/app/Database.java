package com.kckusal.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.*;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public class Database {
    public static Connection getConnection() {
        Connection conn=null;
        try {
            Properties props = new Properties();
            props.put("user", "root");      // username to access the database
            props.put("password", "root");  // password to access the database
            props.put("useUnicode", "true");
            //props.put("useServerPrepStmts", "false"); // use client-side prepared statement
            props.put("characterEncoding", "UTF-8"); // ensure charset is utf8 here
            try {
			    Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/inno-library",props);
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return conn;
    }

    public static void executeQuery(String query_text) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query_text);
            ResultSetMetaData rsmd = rs.getMetaData();

            AsciiTable tbl = new AsciiTable();
            
            tbl.addRule();  // add horizontal line

            int columnCount = rsmd.getColumnCount();
            ArrayList<String> column_names = new ArrayList<String>();
            for (int i=1; i<=columnCount; i++) {
                column_names.add(rsmd.getColumnLabel(i));
            }

            tbl.addRow(column_names);
            tbl.addRule();

            ArrayList<String> row_data;
            while (rs.next()) {
                row_data = new ArrayList<String>();
                for(int i=1; i<=columnCount; i++) {
                    row_data.add(rs.getString(i));
                }
                tbl.addRow(row_data);
                tbl.addRule();
            }
            
            //tbl.setPaddingLeftRight(2);
            tbl.setTextAlignment(TextAlignment.CENTER);     // set entire table's text alignment to center
            System.out.println(tbl.render());   // print the table as string
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}