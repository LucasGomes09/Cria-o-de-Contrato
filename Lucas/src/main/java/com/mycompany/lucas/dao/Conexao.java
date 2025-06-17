package com.mycompany.lucas.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/trabalho";
    public static final String USER = "root";
    public static final String SENHA = "12345678";
    
    public static Connection conectar() throws SQLException{
        return DriverManager.getConnection(URL, USER, SENHA);
    }
}