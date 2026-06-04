package Jogo.Objetos;

public abstract class ObjetoMovel extends ObjetoJogo {
    private double velocidadeX;
    private double velocidadeY;

    public ObjetoMovel(double velocidadeX, double velocidadeY) {
        this.velocidadeX = velocidadeX;
        this.velocidadeY = velocidadeY;
    }
    public double getVelocidadeX(){
        return velocidadeX;
    }
    public double getVelocidadeY(){
        return velocidadeY;
    }
    public void atualizar2(){
        // teste
        // trabalho
        // algo
    }
}
