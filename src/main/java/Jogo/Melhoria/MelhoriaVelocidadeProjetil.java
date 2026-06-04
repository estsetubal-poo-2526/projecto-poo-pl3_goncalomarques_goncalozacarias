package Jogo.Melhoria;

import Jogo.Nave.Jogador.NaveJogador;

public class MelhoriaVelocidadeProjetil extends Melhoria {

    public MelhoriaVelocidadeProjetil(String name, int custo, int nivelAtual) {
        super(name, custo, nivelAtual);
    }

    @Override
    public void aplicar(NaveJogador jogador) {
        setNivelAtual(getNivelAtual() + 1);
    }
}