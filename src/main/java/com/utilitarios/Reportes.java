/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilitarios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;

import com.modelo.Conexion;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author panchito
 */
public class Reportes implements Serializable {

	private static JasperReport reporte;
	private static final HashMap<String, Object> props = new HashMap<>();
	private static File archivo;
	private static final String dire = "/home/reports/salida.pdf";

	public static String generarCotizacion(int id) throws FileNotFoundException, JRException, NullPointerException {
		Reportes.props.put("ven_id", id);
		Reportes.archivo = new File("/home/reports/cotizacion.jasper");
		if (!Reportes.archivo.exists()) {
			throw new FileNotFoundException();
		}
		Reportes.reporte = (JasperReport) JRLoader.loadObject(Reportes.archivo);
		JasperPrint fabrica = JasperFillManager.fillReport(Reportes.reporte, Reportes.props, Conexion.conectar());
		JasperExportManager.exportReportToPdfFile(fabrica, Reportes.dire);
		return Reportes.dire;
	}
}
