<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	 <link rel="stylesheet" type="text/css" href="css/estilo.css">
</head>

<body>
	<div class="contatos">
		<h2>Meus Contatos</h2>
		<ul>
		<?php
    		foreach($agendaContatos as $value) {
                 echo '<li>'.$value->nome.' '.$value->email.'</li>';
            }
		?>
		</ul>
		
	</div>
</body>
</html>