-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- 主機: localhost
-- 建立日期: Apr 16, 2010, 06:39 
-- 伺服器版本: 5.1.37
-- PHP 版本: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 資料庫: `hospital_rec`
--

-- --------------------------------------------------------

--
-- 資料表格式： `allergy`
--

CREATE TABLE IF NOT EXISTS `allergy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=23 ;

--
-- 列出以下資料庫的數據： `allergy`
--

INSERT INTO `allergy` (`id`, `name`, `description`) VALUES
(3, 'Allergic rhinitis', 'Allergic rhinitis is a common chronic respiratory condition marked by sneezing and a runny and itchy nose and eyes. Allergic rhinitis is caused by breathing in microscopic particles of specific allergens, airborne substances to which an individual is sensitive or allergic. This substance is called an allergen.'),
(4, 'Hay fever', 'Hay fever is a common nasal allergy to airborne substances such as pollens or molds, and the term has also been used for other allergies such as to animal dander. It is very common with an estimated 35 million Americans with pollen allergies alone. Hay fever is also known as "allergic rhinitis".'),
(5, 'Latex allergies', 'A body''s abnormal hypersensitive response to contact with latex which is a natural rubber product found in such things as rubber gloves and balloons. A person''s allergic response to latex may be mild or severe and can range from sneezing to anaphylactic shock. Gloves used by medical professionals tend to be made of latex but due to the increasing incidence of latex allergy, other types of gloves are being increasingly used. '),
(6, 'Drug Allergies', 'An allergy to any drug. Drug allergies can result in any of a number of symptoms. A drug allergy is the where the body''s immune system responds inappropriately to the presence of a drug. The response may be mild such as a skin rash or severe such as anaphylaxis. '),
(7, 'Mold allergies', 'Allergies to airborne or household molds. '),
(8, 'Dust mite allergies', 'Allergy to dust mites in household dust.'),
(9, 'MSG Allergy', 'An MSG allergy is an adverse reaction by the body''s immune system to MSG or food containing MSG. The body''s immune system produces immunoglobulin E (IgE - an antibody) and histamine in response to contact with the allergen. The specific symptoms that can result can vary considerably amongst patients from a severe anaphylactic reaction to asthma, abdominal symptoms, eczema or headaches. '),
(10, 'Sulfite', 'A sulfite allergy is an adverse reaction by the body''s immune system to sulfite or food containing sulfite. The body''s immune system produces immunoglobulin E (IgE - an antibody) and histamine in response to contact with the allergen. The specific symptoms that can result can vary considerably amongst patients from a severe anaphylactic reaction to asthma, abdominal symptoms, eczema or headaches. '),
(11, 'Asthma', 'Home medical testing products possibly related to Asthma:'),
(12, 'Allergic rhinitis', 'Allergic rhinitis is a common chronic respiratory condition marked by sneezing and a runny and itchy nose and eyes. Allergic rhinitis is caused by breathing in microscopic particles of specific allergens, airborne substances to which an individual is sensitive or allergic. This substance is called an allergen.'),
(13, 'Celiac Disease', 'eliac disease is a common genetic disorder that affects the small intestine and the body''s ability to digest and absorb nutrients. Celiac disease can be serious, and if left untreated, can result in such conditions as vitamin and mineral deficiencies, malnutrition, small intestine cancer, and anemia.'),
(14, 'Caffeine Allergy', 'A caffeine allergy is an adverse reaction by the body''s immune system to caffeine or caffeine-containing products. The type and severity of symptoms can vary amongst patients.'),
(15, 'Semen allergy', 'n allergic reaction to the semen of a sexual partner. The reaction may be localized or systemic. '),
(16, 'Latex allergy', 'Latex is a plastic derived from plants, primarily the Hevea Brasiliensis tree in Brazil, which is used in gloves, condoms and other common household goods (mats, elastic, insulation, toys, etc). Latex allergy is common in the general population and often results in localised reactions including skin inflammation, itching and hives or systemic reactions including difficulty breathing or low blood pressure. The best method of treating a latex allergy is to avoid the product all together, or if exposure does occur, an adrenaline injection can avert a fatal outcome'),
(17, 'Rhinitis', 'Nasal lining inflammation leading to runny/blocked nose. '),
(18, 'Peanut allergy', 'Peanut allergies are a category of food allergies, which can result in acute discomfort to life-threatening symptoms. The smallest contact with peanuts can cause hypersensitive people to suffer from inflammation of the lips, mouth and tongue, hypotension (low blood pressure), and even anaphylaxis leading to death. Young children are most sensitive to foods as their immune systems are immature, which makes them more likely to develop food allergies, particularly if they are in the family.'),
(19, 'Insect sting allergy', 'An insect sting allergy is an adverse reaction by the body''s immune system to a sting by an insect such as an ant. Multiple stings increase the risk of a severe reaction or death. The specific symptoms that can result can vary amongst patients. '),
(20, 'Cockroach Allergy', 'A cockroach allergy is an adverse reaction by the body''s immune system to cockroaches, in particular their saliva, outer shell, eggs and feces. The specific symptoms that can result can vary amongst patients. '),
(21, 'Grass pollen Allergy', ' A grass pollen allergy is an adverse reaction by the body''s immune system to pollen produced by various grasses. The specific symptoms that can result can vary amongst patients. '),
(22, 'Type IV Hypersensitivity', 'Type IV hypersensitivity is an exaggerated response by the body''s immune system upon exposure to a particular substance which results in some sort of adverse effect on the body. The immune system response is mediated by T-cells. Symptoms are delayed - usually days after the exposure. There are a number of different manifestations that can occur.');

-- --------------------------------------------------------

--
-- 資料表格式： `dia-allergy_rec`
--

CREATE TABLE IF NOT EXISTS `dia-allergy_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pat_id` int(11) NOT NULL,
  `allergy_id` int(11) NOT NULL,
  `valid` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

--
-- 列出以下資料庫的數據： `dia-allergy_rec`
--

INSERT INTO `dia-allergy_rec` (`id`, `pat_id`, `allergy_id`, `valid`) VALUES
(1, 1, 17, 0),
(2, 1, 11, 1),
(3, 1, 17, 1),
(4, 1, 5, 1);

-- --------------------------------------------------------

--
-- 資料表格式： `dia-disease_rec`
--

CREATE TABLE IF NOT EXISTS `dia-disease_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pat_id` int(11) NOT NULL,
  `dis_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- 列出以下資料庫的數據： `dia-disease_rec`
--


-- --------------------------------------------------------

--
-- 資料表格式： `disease`
--

CREATE TABLE IF NOT EXISTS `disease` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- 列出以下資料庫的數據： `disease`
--


-- --------------------------------------------------------

--
-- 資料表格式： `log`
--

CREATE TABLE IF NOT EXISTS `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(20) NOT NULL,
  `user` varchar(20) NOT NULL,
  `content` varchar(50) NOT NULL,
  `hash_value` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `id_2` (`id`),
  KEY `id_3` (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=248 ;

--
-- 列出以下資料庫的數據： `log`
--


-- --------------------------------------------------------

--
-- 資料表格式： `medicine`
--

CREATE TABLE IF NOT EXISTS `medicine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- 列出以下資料庫的數據： `medicine`
--


-- --------------------------------------------------------

--
-- 資料表格式： `patient_personal`
--

CREATE TABLE IF NOT EXISTS `patient_personal` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `gender` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `address` text COLLATE utf8_unicode_ci NOT NULL,
  `contact_no` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `birthday` date NOT NULL,
  `pic` int(11) NOT NULL,
  `description` text CHARACTER SET latin1,
  PRIMARY KEY (`pid`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=21 ;

--
-- 列出以下資料庫的數據： `patient_personal`
--

INSERT INTO `patient_personal` (`pid`, `name`, `gender`, `address`, `contact_no`, `birthday`, `pic`, `description`) VALUES
(1, 'Carrie Ding C.D', 'F', 'HKU, HK', '12345678', '1900-01-01', 6, 'carrie ding works in hku'),
(2, 'Tang Chi Chun', 'M', 'Sai Ying Pun, Hong Kong', '95206892', '1922-01-01', 6, 'Tang Chi chun Chris is one of the developer'),
(3, 'Wat Chun Pang Gilbert', 'M', 'N.T', '34567812', '1904-04-01', 6, 'Old man'),
(4, 'Apple Chan', 'F', 'Road 9, First Street, Hong Kong', '12348902', '1938-10-02', 6, ''),
(5, 'Chung Man Ho Pizza', 'M', 'Hill Road, Hong Kong', '90892713', '1987-01-01', 6, 'pizza is living in mini'),
(6, 'Wilson Yeung', 'M', 'Kowloon, Hong Kong', '12890312', '2008-01-01', 5, 'Small kid'),
(7, 'Ivy Yu ', 'F', 'CausewayBay, Hong Kong', '90829412', '1975-01-05', 5, 'Ivy loves shopping'),
(8, 'David Chan', 'M', 'Teacher Road, Hong Kong', '12390482', '1945-01-01', 5, 'He is a teacher	'),
(9, 'Xenia Yeung', 'M', 'Hong Kong', '12341234', '1900-01-01', 5, ''),
(10, 'Amy Wong', 'M', 'Malaysia', '12390482', '1946-01-01', 5, ''),
(11, 'KaHo Cheung', 'M', 'Yeung Road, Hong Kong', '90892731', '2003-01-01', 5, ''),
(12, 'Chris Yeung', 'M', 'Wong Street, Hong Kong', '89234562', '1900-10-01', 5, ''),
(13, 'Chocolates Wong', 'F', 'chocolates road, HK', '12390876', '1900-01-01', 5, ''),
(14, 'Jamie Lau', 'F', 'Hong Kong', '90876565', '1900-01-01', 5, ''),
(15, 'Joey Yung', 'F', 'Hong Kong', '12311111', '1900-04-01', 5, ''),
(16, 'Egg Wong', 'M', 'Hong Kong', '90876524', '1900-01-01', 5, ''),
(17, 'Eddie Yeung', 'M', 'N.Y', '90876555', '1900-01-01', 5, ''),
(18, 'Maple Wong', 'M', 'Wilson Street', '78665555', '1900-01-01', 5, ''),
(19, 'Yeung Siu Wai', 'M', 'Hong Kong', '88887652', '1900-01-01', 6, ''),
(20, 'Pizza Box', 'M', 'Bonham Road', '12378652', '1977-01-01', 6, '');

-- --------------------------------------------------------

--
-- 資料表格式： `privilege`
--

CREATE TABLE IF NOT EXISTS `privilege` (
  `Role` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `Read` tinyint(1) NOT NULL,
  `Write` tinyint(1) NOT NULL,
  `Add` tinyint(1) NOT NULL,
  PRIMARY KEY (`Role`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 列出以下資料庫的數據： `privilege`
--

INSERT INTO `privilege` (`Role`, `Read`, `Write`, `Add`) VALUES
('doctor', 1, 1, 1),
('nurse', 1, 0, 0);

-- --------------------------------------------------------

--
-- 資料表格式： `swapped_user`
--

CREATE TABLE IF NOT EXISTS `swapped_user` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `ori_pub_key` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ori_mod` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tmp_card_num` int(11) NOT NULL,
  `isValid` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- 列出以下資料庫的數據： `swapped_user`
--


-- --------------------------------------------------------

--
-- 資料表格式： `t-m_rec`
--

CREATE TABLE IF NOT EXISTS `t-m_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- 列出以下資料庫的數據： `t-m_rec`
--


-- --------------------------------------------------------

--
-- 資料表格式： `tmp_user`
--

CREATE TABLE IF NOT EXISTS `tmp_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pub_key` text COLLATE utf8_unicode_ci NOT NULL,
  `mod` text COLLATE utf8_unicode_ci NOT NULL,
  `isTaken` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=6 ;

--
-- 列出以下資料庫的數據： `tmp_user`
--

INSERT INTO `tmp_user` (`id`, `pub_key`, `mod`, `isTaken`) VALUES
(1, '10001', 'bab4f93f57687d9b1b6c6aa4a27078351f6b24806fa2ccb1368d6f6f8755adeb4dc464ed0f000b4e4e3366783a0e1c736bd4df8d3517a255538b6f6a2547057eafb7ae1cec33972ec54452dacd989afce9559126adc3647aef144993b861c49425a2e91da6a4b0439f448727d7b11e7974ba8d776543f24d7463e2f4dfa69abf', 0);

-- --------------------------------------------------------

--
-- 資料表格式： `treatment`
--

CREATE TABLE IF NOT EXISTS `treatment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `pic` int(11) NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `date_of_issue` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- 列出以下資料庫的數據： `treatment`
--

INSERT INTO `treatment` (`id`, `pid`, `pic`, `description`, `date_of_issue`) VALUES
(1, 1, 5, 'testing', '2010-04-09'),
(2, 1, 5, 'testing', '2010-04-09');

-- --------------------------------------------------------

--
-- 資料表格式： `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `Role` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `pub_key` text COLLATE utf8_unicode_ci NOT NULL,
  `mod` text COLLATE utf8_unicode_ci NOT NULL,
  `pwd` text COLLATE utf8_unicode_ci NOT NULL,
  `RegDate` date NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=27 ;

--
-- 列出以下資料庫的數據： `user`
--

INSERT INTO `user` (`uid`, `Role`, `pub_key`, `mod`, `pwd`, `RegDate`, `username`) VALUES
(6, 'nurse', '10001', 'd9c94de943f8a0ad3138b78c6902e11dbf2ee048d2c89f4d9d0b02a5054a1a913e8fbafb9a20baf9dac2e291226898221376cedc41aa7cd5f4bf95b71b587d49a550513828c759a25619a08a9b19bed229ea5822b72c6f2e5ef373750881ebca3da166e1af364c51d3e0d82e6cdc8a1085bc971f2e0e948b942611e43e0b133d', '289dff07669d7a23de0ef88d2f7129e7', '2010-04-13', 'nurse'),
(26, 'doctor', '10001', '8759348a8ec8110c9ee9ded752b1062fd7d5afe973a160796a381707235972cd7bef1e74a6b51be27ee68e678c0ea7b7b1978c90e18cd9461265508fe9639b730911eff2fdef70ea5249b5bf9c4b1f99f9f7de70aa26d56263ea4f68a36addca806b5c0d34e2477c92f4c02696257c278aedcd68008958d4f2e2ba377d561345', '202cb962ac59075b964b07152d234b70', '2010-04-13', 'doctor');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
