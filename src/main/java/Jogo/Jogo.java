package Jogo;

import Jogo.Nave.Inimigo.Inimigo;
import Jogo.Nave.Jogador.NaveJogador;
import Jogo.Objetos.Projetil;

import java.util.*;
public class Jogo{
    private EstadoJogo state;
    private int pontuacao;
    private double NivelDificuldade;
    private double tempoDecorrido;
    private NaveJogador jogador;
    private List<Inimigo> inimigos;
    private List<Projetil> projeteis;
    public void atualizar(){

    }
    public void verificarColisoes(){

    }

    public List<Inimigo> gerarInimigos() {
        return new ArrayList<>();

    }
    private double nivelDificuldade;

    public void aumentarDificuldade() {
        this.nivelDificuldade++;
    }
}