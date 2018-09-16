<?php

/**
 * Material utilizado para as aulas práticas das disciplinas da Faculdade de
 * Computação da Universidade Federal de Mato Grosso do Sul (FACOM / UFMS).
 * Seu uso é permitido para fins apenas acadêmicos, todavia mantendo a
 * referência de autoria.
 * Created on : 13/dez/2016
 * Updated on : 12/set/2018
 * Author     : Prof. Jane Eleutério
 */
/**
 * Controladora não orientada a objetos.
 * @author Prof. Jane Eleutério
 * @version 1.1
 * 
 */


//requisita a classe Contato.
require_once("Contato.php");


//verifica se recebeu por GET o parâmetro 'funcao'.
if (isset($_GET['funcao'])) {
    $f = $_GET['funcao'];
} else {
    $f = "";
}


// para cada valor recebido em 'funcao', 
// será realizada uma ação.
switch ($f) {

    case 'novo'://requisição para o formulário para cadastro
        include('novo.php');

        break;

    case 'cadastra':// requisição que recebe os parametros do contato e salva na sessão
        
        //verifica se o formulário foi recebido via POST
        //o parâmetro "enviado" foi recebido do input submit (botão)
        //exemplo: <input type="submit" value="Salvar" name="enviado"/>
        if (isset($_POST['enviado'])) {
            
            //recebe os parâmetros do formulário
            $nome = $_POST['nome'];
            $email = $_POST['email'];


            //inicia sessão
            session_start();
            

            //verifica se a sessão já foi inicializada
            if (!isset($_SESSION['agenda'])) {
                
                //inicia o vetor 'agenda' na sessão
                $_SESSION['agenda'] = array();
            }

            // se a exceção não foi lançada acima, instancia um 
            // objeto da classe contato
            $contato = new Contato($nome, $email);

            //coloca o novo contato no final do vetor
            array_push($_SESSION['agenda'], $contato);

            $sucesso = true;
        } else {
            $sucesso = false;
        }

        //inclui o mensagem.php
        include('mensagem.php');


        break;

    case 'listar'://requisição para listar os contatos cadastrados
        
        //inicia a sessão
        session_start();

        if (isset($_SESSION['agenda'])) {
            $agendaContatos = $_SESSION['agenda'];
        }        

        include('lista.php');

        break;
        
    case 'limpar'://requisição para destruir a sessão
        session_start();
        
        session_destroy();
        
        include('index.php');

        break;

    default:
        include('index.php');
        
        break;
}
?>