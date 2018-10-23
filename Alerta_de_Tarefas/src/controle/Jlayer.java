package controle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/*@author Ramylson A. Costa*/

public class Jlayer {
 
    public static void main(String[] args) {
        String audio;
        try {
            Properties props = new Properties();
            FileInputStream file = new FileInputStream("./dados.properties");
            props.load(file);
            audio = props.getProperty("audio");
            String path = "audios/"+audio;
            File mp3File = new File(path);
            MP3Musica musica = new MP3Musica();
            musica.tocar(mp3File);            
            // CHAMA O METODO QUE TOCA A MUSICA
            musica.start();
            } catch (IOException ex) {
                Logger.getLogger(Jlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	/**
	 * ====================================================================
	 * CLASS INTERNA MP3 MUSICA QUE EXTENDE DE THREAD PARA TRABALHAR
	 * PERFEITAMENTE NA APLICAÇÂO SEM TRAVAMENTO NA EXECUÇÃO
	 * ====================================================================
	 */
	public static class MP3Musica extends Thread {
		// OBJETO PARA O ARQUIVO MP3 A SER TOCADO
		private File mp3;
		// OBJETO PLAYER DA BIBLIOTECA JLAYER QUE TOCA O ARQUIVO MP3
		private Player player;
 
		/**
		 * CONSTRUTOR RECEBE O OBJETO FILE REFERECIANDO O ARQUIVO MP3 A SER
		 * TOCADO E ATRIBUI AO ATRIBUTO DA CLASS
		 *
		 * @param mp3
		 */
		public void tocar(File mp3) {
			this.mp3 = mp3;
		}
 
		/**
		 * ===============================================================
		 * ======================================METODO RUN QUE TOCA O MP3
		 * ===============================================================
		 */
                @Override
		public void run() {
			try {
				FileInputStream fis = new FileInputStream(mp3);
				BufferedInputStream bis = new BufferedInputStream(fis);
				this.player = new Player(bis);
				//System.out.println("Tocando Musica!");
				this.player.play();
				//System.out.println("Terminado Musica!");
			} catch (FileNotFoundException | JavaLayerException e) {
				JOptionPane.showMessageDialog(null,"Problema ao tocar Musica \n" + mp3);
			}
		}
	}
 
}
