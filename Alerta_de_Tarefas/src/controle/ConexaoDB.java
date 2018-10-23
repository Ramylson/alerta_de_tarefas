package controle;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*@author Ramylson A. Costa*/

public class ConexaoDB {
    FileInputStream file;
    Properties props = new Properties();
    public Statement stm;
    public ResultSet rs;
    private String driver, caminho, usuario, senha;
    public Connection con;

     public ConexaoDB() {
        try {
            file = new FileInputStream("./dados.properties");
            props.load(file);
            driver = props.getProperty("driver");
            usuario = "flexsmart";
            senha = "Dba@51195pcap";
            caminho = props.getProperty("caminho");
        } catch (IOException ex) {
            Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void conexao(){
        
        try {
            System.setProperty("jdbc.Drivers", driver);
            con=DriverManager.getConnection(caminho, usuario, senha);
            //JOptionPane.showMessageDialog(null, "Conexao ok");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na conexão:\n"+ex.getMessage());
        }
    }
    
    public void executaSql(String sql){
        try {
            stm = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ver Pedidos:\n"+ex.getMessage());
        }
    }
    
    public void desconecta(){
        try {
            con.close();
            //JOptionPane.showMessageDialog(null, "Desconectado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao desconectar:\n"+ex.getMessage());
        }
    }
}
