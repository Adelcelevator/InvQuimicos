/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.objetos;

import java.security.MessageDigest;
/**
 *
 * @author panchito
 */
public class Encriptador {

	public final static String encriptar(String txt) throws Exception {
		MessageDigest mg = MessageDigest.getInstance("SHA3-512");
		mg.update(txt.getBytes());
		return new String(mg.digest(),"UTF8");
	}
}
