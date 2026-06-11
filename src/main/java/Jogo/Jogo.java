package Jogo;

import Jogo.Itens.Item;
import Jogo.Itens.ItemEscudo;
import Jogo.Itens.ItemVida;
import Jogo.Nave.Inimigo.Inimigo;
import Jogo.Nave.Inimigo.InimigoBoss;
import Jogo.Nave.Inimigo.InimigoForte;
import Jogo.Nave.Inimigo.InimigoRapido;
import Jogo.Nave.Inimigo.InimigoSimples;
import Jogo.Nave.Jogador.NaveJogador;
import Jogo.Objetos.Projetil;
import Jogo.Melhoria.Melhoria;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogo {
    private EstadoJogo state;
    private int pontuacao;
    private double nivelDificuldade;
    private double tempoDecorrido;
    private String dificuldade;
    private int moedas;
    private int onda;
    private static final int NIVEL_MAX_MELHORIA = 5;
    private int nivelMelhoriaVelocidadeNave;
    private int nivelMelhoriaDano;
    private int nivelMelhoriaVelocidadeTiro;
    private NaveJogador jogador;
    private List<Inimigo> inimigos;
    private List<Projetil> projeteis;
    private List<Item> itens;
    private Random random;

    public Jogo(NaveJogador jogador, String dificuldade) {
        this.dificuldade = dificuldade;
        this.state = EstadoJogo.EM_JOGO;
        this.pontuacao = 0;
        this.moedas = 0;
        this.onda = 1;
        this.nivelMelhoriaVelocidadeNave = 0;
        this.nivelMelhoriaDano = 0;
        this.nivelMelhoriaVelocidadeTiro = 0;
        this.nivelDificuldade = 1;
        this.tempoDecorrido = 0;
        this.jogador = jogador;
        this.inimigos = new ArrayList<>();
        this.projeteis = new ArrayList<>();
        this.itens = new ArrayList<>();
        this.random = new Random();
        this.inimigos.addAll(gerarInimigos());
    }

    public List<Inimigo> gerarInimigos() {
        List<Inimigo> novos = new ArrayList<>();
        int quantidadeBase = dificuldade.equals("FACIL") ? 5 : 8;
        int quantidade = Math.min(18, quantidadeBase + onda - 1);
        if (onda % 5 == 0) {
            novos.add(criarBoss());
            quantidade = Math.max(3, quantidade - 3);
        }
        if (dificuldade.equals("FACIL")) {
            for (int i = 0; i < quantidade; i++) {
                novos.add(criarInimigo(calcularXInimigo(i), calcularYInimigo(i), true, escolherTipoInimigo(i)));
            }
        } else {
            for (int i = 0; i < quantidade; i++) {
                novos.add(criarInimigo(calcularXInimigo(i), calcularYInimigo(i), false, escolherTipoInimigo(i)));
            }
        }
        return novos;
    }

    private InimigoBoss criarBoss() {
        int vidasBoss = dificuldade.equals("FACIL") ? 18 + onda * 2 : 28 + onda * 3;
        int danoBoss = dificuldade.equals("FACIL") ? 2 : 3;
        double velocidadeBoss = dificuldade.equals("FACIL") ? 0.45 + onda * 0.04 : 0.75 + onda * 0.05;
        return new InimigoBoss(330, 35, 90, 90, velocidadeBoss, vidasBoss, danoBoss, 500);
    }

    private int escolherTipoInimigo(int indice) {
        if (indice < 3) return indice;
        return random.nextInt(3);
    }

    private double calcularXInimigo(int indice) {
        return 70 + (indice % 7) * 100;
    }

    private double calcularYInimigo(int indice) {
        return 45 + (indice / 7) * 70 + random.nextInt(35);
    }

    private Inimigo criarInimigo(double x, double y, boolean facil, int tipo) {
        double escala = Math.max(0, onda - 1) * 0.15;
        if (facil) {
            if (tipo == 0) return new InimigoSimples(x, y, 40, 40, 0.6 + escala, 2 + onda / 3, 1, 50);
            if (tipo == 1) return new InimigoRapido(x, y, 36, 36, 0.7 + escala, 2 + onda / 4, 1, 70, 1);
            return new InimigoForte(x, y, 52, 52, 0.45 + escala, 3 + onda / 3, 1, 90, 3 + onda / 3);
        }

        if (tipo == 0) return new InimigoSimples(x, y, 40, 40, 1.7 + escala, 3 + onda / 3, 2, 70);
        if (tipo == 1) return new InimigoRapido(x, y, 36, 36, 1.9 + escala, 3 + onda / 3, 2, 100, 2);
        return new InimigoForte(x, y, 54, 54, 1.2 + escala, 5 + onda / 2, 3, 140, 4 + onda / 2);
    }

    public void atualizar() {
        if (state == EstadoJogo.FIM_DE_JOGO) return;
        tempoDecorrido++;
        for (Inimigo inimigo : inimigos) inimigo.atualizar();
        for (Projetil projetil : projeteis) projetil.atualizar();
        for (Item item : itens) item.atualizar();
        projeteis.removeIf(p -> p.getPosY() + p.getAltura() < 0);
        itens.removeIf(item -> item.getPosY() > 900);
        reposicionarInimigosQueEscaparam();
        verificarColisoes();
        verificarFimDeJogo();
    }

    private void reposicionarInimigosQueEscaparam() {
        for (int i = inimigos.size() - 1; i >= 0; i--) {
            Inimigo inimigo = inimigos.get(i);
            if (inimigo.getPosY() > 900) {
                inimigo.setPosY(-inimigo.getAltura() - random.nextInt(120));
                inimigo.setPosX(40 + random.nextInt(720));
            }
        }
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
                        moedas += calcularMoedas(inimigo);
                        tentarLargarItem(inimigo);
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
        if (jogador.getVidas() <= 0) state = EstadoJogo.FIM_DE_JOGO;
        else if (inimigos.isEmpty()) {
            if (onda % 3 == 0) {
                state = EstadoJogo.MELHORIAS;
            } else {
                iniciarProximaOnda();
            }
        }
    }

    private int calcularMoedas(Inimigo inimigo) {
        if (inimigo instanceof InimigoBoss) return 40;
        if (inimigo instanceof InimigoForte) return 12;
        if (inimigo instanceof InimigoRapido) return 10;
        return 8;
    }

    private void tentarLargarItem(Inimigo inimigo) {
        double chance = inimigo instanceof InimigoBoss ? 0.85 : 0.18;
        if (random.nextDouble() > chance) return;

        double x = inimigo.getPosX() + inimigo.getLargura() / 2 - 11;
        double y = inimigo.getPosY() + inimigo.getAltura() / 2;
        if (random.nextBoolean()) {
            itens.add(new ItemVida(x, y, 22, 22, 2, 0, 2));
        } else {
            itens.add(new ItemEscudo(x, y, 22, 22, 2, 0, 2));
        }
    }

    public void jogadorDisparar() {
        Projetil projetil = jogador.disparar();
        if (projetil != null) projeteis.add(projetil);
    }

    public boolean comprarMelhoria(Melhoria melhoria) {
        String nome = melhoria.getName();
        int nivelAtual = getNivelMelhoria(nome);
        if (nivelAtual >= NIVEL_MAX_MELHORIA) return false;
        int custo = getCustoMelhoria(nome);
        if (moedas < custo) return false;
        moedas -= custo;
        jogador.adicionarMelhoria(melhoria);
        incrementarNivelMelhoria(nome);
        return true;
    }

    private void incrementarNivelMelhoria(String nome) {
        if (nome.equals("Velocidade Nave")) nivelMelhoriaVelocidadeNave++;
        else if (nome.equals("Dano")) nivelMelhoriaDano++;
        else if (nome.equals("Velocidade Tiro")) nivelMelhoriaVelocidadeTiro++;
    }

    public int getNivelMelhoria(String nome) {
        if (nome.equals("Velocidade Nave")) return nivelMelhoriaVelocidadeNave;
        if (nome.equals("Dano")) return nivelMelhoriaDano;
        if (nome.equals("Velocidade Tiro")) return nivelMelhoriaVelocidadeTiro;
        return 0;
    }

    public int getCustoMelhoria(String nome) {
        int nivelAtual = getNivelMelhoria(nome);
        if (nome.equals("Velocidade Nave")) return 120 + nivelAtual * 80;
        if (nome.equals("Dano")) return 150 + nivelAtual * 100;
        if (nome.equals("Velocidade Tiro")) return 100 + nivelAtual * 75;
        return 0;
    }

    public boolean melhoriaNoMaximo(String nome) {
        return getNivelMelhoria(nome) >= NIVEL_MAX_MELHORIA;
    }

    public void continuarDepoisDaLoja() {
        iniciarProximaOnda();
    }

    private void iniciarProximaOnda() {
        aumentarDificuldade();
        onda++;
        state = EstadoJogo.EM_JOGO;
        projeteis.clear();
        itens.clear();
        inimigos.addAll(gerarInimigos());
    }

    public void aumentarDificuldade() { this.nivelDificuldade++; }
    public EstadoJogo getState() { return state; }
    public void setState(EstadoJogo state) { this.state = state; }
    public int getPontuacao() { return pontuacao; }
    public int getMoedas() { return moedas; }
    public int getOnda() { return onda; }
    public int getNivelMaxMelhoria() { return NIVEL_MAX_MELHORIA; }
    public double getNivelDificuldade() { return nivelDificuldade; }
    public NaveJogador getJogador() { return jogador; }
    public List<Inimigo> getInimigos() { return inimigos; }
    public List<Projetil> getProjeteis() { return projeteis; }
    public List<Item> getItens() { return itens; }
}
