package Jogo.Itens;

import Jogo.Objetos.ObjetoMovel;
import Jogo.Nave.Jogador.NaveJogador;

public abstract class Item extends ObjetoMovel {
    public Item(double velocidadeX, double velocidadeY) {
        super(velocidadeX, velocidadeY);
    }
    public abstract NaveJogador aplicarEfeito();
}