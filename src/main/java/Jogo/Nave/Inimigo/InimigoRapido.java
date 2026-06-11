package Jogo.Nave.Inimigo;

public class InimigoRapido extends Inimigo {
    private double velocidadeExtra;

    public InimigoRapido(double posX, double posY, double largura, double altura, double velocidadeBase,
                         int vidas, int dano, int valorPontos, int velocidade) {
        super(posX, posY, largura, altura, velocidadeBase, vidas, dano, valorPontos);
        this.velocidadeExtra = velocidade;
    }

    @Override
    public void atualizar() {
        setPosY(getPosY() + getVelocidade() + velocidadeExtra);
    }
}
