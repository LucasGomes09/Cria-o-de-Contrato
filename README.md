# Cria-o-de-Contrato
Este projeto é um sistema Java Swing para gerenciamento de contratos, clientes e empresas. Utiliza JDBC para interagir com um banco de dados MySQL. Permite criar, ler, atualizar e deletar registros, com validações para associações de contratos.


Dados para criar o Banco de dados:
CREATE DATABASE trabalho
use trabalho
CREATE TABLE cliente (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE empresa (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE contrato (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  id_cliente INT NOT NULL,
  id_empresa INT NOT NULL,
  descricao TEXT,
  valor DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (id),

  CONSTRAINT fk_contrato_cliente
    FOREIGN KEY (id_cliente)
    REFERENCES cliente(id)
    ON DELETE RESTRICT,

  CONSTRAINT fk_contrato_empresa
    FOREIGN KEY (id_empresa)
    REFERENCES empresa(id)
    ON DELETE RESTRICT
);
