package Jogo.Nave.Inimigo;

public class InimigoForte extends Inimigo {
    private int vidaExtra;

    public InimigoForte(double posX, double posY, double largura, double altura, double velocidade,
                        int vidas, int dano, int valorPontos, int vidaExtra) {
        super(posX, posY, largura, altura, velocidade, vidas + vidaExtra, dano, valorPontos);
        this.vidaExtra = vidaExtra;
    }
}
