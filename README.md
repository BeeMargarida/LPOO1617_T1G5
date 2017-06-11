# Downwell

### Entrega Final

O nosso projeto consiste no jogo Downwell, um vertical scroller. O objetivo deste é chegar ao final de cada nível sem perder totalmente os pontos de vida e tentar fazer o máximo de score, que consiste em matar mais monstros. Cada nível tem uma profundidade variável e é gerado aleatoriamente, assim como os inimigos, com algumas restrições. O herói possui uma arma, a qual só pode usar quando esta a saltar. O jogo contém 3 inimigos: o Morcego (Bat), que tem movimento estático quando se encontra longe do herói mas, a partir de uma certa distância, começa a seguir o herói; o Caracol (Snail) que tem um movimento constante a subir e a descer paredes; a Bolha (Bubble) que tem o mesmo comportamento que o Morcego. 

## Setup/Installation Guide

---
## Development Documentation

### Class Diagram

##### Controller
![alt text](https://user-images.githubusercontent.com/25725586/27010333-22b6ce0e-4e9a-11e7-80a3-9dda8c802ebd.jpg)

##### Model
![alt text](https://user-images.githubusercontent.com/25725586/27010336-30548d30-4e9a-11e7-9243-78b3326359f5.jpg)

##### View
![alt text](https://user-images.githubusercontent.com/25725586/27010339-30575952-4e9a-11e7-9732-9428cb0102d4.jpg)

##### Downwell
![alt text](https://user-images.githubusercontent.com/25725586/27010338-305874ae-4e9a-11e7-9df5-aa6fb18aab7e.jpg)

### Package Diagram
![alt text](https://user-images.githubusercontent.com/25725586/27010337-30569e54-4e9a-11e7-8510-c33661342826.jpg)

### State Diagram 
![alt text](https://user-images.githubusercontent.com/25725586/27011137-8730f4b4-4ead-11e7-943e-786a99d374e0.png)
---
---
## User Manual

### Descrição
O objetivo do jogo é alcançar a maior pontuação possível. Para isso o jogador terá que destruir inimigos e subreviver através dos niveis.
A dificuldade do jogo vai aumentando ao longo dos niveis tornado-se mais longos e com maior número de inimigos.
Cada nível é gerado aleatoriamente.

### Menu Principal
Menu de inicio da aplicacao. Inclui duas opções: Play (inicar o jogo) e Exit (sair do jogo).
Navegação: Usar UPKEY e DOWNKEY para controlar a opção e selecionar com Enter.
![alt text](https://user-images.githubusercontent.com/25725586/27011424-3f2e3ae0-4eb3-11e7-8fd3-7c112ed5b263.png)

### Menu dos Resultados
Menu mostrado quando é atingido o game over.
Inclui os resultados de: 
* Level - nivel maximo atingido
* Score - pontuação total
* Kills - numero de inimigos destruidos
* Time - tempo de jogo
Navegação: Carregar Enter para sair deste menu e regressar ao menu principal.
![alt text](https://user-images.githubusercontent.com/25725586/27011427-3f33ce7e-4eb3-11e7-87b8-96f001cb9543.png)

### Jogo

#### Controlos
* A - andar para a esquerda
* B - andar para a direita
* W - saltar
* K - Disparar

#### Personagens

##### Heroi 
* HP: 4
O heroi tem a capacidade de saltar e disparar so enquanto esta no ar.
Apenas pode disparar 8 projeteis no maximo antes de tocar numa superficie ou saltar por cima de um inimigo para recuperar os disparos.

Quando o heroi salta existem duas possiveis animações:
Se a sua velocidade horizontal for diferente de 0, executa um rolamento, senão executa um salto normal.
![alt text](https://user-images.githubusercontent.com/25725586/27011429-3f4439c6-4eb3-11e7-95c0-8ae2b3e4e32e.png)
![alt text](https://user-images.githubusercontent.com/25725586/27011432-3f4a351a-4eb3-11e7-8dff-68eea0d361c0.png)
![alt text](https://user-images.githubusercontent.com/25725586/27011430-3f44ea74-4eb3-11e7-95f1-b4505bfda362.png)

##### Inimigos 

Existem duas formas de atacar os inimigos: saltar por cima ou disparar.
Apenas os inimigos brancos morrem instantaneamente quando o heroi salta por cima deles. Os vermelhos não são vulneraveis a este ataque.
Para todos, os projeteis apenas fazem um ponto de dano.

###### Bubble 
* HP: 3 
* Tipo: Branco
* Pontos: 50 
* Comportamento: segue o heroi quando ele está no seu alcançe.
![alt text](https://user-images.githubusercontent.com/25725586/27011433-3f4df7a4-4eb3-11e7-96b7-5f08385f7384.png)
###### Bat
* HP:3
* Tipo: Branco
* Pontos: 50
* Comportamento: segue o heroi quando ele está no seu alcançe.
![alt text](https://user-images.githubusercontent.com/25725586/27011434-3f4f08ec-4eb3-11e7-9f8d-bcd205759ee7.png)
![alt text](https://user-images.githubusercontent.com/25725586/27011436-3f5e8812-4eb3-11e7-9df7-fecf46363ec8.png)
###### Snail
* HP: 3
* Tipo: Vermelho
* Pontos: 100
* Comportamento: desloca-se pela parede, mudando a sua direção quando encontra um objeto. 
Só pode ser destruido através de projéteis.
![alt text](https://user-images.githubusercontent.com/25725586/27011431-3f45f554-4eb3-11e7-86a1-b79d7be2d54c.png)

#### Mapa
O mapa é composto por dois tipos distintos de blocos (tiles): um bloco destrutivel (atraves de um salto de baixo para cima ou de um projetil) e blocos indestrutiveis.
![alt text](https://user-images.githubusercontent.com/25725586/27011437-3f6066f0-4eb3-11e7-87c7-174af66e1363.png)
![alt text](https://user-images.githubusercontent.com/25725586/27011435-3f5dc9d6-4eb3-11e7-9faf-3f6511582e69.png)

#### HUD
No topo esquerdo do ecrã (à esquerda da área de jogo) econtra-se a barra de vida do heroi que indica a vida atual do mesmo. 
![alt text](https://user-images.githubusercontent.com/25725586/27011425-3f2e7cd0-4eb3-11e7-9a0c-c4e10816350f.png)
No lado direito da área de jogo econtra-se a barra de disparos que indica o número de disparos restantes para o heroi.
![alt text](https://user-images.githubusercontent.com/25725586/27011426-3f2eb510-4eb3-11e7-8d76-f24a61551ed5.png)
 
Inicio de nivel: ![alt text](https://user-images.githubusercontent.com/25725586/27011423-3f2d982e-4eb3-11e7-913d-0397441f72d6.png)
