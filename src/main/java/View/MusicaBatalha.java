package View;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicaBatalha {
    private static final String URL_MUSICA =
            "https://opengameart.org/sites/default/files/Orbital%20Colossus_0.mp3";
    private static MediaPlayer player;

    private MusicaBatalha() {
    }

    public static void preparar() {
        criarPlayerSeNecessario();
        player.setVolume(0);
        player.play();
    }

    public static void iniciar() {
        criarPlayerSeNecessario();
        player.setVolume(0.65);
        player.play();
    }

    public static void parar() {
        if (player != null) {
            player.stop();
            player.setVolume(0);
        }
    }

    private static void criarPlayerSeNecessario() {
        if (player == null) {
            player = new MediaPlayer(new Media(URL_MUSICA));
            player.setCycleCount(MediaPlayer.INDEFINITE);
        }
    }
}
