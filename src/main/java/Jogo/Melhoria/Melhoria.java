package Jogo.Melhoria;

import Jogo.Nave.Jogador.NaveJogador;

public abstract class Melhoria {
    private String name;
    private int custo;
    private int nivelAtual;

    public Melhoria(String name, int custo, int nivelAtual) {
        this.name = name;
        this.custo = custo;
        this.nivelAtual = nivelAtual;
    }

    public String getName() {
        return name;
    }

    public int getCusto() {
        return custo;
    }

    public int getNivelAtual() {
        return nivelAtual;
    }

    public void setNivelAtual(int nivelAtual) {
        this.nivelAtual = nivelAtual;
    }

    public abstract void aplicar(NaveJogador jogador);
}