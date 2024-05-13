package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.model.dto.response.StatisticResponse;
import com.rikkei.managementuser.model.entity.Student;
import com.rikkei.managementuser.service.IEmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;
    @Override
    public String sendMail(Student student, StatisticResponse statistic) {
    try {
        MimeMessage message =javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("tung28111998@gmail.com");
        helper.setTo(student.getEmail());
        helper.setSubject("Thông báo về việc nghỉ học của học viên.");

        //Tạo nội dung cho email
        String emailContent = createEmailContent(statistic);

        helper.setText(emailContent, true);

        javaMailSender.send(message);

        return "gửi mail thành công";

    } catch (Exception e) {
        e.printStackTrace();
    }


        return null;
    }

    private String createEmailContent(StatisticResponse statistic) {
        StringBuilder emailContentBuilder = new StringBuilder();

        emailContentBuilder.append("<html><body style=\"font-family: Arial, sans-serif;\">");
        emailContentBuilder.append("<div style=\"background-color: #4caf50; color: #fff; padding: 20px; text-align: center;\">");
        emailContentBuilder.append("<h2 style=\"margin: 0;\"> THÔNG BÁO VỀ VIỆC NGHỈ HỌC CỦA HỌC VIÊN!</h2>");
        emailContentBuilder.append("</div>");
        emailContentBuilder.append("<div style=\"padding: 20px;\">");
        emailContentBuilder.append("<h3 style=\"color: #4caf50;\">Chi tiết :</h3>");
        emailContentBuilder.append("<div style=\"border: 1px solid #ccc; border-radius: 8px; overflow: hidden;\">");
        emailContentBuilder.append("<table style=\"width: 100%; border-collapse: collapse;\">");
        emailContentBuilder.append("<tr style=\"background-color: #4caf50; color: #fff;\"><th style=\"padding: 10px;\">Thông tin</th><th style=\"padding: 10px;\">Chi tiết</th></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Mã sinh viên</td><td style=\"padding: 10px;\">").append(statistic.getStudentId()).append("</td></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Tên sinh viên</td><td style=\"padding: 10px;\">").append(statistic.getStudentName()).append("</td></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Số ngày có mặt</td><td style=\"padding: 10px;\">").append(statistic.getPresent()).append("</td></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Số ngày nghỉ không phép</td><td style=\"padding: 10px;\">").append(statistic.getAbsenceWithOutPermission()).append("</td></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Số ngày nghỉ có phép</td><td style=\"padding: 10px;\">").append(statistic.getAbsenceWithPermission()).append("</td></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Tí lệ nghỉ trong module học</td><td style=\"padding: 10px;\">").append(statistic.getPercentAbsent()).append("%</td></tr>");

//        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
//        String formattedAmount = decimalFormat.format(booking.getTotal());
//        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Tổng tiền</td><td style=\"padding: 10px;\">").append(formattedAmount).append("</td></tr>");

        emailContentBuilder.append("</table>");
        emailContentBuilder.append("</div>");
        emailContentBuilder.append("</div>");
        emailContentBuilder.append("</body></html>");

        return emailContentBuilder.toString();
    }
}
