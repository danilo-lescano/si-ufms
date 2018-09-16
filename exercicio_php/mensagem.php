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
		<h2>
		<?php
		if($sucesso){
		    echo "SUCESSO";
		}
		else{
		    echo "NUM DEU";
		}
		?>
		</h2>
	<!-- 	if else php (mensagem) -->
	</div>
</body>
</html>