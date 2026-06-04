package Jogo.Objetos;

public abstract class ObjetoJogo {
    private double posX;
    private double posY;
    private double largura;
    private double altura;
    private double velocidade;

    public ObjetoJogo(double posX, double posY, double largura, double altura, double velocidade) {
        this.posX = posX;
        this.posY = posY;
        this.largura = largura;
        this.altura = altura;
        this.velocidade = velocidade;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    public void atualizar() {

    }

    public boolean colideCom(ObjetoJogo outro) {
        return this.getPosX() < outro.getPosX() + outro.getLargura()
                && this.getPosX() + this.getLargura() > outro.getPosX()
                && this.getPosY() < outro.getPosY() + outro.getAltura()
                && this.getPosY() + this.getAltura() > outro.getPosY();
    }
}