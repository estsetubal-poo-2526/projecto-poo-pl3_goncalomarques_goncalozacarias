package Jogo.Nave.Jogador;

import Jogo.Limite;
import Jogo.Melhoria.MelhoriaDano;
import Jogo.Objetos.Projetil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NaveJogadorTest {

    @Test
    void moverDentroDosLimitesAlteraPosicao() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                100, 100, 40, 40, 5,
                limite, 3, 1
        );

        jogador.mover(1, 0);

        assertEquals(105.0, jogador.getPosX(), 0.001);
        assertEquals(100.0, jogador.getPosY(), 0.001);
    }

    @Test
    void moverForaDosLimitesNaoAlteraPosicao() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                0, 100, 40, 40, 5,
                limite, 3, 1
        );

        jogador.mover(-1, 0);

        assertEquals(0.0, jogador.getPosX(), 0.001);
        assertEquals(100.0, jogador.getPosY(), 0.001);
    }

    @Test
    void sofrerDanoSemEscudoReduzVida() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                100, 100, 40, 40, 5,
                limite, 3, 1
        );

        jogador.sofrerDano(1);

        assertEquals(2, jogador.getVidas());
    }

    @Test
    void sofrerDanoComEscudoNaoReduzVidaEDesativaEscudo() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                100, 100, 40, 40, 5,
                limite, 3, 1
        );

        jogador.ativarEscudo();
        jogador.sofrerDano(1);

        assertEquals(3, jogador.getVidas());
        assertFalse(jogador.isEscudoAtivo());
    }

    @Test
    void dispararCriaProjetilComJogadorComoDono() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                100, 100, 40, 40, 5,
                limite, 3, 2
        );

        Projetil projetil = jogador.disparar();

        assertEquals(jogador, projetil.getDono());
        assertEquals(2, projetil.getDano());
    }

    @Test
    void adicionarMelhoriaAplicaMelhoriaAoJogador() {
        Limite limite = new Limite(0, 800, 0, 600);

        NaveJogador jogador = new NaveJogador(
                100, 100, 40, 40, 5,
                limite, 3, 1
        );

        MelhoriaDano melhoria = new MelhoriaDano("Dano", 100, 0);

        jogador.adicionarMelhoria(melhoria);

        assertEquals(2, jogador.getDano());
        assertEquals(1, melhoria.getNivelAtual());
    }
}
