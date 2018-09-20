<?php

require_once("model/Contato.php");



/*
* Material utilizado para as aulas práticas da disciplinas da Faculdade de
* Computação da Universidade Federal de Mato Grosso do Sul (FACOM / UFMS).
* Seu uso é permitido para fins apenas acadêmicos, todavia mantendo a
* referência de autoria.
*
*
*
* Classe controladora que define gerencia do fluxo da aplicação.
*
* @author Jane Eleutério
*
* @version 2.1 - 09/Out/2017
*/

class Controle {



    public function __construct() {


    }

    public function init() {

        if (isset($_GET['funcao'])) {
            $f = $_GET['funcao'];
        } else {
            $f = "";
        }

        switch ($f) {
            case 'novo':
            $this->novo();
            break;
            case 'cadastra':
            $this->cadastra();
            break;
            case 'listar':
            $this->lista();
            break;
            case 'limpar':
            $this->limpa();
            break;
            default:
            $this->home();
            break;
        }
    }

    public function home() {
        require 'view/home.php';
    }

    public function novo() {
        require 'view/novo.php';
    }
    
    public function limpa() {
        session_destroy();
        require 'view/home.php';
    }
    
    public function lista() {
        if (isset($_SESSION['agenda']))
            $agendaContatos = $_SESSION['agenda'];
        require 'view/lista.php';
    }
    
    public function cadastra() {
        if (isset($_POST['enviado'])) {
            
            $nome = $_POST['nome'];
            $email = $_POST['email'];
            
            if (!isset($_SESSION['agenda']))
                $_SESSION['agenda'] = array();

            $contato = new Contato($nome, $email);

            array_push($_SESSION['agenda'], $contato);

            $sucesso = true;
        } else {
            $sucesso = false;
        }

        //inclui o mensagem.php
        include('view/mensagem.php');

    }
    
 //aqui vai o restante do seu código..
 // precisa fazer os demais métodos...

}

?>