package com.utilitarios;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Correo implements Serializable {
	private static final long serialVersionUID = -1429807307434656704L;
	private final static MimeMultipart multi = new MimeMultipart();
	private final static MimeBodyPart cuerpo = new MimeBodyPart();
	private final static MimeBodyPart adjunto = new MimeBodyPart();
	private final static Properties props = new Properties();
	private static Session sesion;
	private static MimeMessage mensaje;
	private static Transport trans;

	public static boolean correoCA(String destino, String asunto, String txt_mensaje, String archivo) {
		try {
			props.put("mail.smtp.host", "smtp.mail.yahoo.com");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", 587);
			props.put("mail.smtp.mail.sender", "eduardopato1999afs@yahoo.com");
			props.put("mail.smtp.user", "eduardopato1999afs@yahoo.com");
			props.put("mail.smtp.auth", "true");
			sesion = Session.getInstance(props);

			cuerpo.setText(txt_mensaje);
			adjunto.attachFile(archivo);
			multi.addBodyPart(cuerpo);
			multi.addBodyPart(adjunto);

			mensaje = new MimeMessage(sesion);
			mensaje.setFrom(new InternetAddress(props.getProperty("mail.smtp.mail.sender")));
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
			mensaje.setContent(multi);
			mensaje.setSubject(asunto);
			mensaje.setFlag(Flags.Flag.FLAGGED, true);

			trans = sesion.getTransport("smtp");
			trans.connect((String) props.getProperty("mail.smtp.user"), "kwugbzwllwqnouqf");
			trans.sendMessage(mensaje, mensaje.getAllRecipients());
			trans.close();
			return true;
		} catch (Exception e) {
			System.out.println("ERROR AL ENVIAR EL CORREO: " + e);
			return false;
		}
	}
}
