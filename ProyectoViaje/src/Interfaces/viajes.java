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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Benjita
 */
public class viajes extends javax.swing.JInternalFrame {

    /**
     * Creates new form adm_viajes
     */
    public viajes() {
        initComponents();
        cargarPlacas();
        cargarOrigen();
        cargarViajes("");
        descargarCampos();
        bloquearBotonesInicio();
        bloquearInicio();
        txtBuscar.requestFocus();

    }
    DefaultTableModel modelo;

    public void cargarViajes(String busqueda) {
        try {
            String[] titulos = {"CODIGO", "PLACA", "ORIGEN", "DESTINO", "SALIDA", "LLEGADA", "KILOMETRAJE", "COSTO", "OBSERVACION"};
            modelo = new DefaultTableModel(null, titulos);
            tblViajes.setModel(modelo);
            String[] datos = new String[9];
            conexion cn = new conexion();
            Connection cc = cn.conectar();
            String sql = "";
            sql = "select * from viajes where AUT_PLACA LIKE '%" + busqueda + "%'";
            //System.out.println(sql);
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("VIA_CODIGO");
                datos[1] = rs.getString("AUT_PLACA");
                datos[2] = verCiudades(rs.getString("CIU_CODIGO_OR"));
                datos[3] = verCiudades(rs.getString("CIU_CODIGO_DES"));
                datos[4] = rs.getString("VIA_FECHA_SALIDA");
                datos[5] = rs.getString("VIA_FECHA_LLEGADA");
                datos[6] = rs.getString("VIA_KILOMETRAJE");
                datos[7] = rs.getString("VIA_COSTO");
                datos[8] = rs.getString("VIA_OBSERVACION");
                //System.out.println(datos[5]);
                modelo.addRow(datos);
            }
            tblViajes.setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void Guargar() {
        if (txtCosto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Deber ingresar Costo");
        } else {
            if (txtHoraLLegada.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar hora de Llegada");
            } else {
                if (txtHoraSalida.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar hora de Llegada");
                } else {
                    if (txtKilometraje.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe ingresar hora de Salida");
                    } else {
                        if (txtObser.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Debe ingresar hora de Observación");
                        }
                    }
                }
            }
        }
        String AUT_PLACA, CIU_CODIGO_OR, CIU_CODIGO_DES, VIA_FECHA_SALIDA, VIA_FECHA_LLEGADA, VIA_OBSERVACION;
        int VIA_KILOMETRAJE, VIA_COSTO;
        String sql = "";
        sql = "INSERT INTO VIAJES(AUT_PLACA,CIU_CODIGO_OR,CIU_CODIGO_DES,VIA_FECHA_SALIDA,VIA_FECHA_LLEGADA,VIA_KILOMETRAJE,VIA_COSTO,VIA_OBSERVACION) VALUES(?,?,?,?,?,?,?,?)";
        conexion cn = new conexion();
        Connection cc = cn.conectar();
        AUT_PLACA = jcmbPlaca.getSelectedItem().toString();
        CIU_CODIGO_OR = verCodigoOrigen(jcmbOrigen.getSelectedItem().toString());
        CIU_CODIGO_DES = verCodigoOrigen(jcmbDestino.getSelectedItem().toString());
        Date salida = jdtfechaSalida.getDate();
        int anio = Integer.valueOf(salida.getYear()) + 1900;
        String mes = String.valueOf(salida.getMonth() + 1);
        String fecha = String.valueOf(salida.getDate());
        VIA_FECHA_SALIDA = anio + "-" + mes + "-" + fecha + " " + txtHoraSalida.getText().trim();
        Date llegada = jdtFechaLlegada.getDate();
        int anio2 = Integer.valueOf(llegada.getYear()) + 1900;
        String mes2 = String.valueOf(llegada.getMonth() + 1);
        String fecha2 = String.valueOf(llegada.getDate());
        VIA_FECHA_LLEGADA = anio2 + "-" + mes2 + "-" + fecha2 + " " + txtHoraLLegada.getText().trim();
        VIA_KILOMETRAJE = Integer.valueOf(txtKilometraje.getText().trim());
        VIA_COSTO = Integer.valueOf(txtCosto.getText().trim());
        VIA_OBSERVACION = txtObser.getText();
        try {
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.setString(1, AUT_PLACA);
            psd.setString(2, CIU_CODIGO_OR);
            psd.setString(3, CIU_CODIGO_DES);
            psd.setString(4, VIA_FECHA_SALIDA);
            psd.setString(5, VIA_FECHA_LLEGADA);
            psd.setInt(6, VIA_KILOMETRAJE);
            psd.setInt(7, VIA_COSTO);
            psd.setString(8, VIA_OBSERVACION);
            //System.out.println(sql);
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Se a insertado una fila");
                cargarViajes("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }
public void trasformarMayusc(JTextField dato) {
        String cad = dato.getText().toUpperCase();
        dato.setText(cad);
    }
    public String verCodigoOrigen(String ciudad) {
        String codigo = "";
        try {
            conexion cn = new conexion();
            Connection cc = cn.conectar();
            String sql = "";
            sql = "Select CIU_CODIGO from CIUDAD where CIU_NOMBRE='" + ciudad + "'";
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);

            while (rs.next()) {
                codigo = rs.getString("CIU_CODIGO");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se puede consultar la codigo");
        }
        return codigo;
    }

    public void cargarOrigen() {
        conexion cn = new conexion();
        Connection cc = cn.conectar();
        String sql = "";
        sql = "Select CIU_NOMBRE from CIUDAD";
        try {
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                jcmbOrigen.addItem(rs.getString("CIU_NOMBRE"));
                jcmbDestino.addItem(rs.getString("CIU_NOMBRE"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void GuardarDatos() {
        conexion cn = new conexion();
        Connection cc = cn.conectar();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        String strDate = sdf.format(cal.getTime());

        String fecs;
        Date salida1 = jdtfechaSalida.getDate();
        int aniov = Integer.valueOf(salida1.getYear()) + 1900;
        String mesv = String.valueOf(salida1.getMonth() + 1);
        String fechav = String.valueOf(salida1.getDate());
        fecs = aniov + "-" + mesv + "-" + fechav + " " + txtHoraSalida.getText().trim();
        Date llegada3 = jdtFechaLlegada.getDate();
        int anio3 = Integer.valueOf(llegada3.getYear()) + 1900;
        String mes3 = String.valueOf(llegada3.getMonth() + 1);
        String fecha3 = String.valueOf(llegada3.getDate());
        String llegasad = anio3 + "-" + mes3 + "-" + fecha3 + " " + txtHoraLLegada.getText().trim();
        

        String sql2 = "select aut_placa from viajes where via_fecha_salida between '"
                + fecs + "' and '" + llegasad + "'"
                + "and  aut_placa ='" + jcmbPlaca.getSelectedItem().toString() + "'";
//        JOptionPane.showMessageDialog(null, sql2);
        try {
            Statement psd2 = cc.createStatement();
            ResultSet rs = psd2.executeQuery(sql2);
            if (rs.next() == true) {
                JOptionPane.showMessageDialog(null, "El auto no puede ser ocupado");
            } else {
                String sql3 = "select aut_placa from viajes where via_fecha_llegada between '"
                        + fecs+ "' and '" + llegasad + "'"
                        + "and  aut_placa ='" + jcmbPlaca.getSelectedItem().toString() + "'";
                Statement psd3 = cc.createStatement();
                ResultSet rs2 = psd3.executeQuery(sql3);
                if (rs.next() == true) {
                    JOptionPane.showMessageDialog(null, "El auto no puede ser ocupado");
                } else {
                    String AUT_PLACA, CIU_CODIGO_OR, CIU_CODIGO_DES, VIA_FECHA_SALIDA, VIA_FECHA_LLEGADA, VIA_OBSERVACION;
                    int VIA_KILOMETRAJE, VIA_COSTO;
                    String sql = "";
                    sql = "INSERT INTO VIAJES(AUT_PLACA,CIU_CODIGO_OR,CIU_CODIGO_DES,VIA_FECHA_SALIDA,VIA_FECHA_LLEGADA,VIA_KILOMETRAJE,VIA_COSTO,VIA_OBSERVACION) VALUES(?,?,?,?,?,?,?,?)";
                    conexion cn1 = new conexion();
                    Connection cc1 = cn1.conectar();
                    AUT_PLACA = jcmbPlaca.getSelectedItem().toString();
                    CIU_CODIGO_OR = verCodigoOrigen(jcmbOrigen.getSelectedItem().toString());
                    CIU_CODIGO_DES = verCodigoOrigen(jcmbDestino.getSelectedItem().toString());
                    Date salida = jdtfechaSalida.getDate();
                    int anio = Integer.valueOf(salida.getYear()) + 1900;
                    String mes = String.valueOf(salida.getMonth() + 1);
                    String fecha = String.valueOf(salida.getDate());
                    VIA_FECHA_SALIDA = anio + "-" + mes + "-" + fecha + " " + txtHoraSalida.getText().trim();
                    Date llegada = jdtFechaLlegada.getDate();
                    int anio2 = Integer.valueOf(llegada.getYear()) + 1900;
                    String mes2 = String.valueOf(llegada.getMonth() + 1);
                    String fecha2 = String.valueOf(llegada.getDate());
                    VIA_FECHA_LLEGADA = anio2 + "-" + mes2 + "-" + fecha2 + " " + txtHoraLLegada.getText().trim();
                    VIA_KILOMETRAJE = Integer.valueOf(txtKilometraje.getText().trim());
                    VIA_COSTO = Integer.valueOf(txtCosto.getText().trim());
                    VIA_OBSERVACION = txtObser.getText();
                    try {
                        PreparedStatement psd = cc.prepareStatement(sql);
                        psd.setString(1, AUT_PLACA);
                        psd.setString(2, CIU_CODIGO_OR);
                        psd.setString(3, CIU_CODIGO_DES);
                        psd.setString(4, VIA_FECHA_SALIDA);
                        psd.setString(5, VIA_FECHA_LLEGADA);
                        psd.setInt(6, VIA_KILOMETRAJE);
                        psd.setInt(7, VIA_COSTO);
                        psd.setString(8, VIA_OBSERVACION);
                        //System.out.println(sql);
                        int n = psd.executeUpdate();
                        if (n > 0) {
                            JOptionPane.showMessageDialog(null, "Se a insertado una fila");
                            cargarViajes("");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, mesv);

        }
    }

    public void cargarPlacas() {
        conexion cn = new conexion();
        Connection cc = cn.conectar();
        String sql = "";
        sql = "Select AUT_PLACA from auto";
        try {
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                jcmbPlaca.addItem(rs.getString("AUT_PLACA"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(viajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void bloquearBotonesInicio() {
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnSalir.setEnabled(true);

    }

    public void desbloquearBotonNuevo() {
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnSalir.setEnabled(true);

    }

    public void bloquearInicio() {
        jcmbPlaca.setEnabled(false);
        jcmbOrigen.setEnabled(false);
        jcmbDestino.setEnabled(false);
        jdtfechaSalida.setEnabled(false);

        jdtfechaSalida.setEnabled(false);
        txtHoraSalida.setEnabled(false);
        txtKilometraje.setEnabled(false);
        txtCosto.setEnabled(false);
        txtHoraLLegada.setEnabled(false);
        txtObser.setEnabled(false);
    }

//    public void restarHoras() {
//        if (txtHoraSalida1.getText().trim().length() == 0) {
//            JOptionPane.showMessageDialog(null, "Debe ingresar hora de Salida");
//            txtHoraSalida1.requestFocus();
//        } else {
//            if (txtHoraLLegada.getText().trim().length() == 0) {
//                JOptionPane.showMessageDialog(null, "Debe ingresar hora de Llegada");
//                txtHoraLLegada.requestFocus();
//            } else {
//                Date ini = jdtfechaSalida.getDate();
//                final long MILLSECS_PER_DAY = 60 * 1000;
//                Date fin = jdtFechaLlegada.getDate();
//                Calendar salida = Calendar.getInstance();
//                salida.set(jdtfechaSalida.getDate().getYear(), jdtfechaSalida.getDate().getMonth(), jdtfechaSalida.getDate().getDate(),
//                        Integer.valueOf(txtHoraSalida1.getText().substring(0, 2)), Integer.valueOf(txtHoraSalida1.getText().substring(3, 5)), Integer.valueOf(txtHoraSalida1.getText().substring(6, 8)));
//                ini = salida.getTime();
//                Calendar llegada = Calendar.getInstance();
//                llegada.set(jdtFechaLlegada.getDate().getYear(), jdtFechaLlegada.getDate().getMonth(), jdtFechaLlegada.getDate().getDate(),
//                        Integer.valueOf(txtHoraLLegada.getText().substring(0, 2)), Integer.valueOf(txtHoraLLegada.getText().substring(3, 5)), Integer.valueOf(txtHoraLLegada.getText().substring(6, 8)));
//                fin = llegada.getTime();
//                float diferencia = (fin.getTime() - ini.getTime()) / MILLSECS_PER_DAY;
//                int minutos = (int) diferencia;
//                String cadena = "";
//                if (diferencia < 60) {
//                    cadena = "00:" + (int) diferencia;
//                } else {
//                    if (minutos / 60 == 0) {
//                        int a = minutos / 60;
//                        JOptionPane.showMessageDialog(this, a + " horas");
//
//                    } else {
//                        int a = minutos / 60;
//                        int b = minutos % 60;
//                        //JOptionPane.showMessageDialog(this, a + "Horas" + b + "minutos");
//                        cadena = a + ":" + b;
//                    }
//                }
//                if (diferencia < 0) {
//                    JOptionPane.showMessageDialog(null, "Fecha invalida: Fecha de llegada menor a fecha de salida");
//                } else {
//
//                    txtDuracion.setText(String.valueOf(cadena));
//                }
//            }
//        }
//
//    }
    public String verCiudades(String codigo_ciudad) {
        conexion cn = new conexion();
        Connection cc = cn.conectar();
        String ciudades = "";
        String sql = "";
        sql = "select CIU_NOMBRE from ciudad where CIU_CODIGO='" + codigo_ciudad + "'";
        // System.out.println(sql);
        try {
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                ciudades = rs.getString("CIU_NOMBRE");
            }
        } catch (Exception e) {

        }
        return ciudades;
    }

    public String verCodigoCiudades(String codigo_ciudad) {
        conexion cn = new conexion();
        Connection cc = cn.conectar();
        String ciudades = "";
        String sql = "";
        sql = "select orides_codigo from origen_destino where orides_codigo='" + codigo_ciudad + "'";
        //System.out.println(sql);
        try {
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                ciudades = rs.getString("orides_codigo");
            }
        } catch (Exception e) {

        }
        return ciudades;
    }

    public void descargarCampos() {
        txtCodigo.setEnabled(false);
        tblViajes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tblViajes.getSelectedRow() != -1) {
                    int fila = tblViajes.getSelectedRow();
                    txtCodigo.setText(tblViajes.getValueAt(fila, 0).toString());
                    jcmbPlaca.setSelectedItem(tblViajes.getValueAt(fila, 1).toString());
                    jcmbOrigen.setSelectedItem(tblViajes.getValueAt(fila, 2).toString());
                    jcmbDestino.setSelectedItem(tblViajes.getValueAt(fila, 3).toString());
                    String[] salida = new String[1];
                    salida = tblViajes.getValueAt(fila, 4).toString().split(" ");
                    String fechaSalida = salida[0];
                    SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
                    txtHoraSalida.setText(salida[1].substring(0, 8));
                    Date fechS = null;
                    try {
                        fechS = formatoDeFecha.parse(fechaSalida);
                        //System.out.println(fechS.toString());
                        jdtfechaSalida.setDate(fechS);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                    String[] llegadas = new String[1];
                    llegadas = tblViajes.getValueAt(fila, 5).toString().split(" ");
                    String fechaLlegada = llegadas[0];
                    SimpleDateFormat formatoDeFecha2 = new SimpleDateFormat("yyyy-MM-dd");
                    txtHoraLLegada.setText(llegadas[1].substring(0, 8));
                    Date fechS2 = null;
                    try {
                        fechS2 = formatoDeFecha2.parse(fechaLlegada);
                        //System.out.println(fechS2.toString());
                        jdtFechaLlegada.setDate(fechS2);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                    txtKilometraje.setText(tblViajes.getValueAt(fila, 6).toString());
                    txtCosto.setText(tblViajes.getValueAt(fila, 7).toString());
                    txtObser.setText(tblViajes.getValueAt(fila, 8).toString());
//                    restarHoras();

                    desbloquearDatos();
                    bloquearBotonesActualizar();
                    btnEliminar.setEnabled(true);
                    btnCancelar.setEnabled(true);
                }
            }
        });
    }

    public void eliminar() {
        if (JOptionPane.showConfirmDialog(null, "Desea eliminar el dato?") == 0) {
            conexion cn = new conexion();
            Connection cc = cn.conectar();
            String sql = "";
            sql = "delete from viajes where VIA_CODIGO='" + txtCodigo.getText() + "'";
            try {
                PreparedStatement psd = cc.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "La fila se elimino correctamente");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se elimino el viaje");
            }
        }
    }

    public void actualizar() {
        if (txtCosto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Deber ingresar Costo");
        } else {
            if (txtHoraLLegada.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar hora de Llegada");
            } else {
                if (txtHoraSalida.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar hora de Llegada");
                } else {
                    if (txtKilometraje.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe ingresar hora de Salida");
                    } else {
                        if (txtObser.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Debe ingresar hora de Observación");
                        }
                    }
                }
            }
        }
        conexion cn = new conexion();
        Connection cc = cn.conectar();
        String sql = "";
        Date salida = jdtfechaSalida.getDate();
        int anio = Integer.valueOf(salida.getYear()) + 1900;
        String mes = String.valueOf(salida.getMonth() + 1);
        String fecha = String.valueOf(salida.getDate());
        String sal = anio + "-" + mes + "-" + fecha + " " + txtHoraSalida.getText().trim();
        Date llegada = jdtFechaLlegada.getDate();
        int anio2 = Integer.valueOf(llegada.getYear()) + 1900;
        String mes2 = String.valueOf(llegada.getMonth() + 1);
        String fecha2 = String.valueOf(llegada.getDate());
        String lle = anio2 + "-" + mes2 + "-" + fecha2 + " " + txtHoraLLegada.getText().trim();
        sql = "update viajes set AUT_PLACA='" + jcmbPlaca.getSelectedItem() + "',"
                + "CIU_CODIGO_OR='" + verCodigoOrigen(jcmbOrigen.getSelectedItem().toString()) + "',"
                + "CIU_CODIGO_DES='" + verCodigoOrigen(jcmbDestino.getSelectedItem().toString()) + "',"
                + "VIA_FECHA_SALIDA='" + sal + "',"
                + "VIA_FECHA_LLEGADA='" + lle + "',"
                + "VIA_KILOMETRAJE='" + txtKilometraje.getText().trim() + "',"
                + "VIA_COSTO='" + txtCosto.getText() + "',"
                + "VIA_OBSERVACION='" + txtObser.getText() + "'"
                + "where VIA_CODIGO='" + txtCodigo.getText() + "'";
        limpiartextos();
        bloquearBotonesInicio();
        bloquearInicio();
        try {
            PreparedStatement psd = cc.prepareStatement(sql);
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Se actualizo una fila");
                cargarViajes("");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void verificarViaje() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        String strDate = sdf.format(cal.getTime());

        String fecs;
        Date salida = jdtfechaSalida.getDate();
        int anio = Integer.valueOf(salida.getYear()) + 1900;
        String mes = String.valueOf(salida.getMonth() + 1);
        String fecha = String.valueOf(salida.getDate());
        fecs = anio + "-" + mes + "-" + fecha + " " + txtHoraSalida.getText().trim();

        String sql2 = "select via_fecha_salida from viajes where via_fecha_salida between '" + strDate + "' and '" + fecs + "'"
                + "and  aut_placa ='" + jcmbPlaca.getSelectedItem().toString() + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql2);
            if (rs.next() == true) {
                JOptionPane.showMessageDialog(null, "El auto no puede ser ocupado");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }
    public void solonumeros(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
    }

    public void desbloquearDatos() {
        jcmbPlaca.setEnabled(true);
        jcmbOrigen.setEnabled(true);
        jcmbDestino.setEnabled(true);
        jdtfechaSalida.setEnabled(true);
        jdtfechaSalida.setEnabled(true);
        txtHoraSalida.setEnabled(true);
        txtKilometraje.setEnabled(true);
        txtCosto.setEnabled(true);
        txtHoraLLegada.setEnabled(true);
        txtObser.setEnabled(true);
    }

    public void bloquearBotonesGuardar() {
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnSalir.setEnabled(true);

    }

    public void bloquearBotonesActualizar() {
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnSalir.setEnabled(true);

    }

    public void limpiartextos() {
        txtCosto.setText("");
        txtHoraLLegada.setText("");
        txtHoraSalida.setText("");
        txtKilometraje.setText("");
        txtObser.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblViajes = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jcmbPlaca = new javax.swing.JComboBox();
        jcmbOrigen = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtKilometraje = new javax.swing.JTextField();
        jdtFechaLlegada = new com.toedter.calendar.JDateChooser();
        jdtfechaSalida = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jcmbDestino = new javax.swing.JComboBox();
        txtHoraSalida = new javax.swing.JTextField();
        txtHoraLLegada = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtObser = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblViajes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblViajes);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
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

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                .addGap(6, 6, 6)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnSalir))
                .addGap(154, 154, 154))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setText("Código:");

        jcmbPlaca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione" }));

        jcmbOrigen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione" }));

        jLabel2.setText("Origen: ");

        jLabel1.setText("Placa:");

        jLabel4.setText("Fecha Salida:");

        jLabel5.setText("Fecha LLegada: ");

        jLabel6.setText("Kilometraje:");

        txtKilometraje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKilometrajeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKilometrajeKeyTyped(evt);
            }
        });

        jLabel3.setText("Destino:");

        jcmbDestino.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione" }));
        jcmbDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmbDestinoActionPerformed(evt);
            }
        });

        txtHoraSalida.setToolTipText("HH:MM:SS");
        txtHoraSalida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHoraSalidaKeyTyped(evt);
            }
        });

        txtHoraLLegada.setToolTipText("HH:MM:SS");
        txtHoraLLegada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHoraLLegadaKeyTyped(evt);
            }
        });

        jLabel8.setText("Hora:");

        jLabel7.setText("Hora:");

        jLabel11.setText("Costo:");

        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoKeyTyped(evt);
            }
        });

        jLabel12.setText("Observación:");

        txtObser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtObserFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(42, 42, 42)
                        .addComponent(jdtfechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel10))
                        .addGap(77, 77, 77)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcmbOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcmbPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdtFechaLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtObser, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKilometraje, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel8)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jcmbDestino, 0, 107, Short.MAX_VALUE)
                                .addComponent(txtHoraSalida)
                                .addComponent(txtHoraLLegada))
                            .addComponent(txtCosto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcmbPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(21, 21, 21))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jcmbOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jdtfechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jdtFechaLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtKilometraje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(txtObser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jcmbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHoraLLegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel9.setText("Buscar:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setText("Gestion de Viajes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(275, 275, 275)
                                .addComponent(jLabel13))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 328, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jcmbDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmbDestinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcmbDestinoActionPerformed

    private void txtKilometrajeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKilometrajeKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtKilometrajeKeyReleased

    private void txtKilometrajeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKilometrajeKeyTyped
        // TODO add your handling code here:
        char caracter = evt.getKeyChar();
        // Verificar si la tecla pulsada no es un digito
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
            if (txtKilometraje.getText().length() == 2) {
                //System.out.println("ok");
                txtKilometraje.setText(txtKilometraje.getText() + ":");
            }
            evt.consume();  // ignorar el evento de teclado

        }
    }//GEN-LAST:event_txtKilometrajeKeyTyped

    private void txtHoraSalidaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoraSalidaKeyTyped
        // TODO add your handling code here:
        char caracter = evt.getKeyChar();

        if (txtHoraSalida.getText().length() == 2) {
            if (Integer.valueOf(txtHoraSalida.getText()) > 24) {
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese una hora valida");
                txtHoraSalida.setText("");
            } else {
                txtHoraSalida.setText(txtHoraSalida.getText() + ":");
            }

        }
        if (txtHoraSalida.getText().length() == 5) {
            String minutos = txtHoraSalida.getText().substring(3, txtHoraSalida.getText().length());
            //System.out.println(minutos);
            if (Integer.valueOf(minutos) > 59) {
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese minutos validos");
                txtHoraSalida.setText(txtHoraSalida.getText().substring(0, txtHoraSalida.getText().length() - 2));
            } else {
                txtHoraSalida.setText(txtHoraSalida.getText() + ":");
            }
        }
        if (txtHoraSalida.getText().length() >= 8) {
            String segundos = txtHoraSalida.getText().substring(6, txtHoraSalida.getText().length());
            if (Integer.valueOf(segundos) > 59) {
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese minutos validos");
                txtHoraSalida.setText(txtHoraSalida.getText().substring(0, txtHoraSalida.getText().length() - 2));
            } else {
                evt.consume();
            }

        }
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
            if (txtKilometraje.getText().length() == 2) {
                System.out.println("ok");
                txtKilometraje.setText(txtKilometraje.getText() + ":");
            }
            evt.consume();  // ignorar el evento de teclado

        }

    }//GEN-LAST:event_txtHoraSalidaKeyTyped

    private void txtHoraLLegadaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoraLLegadaKeyTyped
        // TODO add your handling code here:
        char caracter = evt.getKeyChar();
        if (txtHoraLLegada.getText().length() == 2) {
            if (Integer.valueOf(txtHoraLLegada.getText()) > 24) {
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese una hora valida");
                txtHoraLLegada.setText("");
            } else {
                txtHoraLLegada.setText(txtHoraLLegada.getText() + ":");
            }

        }
        if (txtHoraLLegada.getText().length() == 5) {
            String minutos = txtHoraLLegada.getText().substring(3, txtHoraLLegada.getText().length());
            //System.out.println(minutos);
            if (Integer.valueOf(minutos) > 59) {
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese minutos validos");
                txtHoraLLegada.setText(txtHoraLLegada.getText().substring(0, txtHoraLLegada.getText().length() - 2));
            } else {
                txtHoraLLegada.setText(txtHoraLLegada.getText() + ":");
            }
        }
        if (txtHoraLLegada.getText().length() >= 8) {
            String segundos = txtHoraLLegada.getText().substring(6, txtHoraLLegada.getText().length());
            if (Integer.valueOf(segundos) > 59) {
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese minutos validos");
                txtHoraLLegada.setText(txtHoraLLegada.getText().substring(0, txtHoraLLegada.getText().length() - 2));
            } else {
                evt.consume();
            }

        }
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {

            evt.consume();  // ignorar el evento de teclado

        }

    }//GEN-LAST:event_txtHoraLLegadaKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
//        Guargar();
        GuardarDatos();
        limpiartextos();
        bloquearBotonesInicio();
        bloquearInicio();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        txtCodigo.setEnabled(false);
        actualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        eliminar();
        bloquearBotonesInicio();
        limpiartextos();
        bloquearInicio();
        cargarViajes("");
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        desbloquearBotonNuevo();
        desbloquearDatos();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiartextos();
        bloquearInicio();
        bloquearBotonesInicio();
//        limpiarTextos();
//        bloquearTextos();
//        bloquearBotonesGuardar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        cargarViajes(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyTyped
        // TODO add your handling code here:
        solonumeros(evt);
    }//GEN-LAST:event_txtCostoKeyTyped

    private void txtObserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtObserFocusLost
        // TODO add your handling code here:
        trasformarMayusc(txtObser);
    }//GEN-LAST:event_txtObserFocusLost

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
            java.util.logging.Logger.getLogger(viajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viajes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcmbDestino;
    private javax.swing.JComboBox jcmbOrigen;
    private javax.swing.JComboBox jcmbPlaca;
    private com.toedter.calendar.JDateChooser jdtFechaLlegada;
    public com.toedter.calendar.JDateChooser jdtfechaSalida;
    private javax.swing.JTable tblViajes;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtHoraLLegada;
    private javax.swing.JTextField txtHoraSalida;
    private javax.swing.JTextField txtKilometraje;
    private javax.swing.JTextField txtObser;
    // End of variables declaration//GEN-END:variables
}
