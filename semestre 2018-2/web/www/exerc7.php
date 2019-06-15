<!DOCTYPE html>
<html lang="pt">
<head>
<title>Eu sou um titulo</title>
</head>
<body>
<?php
/*
 *Exercicio 8 do ead
 * O objetivo é "experimentar" sintaxe do PHP, e para tanto deve-se:
 *criar ao menos uma variável de tipo simples (numeral ou textual);
 *criar um vetor simples, apenas com valor;
 *criar um vetor usando a sintaxe de par chave-valor;
 *criar uma estrutura condicional (if-else);
 *uma uma estrutura de repetição de cada tipo (for, while, e foreach):
 *É obrigatório que o arquivo gerado seja um HTML válido!
*/
?>
<?php
    $variavelSimples = "Hello world!";
    $vetorSimples = array(0,1,2,3,4,5,6,7,8,9);
    $vetorDePar = array("arroz" => "grão", "boi" => "animal", "besouro" => "inseto");
    $x = 3;
    
    echo "<p>";
    while($x > 0) {
        echo "$x...<br>";
        $x--;
    }
    echo "</p>";
    
    if($variavelSimples == "Hello world!"){
        echo "<ul>";
        for($i = 0; $i < count($vetorSimples); $i++){
            echo "<li>";
            echo $vetorSimples[$i];
            echo "</li>";
        }
        echo "</ul>";
    }

    echo "<p>";
    foreach($vetorDePar as $coisa => $tipo) {
        echo $coisa . " é " . $tipo;
        echo "<br>";
    }
    echo "</p>";
?>

</body>
</html>