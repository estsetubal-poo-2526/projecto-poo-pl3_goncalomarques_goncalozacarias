package Jogo.Nave.Jogador;

import Jogo.Limite;
import Jogo.Melhoria.Melhoria;
import Jogo.Nave.Nave;
import Jogo.Objetos.Projetil;

public class NaveJogador extends Nave {
    private Limite areaLimite;
    private boolean escudoAtivo;
    private long ultimoDisparo;
    private long cooldownDisparo;
    private double velocidadeProjetil;

    public NaveJogador(double posX, double posY, double largura, double altura, double velocidade,
                       Limite areaLimite, int vidas, int dano) {
        super(posX, posY, largura, altura, velocidade, vidas, dano);
        this.areaLimite = areaLimite;
        this.escudoAtivo = false;
        this.ultimoDisparo = 0;
        this.cooldownDisparo = 250_000_000;
        this.velocidadeProjetil = 8;
    }

    public Projetil disparar() {
        long agora = System.nanoTime();
        if (agora - ultimoDisparo < cooldownDisparo) return null;
        ultimoDisparo = agora;

        return new Projetil(
                this,
                getDano(),
                getPosX() + getLargura() / 2,
                getPosY(), 5, 10, velocidadeProjetil, 0, -velocidadeProjetil
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

    public Limite getAreaLimite() {
        return areaLimite;
    }

    public void adicionarMelhoria(Melhoria melhoria) {
        melhoria.aplicar(this);
    }

    public void reduzirCooldownDisparo(long reducao) {
        cooldownDisparo = Math.max(90_000_000, cooldownDisparo - reducao);
    }

    public void aumentarVelocidadeProjetil(double quantidade) {
        velocidadeProjetil += quantidade;
    }
}
