package Jogo;

import Jogo.Itens.Item;
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
    private List<Item> itens;

    public Jogo(NaveJogador jogador) {
        this.state = EstadoJogo.MENU;
        this.pontuacao = 0;
        this.nivelDificuldade = 1;
        this.tempoDecorrido = 0;
        this.jogador = jogador;
        this.inimigos = new ArrayList<>();
        this.projeteis = new ArrayList<>();
        this.itens = new ArrayList<>();

        this.inimigos.addAll(gerarInimigos());
    }

    public void atualizar() {
        if (state == EstadoJogo.FIM_DE_JOGO) {
            return;
        }

        tempoDecorrido++;

        for (Inimigo inimigo : inimigos) {
            inimigo.atualizar();
        }

        for (Projetil projetil : projeteis) {
            projetil.atualizar();
        }

        for (Item item : itens) {
            item.atualizar();
        }

        projeteis.removeIf(projetil ->
                projetil.getPosY() + projetil.getAltura() < 0
        );

        verificarColisoes();
        verificarFimDeJogo();
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

        for (int i = itens.size() - 1; i >= 0; i--) {
            Item item = itens.get(i);

            if (jogador.colideCom(item)) {
                item.aplicarEfeito(jogador);
                itens.remove(i);
            }
        }

        for (int i = inimigos.size() - 1; i >= 0; i--) {
            Inimigo inimigo = inimigos.get(i);

            if (jogador.colideCom(inimigo)) {
                jogador.sofrerDano(inimigo.getDano());
                inimigos.remove(i);
            }
        }
    }

    public void verificarFimDeJogo() {
        if (jogador.getVidas() <= 0) {
            state = EstadoJogo.FIM_DE_JOGO;
        }
    }

    public void iniciarJogo() {
        state = EstadoJogo.EM_JOGO;
    }

    public void abrirMelhorias() {
        state = EstadoJogo.MELHORIAS;
    }

    public void voltarAoMenu() {
        state = EstadoJogo.MENU;
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

    public List<Item> getItens() {
        return itens;
    }
}