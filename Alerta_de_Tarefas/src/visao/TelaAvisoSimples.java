package visao;

//Tela de aviso simples vermelha para aparecer no canto    

public final class TelaAvisoSimples extends javax.swing.JFrame {

    public TelaAvisoSimples() {
        initComponents();
    }
    int retorno = 5;
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    public void initComponents() {

        jLabelFraseAvisoSimples = new javax.swing.JLabel();
        jButtonOKAvisoSimples = new javax.swing.JButton();
        jLabelAvisoSimples = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        //setResizable(false);
        jLabelFraseAvisoSimples.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabelFraseAvisoSimples.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFraseAvisoSimples.setText("Tarefas Pendentes "+(retorno));
        getContentPane().add(jLabelFraseAvisoSimples, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jButtonOKAvisoSimples.setText("OK");
        jButtonOKAvisoSimples.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKAvisoSimplesActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonOKAvisoSimples, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, -1, -1));

        jLabelAvisoSimples.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fundoavisosimples_1.jpg"))); // NOI18N
        getContentPane().add(jLabelAvisoSimples, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 238, -1));

        setSize(new java.awt.Dimension(254, 139));
        setLocationRelativeTo(null);
        //setLocation(1000,1000);
    }// </editor-fold>        

    public int getRetorno() {
        return retorno;
    }

    public void setRetorno(int retorno) {
        this.retorno = retorno;
        jLabelFraseAvisoSimples.setText("Tarefas Pendentes ("+retorno+")");
    }

    private void jButtonOKAvisoSimplesActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }
    private javax.swing.JButton jButtonOKAvisoSimples;
    private javax.swing.JLabel jLabelAvisoSimples;
    private javax.swing.JLabel jLabelFraseAvisoSimples;
}
