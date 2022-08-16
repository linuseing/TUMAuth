import org.junit.jupiter.api.Test;
import tum.auth.mail.Sender;

public class Mailer {
    @Test
    public void sendMail() {
        Sender sender = new Sender(null);
        sender.sendAuthCode("abc", "linus@r13.dex");
    }

    @Test
    public void testMailer() {
        Sender sender = new Sender(null);
        sender.getMailer().testConnection();
    }
}
