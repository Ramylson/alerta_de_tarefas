package visao;

import modelo.Audio;
import modelo.ListaAudios;
import controle.Jlayer;
import controle.ConexaoDB;
import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;

/*@author Ramylson A. Costa*/
public class TelaAviso extends javax.swing.JFrame {

    private final ListaAudios listaAudios;
    ConexaoDB conecta = new ConexaoDB();
    TelaAvisoSimples vermelho = new TelaAvisoSimples();
    CheckboxMenuItem cheque1 = new CheckboxMenuItem("Alarme Sonoro");
    CheckboxMenuItem cheque2 = new CheckboxMenuItem("Ativar Notificação");
    Jlayer play = new Jlayer();
    Properties props = new Properties();
    FileInputStream file;
    FileOutputStream arquivoOut = null;
    private String sql;
    public static long TEMPO;
    Timer timer = null;
    boolean alertateste = false;
    Image image, image2;
    String audioatual, teste, credito;
    Integer tempo_refresh, retornoquery;
    TrayIcon trayIcon;

    public TelaAviso() throws FileNotFoundException, IOException {
        File filemp3 = new File("C:\\PerimSistemas\\AlertaTarefas\\audios");
        if (!filemp3.exists()){
            JOptionPane.showMessageDialog(null, "Não encontrado diretório 'audios'!");
            System.exit(0);
        }
        File[] arquivos = filemp3.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
        ArrayList lista = new ArrayList();
        for (File fileTmp : arquivos) {
            lista.add(fileTmp.getName());
        }
        listaAudios = new ListaAudios();
        for (int i = 0; i < arquivos.length; i++) {
            Audio a = new Audio();
            a.setAudios((String) lista.get(i));
            listaAudios.addAudio(a);
        }
        file = new FileInputStream("./dados.properties");
        props.load(file);
        audioatual = props.getProperty("audio");
        try {
            tempo_refresh = Integer.parseInt(props.getProperty("tempo_refresh"));
        } catch (NumberFormatException errTempo) {
            JOptionPane.showMessageDialog(null, "'tempo_refresh' inválido!");
            System.exit(0);
        }
        tray();
        cheque1.setState(true);
        cheque2.setState(true);
        initComponents();
        this.setIconImage(image);
        this.setVisible(false); // Define a janela como invisivel
        this.setDefaultCloseOperation(HIDE_ON_CLOSE); // Define o modo de fechamento
        this.setResizable(false);
        this.jComboBoxAudios.setSelectedIndex(lista.indexOf(audioatual));
        conecta.conexao();
        testepedido();
    }
//Testar pedidos no servidor

    private void testepedido() {
        sql = props.getProperty("sql");
        String[] s = sql.trim().split(" ");
        if (!"select".equals(s[0])) {
            JOptionPane.showMessageDialog(null, "Comando SQL inválido!");
            conecta.desconecta();
            System.exit(0);
        }
        if (timer == null) {
            timer = new Timer();
            TimerTask tarefa = new TimerTask() {
                @Override
                public void run() {
                    try {
                        conecta.executaSql(sql);
                        conecta.rs.last();
                        retornoquery = conecta.rs.getRow();
                        if (retornoquery >= 1) {
                            jLabelAviso.setText("Tarefas pendentes (" + retornoquery + ")");
                            vermelho.setRetorno(retornoquery);
                            trayIcon.setImage(image2);
                            if (cheque2.getState() == true && alertateste == false) {
                                display();
                            }
                            if (cheque1.getState() == true) {
                                play.play();
                            }
                        } else {
                            jLabelAviso.setText("Não há tarefas!");
                            trayIcon.setImage(image);
                            vermelho.dispose();
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro na Pesquisa:\n" + ex.getMessage());
                    } catch (JavaLayerException | IOException | URISyntaxException ex) {
                        Logger.getLogger(TelaAviso.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            TEMPO = (1000 * tempo_refresh); //executa o teste a cada 10 segundos
            timer.scheduleAtFixedRate(tarefa, TEMPO, TEMPO);
        }
    }
//Construção dos componentes da Tela de aviso principal

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonSair = new javax.swing.JButton();
        jLabelAviso = new javax.swing.JLabel();
        jComboBoxAudios = new javax.swing.JComboBox<>();
        jBtnOcultar = new javax.swing.JButton();
        jlabelfundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jButtonSair.setBackground(new java.awt.Color(255, 0, 0));
        jButtonSair.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSair.setText("Sair");
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSair);
        jButtonSair.setBounds(310, 180, 60, 25);

        jLabelAviso.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAviso.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabelAviso.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAviso.setText("Não há tarefas!");
        jLabelAviso.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabelAvisoAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        getContentPane().add(jLabelAviso);
        jLabelAviso.setBounds(90, 90, 220, 22);

        jComboBoxAudios.setModel(listaAudios
        );
        jComboBoxAudios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAudiosActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBoxAudios);
        jComboBoxAudios.setBounds(20, 180, 160, 24);

        jBtnOcultar.setText("Minimizar");
        jBtnOcultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnOcultarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnOcultar);
        jBtnOcultar.setBounds(203, 180, 90, 25);

        jlabelfundo.setBackground(new java.awt.Color(51, 51, 255));
        jlabelfundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fundo.jpg"))); // NOI18N
        getContentPane().add(jlabelfundo);
        jlabelfundo.setBounds(0, 0, 410, 236);

        setSize(new java.awt.Dimension(416, 262));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        timer.cancel();
        timer.purge();
        conecta.desconecta();
        System.exit(0);
    }//GEN-LAST:event_jButtonSairActionPerformed
    private void jLabelAvisoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabelAvisoAncestorAdded
    }//GEN-LAST:event_jLabelAvisoAncestorAdded
    private void jBtnOcultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnOcultarActionPerformed
        setVisible(false);
        alertateste = false;
    }//GEN-LAST:event_jBtnOcultarActionPerformed
//Lista Audios
    private void jComboBoxAudiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAudiosActionPerformed
        Object ad = jComboBoxAudios.getSelectedItem();
        try {
            arquivoOut = new FileOutputStream("./dados.properties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TelaAviso.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Provavel que o arquivo de configuração tenha corrompido, reinstale para resolver");
        }
        props.setProperty("audio", "" + ad);
        try {
            props.store(arquivoOut, null);
            //JOptionPane.showMessageDialog(null, "Audio alterado!");
        } catch (IOException ex) {
            Logger.getLogger(TelaAviso.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao selecionar Audio!");
        }
    }//GEN-LAST:event_jComboBoxAudiosActionPerformed
//TrayCat quando minimizar

    public final void tray() {
        // o sistema suporta o trayicon?
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            image = Toolkit.getDefaultToolkit().getImage("res/icon.png");
            image2 = Toolkit.getDefaultToolkit().getImage("res/iconv.png");
            // ActionListener para o programa
            ActionListener sairListener = (ActionEvent e) -> {
                timer.cancel();
                timer.purge();
                conecta.desconecta();
                System.exit(0);
            };
            ActionListener versaolistener = (ActionEvent e) -> {
                JOptionPane.showMessageDialog(null, "Versão 23/10/2018");
            };
            ActionListener janelaListener = (ActionEvent e) -> {
                setVisible(true);
                alertateste = true;
                vermelho.dispose();
            };
            //criando itens do menu
            MenuItem janelatray = new MenuItem("Abrir");
            MenuItem versaotray = new MenuItem("Versão");
            MenuItem sairtray = new MenuItem("Sair");
            //objetos com eventos
            versaotray.addActionListener(versaolistener);
            sairtray.addActionListener(sairListener);
            janelatray.addActionListener(janelaListener);
            //Criando um objeto PopupMenu
            PopupMenu popup = new PopupMenu("Menu de Opções");
            popup.add(janelatray);
            popup.addSeparator();//adiconando um separador
            popup.add(cheque1); //Criando objetos do tipo Checkbox
            //popup.add(cheque2);
            popup.addSeparator();
            popup.add(versaotray);
            popup.add(sairtray);
            //objeto do tipo TrayIcon e Na linha a seguida a imagem icone sera redimensionada
            trayIcon = new TrayIcon(image, "Sis. Alerta de Pedidos", popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(janelaListener);//Acionar janela ao clicar no icon
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("Erro, TrayIcon não sera adicionado.");
            }
        } else {
            setVisible(true);
            alertateste = true;
            JOptionPane.showMessageDialog(null, "Recurso de TrayCat ainda não esta disponível pra o seu sistema, por favor entrar em contato com suporte");
        }
    }
//Abrir a Tela de aviso simples vermelha no canto inferior direito

    private void display() {
        vermelho.setIconImage(image2);
        vermelho.pack();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //area total da altura tirando subtraindo o menu iniciar
        Rectangle rect = ge.getMaximumWindowBounds();
        int x = (int) rect.getMaxX() - vermelho.getWidth();
        int y = (int) rect.getMaxY() - vermelho.getHeight();
        vermelho.setLocation(x, y);
        vermelho.setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new TelaAviso();
            } catch (IOException ex) {
                Logger.getLogger(TelaAviso.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnOcultar;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JComboBox<String> jComboBoxAudios;
    private javax.swing.JLabel jLabelAviso;
    private javax.swing.JLabel jlabelfundo;
    // End of variables declaration//GEN-END:variables

    private boolean accept(File filemp3, File arquivo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
