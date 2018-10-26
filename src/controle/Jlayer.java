package controle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/*@author Ramylson A. Costa*/
public class Jlayer {

    private FileInputStream is;
    private Player player;
    private boolean repetir;
    private boolean pausar;
    private long pausarLocal;
    private long tempoTotaldoMP3;
    private String localArquivoMP3;
    private String audio;

    /**
     * @throws FileNotFoundException
     * @throws JavaLayerException
     * @throws IOException
     * @throws java.net.URISyntaxException
     */
    public void play() throws FileNotFoundException, JavaLayerException, IOException, URISyntaxException {
        stop();
        Properties props = new Properties();
        FileInputStream file = new FileInputStream("./dados.properties");
        props.load(file);
        audio = props.getProperty("audio");
        this.localArquivoMP3 = "audios/" + audio;
        is = new FileInputStream(localArquivoMP3);
        tempoTotaldoMP3 = is.available();
        player = new Player(is);
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                    if (player.isComplete() && repetir) {
                        play(localArquivoMP3);
                    }
                } catch (JavaLayerException ex) {
                    JOptionPane.showMessageDialog(null, "Houve um erro ao executar " + localArquivoMP3);
                }
            }

            private void play(String localArquivoMP3) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }.start();
    }

    public void resume() throws FileNotFoundException, JavaLayerException, IOException, URISyntaxException {
        pausar = false;
        is = (FileInputStream) this.getClass().getResourceAsStream(localArquivoMP3);
        is.skip(tempoTotaldoMP3 - pausarLocal);
        player = new Player(is);
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                    JOptionPane.showMessageDialog(null, "Houve um erro ao executar " + localArquivoMP3);
                }
            }
        }.start();
    }

    public void stop() {
        pausar = false;

        if (null != player) {
            player.close();
            tempoTotaldoMP3 = 0;
            pausarLocal = 0;
        }
    }

    public void pause() {
        pausar = true;
        if (null != player) {
            try {
                pausarLocal = is.available();
                player.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Houve um erro ao pausar a musica");
            }

        }
    }

    /**
     * @return true se a musica tiver terminado, se não false
     */
    public boolean isRepeat() {
        return repetir;
    }

    /**
     * @param repeat
     */
    public void setRepeat(boolean repeat) {
        this.repetir = repeat;
    }

    public boolean isPaused() {
        return pausar;
    }

    public void setPaused(boolean paused) {
        this.pausar = paused;
    }

}
