package Jogo.Itens;

import Jogo.Nave.Jogador.NaveJogador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    @Test
    void atualizarMoveItemDeAcordoComVelocidade() {
        Item item = new Item(10, 20, 30, 30, 1, 2, 3) {
            @Override
            public void aplicarEfeito(NaveJogador jogador) {
                // Não será necessário para este teste.
            }
        };

        item.atualizar();

        assertEquals(12.0, item.getPosX(), 0.001);
        assertEquals(23.0, item.getPosY(), 0.001);
    }
}