package Jogo;

import Jogo.Nave.Inimigo.Inimigo;
import Jogo.Nave.Inimigo.InimigoSimples;
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

        this.inimigos.addAll(gerarInimigos());
    }

    public void atualizar() {
        tempoDecorrido++;

        for (Inimigo inimigo : inimigos) {
            inimigo.atualizar();
        }

        for (Projetil projetil : projeteis) {
            projetil.atualizar();
        }

        projeteis.removeIf(projetil ->
                projetil.getPosY() + projetil.getAltura() < 0
        );

        verificarColisoes();
    }

    public void verificarColisoes() {
        for (int i = projeteis.size() - 1; i >= 0; i--) {
            Projetil projetil = projeteis.get(i);

            for (int j = inimigos.size() - 1; j >= 0; j--) {
                Inimigo inimigo = inimigos.get(j);

                if (projetil.colideCom(inimigo)) {
                    inimigo.perderVida(projetil.getDano());
                    projeteis.remove(i);

                    if (inimigo.getVidas() <= 0) {
                        pontuacao += inimigo.getValorPontos();
                        inimigos.remove(j);
                    }

                    break;
                }
            }
        }
    }

    public void jogadorDisparar() {
        Projetil projetil = jogador.disparar();
        projeteis.add(projetil);
    }

    public List<Inimigo> gerarInimigos() {
        List<Inimigo> novosInimigos = new ArrayList<>();

        novosInimigos.add(new InimigoSimples(
                100, 50, 40, 40, 2,
                3, 1, 100
        ));

        return novosInimigos;
    }

    public void aumentarDificuldade() {
        this.nivelDificuldade++;
    }

    public EstadoJogo getState() {
        return state;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public double getNivelDificuldade() {
        return nivelDificuldade;
    }

    public double getTempoDecorrido() {
        return tempoDecorrido;
    }

    public NaveJogador getJogador() {
        return jogador;
    }

    public List<Inimigo> getInimigos() {
        return inimigos;
    }

    public List<Projetil> getProjeteis() {
        return projeteis;
    }
}