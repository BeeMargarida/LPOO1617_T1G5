# Downwell

### Entrega Intermédia

O nosso projeto consiste no jogo Downwell, um vertical scroller. O objetivo deste é chegar ao final de cada nível sem perder totalmente os pontos de vida e tentar fazer o máximo de score, que consiste em matar mais monstros. Cada nível tem uma profundidade fixa e é gerado aleatoriamente, com algumas restrições. O herói possui uma arma, a qual só pode usar quando esta a saltar. O jogo contém 3 inimigos: o Morcego (Bat), que tem movimento estático quando se encontra longe do herói mas, a partir de uma certa distância, começa a seguir o herói; o Caracol (Snail) que tem um movimento constante a subir e a descer paredes; a Bolha (Bubble) que tem o mesmo comportamento que o Morcego. 

--- 

#### Class Diagram

![alt text](https://cloud.githubusercontent.com/assets/25725586/25564275/a9512f04-2da7-11e7-97d1-3055dcd02222.png)

---

#### Package Diagram

![alt text](https://cloud.githubusercontent.com/assets/25725586/25557297/cd648a7c-2d06-11e7-84e8-15c81e126104.png)

---

#### State Diagram

![alt text](https://cloud.githubusercontent.com/assets/25725586/25557311/fc8c3aa2-2d06-11e7-8fbe-9e3ad83b3672.png)

---
#### Design Patterns

##### Model-View-Controller Pattern
Este padrão será usado para separar as responsabilidades da aplicação.
  Model - representa o objeto. Pode ter lógica para atualizar o controlador, caso os dados mudem.
  View - representa a visualização dos dados que o respetivo modelo contém.
	Controller - atua no Model e View. Controla os dados do Model e atualiza a View quando os dados mudam. Controla também a separação entre View e Model. Para além disso, lida com a lógica por trás da física dos objetos.

##### Strategy Pattern
Este padrão será usado para controlar o comportamente dos inimigos, por exemplo do morcego (Bat), que se comporta de maneira diferente dependendo da sua distância ao herói (quando está mais perto deste, o morcego segue o heroi).

##### State Pattern
Este padrão será usado para controlar o comportamente do herói no que toca ao seu estado. Isto é, quando o herói está no meio de um salto, existem certas ações que este não pode fazer. Este padrão ajuda a organizar tais situações.

##### Pool Pattern 
Este padrão será usado para controlar as partículas, mais precisamente as balas do herói. 

##### Factory Pattern
Este padrão será usado para desenhar o mapa, mais precisamente escolher os métodos corretos para cada bloco do mapa do jogo, pois existem uns com comportamentos diferentes. Existem tiles indestrutíveis e outras tiles que se destroem caso hajam colisões entre elas e as balas do herói. 

##### Observer Pattern
Este padrão é usado pelo Screen do LibGDX. A ApplicationListener (aplicação de diferentes módulos/plataformas) é o sujeito e qualquer ecrã (Screen) que se cria é o observador. Então, o Screen é a interface usada para comunicar entre elas.

---

#### GUI Design

##### Menu Design
![alt text](https://cloud.githubusercontent.com/assets/25725586/25557553/df869c0e-2d0b-11e7-9da2-fa293d4683b5.png)

##### Game Area Design
![alt text](https://cloud.githubusercontent.com/assets/25725586/25557554/e2e05944-2d0b-11e7-8748-278fa951b97f.png)

##### GameOver Design
![alt text](https://cloud.githubusercontent.com/assets/25725586/25557551/dce4fd42-2d0b-11e7-9a6a-b038913a3d55.png)

##### Score Screen Design
![alt text](https://cloud.githubusercontent.com/assets/25725586/25557555/e2f72854-2d0b-11e7-9f52-fb7dd9f036b2.png)

--- 

#### Test Cases
* Cada vez que matar um inimigo, aumentar o score.
* Cada vez que matar um inimigo, ele é iliminado na estrutura de dados.
* Cada vez que é tocado por um inimigo, o heroi perde 1 ponto de vida.
* Cada vez que o heroi toca o inimigo por cima, este destroi o inimigo.
* Cada vez que o heroi toca o inimigo não por cima, este perde um ponto de vida.
* Cada vez que o heroi dispara e destroi um bloco, este bloco desaparece da estrutura de dados.
* Verificar que o heroi nao passa pelas paredes.
* Cada vez que o disparo do heroi toca num inimigo, este destroi-se.
* Verificar se o nível é jogável.
* Verificar se o tipo de inimigo que segue o herói realmente o faz.
* Verificar que por cada inimigo morto, o número de mortes aumenta. 
* Verificar que, quando o heroi chega a 0 pontos de vida, que passa a estado de GameOver.

