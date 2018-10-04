<!DOCTYPE html>
<!--
* Material utilizado para as aulas práticas da disciplinas da Faculdade de
* Computação da Universidade Federal de Mato Grosso do Sul (FACOM / UFMS).
* Seu uso é permitido para fins apenas acadêmicos, todavia mantendo a
* referência de autoria.
* @author Profª Jane Eleutério

Página para cadastro de novo contato na agenda.
-->
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Agenda de Contatos</title>
    <link rel="stylesheet" type="text/css" href="view/estilo.css"/>
</head>
<body>
    <h1>Agenda de Contatos</h1>

    <p>Aqui é possível alterar seu contato.</p>
    <form action="?funcao=editar" method="post">

		<input type="hidden" id="id" name="id" 
		value="<?= $contato->getId(); ?>">
		
        <label for="nome">Nome:</label>
		<input name="nome" id="nome" type="text" maxlength="70"
		placeholder="Preencha o nome do contato" required="required"
		value="<?= $contato->getNome(); ?>"/>

		<label for="email">E-mail:</label>
		<input name="email" id="email" type="email"
		placeholder="Preencha o email" required="required"
		value="<?= $contato->getEmail(); ?>"/>

		<input type="submit" value="Salvar" name="enviado"/>

    </form>


</body>
</html>
