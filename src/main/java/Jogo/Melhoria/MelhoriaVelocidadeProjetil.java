package Jogo.Melhoria;

import Jogo.Nave.Jogador.NaveJogador;

public class MelhoriaVelocidadeProjetil extends Melhoria {

    public MelhoriaVelocidadeProjetil(String name, int custo, int nivelAtual) {
        super(name, custo, nivelAtual);
    }

    @Override
    public void aplicar(NaveJogador jogador) {
        jogador.aumentarVelocidadeProjetil(1.5);
        jogador.reduzirCooldownDisparo(50_000_000);
        setNivelAtual(getNivelAtual() + 1);
    }
}
