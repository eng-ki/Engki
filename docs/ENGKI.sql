create database ENGKI;
use ENGKI;
-- 부모
CREATE TABLE PARENT (
	parent_id INTEGER 		PRIMARY KEY AUTO_INCREMENT, -- 부모_아이디
	email     VARCHAR(50)   NOT NULL, -- 비밀번호
	password  CHARACTER(64) NOT NULL, -- 이메일
	bitrhday  DATE          NOT NULL, -- 생일
	is_vaild  BOOLEAN       DEFAULT FALSE      -- 이메일인증
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 자녀
CREATE TABLE KID (
	kid_id    INTEGER 		PRIMARY KEY AUTO_INCREMENT, -- 자녀_아이디
	parent_id INTEGER     NOT NULL, -- 부모_아이디
	bitrhday  DATE        NOT NULL, -- 생일
	icon      VARCHAR(20) NULL      -- 아이콘
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 스티커
CREATE TABLE STICKER (
	sticker_id INTEGER 		PRIMARY KEY AUTO_INCREMENT, -- 스티커_아이디
	file_path  VARCHAR(40) NULL      -- 이미지 경로
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 자녀_감정
CREATE TABLE KID_EMOTION (
	kid_id    INTEGER      NOT NULL, -- 자녀_아이디
	timestamp TIMESTAMP    NULL,     -- 타임스탬프
	emotion   VARCHAR(200) NULL      -- 감정
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 자녀_감정
ALTER TABLE KID_EMOTION
	ADD
		CONSTRAINT PK_KID_EMOTION -- 자녀_감정 기본키
		PRIMARY KEY (
			kid_id -- 자녀_아이디
		);

-- 자녀_단어
CREATE TABLE KID_WORD (
	kid_id      INTEGER NOT NULL, -- 자녀_아이디
	word_id     INTEGER NOT NULL, -- 단어_아이디
	count       INTEGER DEFAULT 1,     -- 횟수
	recent_date DATE    NULL      -- 최근학습시간
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 자녀_단어
ALTER TABLE KID_WORD
	ADD
		CONSTRAINT PK_KID_WORD -- 자녀_단어 기본키
		PRIMARY KEY (
			kid_id,  -- 자녀_아이디
			word_id  -- 단어_아이디
		);

-- 자녀_스티커
CREATE TABLE KID_STICKER (
	kid_id     INTEGER NOT NULL, -- 자녀_아이디
	sticker_id INTEGER NOT NULL  -- 스티커_아이디
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 자녀_스티커
ALTER TABLE KID_STICKER
	ADD
		CONSTRAINT PK_KID_STICKER -- 자녀_스티커 기본키
		PRIMARY KEY (
			kid_id,     -- 자녀_아이디
			sticker_id  -- 스티커_아이디
		);

-- 단어_테마
CREATE TABLE WORD (
	word_id  INTEGER	 PRIMARY KEY AUTO_INCREMENT, -- 단어_아이디
	theme_id INTEGER     NOT NULL, -- 테마_아이디
	word     VARCHAR(20) NULL      -- 단어
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테마
CREATE TABLE THEME (
	theme_id INTEGER 	 PRIMARY KEY AUTO_INCREMENT, -- 테마_아이디
	name     VARCHAR(20) NULL,     -- 이름
	icon     VARCHAR(40) NULL      -- 아이콘
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 이미지_바운더리
CREATE TABLE IMAGE_WORD (
	image_id INTEGER      NOT NULL, -- 이미지_아이디
	word_id  INTEGER      NOT NULL, -- 단어_아이디
	boundary VARCHAR(100) NOT NULL  -- 바운더리
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 이미지_바운더리
ALTER TABLE IMAGE_WORD
	ADD
		CONSTRAINT PK_IMAGE_WORD -- 이미지_바운더리 기본키
		PRIMARY KEY (
			image_id, -- 이미지_아이디
			word_id   -- 단어_아이디
		);

-- 이미지
CREATE TABLE IMAGE (
	image_id  INTEGER 	  PRIMARY KEY AUTO_INCREMENT, -- 이미지_아이디
	file_path VARCHAR(40) NULL      -- 이미지_경로
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 이미지_캡션
CREATE TABLE IMAGE_CAPTION (
	image_id INTEGER      NOT NULL, -- 이미지_아이디
	word_id  INTEGER      NOT NULL, -- 단어_아이디
	caption  VARCHAR(100) NULL      -- 캡션
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 이미지_캡션
ALTER TABLE IMAGE_CAPTION
	ADD
		CONSTRAINT PK_IMAGE_CAPTION -- 이미지_캡션 기본키
		PRIMARY KEY (
			image_id, -- 이미지_아이디
			word_id   -- 단어_아이디
		);

-- 이메일인증
CREATE TABLE AUTH (
	parent_id INTEGER     NOT NULL, -- 부모_아이디
	rand      VARCHAR(20) NULL      -- 난수
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 이메일인증
ALTER TABLE AUTH
	ADD
		CONSTRAINT PK_AUTH -- 이메일인증 기본키
		PRIMARY KEY (
			parent_id -- 부모_아이디
		);

-- 자녀
ALTER TABLE KID
	ADD
		CONSTRAINT FK_PARENT_TO_KID -- 부모 -> 자녀
		FOREIGN KEY (
			parent_id -- 부모_아이디
		)
		REFERENCES PARENT ( -- 부모
			parent_id -- 부모_아이디
		);

-- 자녀_감정
ALTER TABLE KID_EMOTION
	ADD
		CONSTRAINT FK_KID_TO_KID_EMOTION -- 자녀 -> 자녀_감정
		FOREIGN KEY (
			kid_id -- 자녀_아이디
		)
		REFERENCES KID ( -- 자녀
			kid_id -- 자녀_아이디
		);

-- 자녀_단어
ALTER TABLE KID_WORD
	ADD
		CONSTRAINT FK_KID_TO_KID_WORD -- 자녀 -> 자녀_단어
		FOREIGN KEY (
			kid_id -- 자녀_아이디
		)
		REFERENCES KID ( -- 자녀
			kid_id -- 자녀_아이디
		);

-- 자녀_단어
ALTER TABLE KID_WORD
	ADD
		CONSTRAINT FK_WORD_TO_KID_WORD -- 단어_테마 -> 자녀_단어
		FOREIGN KEY (
			word_id -- 단어_아이디
		)
		REFERENCES WORD ( -- 단어_테마
			word_id -- 단어_아이디
		);

-- 자녀_스티커
ALTER TABLE KID_STICKER
	ADD
		CONSTRAINT FK_STICKER_TO_KID_STICKER -- 스티커 -> 자녀_스티커
		FOREIGN KEY (
			sticker_id -- 스티커_아이디
		)
		REFERENCES STICKER ( -- 스티커
			sticker_id -- 스티커_아이디
		);

-- 자녀_스티커
ALTER TABLE KID_STICKER
	ADD
		CONSTRAINT FK_KID_TO_KID_STICKER -- 자녀 -> 자녀_스티커
		FOREIGN KEY (
			kid_id -- 자녀_아이디
		)
		REFERENCES KID ( -- 자녀
			kid_id -- 자녀_아이디
		);

-- 단어_테마
ALTER TABLE WORD
	ADD
		CONSTRAINT FK_THEME_TO_WORD -- 테마 -> 단어_테마
		FOREIGN KEY (
			theme_id -- 테마_아이디
		)
		REFERENCES THEME ( -- 테마
			theme_id -- 테마_아이디
		);

-- 이미지_바운더리
ALTER TABLE IMAGE_WORD
	ADD
		CONSTRAINT FK_IMAGE_TO_IMAGE_WORD -- 이미지 -> 이미지_바운더리
		FOREIGN KEY (
			image_id -- 이미지_아이디
		)
		REFERENCES IMAGE ( -- 이미지
			image_id -- 이미지_아이디
		);

-- 이미지_캡션
ALTER TABLE IMAGE_CAPTION
	ADD
		CONSTRAINT FK_IMAGE_TO_IMAGE_CAPTION -- 이미지 -> 이미지_캡션
		FOREIGN KEY (
			image_id -- 이미지_아이디
		)
		REFERENCES IMAGE ( -- 이미지
			image_id -- 이미지_아이디
		);

-- 이메일인증
ALTER TABLE AUTH
	ADD
		CONSTRAINT FK_PARENT_TO_AUTH -- 부모 -> 이메일인증
		FOREIGN KEY (
			parent_id -- 부모_아이디
		)
		REFERENCES PARENT ( -- 부모
			parent_id -- 부모_아이디
		);