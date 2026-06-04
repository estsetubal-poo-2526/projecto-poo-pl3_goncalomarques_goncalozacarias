package Jogo.Nave.Inimigo;

import Jogo.Itens.Item;
import Jogo.Nave.Nave;

public abstract class Inimigo extends Nave {
    private int valorPontos;

    public Inimigo(double posX, double posY, double largura, double altura, double velocidade,
                   int vidas, int dano, int valorPontos) {
        super(posX, posY, largura, altura, velocidade, vidas, dano);
        this.valorPontos = valorPontos;
    }

    public int getValorPontos() {
        return valorPontos;
    }

    public void setValorPontos(int valorPontos) {
        this.valorPontos = valorPontos;
    }

    public Item largarItem() {
        return null;
    }
}