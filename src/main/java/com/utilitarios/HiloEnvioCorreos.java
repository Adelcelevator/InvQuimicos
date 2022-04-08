/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilitarios;

public class HiloEnvioCorreos implements Runnable {

	private final String destino,mensaje;
	private final int id;

	@Override
	public void run() {
		try {
			String report = Reportes.generarCotizacion(id);
			Correo.correoCA(this.destino, "Cotización", this.mensaje, report);
		} catch (Exception e) {
                    System.err.println("ERROR: "+e.getMessage());
                    System.err.println(e);
		}
	}

	public HiloEnvioCorreos(String destino, String mensaje, int id) {
		this.destino = destino;
		this.mensaje = mensaje;
		this.id = id;
	}

}
