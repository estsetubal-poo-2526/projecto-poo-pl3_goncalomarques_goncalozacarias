package Jogo.Nave.Jogador;

import Jogo.Limite;
import Jogo.Melhoria.Melhoria;
import Jogo.Nave.Nave;
import Jogo.Objetos.Projetil;

public class NaveJogador extends Nave {
    private Limite areaLimite;
    private boolean escudoAtivo;

    public NaveJogador(double posX, double posY, double largura, double altura, double velocidade,
                       Limite areaLimite, int vidas, int dano) {
        super(posX, posY, largura, altura, velocidade, vidas, dano);
        this.areaLimite = areaLimite;
        this.escudoAtivo = false;
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
        if (escudoAtivo) {
            escudoAtivo = false;
        } else {
            perderVida(dano);
        }
    }

    public void ativarEscudo() {
        this.escudoAtivo = true;
    }

    public boolean isEscudoAtivo() {
        return escudoAtivo;
    }

    public void adicionarMelhoria(Melhoria melhoria) {
        melhoria.aplicar(this);
    }
}