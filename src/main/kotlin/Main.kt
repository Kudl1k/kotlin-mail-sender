package org.example

import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


val password: String = "your_password_to_smtp"


fun main() {

    val userName =  "your_email_address@gmail.com"
    val password =  password

    val emailFrom = userName
    val emailTo = "target_email@gmail.com"

    val subject = "subject"
    val text = "text"

    val props = Properties()
    //GMAIL Props
    putIfMissing(props, "mail.smtp.host", "smtp.gmail.com")
    putIfMissing(props, "mail.smtp.socketFactory.port", "465")
    putIfMissing(props, "mail.smtp.auth", "true")
    putIfMissing(props, "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")

    val session = Session.getDefaultInstance(props, object : javax.mail.Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(userName, password)
        }
    })

    session.debug = true

    try {
        val mimeMessage = MimeMessage(session)
        mimeMessage.setFrom(InternetAddress(emailFrom))
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo, false))
        mimeMessage.setText(text)
        mimeMessage.subject = subject
        mimeMessage.sentDate = Date()

        val smtpTransport = session.getTransport("smtp")
        smtpTransport.connect()
        smtpTransport.sendMessage(mimeMessage, mimeMessage.allRecipients)
        smtpTransport.close()
    } catch (messagingException: MessagingException) {
        messagingException.printStackTrace()
    }
}

fun putIfMissing(props: Properties, key: String, value: String) {
    if (!props.containsKey(key)) {
        props[key] = value
    }
}