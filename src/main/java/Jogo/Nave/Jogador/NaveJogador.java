package Jogo.Nave.Jogador;

import Jogo.Limite;
import Jogo.Melhoria.Melhoria;
import Jogo.Nave.Nave;

public class NaveJogador extends Nave {
    private Limite areaLimite;

    public NaveJogador(double posX, double posY, double largura, double altura, double velocidade,
                       Limite areaLimite, int vidas, int dano) {
        super(posX, posY, largura, altura, velocidade, vidas, dano);
        this.areaLimite = areaLimite;
    }

    public void disparar() {

    }

    public void mover(int dx, int dy) {

    }

    public void sofrerDano(int dano) {
        perderVida(dano);
    }

    public void adicionarMelhoria(Melhoria melhoria) {
        melhoria.aplicar();
    }
}