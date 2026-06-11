package View;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class MusicaBatalha {
    private static volatile boolean aTocar = false;
    private static Thread threadMusica;

    private MusicaBatalha() {
    }

    public static void iniciar() {
        if (aTocar) return;
        aTocar = true;
        threadMusica = new Thread(MusicaBatalha::tocarLoop, "MusicaBatalha");
        threadMusica.setDaemon(true);
        threadMusica.start();
    }

    public static void parar() {
        aTocar = false;
    }

    private static void tocarLoop() {
        float sampleRate = 44100f;
        AudioFormat formato = new AudioFormat(sampleRate, 16, 1, true, false);
        int[] notas = { 110, 110, 165, 147, 110, 220, 196, 165 };

        try (SourceDataLine linha = AudioSystem.getSourceDataLine(formato)) {
            linha.open(formato, 4096);
            linha.start();

            while (aTocar) {
                for (int nota : notas) {
                    if (!aTocar) break;
                    escreverNota(linha, sampleRate, nota, 0.18);
                    escreverNota(linha, sampleRate, 55, 0.06);
                }
            }

            linha.drain();
        } catch (Exception ignored) {
            aTocar = false;
        }
    }

    private static void escreverNota(SourceDataLine linha, float sampleRate, int frequencia, double segundos) {
        int totalSamples = (int) (sampleRate * segundos);
        byte[] buffer = new byte[totalSamples * 2];

        for (int i = 0; i < totalSamples; i++) {
            double onda = Math.sin(2.0 * Math.PI * frequencia * i / sampleRate);
            double pulso = Math.sin(2.0 * Math.PI * frequencia * 0.5 * i / sampleRate) > 0 ? 1 : -1;
            short valor = (short) ((onda * 0.65 + pulso * 0.35) * 6500);
            buffer[i * 2] = (byte) (valor & 0xff);
            buffer[i * 2 + 1] = (byte) ((valor >> 8) & 0xff);
        }

        linha.write(buffer, 0, buffer.length);
    }
}
