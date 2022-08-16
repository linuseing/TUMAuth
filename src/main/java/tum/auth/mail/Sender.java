package tum.auth.mail;

import org.simplejavamail.api.email.CalendarMethod;
import org.simplejavamail.api.email.ContentTransferEncoding;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import tum.auth.TumAuth;

import static jakarta.mail.Message.RecipientType.BCC;
import static org.simplejavamail.api.email.ContentTransferEncoding.BASE_64;

public class Sender {
    private TumAuth tumAuth;
    private Mailer mailer;

    public Sender(TumAuth tumAuth) {
        this.tumAuth = tumAuth;
        this.mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "tumauth", "fysEqWPWXV2fP97")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .clearEmailValidator()
                .withDebugLogging(true)
                .async()
                .buildMailer();
    }

    public void sendAuthCode(String code, String address) {
        Email email = EmailBuilder.startingBlank()
                .to("linus eing", address)
                .from("tumauth@gmail.com")
                .withSubject("Your TUM MC authentication code")
                .withHTMLText("<h1>"+code+"</h1>")
                .withPlainText("Please view this email in a modern email client!")
                .withHeader("X-Priority", 5)
                .withReturnReceiptTo()
                .withDispositionNotificationTo("notify-read-emails@candyshop.com")
                .withBounceTo("tech@candyshop.com")
                .withContentTransferEncoding(BASE_64)
                .buildEmail();

        mailer.sendMail(email);
    }

    public Mailer getMailer() {
        return mailer;
    }
}
