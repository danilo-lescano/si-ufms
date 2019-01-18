<?php
require_once("model/Contato.php");
require_once("model/ContatoFactory.php");

class ContatoManager {
    private $factory;

    public function __construct() {
        $this->factory = new ContatoFactory();
    }

    public function cadastra(string $nome, string $email): bool {
        try {
            if ($nome == "" )
                throw new Exception("O campo <strong>Nome</strong> deve ser preenchido!");
            elseif ($email == "")
                throw new Exception("O campo <strong>e-mail</strong> deve ser preenchido!");

            //consulta o e-mail no banco
            $result = $this->factory->buscarPorEmail($email);

            // se o resultado for igual a 0 itens, então salva contato
            if (count($result) == 0) {

                $contato = new Contato("", $nome, $email);
                $sucesso = $this->factory->salvar($contato);
                $msg = "O contato <em>" . $nome . "</em> - <em>" . $email . "</em> foi cadastrado com sucesso!";
            }
            else {
                throw new Exception("O contato n&atilde;o foi adicionado. E-mail j&aacute; existente na agenda!");

            }
        } catch (Exception $e) {
            $sucesso = false;
            $msg = $e->getMessage();
        }
        return $sucesso;
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