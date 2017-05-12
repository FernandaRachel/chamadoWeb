package br.usjt.chamado.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    
    private static Connection connection;
    
    private static String dsn = "jdbc:mysql://localhost:3306/chamados";
    private static String username = "root";
    private static String password = "654321";
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(dsn, username, password);
            } catch (SQLException ex) {
                System.out.println("Houve um erro ao conectar com o Banco de Dados.");
            }
        }
        
        return connection;
    }
    
}
