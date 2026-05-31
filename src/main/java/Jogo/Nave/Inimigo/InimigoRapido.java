package Jogo.Nave.Inimigo;

public class InimigoRapido extends Inimigo {
    private int velocidade;
    public InimigoRapido(int vidas, int dano, int valorPontos, int velocidade){
        super(vidas, dano, valorPontos);
        this.velocidade = velocidade;
    }

}
