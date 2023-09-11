package enviando.email;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaListaPDF {
	
	private String username = "yourEmail";
	private String password = "yourPassword";
	private String listaDestinatarios = "";
	private String nomeRemtente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	public ObjetoEnviaListaPDF(String listaDestinarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinarios;
		this.nomeRemtente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}
			
	
	public void EnviarEmailListaPDF(boolean envioHTML) throws Exception {

		Properties properties = new Properties();
		// properties.put("mail.smtp.ssl.trust", "*"); // ajuste1
		properties.put("mail.smtp.auth", "true"); // Autorização
		properties.put("mail.smtp.starttls", "true"); // autenticacao
		properties.put("mail.smtp.host", "smtp.gmail.com"); // servidor gmail
		properties.put("mail.smtp.port", "465"); // porta servidor
		properties.put("mail.smtp.socketFactory.port", "465"); // especifica a port a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // classe socket de conexao
																							// ao SMTP

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		// System.out.println(session);

		Address[] toUsers = InternetAddress.parse(listaDestinatarios);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username, nomeRemtente)); // quem esta enviando
		message.setRecipients(Message.RecipientType.TO, toUsers); // email de destino: array de emails
		message.setSubject(assuntoEmail); // assunto do email, titulo

		
		/* Parte 1 do email: o texto e a descrição do Email*/
		MimeBodyPart corpoEmail = new MimeBodyPart();
		
		
		if (envioHTML) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			corpoEmail.setText(textoEmail);
		}
		
		List<FileInputStream> arquivos = new ArrayList<FileInputStream>(); 
		arquivos.add(simuladorDePDF1()); /*certificado*/
		arquivos.add(simuladorDePDF2()); /*nota fiscal*/
		arquivos.add(simuladorDePDF3()); /* etc..*/
		
		
		
		Multipart multipart = new MimeMultipart();
		/* Corpo do email */
		multipart.addBodyPart(corpoEmail);
		/* Inserindo varios anexos */
		int indexPDF = 1;
		for (FileInputStream fileInputStream : arquivos) {		
			/* Parte 2 do email: anexos do PDF */
			MimeBodyPart anexoEmail = new MimeBodyPart();
			/*
			 * Onde é passado o simulador de PDF pode ser passado o arquivo gravdo no banco
			 * de dados
			 */
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("anexoEmail"+indexPDF+".pdf");
			multipart.addBodyPart(anexoEmail);
			indexPDF++;
		}
		
		message.setContent(multipart);
		Transport.send(message);
		// Thread.sleep(5000); // caso o email não seja enviado , configura um tempo de
		// espera
	}
	
	
	/**  Esse metodo simula o PDF ou qualqer arquivo que possa ser enviado por anexo no email
	 *   Voce pode pegar o arquivo no seu banco de dados base64, byte[], Stream de Arquivos
	 *   Pode estar no banco de dados, ou em uma pasta. 
	 *   O metodo retorna um PDF com um paragrafo de exemplo
	 * @throws Exception 
	 * */	
	private FileInputStream simuladorDePDF1() throws Exception {
		Document document= new Document();
		File file = new File("Arquivo-Anexo1");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file)); 
		document.open();
		document.add(new Paragraph("Conteudo AAAAAAAAAAAA"));
		document.close();
		return new FileInputStream(file); 		
	}
	
	private FileInputStream simuladorDePDF2() throws Exception {
		Document document = new Document();
		File file = new File("Arquivo-Anexo2");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file)); 
		document.open();
		document.add(new Paragraph("Conteudo BBBBBBBBBBBBBB"));
		document.close();
		return new FileInputStream(file); 		
	}
	
	private FileInputStream simuladorDePDF3() throws Exception {
		Document document = new Document();
		File file = new File("Arquivo-Anexo3");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file)); 
		document.open();
		document.add(new Paragraph("Conteudo CCCCCCCCCCCCCCC"));
		document.close();
		return new FileInputStream(file); 		
	}
	
	
}
