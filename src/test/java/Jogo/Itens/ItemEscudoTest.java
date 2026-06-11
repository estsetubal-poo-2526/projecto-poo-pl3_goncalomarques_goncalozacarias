package Jogo.Itens;

import Jogo.Limite;
import Jogo.Nave.Jogador.NaveJogador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemEscudoTest {

    @Test
    void aplicarEfeitoAtivaEscudoDoJogador() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                100, 100, 40, 40, 5,
                limite, 3, 1
        );

        ItemEscudo itemEscudo = new ItemEscudo(
                200, 200, 30, 30, 1,
                0, 1
        );

        itemEscudo.aplicarEfeito(jogador);

        assertTrue(jogador.isEscudoAtivo());
    }
}
