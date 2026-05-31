package Jogo.Itens;

import Jogo.Nave.Jogador.NaveJogador;
public class ItemEscudo extends Item {
    public ItemEscudo(double velocidadeX, double velocidadeY) {
        super(velocidadeX, velocidadeY);
    }
    @Override
    public NaveJogador aplicarEfeito() {
        return null;
    }
}