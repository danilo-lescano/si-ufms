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
		<h2> Novo Contato </h2>
		<div class="corpo">
			<form action="gerenciador.php?funcao=cadastra" method="post">
				  Nome:<br>
				  <input type="text" name="nome">
				  <br>
				  Email:<br>
				  <input type="email" name="email"><br>
				  <input type="submit" value="Salvar" name="enviado">
			</form>
		</div>
	</div>
</body>
</html>