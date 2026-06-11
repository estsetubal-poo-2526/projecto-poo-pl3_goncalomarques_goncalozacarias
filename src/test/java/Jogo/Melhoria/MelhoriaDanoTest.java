package Jogo.Melhoria;

import Jogo.Limite;
import Jogo.Nave.Jogador.NaveJogador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MelhoriaDanoTest {

    @Test
    void aplicarAumentaDanoDoJogadorENivelDaMelhoria() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                100, 100, 40, 40, 5,
                limite, 3, 1
        );

        MelhoriaDano melhoria = new MelhoriaDano(
                "Dano", 100, 0
        );

        melhoria.aplicar(jogador);

        assertEquals(2, jogador.getDano());
        assertEquals(1, melhoria.getNivelAtual());
    }
}
