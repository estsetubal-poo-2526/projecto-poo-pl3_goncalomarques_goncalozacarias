package Jogo.Melhoria;

import Jogo.Nave.Jogador.NaveJogador;

public abstract class Melhoria {
    private String name;
    private int custo;
    private int nivelAtual;
    public abstract NaveJogador aplicar();
}