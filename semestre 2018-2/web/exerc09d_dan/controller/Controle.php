<?php

require_once("model/Contato.php");
require_once("model/ContatoManager.php");



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
* @version 2.2 - 19/Set/2018
*/

class Controle {

    private $contatoManager;
    private $factory;

    public function __construct() {
        $this->contatoManager = new ContatoManager();
        $this->factory = new ContatoFactory();
    }

    public function init() {
        if (isset($_GET['funcao']))
            $f = $_GET['funcao'];
        else 
            $f = "";

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
            case 'excluir':
            $this->excluir();
            break;
            case 'editar':
            $this->editar();
            break;
            default:
            $this->home();
            break;
        }
    }
    public function home() {
        require 'view/home.php';
    }
    public function editar() {
        if (isset($_POST['enviado'])) {

            $id = $_POST['id'];
            $nome = $_POST['nome'];
            $email = $_POST['email'];

            $msg = $this->contatoManager->atualiza($id, $nome, $email);

            require 'view/mensagem.php';
        }
        else if(isset($_GET['id'])){
            $contato = $this->contatoManager->busca($_GET['id']);
            require 'view/edita.php';
        }
        else
            require 'view/home.php';
    }
    public function novo() {
        require 'view/novo.php';
    }
    public function cadastra() {
        if (isset($_POST['enviado'])) {

            $nome = $_POST['nome'];
            $email = $_POST['email'];

            try {
                if ($nome == "" ) {
                    throw new Exception("O campo <strong>Nome</strong> deve ser preenchido!");
                } elseif ($email == "") {
                    throw new Exception("O campo <strong>e-mail</strong> deve ser preenchido!");
                }


                //consulta o e-mail no banco
                $result = $this->factory->buscar($email);

                // se o resultado for igual a 0 itens, então salva contato
                if (count($result) == 0) {
                    $contatoUltimoId = $this->factory->ultimoId();
                    $novoId = "" . (intval($contatoUltimoId) + 1);

                    $contato = new Contato($novoId, $nome, $email);
                    $sucesso = $this->factory->salvar($contato);
                }
                else {
                    throw new Exception("O contato n&atilde;o foi adicionado. E-mail j&aacute; existente na agenda!");
                }

                //$sucesso = true;
                    $msg = "O contato " . $nome . " (" . $email . ") foi cadastrado com sucesso!";

                unset($nome);
                unset($email);

            } catch (Exception $e) {
                $sucesso = false;
                $msg = $e->getMessage();

            }
            require 'view/mensagem.php';
        }
    }
    public function lista() {

        $agendaContatos = $this->factory->listar();
        require 'view/lista.php';
    }
    public function excluir() {
        if(isset($_GET['id'])){
            $id = $_GET['id'];

            try {
                if ($id == "" ) {
                    throw new Exception("O campo <strong>id</strong> deve ser preenchido!");
                }
                $result = $this->factory->buscarPorId($id);
                $contato = current($result);
                if(count($result)){
                    echo "O contato " . $contato->getNome() . " (" . $contato->getEmail() . ") foi excluido com sucesso!";

                    $sucesso = $this->factory->excluir($id);

                    $msg = "O contato " . $contato->getNome() . " (" . $contato->getEmail() . ") foi excluido com sucesso!";
                }

                $msg = "O id não existe";

                unset($id);

            } catch (Exception $e) {
                $sucesso = false;
                $msg = $e->getMessage();

            }
            require 'view/mensagem.php';
        }
        else
            require 'view/home.php';
    }
}

?>
