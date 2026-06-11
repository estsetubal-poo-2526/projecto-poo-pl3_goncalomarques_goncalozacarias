package Jogo.Melhoria;

import Jogo.Limite;
import Jogo.Nave.Jogador.NaveJogador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MelhoriaVelocidadeNaveTest {

    @Test
    void aplicarAumentaVelocidadeDoJogadorENivelDaMelhoria() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                100, 100, 40, 40, 5,
                limite, 3, 1
        );

        MelhoriaVelocidadeNave melhoria = new MelhoriaVelocidadeNave(
                "Velocidade Nave", 100, 0
        );

        melhoria.aplicar(jogador);

        assertEquals(6.0, jogador.getVelocidade(), 0.001);
        assertEquals(1, melhoria.getNivelAtual());
    }
}