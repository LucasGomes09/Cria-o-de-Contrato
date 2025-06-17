package com.mycompany.lucas.dao;

import com.mycompany.lucas.model.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    public void create(Empresa empresa) throws SQLException {
        String sql = "INSERT INTO empresa (nome) VALUES (?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, empresa.getNome());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    empresa.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Empresa> read() throws SQLException {
        List<Empresa> lista = new ArrayList<>();
        String sql = "SELECT id, nome FROM empresa ORDER BY nome";
        try (Connection conn = Conexao.conectar();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Empresa e = new Empresa(
                    rs.getInt("id"),
                    rs.getString("nome")
                );
                lista.add(e);
            }
        }
        return lista;
    }
    
    public void update(Empresa empresa) throws SQLException {
        String sql = "UPDATE empresa SET nome = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, empresa.getNome());
            ps.setInt(2, empresa.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        // Adicionado para verificar se a empresa possui contratos antes de excluir.
        String checkSql = "SELECT COUNT(*) FROM contrato WHERE id_empresa = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            checkPs.setInt(1, id);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Não é possível excluir a empresa pois ela está associada a um ou mais contratos.");
                }
            }
        }

        String deleteSql = "DELETE FROM empresa WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement deletePs = conn.prepareStatement(deleteSql)) {
            deletePs.setInt(1, id);
            deletePs.executeUpdate();
        }
    }
}