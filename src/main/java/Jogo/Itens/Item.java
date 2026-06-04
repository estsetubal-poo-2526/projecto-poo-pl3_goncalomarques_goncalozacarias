package Jogo.Itens;

import Jogo.Nave.Jogador.NaveJogador;
import Jogo.Objetos.ObjetoMovel;

public abstract class Item extends ObjetoMovel {

    public Item(double posX, double posY, double largura, double altura, double velocidade,
                double velocidadeX, double velocidadeY) {
        super(posX, posY, largura, altura, velocidade, velocidadeX, velocidadeY);
    }

    public abstract NaveJogador aplicarEfeito();
}