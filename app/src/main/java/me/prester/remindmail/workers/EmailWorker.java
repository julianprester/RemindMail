package me.prester.remindmail.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.security.Security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import me.prester.remindmail.util.Constants;
import me.prester.remindmail.util.JSSEProvider;

public class EmailWorker extends Worker {
    
    public EmailWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Properties props = getProperties();
            Session session = Session.getInstance(props);
            MimeMessage message = new MimeMessage(session);
            message.setSender(new InternetAddress(props.getProperty("mail.smtp.sender")));
            if(getInputData().getBoolean(Constants.KEY_WORKER_DATA_SUBJECT, false)) {
                message.setSubject(getInputData().getString(Constants.KEY_WORKER_DATA_SUBJECT_LINE));
                message.setText(getInputData().getString(Constants.KEY_WORKER_DATA_TEXT));
            } else {
                message.setSubject(getInputData().getString(Constants.KEY_WORKER_DATA_TEXT));
                message.setText("");
            }
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(props.getProperty("mail.smtp.recipient")));
            Transport.send(message, props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    static {
        Security.addProvider(new JSSEProvider());
    }

    private Properties getProperties() {

        Data data = getInputData();

        boolean auth = data.getBoolean(Constants.KEY_WORKER_DATA_AUTH, true);
        int port = data.getInt(Constants.KEY_WORKER_DATA_PORT, 465);
        String security = data.getString(Constants.KEY_WORKER_DATA_SECURITY);

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", data.getString(Constants.KEY_WORKER_DATA_SERVER));
        props.setProperty("mail.smtp.sender", data.getString(Constants.KEY_WORKER_DATA_SENDER));
        props.setProperty("mail.smtp.auth", String.valueOf(auth));
        props.setProperty("mail.smtp.port", String.valueOf(port));
        props.setProperty("mail.smtp.recipient", data.getString(Constants.KEY_WORKER_DATA_RECIPIENT));
        props.setProperty("mail.smtp.quitwait", "false");

        if(auth) {
            props.setProperty("mail.smtp.user", data.getString(Constants.KEY_WORKER_DATA_USERNAME));
            props.setProperty("mail.smtp.password", data.getString(Constants.KEY_WORKER_DATA_PASSWORD));

            if(security != null && security.equals("ssltls")) {
                props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.setProperty("mail.smtp.socketFactory.port", String.valueOf(port));
                props.setProperty("mail.smtp.socketFactory.fallback", "false");
                props.setProperty("mail.smtp.ssl.checkserveridentity", "true");
            } else if(security != null && security.equals("starttls")) {
                props.setProperty("mail.smtp.starttls.enable", "true");
            }

        } else {
            props.setProperty("mail.smtp.localhost", "android.com");
        }

        return props;
    }
}
