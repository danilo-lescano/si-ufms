<?php

/*
 * Material utilizado para as aulas práticas da disciplinas da Faculdade de
 * Computação da Universidade Federal de Mato Grosso do Sul (FACOM / UFMS).
 * Seu uso é permitido para fins apenas acadêmicos, todavia mantendo a
 * referência de autoria.
 *
 *
 * Classe abstrata que define o padrão para todas as fábricas.
 *
 * @author Jane Eleutério
 * @author Rodrigo Lopes - Acadêmico CC (Otimização do método queryRowsToListOfObjects)
 * @author Wandermar Marques Ferreira - Acadêmico TADS (melhoria na conexão com
 * o banco, mostrando as mensagens de erro retornadas pelo banco)
 *
 * @version 2.2 - 22/Dez/2016
 */

abstract class AbstractFactory {

    protected $db;

    public function __construct() {

        try {
            $this->db = new PDO("sqlite:model/DB_Contato.sqlite");

            $this->db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        } catch (PDOException $exception) {
            echo $exception->getMessage();
        } catch (Exception $exception) {
            echo $exception->getMessage();
        }
    }

    /**
     * Persiste um objeto no banco. Executa um SQL "Insert into..."
     * @param Object $obj - Objeto a ser persistido.
     * @return boolean - se conseguiu salvar ou não.
     */
    abstract public function salvar($obj);

    /**
     * Lista os objetos persistidos no banco, ou seja, executa um
     * SQL "Select - From"
     * @return array -  Array de objetos da classe.
     */
    abstract public function listar(): array;

    /**
     * Busca objetos no banco de dados que atendem a um parâmetro.
     * Executa um SQL "Select - From - Where"
     * @param string $param - parâmetro a ser buscado.
     * @return  array -  Array de objetos da classe, ou null se não encontrar
     * objetos.
     */
    abstract public function buscar(string $param): array;


    abstract public function buscarPorId(string $param): array;
    abstract public function excluir(string $param): bool;
    abstract public function ultimoId(): array;
    abstract public function editar(string $id, string $nome, string $email): bool;

    /**
     * Faz o mapeamento Objeto-Relacional, transformando um resultado de
     * consulta (PDOStatement) em uma lista de Objetos recuperados.
     * @param PDOStatement $result - o resultado da consulta
     * @param String $nameObject - nome da Classe de objetos
     * @return  $list lista de objetos da classe $nameObject
     */
    protected function queryRowsToListOfObjects
    (PDOStatement $result, $nameObject): array {
        $list = array();
        $r = $result->fetchAll(PDO::FETCH_NUM);
        foreach ($r as $row) {
            $ref = new ReflectionClass($nameObject);
            $list[] = $ref->newInstanceArgs($row);
        }
        return $list;
    }

}

?>
