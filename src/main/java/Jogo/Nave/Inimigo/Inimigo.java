package Jogo.Nave.Inimigo;


import Jogo.Itens.Item;
import Jogo.Nave.Nave;



public abstract class Inimigo extends Nave {
    private int valorPontos;

    public Inimigo(int vidas, int dano, int valorPontos) {
        super(vidas, dano);
        this.valorPontos = valorPontos;
    }

    public Item largarItem(){
        return null;
    }
}
