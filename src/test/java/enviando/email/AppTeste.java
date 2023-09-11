package enviando.email;



public class AppTeste {
	
	@org.junit.Test 
	public void TestEmail() throws Exception {
		
		StringBuilder stringHtml = new StringBuilder();
		stringHtml.append("<b>Hello </b><br/><br/>");
		stringHtml.append("<h1> Teste recebimento email</h1><br/><br/>");
		stringHtml.append("<a target=\"_blank\" href=\"http://g1.com.br\" style=\"color:#2525a7; "
				+ "padding: 14px 25px; text-align:center; text-decoration: none; display: inline-block; "
				+ "border-radius: 30px; fon-size:  20px; font-family: verdana; border: 3px solid green; background: #99DA39;\"> "
				+ "Acesse o portal de noticias</a><br/>");
		stringHtml.append("<span style=\"font-size= 4px\">Ass: Natan do JR Treinamentos</span>");
		
		
		
		
		ObjetoEnviaListaPDF enviaEmailListaPDF = new ObjetoEnviaListaPDF(
				"natan.nbg@gmail.com", 
				"Natan GMBH", 
				"texte assunto", 
				stringHtml.toString());
		
		//enviaEmail.EnviarEmail(true);	
		enviaEmailListaPDF.EnviarEmailListaPDF(true);

	}	
}
