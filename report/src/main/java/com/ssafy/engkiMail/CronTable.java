package com.ssafy.engkiMail;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.engkiMail.dto.ReportDto;
import com.ssafy.engkiMail.service.EmailService;
import com.ssafy.engkiMail.service.ReportService;

@Component
public class CronTable {

	@Autowired
	private EmailService emailService;
	@Autowired
	private ReportService reportServcice;

	// 매일 22시 0분 0초에 실행한다.
	@Scheduled(cron = "0 0 22 * * *")
	public void aJob() {

		List<ReportDto> reportList = reportServcice.selectAllKids();

		for (ReportDto report : reportList) {
			int kid_id = report.getKid_id();
			List<String> words = reportServcice.selectWordByKidId(kid_id);
			report.setWords(words);
			String emotion = reportServcice.selectEmotionByKidId(kid_id);
			report.setEmotion(emotion);
			String to = report.getEmail();
			String subject = "Today's Report for " + report.getKid_name() + " from Engki";
			StringBuilder sb = new StringBuilder();
			sb.append("Today's Report for " + report.getKid_name() + "\n")
			.append("<오늘의 학습 단어> \n" + report.getWords()+"\n")
			.append("<오늘의 학습 감정> \n" + report.getEmotion()+"\n")
			.append("\n\n 아이를 칭찬 해주세요~!")
			.append("\n\n\n\n👧 잉키 : English for kids 👦\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "사진으로 즐겁게, 감정 인식으로 똑똑하게 ✏\r\n"
					+ "기분 변화에 따라 학습 시간을 조절해주는 영어 선생님 티키 🐦\r\n"
					+ "\r\n"
					+ "Made by. Team 디키 (Developers for kids)");
			String text = sb.toString();
			emailService.sendSimpleMessage(to, subject, text);
//			System.out.println(report.getKid_name() + " 전송 완료");
		}
		System.out.println(reportList.size() + " 개의 레포트 전송 완료");
	}
}