package Jogo.Nave;

import Jogo.Objetos.ObjetoJogo;

public abstract class Nave extends ObjetoJogo {
    private int vidas;
    private int dano;
    public Nave(int vidas, int dano){
        this.vidas = vidas;
        this.dano = dano;
    }
}
