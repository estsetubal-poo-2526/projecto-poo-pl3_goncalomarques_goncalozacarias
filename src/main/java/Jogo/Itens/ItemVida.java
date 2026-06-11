package Jogo.Itens;

import Jogo.Nave.Jogador.NaveJogador;

public class ItemVida extends Item {

    public ItemVida(double posX, double posY, double largura, double altura, double velocidade,
                    double velocidadeX, double velocidadeY) {
        super(posX, posY, largura, altura, velocidade, velocidadeX, velocidadeY);
    }

    @Override
    public void aplicarEfeito(NaveJogador jogador) {
        jogador.aumentarVida(1);
        jogador.ativarEscudo();
    }
}
