package com.mycompany.lucas.dao;

import com.mycompany.lucas.model.Contrato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {
    
    public void create(Contrato f) throws SQLException {
       String sql = "INSERT INTO contrato (nome, id_cliente, id_empresa, descricao, valor) VALUES (?, ?, ?, ?, ?)";
       try (Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {
           ps.setString(1, f.getNome());
           ps.setInt(2, f.getId_cliente());
           ps.setInt(3, f.getId_empresa());
           ps.setString(4, f.getDescricao());
           ps.setDouble(5, f.getValor());
           ps.executeUpdate();
       }
    }
    
    public List<Contrato> read() throws SQLException {
        List<Contrato> lista = new ArrayList<>();
        String sql = "SELECT id, nome, id_cliente, id_empresa, descricao, valor FROM contrato";
        try (Connection conn = Conexao.conectar();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()){
                Contrato f = new Contrato(
                    rs.getInt("id"), 
                    rs.getString("nome"), 
                    rs.getInt("id_cliente"),
                    rs.getInt("id_empresa"),
                    rs.getString("descricao"), 
                    rs.getDouble("valor")
                );
                lista.add(f);
            }
        }
       return lista;
    }
}