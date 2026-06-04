package Jogo.Melhoria;

import Jogo.Nave.Jogador.NaveJogador;

public class MelhoriaDano extends Melhoria {

    public MelhoriaDano(String name, int custo, int nivelAtual) {
        super(name, custo, nivelAtual);
    }

    @Override
    public NaveJogador aplicar() {
        return null;
    }
}