# Space Wars

Projeto de POO desenvolvido em Java com JavaFX — um jogo de naves estilo *shoot 'em up* vertical, com sistema de ondas, inimigos variados, melhorias, itens e loja entre ondas.

## Tecnologias

- Java 17
- JavaFX
- Maven
- JUnit 5 (testes)

## Estrutura do Projeto

```
src/resources/            # Sprites (naves, inimigos, itens)
src/test/java/            # Testes unitários (JUnit 5)
```

## Como Executar
App.java a partir do IntelliJ IDEA, garantindo que o JavaFX está configurado nas VM options/módulos do projeto.

### Requisitos

- JDK 17+
- Maven
- SDK do JavaFX configurado


## Como Jogar

| Tecla | Ação |
|---|---|
| `W` / `Seta para cima` | Mover para cima |
| `S` / `Seta para baixo` | Mover para baixo |
| `A` / `Seta para a esquerda` | Mover para a esquerda |
| `D` / `Seta para a direita` | Mover para a direita |
| `Espaço` / Clique do rato | Disparar |
| `Esc` | Pausar / Continuar |

## Funcionalidades

- Sistema de ondas com dificuldade progressiva
- Vários tipos de inimigos: simples, rápido, forte e boss
- Itens recolhíveis: vida e escudo
- Loja de melhorias entre ondas (velocidade da nave, dano, velocidade de tiro)
- Sistema de pontuação e moedas
- Música de fundo durante a batalha
- Dois níveis de dificuldade: Fácil e Difícil

## Autores

- Gonçalo Marques
- Gonçalo Zacarias