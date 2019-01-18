<?php

require_once("model/Contato.php");
require_once("model/ContatoFactory.php");
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
* Apesar do nome, essa classe AINDA NÃO É uma classe controller do
* padrão arquitetural MVC... assunto a ser discutido em sala de aula. 
*
* @author Jane Eleutério
*
* @version 2.2 - 19/Set/2018
*/

class Controle {

    private $factory;
    private $contatoManager;

    public function __construct() {
        $this->factory = new ContatoFactory();
        $this->contatoManager = new ContatoManager();
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
            case 'excluir':
            $this->exclui();
            break;
            case 'editar':
            $this->edita();
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

    public function cadastra() {
        if (isset($_POST['enviado'])) {

            $nome = $_POST['nome'];
            $email = $_POST['email'];

            $sucesso = $this->contatoManager->cadastra($nome, $email);

            require 'view/mensagem.php';
        }
        else
            require 'view/home.php';
    }

	
	public function lista() {

        $agendaContatos = $this->factory->listar();
        require 'view/lista.php';
    }
	
    public function exclui() {
		
		try{

			$id = $_GET['id'];
			
			//consulta o objeto no banco de dados, buscando pelo id
			$result = $this->factory->buscar($id);

			// Se o resultado da busca conter 0 itens, 
			// então não foi encontrado o objeto no banco de dados,
			// caso contrário, é solicitada a exclusão do objeto
			if (count($result) == 0) {
				throw new Exception("O contato n&atilde;o foi encontrado na agenda!");
			}
			else {
				$contato = current($result);
				$sucesso = $this->factory->excluir($contato);
					
				if ($sucesso){					
					$msg = "O contato <em>" . $contato->getNome() . "</em> foi excluído com sucesso!";
				}else{
					throw new Exception("O contato n&atilde;o foi excluído na agenda!");
				}

			}
		
		} catch (Exception $e) {
                $sucesso = false;
                $msg = $e->getMessage();

            }
        require 'view/mensagem.php';
    }
	
	public function edita() {
		
		//Se a função edita não vier com os dados do formulário, 
		// então busca o objeto para edição,
		// caso contrário, atualiza os dados editados do objeto. 
        if (!isset($_POST['enviado'])) {
			$id = $_GET['id'];
			
			//consulta o objeto no banco de dados, buscando pelo id
			$result = $this->factory->buscar($id);

			// Se o resultado da busca conter 0 itens, 
			// então não foi encontrado o objeto no banco de dados,
			// caso contrário, o objeto é enviado para a página de edição
			if (count($result) == 0) {
				throw new Exception("O contato n&atilde;o foi encontrado na agenda!");
			}
			else {	
				$contato = current($result);
				require 'view/edita.php';
			}
			
		}else{
			$id = $_POST['id'];
            $nome = $_POST['nome'];
            $email = $_POST['email'];

            try {
                //consulta o objeto no banco de dados, buscando pelo id
                $result = $this->factory->buscar($id);

                // Se o resultado da busca conter 0 itens, 
				// então não foi encontrado o objeto no banco de dados,
				// caso contrário, é solicitada a atualização do objeto
				if (count($result) == 0) {
					throw new Exception("O contato n&atilde;o foi encontrado na agenda!");
				}
				else{

                    $contato = current($result);
					$contato->setNome($nome);
					$contato->setEmail($email);
                    $sucesso = $this->factory->atualizar($contato);
					
					if ($sucesso){					
						$msg = "O contato <em>" . $nome . "</em> - <em>" . $email . "</em> foi atualizado com sucesso!";
					}else{
						throw new Exception("O contato n&atilde;o foi atualizado na agenda!");
					}
				
				}

            } catch (Exception $e) {
                $sucesso = false;
                $msg = $e->getMessage();

            }
            require 'view/mensagem.php';
        }
    }

}













?>
