<?php
session_start();
?>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	 <link rel="stylesheet" type="text/css" href="css/estilo.css">
</head>

<body>
	<div class="box">
		<h2>Agenda de contatos</h2>
		<div class="corpo">
			<a href="gerenciador.php?funcao=novo" class="button"> Cadastrar Contato</a>
			<a href="gerenciador.php?funcao=listar" class="button"> Listar Contatos</a>
		</div>
	</div>
</body>
</html>