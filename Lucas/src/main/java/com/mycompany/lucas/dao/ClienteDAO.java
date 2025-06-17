package com.mycompany.lucas.dao;

import com.mycompany.lucas.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void create(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome) VALUES (?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNome());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Cliente> read() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nome FROM cliente ORDER BY nome";
        try (Connection conn = Conexao.conectar();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome")
                );
                lista.add(c);
            }
        }
        return lista;
    }
    
    public void update(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setInt(2, cliente.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        // Adicionado para verificar se o cliente possui contratos antes de excluir.
        String checkSql = "SELECT COUNT(*) FROM contrato WHERE id_cliente = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            checkPs.setInt(1, id);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Não é possível excluir o cliente pois ele está associado a um ou mais contratos.");
                }
            }
        }
        
        String deleteSql = "DELETE FROM cliente WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement deletePs = conn.prepareStatement(deleteSql)) {
            deletePs.setInt(1, id);
            deletePs.executeUpdate();
        }
    }
}