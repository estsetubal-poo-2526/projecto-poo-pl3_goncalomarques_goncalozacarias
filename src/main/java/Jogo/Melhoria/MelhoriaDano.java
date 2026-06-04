package Jogo.Melhoria;

import Jogo.Nave.Jogador.NaveJogador;

public class MelhoriaDano extends Melhoria {

    public MelhoriaDano(String name, int custo, int nivelAtual) {
        super(name, custo, nivelAtual);
    }

    @Override
    public void aplicar(NaveJogador jogador) {
        jogador.setDano(jogador.getDano() + 1);
        setNivelAtual(getNivelAtual() + 1);
    }
}