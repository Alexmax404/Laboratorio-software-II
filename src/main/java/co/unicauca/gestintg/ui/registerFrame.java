
package co.unicauca.gestintg.ui;
import co.unicauca.gestintg.ui.resources.*;
import javax.swing.*;
import java.awt.*;

public class registerFrame extends javax.swing.JFrame {

    public registerFrame() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Tf_correoElectronico = new co.unicauca.gestintg.ui.resources.RoundedTextField(20, java.awt.Color.GRAY, 1);
        jPanel1 = new javax.swing.JPanel();
        pn_logInData = new RoundedPanel(30);
        txtNombres = new co.unicauca.gestintg.ui.resources.RoundedTextField(20, java.awt.Color.GRAY, 1,"Nombres");
        jLabel3 = new javax.swing.JLabel();
        btn_logIn = new RoundedButton("Iniciar Sesión") ;
        txtApellidos = new co.unicauca.gestintg.ui.resources.RoundedTextField(20, java.awt.Color.GRAY, 1,"Apellidos");
        cbPrograma = new javax.swing.JComboBox<>();
        txtCorreoElectronico = new co.unicauca.gestintg.ui.resources.RoundedTextField(20, java.awt.Color.GRAY, 1,"Correo Electrónico");
        txtTelefono = new co.unicauca.gestintg.ui.resources.RoundedTextField(20, java.awt.Color.GRAY, 1,"Teléfono");
        chkEstudiante = new javax.swing.JCheckBox();
        pswContrasenia = new RoundedPasswordField(20, Color.gray, 1,"Contraseña");
        chkDocente = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        Tf_correoElectronico.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pn_logInData.setBackground(new java.awt.Color(204, 204, 204));

        txtNombres.setToolTipText("aaa");
        txtNombres.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel3.setText("Registrarse");

        btn_logIn.setText("Registrate");
        btn_logIn.setBorder(null);
        btn_logIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogInActionPerformed(evt);
            }
        });

        txtApellidos.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        cbPrograma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ingenieria De Sistemas", "Ingenieria Electronica Y Telecomunicaciones", "Automatica Industrial", "Tecnologia Industrial" }));
        cbPrograma.setSelectedIndex(-1);
        cbPrograma.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        cbPrograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProgramaActionPerformed(evt);
            }
        });

        txtCorreoElectronico.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        txtTelefono.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        chkEstudiante.setBackground(new java.awt.Color(204, 204, 204));
        chkEstudiante.setText("Estudiante");
        chkEstudiante.setActionCommand("");
        chkEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEstudianteActionPerformed(evt);
            }
        });

        pswContrasenia.setToolTipText("");

        chkDocente.setBackground(new java.awt.Color(204, 204, 204));
        chkDocente.setText("Docente");
        chkDocente.setActionCommand("");
        chkDocente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDocenteActionPerformed(evt);
            }
        });

        jLabel1.setText("Programa");

        javax.swing.GroupLayout pn_logInDataLayout = new javax.swing.GroupLayout(pn_logInData);
        pn_logInData.setLayout(pn_logInDataLayout);
        pn_logInDataLayout.setHorizontalGroup(
            pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_logInDataLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_logInDataLayout.createSequentialGroup()
                        .addGap(298, 298, 298)
                        .addGroup(pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                                    .addComponent(pswContrasenia))
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pn_logInDataLayout.createSequentialGroup()
                        .addComponent(chkEstudiante)
                        .addGap(18, 18, 18)
                        .addComponent(chkDocente)
                        .addGap(37, 37, 37)
                        .addComponent(btn_logIn, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11))
            .addGroup(pn_logInDataLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_logInDataLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombres)
                                .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_logInDataLayout.setVerticalGroup(
            pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_logInDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_logInDataLayout.createSequentialGroup()
                        .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pswContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pn_logInDataLayout.createSequentialGroup()
                        .addGroup(pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pn_logInDataLayout.createSequentialGroup()
                                .addGap(159, 159, 159)
                                .addComponent(cbPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn_logInDataLayout.createSequentialGroup()
                                .addComponent(txtNombres)
                                .addGap(18, 18, 18)
                                .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addGroup(pn_logInDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_logIn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkEstudiante)
                            .addComponent(chkDocente))
                        .addGap(30, 30, 30))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(194, Short.MAX_VALUE)
                .addComponent(pn_logInData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(171, 171, 171))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(pn_logInData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogInActionPerformed

    }//GEN-LAST:event_btnLogInActionPerformed

    private void cbProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProgramaActionPerformed
        if (espaciosVacios() == false){
            
        }
    }//GEN-LAST:event_cbProgramaActionPerformed

    private void chkEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEstudianteActionPerformed
        if (chkEstudiante.isSelected()) {
        chkDocente.setSelected(false);
    }
    }//GEN-LAST:event_chkEstudianteActionPerformed

    private void chkDocenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDocenteActionPerformed
        if (chkDocente.isSelected()) {
        chkEstudiante.setSelected(false);
    }
    }//GEN-LAST:event_chkDocenteActionPerformed

   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(registerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(registerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(registerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(registerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registerFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Tf_correoElectronico;
    private javax.swing.JButton btn_logIn;
    private javax.swing.JComboBox<String> cbPrograma;
    private javax.swing.JCheckBox chkDocente;
    private javax.swing.JCheckBox chkEstudiante;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pn_logInData;
    private javax.swing.JPasswordField pswContrasenia;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCorreoElectronico;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
    private boolean espaciosVacios(){
    
        String contrasenia = new String(pswContrasenia.getPassword());
        
        if (txtNombres.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Espacio vacio. Ingrese su/s nombre/s");
            return true;
        }
        if (txtApellidos.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Espacio vacio. Ingrese sus apellidos");
            return true;
        }
        if (txtCorreoElectronico.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Espacio vacio. Ingrese un correo");
            return true;
        }
        if (contrasenia.isEmpty()){
            JOptionPane.showMessageDialog(null, "Espacio vacio. Ingrese la contraseña");
            return true;
        }
        if (cbPrograma.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(null, "Espacio vacio. Seleccione el programa al cual pertenece");
            return true;
        }
        if (chkDocente.isSelected() == false && chkEstudiante.isSelected()==false){
            JOptionPane.showMessageDialog(null, "Espacio vacio. Seleccione un rol");
            return true;
        }
        return false;
    }
    
    private void datos(){
        
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String correo= txtCorreoElectronico.getText();
        String contrasenia = new String(pswContrasenia.getPassword());
        String telefono = txtTelefono.getText();
        
    }
}
