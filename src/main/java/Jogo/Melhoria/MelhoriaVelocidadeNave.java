package Jogo.Melhoria;

import Jogo.Nave.Jogador.NaveJogador;

public class MelhoriaVelocidadeNave extends Melhoria {

    public MelhoriaVelocidadeNave(String name, int custo, int nivelAtual) {
        super(name, custo, nivelAtual);
    }

    @Override
    public NaveJogador aplicar() {
        return null;
    }
}