package Jogo.Nave.Inimigo;

public class InimigoForte extends Inimigo {
    private int vidaExtra;
    public InimigoForte(int vidas, int dano, int vidaExtra) {
        super(vidas + vidaExtra, dano, 0);
        this.vidaExtra = vidaExtra;
    }
}
