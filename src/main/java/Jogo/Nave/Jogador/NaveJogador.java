package Jogo.Nave.Jogador;

import Jogo.Limite;
import Jogo.Melhoria.Melhoria;
import Jogo.Nave.Nave;

public class NaveJogador extends Nave {
    private Limite areaLimite;

    public NaveJogador(Limite areaLimite, int vidas, int dano) {
        super(vidas, dano);
        this.areaLimite = areaLimite;
    }

    public void disparar() {

    }

    public void mover(int dx, int dy) {

    }

    public void sofrerDano(int dano) {

    }

    public void adicionarMelhoria(Melhoria melhoria) {
        melhoria.aplicar();
    }
}