# Trabalho POO

Quem nunca brincou com um quebra-cabeça de mão? Esse jogo é aquele em que há
15 peças quadradas em um tabuleiro quadrado com 16 quadrados pequenos, estando
um destes sempre vazio, embora em local variável. As peças são dispostas
aleatoriamente e o objetivo é ordená-las.

Se supusermos que, na posição inicial, o quadrado vazio está localizado no
canto inferior direito, há 15! (= 1.307.674.368.000) possibilidades diferentes
para as posições de partida (iniciais) das 15 peças. Destas, só para metade
delas é possível resolver o problema: essas posições são aquelas que
correspondem às permutações pares da ordenação pretendida. O quebra-cabeça,
deve por padrão oferecer sempre uma posição de partida par, que permite
concluir o jogo, mas deve ser possível escolher a opção "embaralhar ímpar",
para qual se obtém posições de partida em que é impossível resolver o jogo.

O tempo que um jogador leva para arrumar uma solução deve ser a base para sua
pontuação:
- se tempo<1 minuto pontos=1000,
- se tempo<10 minutos pontos=100,
- se tempo<100 minutos pontos=10
Considerar as frações de tempo:
- Até 1 minuto cada segundo vale 50/3 pontos,
- Até 10 minutos cada segundo vale 6 pontos.
- Até 100 minutos cada segundo vale 1/600 pontos

Cada Jogador deve ser cadastrado(apelido e código que tem que ser único) antes
de começar uma Partida que é disputada por no máximo três Jogadores. Ganha
quem conseguir resolver o jogo com maior pontuação. Ao final o jogo deve
exibir um ranking histórico com o tempo, a pontuação de cada jogador que já
jogou esse jogo. Deve existir uma opção limpar histórico que apaga toda lista.

Um jogo pode parar/pausar e ser reiniciado/continuado a qualquer momento. Um
jogador pode salvar o jogo, fechar o programa e depois jogar novamente a mesma
partida. Uma opção de resolver automaticamente se implementada garante pontos
extras para equipe. Deve ser possível escolher imagens diferentes para o Jogo
(ver figura abaixo).

## O Trabalho

Construir um jogo de Quebra-Cabeça de mão informatizado utilizando somente os recursos da linguagem Java.

## Nossas Regras

- Os alunos devem se reunir em equipes de no máximo 3(três) pessoas para realizar o trabalho.

- O trabalho deverá ser apresentado em sala de aula no formato digital.

- Na apresentação o professor realizará uma argüição sobre trabalho para cada componente da equipe.

- A nota do trabalho é individual para cada componente.

- Trabalhos copiados da INTERNET ou de qualquer outra fonte e trabalhos iguais terão nota ZERO e os alunos serão levados ao núcleo pedagógico para que o mesmo tome as medidas cabíveis.(existe uma versão em Java desse jogo no site http://www.atractor.pt/mat/puzzle-15/index.html 

- A apresentação do programa rodando no laboratório é de responsabilidade da equipe.

## Avaliação

O trabalho tem valor 10,0(DEZ). Os quesitos de avaliação são:

1. Organização(Peso 3)

2. Criatividade(Peso 5)

3. Interface(Peso 2)

4. Penalidades(Descontos avaliados pelo professor)

Só serão avaliados os trabalhos que forem apresentados em sala de aula no prazo estabelecido e que atendam aos requisitos estabelecidos.

Entrega 09/01/
