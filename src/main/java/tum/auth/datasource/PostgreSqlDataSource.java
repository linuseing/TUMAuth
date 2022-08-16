package tum.auth.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class PostgreSqlDataSource {

    private String host;
    private String port;
    private String username;
    private String password;
    private String database;


    public PostgreSqlDataSource(String url, String username, String password) {
//        String url = "jdbc:postgresql://localhost/test";
//        Connection conn = DriverManager.getConnection(url, props);
//        String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
//        Connection conn = DriverManager.getConnection(url);
    }

    public boolean connect() {
//        Connection conn = DriverManager.getConnection(url, props);
    }


}
