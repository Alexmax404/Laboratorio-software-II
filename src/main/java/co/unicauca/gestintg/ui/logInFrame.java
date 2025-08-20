
package co.unicauca.gestintg.ui;

import co.unicauca.gestintg.ui.resources.*;
import co.unicauca.gestiontg.access.GestionUsuario;
import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.service.Servicio;
import javax.swing.*;
import java.awt.*;

public class logInFrame extends javax.swing.JFrame {
    
    private Servicio service; 
    
    public logInFrame() {
        initComponents();
        
        IUsuarioRepositorio repositorio = GestionUsuario.getInstancia().getRepositorio("SQLite");       
        service = new Servicio(repositorio);
    }
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new logInFrame().setVisible(true);
            }
        });
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlogIn = new javax.swing.JPanel();
        pnlogInData = new RoundedPanel(30);
        jLabel1 = new javax.swing.JLabel();
        txtCorreoElectronico = new co.unicauca.gestintg.ui.resources.RoundedTextField(20, java.awt.Color.GRAY, 1,"ingrese su nombbre");
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pswContrasenia = new RoundedPasswordField(20, Color.BLUE, 2);
        ;
        btnInicioSesion = new RoundedButton("Iniciar Sesión") ;

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlogIn.setBackground(new java.awt.Color(255, 255, 255));

        pnlogInData.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel1.setText("Correo Electrónico");

        txtCorreoElectronico.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel3.setText("Iniciar Sesión");

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel2.setText("Contraseña");

        btnInicioSesion.setText("Iniciar Sesión");
        btnInicioSesion.setBorder(null);
        btnInicioSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlogInDataLayout = new javax.swing.GroupLayout(pnlogInData);
        pnlogInData.setLayout(pnlogInDataLayout);
        pnlogInDataLayout.setHorizontalGroup(
            pnlogInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlogInDataLayout.createSequentialGroup()
                .addGroup(pnlogInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlogInDataLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(pnlogInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlogInDataLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pswContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInicioSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlogInDataLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        pnlogInDataLayout.setVerticalGroup(
            pnlogInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlogInDataLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pswContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnInicioSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlogInLayout = new javax.swing.GroupLayout(pnlogIn);
        pnlogIn.setLayout(pnlogInLayout);
        pnlogInLayout.setHorizontalGroup(
            pnlogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlogInLayout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addComponent(pnlogInData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        pnlogInLayout.setVerticalGroup(
            pnlogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlogInLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(pnlogInData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlogIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlogIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicioSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioSesionActionPerformed
       String correo=txtCorreoElectronico.getText();
       String contrasenia=new String(pswContrasenia.getPassword());
       boolean validar= service.inicioSesion(correo, contrasenia);
       
       if(espaciosVacios()==false){
            if(validar)
                JOptionPane.showMessageDialog(null, "Si existe");
            else{
                JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos");
                txtCorreoElectronico.setText("");
                pswContrasenia.setText("");
            }
       }
    }//GEN-LAST:event_btnInicioSesionActionPerformed


   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInicioSesion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel pnlogIn;
    private javax.swing.JPanel pnlogInData;
    private javax.swing.JPasswordField pswContrasenia;
    private javax.swing.JTextField txtCorreoElectronico;
    // End of variables declaration//GEN-END:variables
    // ✅ Getter y Setter para el botón de login
    public JButton getBtnLogIn() {
        return btnInicioSesion;
    }

    public void setBtnLogIn(JButton btn_logIn) {
        this.btnInicioSesion = btn_logIn;
    }

    // ✅ Getter y Setter para el JTextField (correo electrónico)
    public JTextField getTfCorreoElectronico() {
        return txtCorreoElectronico;
    }

    public void setTfCorreoElectronico(JTextField tfCorreoElectronico) {
        this.txtCorreoElectronico = tfCorreoElectronico;
    }

    // ✅ Getter y Setter para el JPasswordField (contraseña)
    public JPasswordField getPwtfContraseña() {
        return pswContrasenia;
    }

    public void setPwtfContraseña(JPasswordField pwtf_contraseña) {
        this.pswContrasenia = pwtf_contraseña;
    }
    
    private boolean espaciosVacios(){
        
        String contrasenia = new String(pswContrasenia.getPassword());
        
        if (txtCorreoElectronico.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Espacio vacio. Ingrese un correo");
            return true;
        }
        if (contrasenia.isEmpty()){
            JOptionPane.showMessageDialog(null, "Espacio vacio. Ingrese la contraseña");
            return true;
        }
        return false;
    }
    
    
}
