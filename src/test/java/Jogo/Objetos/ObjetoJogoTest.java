package Jogo.Objetos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoJogoTest {

    @Test
    void objetosSobrepostosColidem() {
        ObjetoJogo objeto1 = new ObjetoJogo(0, 0, 50, 50, 0) {};
        ObjetoJogo objeto2 = new ObjetoJogo(25, 25, 50, 50, 0) {};

        assertTrue(objeto1.colideCom(objeto2));
    }

    @Test
    void objetosSeparadosNaoColidem() {
        ObjetoJogo objeto1 = new ObjetoJogo(0, 0, 50, 50, 0) {};
        ObjetoJogo objeto2 = new ObjetoJogo(100, 100, 50, 50, 0) {};

        assertFalse(objeto1.colideCom(objeto2));
    }

    @Test
    void settersAlteramValoresDoObjeto() {
        ObjetoJogo objeto = new ObjetoJogo(0, 0, 10, 10, 1) {};

        objeto.setPosX(20);
        objeto.setPosY(30);
        objeto.setLargura(40);
        objeto.setAltura(50);
        objeto.setVelocidade(5);

        assertEquals(20.0, objeto.getPosX(), 0.001);
        assertEquals(30.0, objeto.getPosY(), 0.001);
        assertEquals(40.0, objeto.getLargura(), 0.001);
        assertEquals(50.0, objeto.getAltura(), 0.001);
        assertEquals(5.0, objeto.getVelocidade(), 0.001);
    }
}
