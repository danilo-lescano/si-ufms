<?php
require_once("model/Contato.php");
require_once("model/ContatoFactory.php");

class ContatoManager {
    private $factory;

    public function __construct() {
        $this->factory = new ContatoFactory();
    }

    public function cadastra(string $nome, string $email): string {

    }
    public function lista(): array {

    }
    public function excluir(string $id):string {

    }
    public function busca(string $id): Contato {
    	return current($this->factory->buscarPorId($_GET['id']));
    }
    public function atualiza(string $id, string $nome, string $email): string {
        try {
            if ($nome == "" ) {
                throw new Exception("O campo <strong>Nome</strong> deve ser preenchido!");
            } elseif ($email == "") {
                throw new Exception("O campo <strong>e-mail</strong> deve ser preenchido!");
            } elseif ($id == "") {
                throw new Exception("Operacao invalida!");
            }

            $sucesso = $this->factory->editar($id, $nome, $email);
        } catch (Exception $e) {
            $sucesso = false;
            $msg = $e->getMessage();
        }

        return $msg;
	}
}
?>