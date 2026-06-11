package Jogo.Objetos;

import Jogo.Limite;
import Jogo.Nave.Jogador.NaveJogador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjetilTest {

    @Test
    void atualizarMoveProjetilDeAcordoComVelocidade() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                100, 100, 40, 40, 5,
                limite, 3, 1
        );

        Projetil projetil = new Projetil(
                jogador, 1,
                50, 50, 5, 10,
                5, 0, -5
        );

        projetil.atualizar();

        assertEquals(50.0, projetil.getPosX(), 0.001);
        assertEquals(45.0, projetil.getPosY(), 0.001);
    }

    @Test
    void getDonoEGetDanoDevolvemValoresCorretos() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                100, 100, 40, 40, 5,
                limite, 3, 2
        );

        Projetil projetil = new Projetil(
                jogador, 2,
                50, 50, 5, 10,
                5, 0, -5
        );

        assertEquals(jogador, projetil.getDono());
        assertEquals(2, projetil.getDano());
    }
}