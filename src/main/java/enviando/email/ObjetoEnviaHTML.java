package enviando.email;



import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class ObjetoEnviaHTML {
	
	private String username = "yourEmail";
	private String password = "yourPassword";
	private String listaDestinatarios = "";
	private String nomeRemtente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	public ObjetoEnviaHTML(String listaDestinarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinarios;
		this.nomeRemtente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}
			
	
	public void EnviarEmail(boolean envioHTML) throws Exception {
		
		Properties properties = new Properties();
		//properties.put("mail.smtp.ssl.trust", "*");    // ajuste1
		properties.put("mail.smtp.auth", "true"); // Autorização
		properties.put("mail.smtp.starttls", "true"); // autenticacao
		properties.put("mail.smtp.host", "smtp.gmail.com"); // servidor gmail
		properties.put("mail.smtp.port", "465"); // porta servidor
		properties.put("mail.smtp.socketFactory.port", "465"); // especifica a port a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // classe socket de conexao ao SMTP
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {					
				return new PasswordAuthentication(username, password);
			}
		});
		
		// System.out.println(session);																						
	
		Address [] toUsers = InternetAddress.parse(listaDestinatarios);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username, nomeRemtente)); // quem esta enviando
		message.setRecipients(Message.RecipientType.TO, toUsers);  // email de destino: array de emails	 
		message.setSubject(assuntoEmail); // assunto do email, titulo
		
		if (envioHTML) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			message.setText(textoEmail);
		}
		
		Transport.send(message);
		//Thread.sleep(5000); // caso o email não seja enviado , configura um tempo de espera
	}

}
