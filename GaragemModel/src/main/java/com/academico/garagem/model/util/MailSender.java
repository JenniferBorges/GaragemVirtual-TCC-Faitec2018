package com.academico.garagem.model.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    private String mailID;
    private String mailPass;
    private final String mailSMTPServer;
    private final String mailSMTPServerPort;

    /*
	 * quando instanciar um Objeto ja sera atribuido o servidor SMTP do GMAIL
	 * e a porta usada por ele
     */
    public MailSender() { //Para o Outlook
        mailID = "garagemvirtualfai@gmail.com";
        mailPass = "jjlmgaragem2018";
        mailSMTPServer = "smtp.gmail.com";
        mailSMTPServerPort = "465";
    }

    /*
	 * caso queira mudar o servidor e a porta, so enviar para o contrutor
	 * os valor como string
     */
    public MailSender(String mailSMTPServer, String mailSMTPServerPort) { //Para outro Servidor
        this.mailSMTPServer = mailSMTPServer;
        this.mailSMTPServerPort = mailSMTPServerPort;
    }

    public void sendMail(String to, String subject, String message) throws Exception {

        Properties props = new Properties();

        // quem estiver utilizando um SERVIDOR PROXY descomente essa parte e atribua as propriedades do SERVIDOR PROXY utilizado
        /*
                props.setProperty("proxySet","true");
                props.setProperty("socksProxyHost","192.168.155.1"); // IP do Servidor Proxy
                props.setProperty("socksProxyPort","1080");  // Porta do servidor Proxy
         */
        props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailSMTPServer); //server SMTP do GMAIL
        props.put("mail.smtp.auth", "true"); //ativa autenticacao
        props.put("mail.smtp.user", mailID); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", mailSMTPServerPort); //porta
        props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        //Cria um autenticador que sera usado a seguir
        SimpleAuth auth = new SimpleAuth(mailID, mailPass);

        //Session - objeto que ira realizar a conexão com o servidor
        /*Como há necessidade de autenticação é criada uma autenticacao que é responsavel por solicitar e retornar o usuário e senha para autenticação */
        Session session = Session.getDefaultInstance(props, auth);
        session.setDebug(true); //Habilita o LOG das ações executadas durante o envio do email

        //Objeto que contém a mensagem
        Message msg = new MimeMessage(session);

        //Setando o destinatário
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        //Setando a origem do email
        msg.setFrom(new InternetAddress(mailID));
        //Setando o assunto
        msg.setSubject(subject);
        //Setando o conteúdo/corpo do email
        msg.setContent(message, "text/html; charset=utf-8");

        //Objeto encarregado de enviar os dados para o email
        Transport tr;

        tr = session.getTransport("smtp"); //define smtp para transporte
        /*
		 *  1 - define o servidor smtp
		 *  2 - seu nome de usuario do gmail
		 *  3 - sua senha do gmail
         */
        tr.connect(mailSMTPServer, mailID, mailPass);
        msg.saveChanges(); // don't forget this
        //envio da mensagem
        tr.sendMessage(msg, msg.getAllRecipients());
        tr.close();

    }
}
