package Options;//package Options;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class SendEmail {

    /**
     * Represents the subject line of the email to be sent.
     * This field is used to store the content of the email subject,
     * which is typically a brief description of the email's content.
     */
    static String emailSubject;
    /**
     * Represents the body content of an email message to be sent.
     * This string variable stores the text or HTML content that forms the main part of the email.
     * It is intended to be set with the specific message content during the email creation process.
     */
    static String emailBody;
    /**
     * Represents the email address of the sender.
     * It is used to specify the sender's identity in email communications.
     * This variable is immutable and its value is assigned during the initialization of the SendEmail object.
     */
    final String senderEmail;
    /**
     * The password of the sender's email account.
     * This is used for authentication when sending an email through the specified SMTP server.
     * <p>
     * It is recommended to handle this field securely to prevent unauthorized access.
     */
    final String senderPassword;
    /**
     * Represents the SMTP server address used for sending emails.
     * This field specifies the SMTP (Simple Mail Transfer Protocol)
     * server to be utilized for email transmission.
     * <p>
     * In this implementation, it is set to "smtp.gmail.com", which
     * is the SMTP server for Gmail.
     */
    final String emailSMTPserver = "smtp.gmail.com";
    /**
     * Specifies the port number used by the email server for establishing an SMTP connection.
     * Typically, email servers use port 465 for secure communication over SSL/TLS.
     */
    final String emailServerPort = "465";
    /**
     * Represents the state of the email-sending process.
     * It indicates whether the email operation is active or inactive.
     */
    public boolean state;
    /**
     * Represents the email address of the receiver to whom the email will be sent.
     * This variable stores the destination email address and is typically provided
     * during the initialization of the email sending process.
     */
    String receiverEmail = null;
    /**
     * Represents the thread responsible for managing the state of message handling
     * in the SendEmail process. This thread may be used to perform asynchronous
     * operations or maintain the progress/state of the email sending procedure.
     */
    private Thread msgState;
    /**
     * Represents the logging type categorization used within the SendEmail process.
     * This variable determines the type or level of logging applied during the email sending operation.
     * Typically used internally for debugging or tracking the execution status.
     */
    private int logTyped = 0;
    /**
     * Indicates whether the email sending process has been completed.
     * This variable is used to track the status of the task within the class.
     */
    private boolean finished = true;

    /**
     * Constructs a SendEmail object and sends an email using the specified parameters.
     *
     * @param SenderEmail the email address of the sender
     * @param SenderPassword the password for the sender's email account
     * @param receiverEmail the email address of the receiver
     * @param subject the subject line of the email
     * @param body the body content of the email
     * @param withAttach a flag indicating whether an attachment should be included
     * @param AttachmentPath the file path to the attachment (if any)
     */
    public SendEmail(String SenderEmail, String SenderPassword, String receiverEmail, String subject, String body, boolean withAttach, String AttachmentPath) {
        this.senderEmail = SenderEmail;
        this.senderPassword = SenderPassword;
        this.receiverEmail = receiverEmail;
        emailSubject = subject;
        emailBody = body;
        Properties props = new Properties();
        props.put("mail.smtp.user", senderEmail);
        props.put("mail.smtp.host", emailSMTPserver);
        props.put("mail.smtp.port", emailServerPort);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", emailServerPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
//        SecurityManager security = System.getSecurityManager();
        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);
            Multipart multiPart = new MimeMultipart();
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(emailBody);
            multiPart.addBodyPart(textPart);
            if (withAttach) {
                MimeBodyPart filePart = new MimeBodyPart();
                filePart.attachFile(new File(AttachmentPath));
                multiPart.addBodyPart(filePart);
            }
            msg.setSubject(emailSubject);
            msg.setFrom(new InternetAddress(senderEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            msg.setContent(multiPart);
            ThreadFunc();
            state = false;
            Transport.send(msg);
            state = true;
            msgState.interrupt();
            finished = false;
            System.out.println("Message sent successfully");
        } catch (java.net.UnknownHostException exx) {
            System.out.println("Check Internet Connection");
        } catch (Exception e) {
            if (e.getMessage().contains("Username and Password not accepted")) {
                MyOptions.showAlert("Warning", "Sending Failed", "", "Please reActivate links");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * Manages a thread responsible for monitoring and signaling the state of message transmission.
     * The thread periodically checks the current state and logs the progress of the message-sending process.
     * If the state indicates that the message has been sent successfully, the thread terminates.
     *
     * Functionality:
     * - Continuously checks the `state` and `logTyped` flags to determine the status.
     * - Logs "Sending..." when the message sending process starts.
     * - Logs "Message Sent Successfully" upon successful transmission.
     * - Interrupts the thread upon completion of the sending process.
     * - Sleeps for 1 second between status checks to reduce processing load.
     *
     * This method should be called to initiate and manage the asynchronous message transmission process.
     */
    private void ThreadFunc() {
        msgState = new Thread() {
            public void run() {
                while (true && finished) {
                    if (state == false && logTyped == 0) {
                        System.out.println("Sending...");
                        logTyped = 1;
                    } else if (state == true) {
                        System.out.println("Message Sent Successfully");
                        msgState.interrupt();
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {

                    }
                }
            }
        };
        msgState.start();
    }

    /**
     * This class is responsible for authenticating the sender's email credentials
     * when establishing a connection with the SMTP server.
     *
     * It extends `javax.mail.Authenticator` and provides an implementation for
     * retrieving the sender's email address and password required for email
     * authentication during the email sending process.
     */
    public class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
        }
    }
}
