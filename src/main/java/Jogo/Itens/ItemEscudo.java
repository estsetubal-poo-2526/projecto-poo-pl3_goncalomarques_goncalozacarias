package Jogo.Itens;

import Jogo.Nave.Jogador.NaveJogador;

public class ItemEscudo extends Item {

    public ItemEscudo(double posX, double posY, double largura, double altura, double velocidade,
                      double velocidadeX, double velocidadeY) {
        super(posX, posY, largura, altura, velocidade, velocidadeX, velocidadeY);
    }

    @Override
    public void aplicarEfeito(NaveJogador jogador) {
        jogador.ativarEscudo();
    }
}