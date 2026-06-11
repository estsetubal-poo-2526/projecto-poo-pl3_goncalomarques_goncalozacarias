package Jogo.Itens;

import Jogo.Limite;
import Jogo.Nave.Jogador.NaveJogador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemVidaTest {

    @Test
    void aplicarEfeitoAumentaVidaDoJogador() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                100, 100, 40, 40, 5,
                limite, 3, 1
        );

        ItemVida itemVida = new ItemVida(
                200, 200, 30, 30, 1,
                0, 1
        );

        itemVida.aplicarEfeito(jogador);

        assertEquals(4, jogador.getVidas());
    }
}