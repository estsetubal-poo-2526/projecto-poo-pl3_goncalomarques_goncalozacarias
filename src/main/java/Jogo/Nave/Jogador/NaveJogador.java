package Jogo.Nave.Jogador;

import Jogo.Limite;
import Jogo.Melhoria.Melhoria;
import Jogo.Nave.Nave;
import Jogo.Objetos.Projetil;

public class NaveJogador extends Nave {
    private Limite areaLimite;

    public NaveJogador(double posX, double posY, double largura, double altura, double velocidade,
                       Limite areaLimite, int vidas, int dano) {
        super(posX, posY, largura, altura, velocidade, vidas, dano);
        this.areaLimite = areaLimite;
    }

    public Projetil disparar() {
        return new Projetil(
                this,
                getDano(),
                getPosX() + getLargura() / 2,
                getPosY(), 5, 10, 5, 0, -5
        );
    }

    public void mover(int dx, int dy) {
        double novoX = getPosX() + dx * getVelocidade();
        double novoY = getPosY() + dy * getVelocidade();

        if (novoX >= areaLimite.getXMin() && novoX + getLargura() <= areaLimite.getXMax()) {
            setPosX(novoX);
        }

        if (novoY >= areaLimite.getYMin() && novoY + getAltura() <= areaLimite.getYMax()) {
            setPosY(novoY);
        }
    }

    public void sofrerDano(int dano) {
        perderVida(dano);
    }

    public void adicionarMelhoria(Melhoria melhoria) {
        melhoria.aplicar();
    }
}