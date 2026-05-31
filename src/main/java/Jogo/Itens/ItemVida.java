package Jogo.Itens;

import Jogo.Nave.Jogador.NaveJogador;

public class ItemVida extends Item {
    public ItemVida(double velocidadeX, double velocidadeY) {
        super(velocidadeX, velocidadeY);
    }
    @Override
    public NaveJogador aplicarEfeito() {
        return null;
    }
}