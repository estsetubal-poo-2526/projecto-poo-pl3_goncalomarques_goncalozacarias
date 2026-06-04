package Jogo;

import Jogo.Nave.Inimigo.Inimigo;
import Jogo.Nave.Jogador.NaveJogador;
import Jogo.Objetos.Projetil;

import java.util.ArrayList;
import java.util.List;

public class Jogo {
    private EstadoJogo state;
    private int pontuacao;
    private double nivelDificuldade;
    private double tempoDecorrido;
    private NaveJogador jogador;
    private List<Inimigo> inimigos;
    private List<Projetil> projeteis;

    public Jogo(NaveJogador jogador) {
        this.state = EstadoJogo.MENU;
        this.pontuacao = 0;
        this.nivelDificuldade = 1;
        this.tempoDecorrido = 0;
        this.jogador = jogador;
        this.inimigos = new ArrayList<>();
        this.projeteis = new ArrayList<>();
    }

    public void atualizar() {

    }

    public void verificarColisoes() {

    }

    public List<Inimigo> gerarInimigos() {
        return new ArrayList<>();
    }

    public void aumentarDificuldade() {
        this.nivelDificuldade++;
    }
}