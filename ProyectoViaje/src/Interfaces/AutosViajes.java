/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Joc
 */
public class AutosViajes extends javax.swing.JInternalFrame {

    DefaultTableModel modelo;

    /**
     * Creates new form autos
     */
    public AutosViajes() {
        initComponents();
        BotonesInicio();
        bloquearTextos();
        cargarTablaAutos("");
        tblAutos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int fila = tblAutos.getSelectedRow();
                if (fila != -1) {
                    txtPlaca.setText(tblAutos.getValueAt(fila, 0).toString());
                    txtMarca.setText(tblAutos.getValueAt(fila, 1).toString());
                    txtModelo.setText(tblAutos.getValueAt(fila, 2).toString());
                    txtColor.setText(tblAutos.getValueAt(fila, 3).toString());
                    txtAño.setText(tblAutos.getValueAt(fila, 4).toString());
                    txtDescrip.setText(tblAutos.getValueAt(fila, 5).toString());
                    desbloquearTextos();
                    txtPlaca.setEnabled(false);

                    BotonesActualizar();
                    btnBorrar.setEnabled(true);

                }
            }
        });

    }

    public void cargarTablaAutos(String Dato) {
        String[] titulos = {"PLACA", "MARCA", "MODELO", "COLOR", "AÑO", "OBSERVACIÒN"};
        String[] registros = new String[6];
        String sql = "";
        modelo = new DefaultTableModel(null, titulos);
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        sql = "SELECT * FROM AUTO WHERE AUT_PLACA LIKE '%" + Dato + "%' AND AUT_ESTADO='1'";
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {// mientras hay una fila mas se repite y sique cogiendo los valores en rs 
                registros[0] = rs.getString("AUT_PLACA");
                registros[1] = rs.getString("AUT_MARCA");
                registros[2] = rs.getString("AUT_MODELO");
                registros[3] = rs.getString("AUT_COLOR");
                registros[4] = rs.getString("AUT_ANIO");
                registros[5] = rs.getString("AUT_DESCRIPCION");
                modelo.addRow(registros);
            }

            tblAutos.setModel(modelo);// se pone fuerra del whilw para que no se setee cada ves q encuentra una nueva fila 
            modelo.addTableModelListener(new TableModelListener() {

                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.UPDATE) {
                        int columna = e.getColumn();
                        int fila = e.getFirstRow();
                        conexion cc= new conexion();
                        Connection cn= cc.conectar();
                        if (columna == 1) {
                            String sql = "UPDATE AUTO SET AUT_MARCA='" + tblAutos.getValueAt(fila, columna) + "'WHERE AUT_PLACA='" + tblAutos.getValueAt(fila, 0) + "'";
                            try {
                                PreparedStatement psd = cn.prepareStatement(sql);
                                int n = psd.executeUpdate();
                                if (n > 0) {
                                    JOptionPane.showMessageDialog(null, "Se actualizo el registro correctamente");
                                    cargarTablaAutos("");
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex);
                                JOptionPane.showMessageDialog(null, "No se actualizo ningun registro");
                            }
                        }
                         if (columna == 2) {
                            String sql = "UPDATE AUTO SET AUT_MODELO='" + tblAutos.getValueAt(fila, columna) + "'WHERE AUT_PLACA='" + tblAutos.getValueAt(fila, 0) + "'";
                            try {
                                PreparedStatement psd = cn.prepareStatement(sql);
                                int n = psd.executeUpdate();
                                if (n > 0) {
                                    JOptionPane.showMessageDialog(null, "Se actualizo el registro correctamente");
                                    cargarTablaAutos("");
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex);
                                JOptionPane.showMessageDialog(null, "No se actualizo ningun registro");
                            }
                        }
                          if (columna == 3) {
                            String sql = "UPDATE AUTO SET AUT_COLOR='" + tblAutos.getValueAt(fila, columna) + "'WHERE AUT_PLACA='" + tblAutos.getValueAt(fila, 0) + "'";
                            try {
                                PreparedStatement psd = cn.prepareStatement(sql);
                                int n = psd.executeUpdate();
                                if (n > 0) {
                                    JOptionPane.showMessageDialog(null, "Se actualizo el registro correctamente");
                                    cargarTablaAutos("");
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex);
                                JOptionPane.showMessageDialog(null, "No se actualizo ningun registro");
                            }
                        }
                           if (columna == 4) {
                            String sql = "UPDATE AUTO SET AUT_ANIO='" + tblAutos.getValueAt(fila, columna) + "'WHERE AUT_PLACA='" + tblAutos.getValueAt(fila, 0) + "'";
                            try {
                                PreparedStatement psd = cn.prepareStatement(sql);
                                int n = psd.executeUpdate();
                                if (n > 0) {
                                    JOptionPane.showMessageDialog(null, "Se actualizo el registro correctamente");
                                    cargarTablaAutos("");
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex);
                                JOptionPane.showMessageDialog(null, "No se actualizo ningun registro");
                            }
                        }
                            if (columna == 5) {
                            String sql = "UPDATE AUTO SET AUT_DESCRIPCION='" + tblAutos.getValueAt(fila, columna) + "'WHERE AUT_PLACA='" + tblAutos.getValueAt(fila, 0) + "'";
                            try {
                                PreparedStatement psd = cn.prepareStatement(sql);
                                int n = psd.executeUpdate();
                                if (n > 0) {
                                    JOptionPane.showMessageDialog(null, "Se actualizo el registro correctamente");
                                    cargarTablaAutos("");
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex);
                                JOptionPane.showMessageDialog(null, "No se actualizo ningun registro");
                            }
                        }
                    }
                }
            });
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void limpiarTextos() {
        txtPlaca.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtColor.setText("");
        txtAño.setText("");
        txtDescrip.setText("");
    }

    public void bloquearTextos() {
        txtPlaca.setEnabled(false);
        txtMarca.setEnabled(false);
        txtModelo.setEnabled(false);
        txtColor.setEnabled(false);
        txtAño.setEnabled(false);
        txtDescrip.setEnabled(false);

    }

    public void desbloquearTextos() {
        txtPlaca.setEnabled(true);
        txtPlaca.requestFocus();
        txtMarca.setEnabled(true);
        txtModelo.setEnabled(true);
        txtColor.setEnabled(true);
        txtAño.setEnabled(true);
        txtDescrip.setEnabled(true);

    }

    public void BotonesActualizar() {
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);

    }

    public void BotonesBorrar() {
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnBorrar.setEnabled(true);
        btnSalir.setEnabled(true);

    }

    public void BotonesInicio() {
        bloquearTextos();
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);

    }

    public void BotonNuevo() {
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);

    }

    public void guardar() {
        if (txtPlaca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe Ingresar la Placa ");
            txtPlaca.requestFocus();
        } else {
            if (txtMarca.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe Ingresar la Marca ");
                txtMarca.requestFocus();
            } else {
                if (txtModelo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe Ingresar la Modelo ");
                    txtModelo.requestFocus();
                } else {
                    if (txtColor.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe Ingresar la Color ");
                        txtColor.requestFocus();
                    } else {
                        if (txtAño.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Debe Ingresar la Año ");
                            txtAño.requestFocus();
                        } else {
                            if (txtDescrip.getText().isEmpty()) {

                            }
                            conexion cc = new conexion();
                            Connection cn = cc.conectar();
                            String AUT_PLACA, AUT_MARCA, AUT_MODELO, AUT_COLOR, AUT_DESCRIPCION, AUT_ESTADO;
                            int AUT_ANIO;
                            AUT_PLACA = txtPlaca.getText();
                            AUT_MARCA = txtMarca.getText();
                            AUT_MODELO = txtModelo.getText();
                            AUT_COLOR = txtColor.getText();
                            AUT_ANIO = Integer.valueOf(txtAño.getText());
                            AUT_DESCRIPCION = txtDescrip.getText();
                            AUT_ESTADO = "1";

                            if (AUT_DESCRIPCION.isEmpty()) {
                                AUT_DESCRIPCION = "Sin Observación";
                            }
                            String sql = "";//para poder controlar las exccepciones 
                            sql = "INSERT INTO AUTO (AUT_PLACA,AUT_MARCA,AUT_MODELO,AUT_COLOR,AUT_ANIO,AUT_DESCRIPCION,AUT_ESTADO)VALUES(?,?,?,?,?,?,?) ";
                            try {
                                PreparedStatement psd = cn.prepareStatement(sql);
                                psd.setString(1, AUT_PLACA);
                                psd.setString(2, AUT_MARCA);
                                psd.setString(3, AUT_MODELO);
                                psd.setString(4, AUT_COLOR);
                                psd.setInt(5, AUT_ANIO);
                                psd.setString(6, AUT_DESCRIPCION);
                                psd.setString(7, AUT_ESTADO);
                                int n = psd.executeUpdate();

                                if (n > 0) {
                                    JOptionPane.showMessageDialog(null, "Se Inserto un Información Correctamente");
                                    cargarTablaAutos("");
                                }

                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "No se Inserto la Información" + ex);
                            }
                        }
                    }
                }
            }
        }
    }

    public void acrualizar() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "UPDATE AUTO SET AUT_MARCA='" + txtMarca.getText() + "',"
                + "AUT_MODELO='" + txtModelo.getText() + "',"
                + "AUT_COLOR='" + txtColor.getText() + "',"
                + "AUT_ANIO='" + txtAño.getText() + "',"
                + "AUT_DESCRIPCION='" + txtDescrip.getText() + "'"
                + "WHERE AUT_PLACA='" + txtPlaca.getText() + "'";
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Se actualizo el registro correctamente");
                cargarTablaAutos("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "No se actualizo ningun registro");
        }
    }

    public void autoActivo() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "update AUTO set AUT_ESTADO='0'" + "WHERE AUT_PLACA='" + txtPlaca.getText() + "'";
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Se borro el registro con exito  Correctamente");
                cargarTablaAutos("");
                limpiarTextos();
                BotonesInicio();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void borrar() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "DELETE  FROM AUTO WHERE AUT_PLACA='" + txtPlaca.getText() + "'";
        int confirmar = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Boorrar el auto");
        if (JOptionPane.OK_OPTION == confirmar) {
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se borro con exito el registro");
                    cargarTablaAutos("");
                    limpiarTextos();
                    BotonesInicio();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
                JOptionPane.showMessageDialog(null, "No se borro con exito el registro");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        txtColor = new javax.swing.JTextField();
        txtDescrip = new javax.swing.JTextField();
        txtBuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAño = new uctexletras.componenteAnio();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAutos = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Placa:");

        jLabel2.setText("Marca:");

        jLabel3.setText("Modelo:");

        jLabel4.setText("Color:");

        jLabel5.setText("Año:");

        jLabel6.setText("Observacion:");

        txtMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarcaActionPerformed(evt);
            }
        });

        txtColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtColorActionPerformed(evt);
            }
        });

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel7.setText("Buscar:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDescrip, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(txtPlaca)
                    .addComponent(txtModelo)
                    .addComponent(txtMarca)
                    .addComponent(txtColor)
                    .addComponent(txtBuscar)
                    .addComponent(txtAño, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDescrip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnCancelar))
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir)
                    .addComponent(btnBorrar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblAutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblAutos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Gestion de Autos ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 28, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardar();
        limpiarTextos();

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:

        BotonNuevo();
        desbloquearTextos();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiarTextos();
        BotonesInicio();
        bloquearTextos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaActionPerformed

    private void txtColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtColorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtColorActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        cargarTablaAutos(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        //autoActivo();
        borrar();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        acrualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(AutosViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AutosViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AutosViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutosViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AutosViajes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAutos;
    private uctexletras.componenteAnio txtAño;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtDescrip;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtPlaca;
    // End of variables declaration//GEN-END:variables
}
