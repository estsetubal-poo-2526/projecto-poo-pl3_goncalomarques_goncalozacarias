package Jogo.Nave.Inimigo;

public class InimigoRapido extends Inimigo {
    private int velocidade;

    public InimigoRapido(double posX, double posY, double largura, double altura, double velocidadeBase,
                         int vidas, int dano, int valorPontos, int velocidade) {
        super(posX, posY, largura, altura, velocidadeBase, vidas, dano, valorPontos);
        this.velocidade = velocidade;
    }
}
