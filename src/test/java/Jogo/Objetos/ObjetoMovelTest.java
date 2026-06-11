package Jogo.Objetos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjetoMovelTest {

    @Test
    void moverAlteraPosicaoComVelocidadeXEVelocidadeY() {
        ObjetoMovel objeto = new ObjetoMovel(
                10, 20, 30, 30, 1,
                2, 3
        ) {};

        objeto.mover();

        assertEquals(12.0, objeto.getPosX(), 0.001);
        assertEquals(23.0, objeto.getPosY(), 0.001);
    }

    @Test
    void settersAlteramVelocidadeXEVelocidadeY() {
        ObjetoMovel objeto = new ObjetoMovel(
                10, 20, 30, 30, 1,
                2, 3
        ) {};

        objeto.setVelocidadeX(5);
        objeto.setVelocidadeY(-2);

        assertEquals(5.0, objeto.getVelocidadeX(), 0.001);
        assertEquals(-2.0, objeto.getVelocidadeY(), 0.001);
    }
}