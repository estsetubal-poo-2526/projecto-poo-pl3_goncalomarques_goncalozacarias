package Jogo.Objetos;

public abstract class ObjetoMovel extends ObjetoJogo {
    private double velocidadeX;
    private double velocidadeY;

    public ObjetoMovel(double posX, double posY, double largura, double altura, double velocidade,
                       double velocidadeX, double velocidadeY) {
        super(posX, posY, largura, altura, velocidade);
        this.velocidadeX = velocidadeX;
        this.velocidadeY = velocidadeY;
    }

    public double getVelocidadeX() {
        return velocidadeX;
    }

    public void setVelocidadeX(double velocidadeX) {
        this.velocidadeX = velocidadeX;
    }

    public double getVelocidadeY() {
        return velocidadeY;
    }

    public void setVelocidadeY(double velocidadeY) {
        this.velocidadeY = velocidadeY;
    }

    public void mover() {
        setPosX(getPosX() + velocidadeX);
        setPosY(getPosY() + velocidadeY);
    }
}
