package Jogo.Objetos;

import Jogo.Nave.Nave;


public class Projetil extends ObjetoMovel{
    private Nave dono;
    private int dano;
    public Projetil(Nave dono, int dano, double velocidadeX, double velocidadeY){
        super(velocidadeX, velocidadeY);
        this.dono = dono;
        this.dano = dano;
    }
    @Override
    public void atualizar(){

    }
}
