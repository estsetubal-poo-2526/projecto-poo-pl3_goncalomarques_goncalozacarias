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

    public Jogo() {
        this.state           = EstadoJogo.EM_JOGO;
        this.pontuacao       = 0;
        this.nivelDificuldade = 1.0;
        this.inimigos        = new ArrayList<>();
        this.projeteis       = new ArrayList<>();

        // Limite da área de jogo = tamanho da janela
        Limite limite = new Limite(0, 800, 0, 900);
        this.jogador = new NaveJogador(limite, 3, 10);
    }

    // ── Lógica principal (a completar pelo grupo) ──────────────────────────

    public void atualizar() {
        // TODO: mover inimigos, mover projéteis, verificar colisões, etc.
    }

    public void verificarColisoes() {
        // TODO
    }

    public List<Inimigo> gerarInimigos() {
        return new ArrayList<>();
    }

    public void aumentarDificuldade() {
        this.nivelDificuldade++;
    }

    // ── Getters necessários para a UI ──────────────────────────────────────

    public EstadoJogo getState() {
        return state;
    }

    public void setState(EstadoJogo state) {
        this.state = state;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public double getNivelDificuldade() {
        return nivelDificuldade;
    }

    public void setNivelDificuldade(double nivelDificuldade) {
        this.nivelDificuldade = nivelDificuldade;
    }

    public NaveJogador getJogador() {
        return jogador;
    }

    public void setJogador(NaveJogador jogador) {
        this.jogador = jogador;
    }

    public List<Inimigo> getInimigos() {
        return inimigos;
    }

    public void setInimigos(List<Inimigo> inimigos) {
        this.inimigos = inimigos;
    }

    public List<Projetil> getProjeteis() {
        return projeteis;
    }

    public void setProjeteis(List<Projetil> projeteis) {
        this.projeteis = projeteis;
    }
}