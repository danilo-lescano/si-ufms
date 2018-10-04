<?php

/*
 * Material utilizado para as aulas pr�ticas das disciplinas da Faculdade de
 * Computa��o da Universidade Federal de Mato Grosso do Sul (FACOM / UFMS).
 * Seu uso � permitido para fins apenas acad�micos, todavia mantendo a
 * refer�ncia de autoria.
 * Created on : 20/09/2013
 * Author     : Prof. Jane Eleutério
 */

/**
 * Classe Contato
 * @author Prof. Jane Eleutério
 * @version 1.0
 *
 */
class Contato {

	private  $id;
    private  $nome;
    private  $email;

    /**
     * Construtor de contato. Nesse contrutor foi utilizado o type hint.
     * @param string $id (opcional)
     * @param string $nome
     * @param string $email
     */
    public function __construct(string $id="", string $nome, string $email) {
        $this->id = $id;
		$this->nome = $nome;
        $this->email = $email;
    }
	
	

	/**
     * Retorna o id do contato. A declaração do método está usando o tipo de retorno.
     * @return id - string
     */
    public function getId():string {
        return $this->id;
    }
	
    /**
     * Retorna o nome do contato. A declaração do método está usando o tipo de retorno.
     * @return nome - string
     */
    public function getNome(): string {
        return $this->nome;
    }

    /**
     * Retorna o email do contato. A declaração do método está usando o tipo de retorno.
     * @return email - string
     */
    public function getEmail(): string {
        return $this->email;
    }
	
	/**
     * Altera o nome. Nesse método foi utilizado o type hint.
     * @param string $nome
     */
    public function setNome(string $nome) {
        $this->nome = $nome;
    }
	
	/**
     * Altera o email. Nesse método  foi utilizado o type hint.
     * @param string $email
     */
    public function setEmail(string $email) {
        $this->email = $email;
    }

}

?>
