package com.example.school.auth.service;

import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.auth.config.util.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;
//    private final MailDao mailDao;

    private final RedisUtils redisUtils; //redis 관련

    @Autowired
    public MailService(JavaMailSender javaMailSender, RedisUtils redisUtils )
    {
        this.javaMailSender = javaMailSender;
        this.redisUtils = redisUtils;
    }




//    private MimeMessage createMessage(String code, String email) throws Exception{
//        MimeMessage message = javaMailSender.createMimeMessage();
//
//        System.out.println("Email address: " + email);
//        message.addRecipients(Message.RecipientType.TO, extractEmailAddress(email));
//        message.setSubject("지금 우리 학교는 인증 번호입니다.");
//        message.setText("이메일 인증코드: "+code);
//
//        message.setFrom("5959kop@naver.com"); //보내는사람.
//
//        return  message;
//    }
    private MimeMessage createMessage(String code, String email) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        System.out.println("Email address: " + email);
        message.addRecipients(Message.RecipientType.TO, extractEmailAddress(email));
        message.setSubject("지금 우리 학교는 인증 번호입니다.");
        message.setText("이메일 인증코드: " + code);

        message.setFrom("5959kop@naver.com"); // 보내는 사람.

        return message;
    }


//    private String extractEmailAddress(String emailJson) {
//
//        // JSON 형태의 문자열에서 이메일 주소 추출
//        return emailJson.replaceAll("[{}\"]", "").split(":")[1].trim();
//    }

    private String extractEmailAddress(String emailJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(emailJson);
        return jsonNode.get("email").asText();
    }

    public void sendMail(String code, String email) throws Exception{

        try{
            MimeMessage mimeMessage = createMessage(code, email);
            javaMailSender.send(mimeMessage);
        }catch (MailException mailException){
            mailException.printStackTrace();
            throw   new IllegalAccessException();
        }
    }

    public String sendCertificationMail(String email)  throws GeneralException {

        try{
            String code = UUID.randomUUID().toString().substring(0, 6); //랜덤 인증번호 uuid를 이용!
            sendMail(code,email);

            redisUtils.setDataExpire(extractEmailAddress(email),code,60*3L); // {key,value} 3분동안 저장.

            // 추가된 로깅
            System.out.println("Data stored in Redis - Email: " + email + ", Code: " + code);

            return  code;
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GeneralException(ErrorStatus.REDIS_ERROR);
        }
    }

    public void verifyCertificationCode(String email, String authCode) throws GeneralException {
        try {
            System.out.println("Before fetching code - Email: " + email);
            System.out.println(authCode);
            String storedCode = redisUtils.getData(email);
            System.out.println("Fetched code - Email: " + email + ", Code: " + storedCode);

            if (storedCode == null || !storedCode.equals(authCode)) {
                throw new GeneralException(ErrorStatus.EMAIL_CODE_ERROR);
            }

            // 인증 성공 시 해당 이메일의 Redis 데이터 삭제
            redisUtils.deleteData(email);

            // 추가된 로깅
            System.out.println("Verification successful - Email: " + email);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new GeneralException(ErrorStatus.REDIS_ERROR);
        }
    }

}