-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 09, 2010 at 10:37 ¤W¤È
-- Server version: 5.1.41
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `hospital_rec`
--

-- --------------------------------------------------------

--
-- Table structure for table `allergy`
--

CREATE TABLE IF NOT EXISTS `allergy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=30 ;

--
-- Dumping data for table `allergy`
--

INSERT INTO `allergy` (`id`, `name`, `description`) VALUES
(3, 'Insect Allergy', 'Patients must not contact with insects'),
(4, 'Dog Allergy', 'Patients must not contact with dogs'),
(5, 'Cat Allergy', 'Patients must not contact with cats'),
(6, 'Hay Fever', 'Hay fever is a common nasal allergy to airborne substances such as pollens or molds, and the term has also been used for other allergies such as to animal dander. It is very common with an estimated 35 million Americans with pollen allergies alone. Hay fever is also known as "allergic rhinitis".'),
(7, 'Allergic rhinitis', 'Allergic rhinitis is a common chronic respiratory condition marked by sneezing and a runny and itchy nose and eyes. Allergic rhinitis is caused by breathing in microscopic particles of specific allergens, airborne substances to which an individual is sensitive or allergic. This substance is called an allergen.'),
(8, 'Latex allergies', 'A body''s abnormal hypersensitive response to contact with latex which is a natural rubber product found in such things as rubber gloves and balloons. A person''s allergic response to latex may be mild or severe and can range from sneezing to anaphylactic shock. Gloves used by medical professionals tend to be made of latex but due to the increasing incidence of latex allergy, other types of gloves are being increasingly used. '),
(9, 'Insect sting allergies', 'An allergy to an insect sting. An allergy is a body''s abnormal hypersensitive response to the presence of a particular substance which in this case is the chemicals associated in the insect''s sting.'),
(10, 'Mold allergies', 'Allergies to airborne or household molds.'),
(11, 'MSG Allergy', 'Food Allergy -- MSG: An MSG allergy is an adverse reaction by the body''s immune system to MSG or food containing MSG. The body''s immune system produces immunoglobulin E (IgE - an antibody) and histamine in response to contact with the allergen. The specific symptoms that can result can vary considerably amongst patients from a severe anaphylactic reaction to asthma, abdominal symptoms, eczema or headaches. More detailed information about the symptoms, causes, and treatments of Food Allergy -- MSG is available below.'),
(12, 'Sulfite Allergy', 'A sulfite allergy is an adverse reaction by the body''s immune system to sulfite or food containing sulfite. The body''s immune system produces immunoglobulin E (IgE - an antibody) and histamine in response to contact with the allergen. The specific symptoms that can result can vary considerably amongst patients from a severe anaphylactic reaction to asthma, abdominal symptoms, eczema or headaches. More detailed information about the symptoms, causes, and treatments of Food Allergy -- sulfite is available below.'),
(13, 'Dust mite allergies', 'Allergy to dust mites in household dust. More detailed information about the symptoms, causes, and treatments of Dust mite allergies is available below.'),
(14, 'Atopic dermatitis', 'Skin disorder characterized by chronic inflammation, and pruritis. Often hereditary and associated with allergic rhinitis and asthma. More detailed information about the symptoms, causes, and treatments of Atopic dermatitis is available below.'),
(15, 'Nickel Contact Allergy', 'Nickel contact allergy usually refers to an allergic response to nickel which is found in most jewellery. Even high carat gold has some nickel content which may pose problems for some people. Symptoms usually only involve the skin that is in contact with the jewellery. Nickel may also be found in watch straps, belt buckles and jeans studs. More detailed information about the symptoms, causes, and treatments of Nickel contact allergy is available below.'),
(16, 'Caffeine Allergy', 'A caffeine allergy is an adverse reaction by the body''s immune system to caffeine or caffeine-containing products. The type and severity of symptoms can vary amongst patients. More detailed information about the symptoms, causes, and treatments of Caffeine Allergy is available below.'),
(17, 'Heiner Syndrome', 'A disease caused by the precipitation in the blood of antibodies to cow''s milk. More detailed information about the symptoms, causes, and treatments of Heiner syndrome is available below.'),
(18, 'Semen Allergy', 'Semen allergy: An allergic reaction to the semen of a sexual partner. The reaction may be localized or systemic. More detailed information about the symptoms, causes, and treatments of Semen allergy is available below'),
(19, 'Latex Allergy', 'atex is a plastic derived from plants, primarily the Hevea Brasiliensis tree in Brazil, which is used in gloves, condoms and other common household goods (mats, elastic, insulation, toys, etc). Latex allergy is common in the general population and often results in localised reactions including skin inflammation, itching and hives or systemic reactions including difficulty breathing or low blood pressure. The best method of treating a latex allergy is to avoid the product all together, or if exposure does occur, an adrenaline injection can avert a fatal outcome.'),
(20, 'Fish Allergy', 'A fish allergy is an adverse reaction by the body''s immune system to fish or food containing fish. The body''s immune system produces immunoglobulin E (IgE - an antibody) and histamine in response to contact with the allergen. The specific symptoms that can result can vary considerably amongst patients from a severe anaphylactic reaction to asthma, abdominal symptoms, eczema or headaches. More detailed information about the symptoms, causes, and treatments of Food Allergy -- fish is available below.'),
(21, 'Seafood Allergy', ' A seafood allergy is an adverse reaction by the body''s immune system to seafood or food containing seafood. The specific symptoms that can result can vary considerably amongst patients from a severe anaphylactic reaction to asthma, abdominal symptoms, eczema or headaches. More detailed information about the symptoms, causes, and treatments of Seafood allergy is available below.'),
(22, 'Type IV Hypersensitivity', 'Type IV hypersensitivity is an exaggerated response by the body''s immune system upon exposure to a particular substance which results in some sort of adverse effect on the body. The immune system response is mediated by T-cells. Symptoms are delayed - usually days after the exposure. There are a number of different manifestations that can occur. More detailed information about the symptoms, causes, and treatments of Type IV Hypersensitivity is available below.'),
(23, 'Type I Hypersensitivity', 'Type I hypersensitivity is an exaggerated response by the body''s immune system to an allergen which results in some sort of adverse effect on the body. Allergens usually in the form of proteins such as pollen, foods, house dust mite and cat hair. This form of hypersensitivity results in an immediate response by the immune system following exposure. More detailed information about the symptoms, causes, and treatments of Type I Hypersensitivity is available below.'),
(24, 'Wheat Allergy', 'A wheat allergy is an adverse reaction by the body''s immune system to wheat or food containing wheat. The body''s immune system produces immunoglobulin E (IgE - an antibody) and histamine in response to contact with the allergen. The specific symptoms that can result can vary considerably amongst patients from a severe anaphylactic reaction to asthma, abdominal symptoms, eczema or headaches. More detailed information about the symptoms, causes, and treatments of Food Allergy -- wheat is available below.'),
(25, 'Meat Allergy', 'A meat allergy is an adverse reaction by the body''s immune system to meat. This type of allergy is rare and severe reactions are even rarer. The body''s immune system produces immunoglobulin E (IgE - an antibody) and histamine in response to contact with the allergen. The specific symptoms that can result can vary considerably amongst patients from a severe anaphylactic reaction to asthma, abdominal symptoms, eczema or headaches. More detailed information about the symptoms, causes, and treatments of Food Allergy -- meat is available below.'),
(26, 'Fruit Allergy', 'A fruit allergy is an adverse reaction by the body''s immune system to fruit or food containing fruit. This type of allergy is rare and serious reactions are very rare. Cooking the fruit may reduce or eliminate the reaction. The specific symptoms that can result can vary considerably amongst patients e.g. skin, respiratory and behavioral symptoms. More detailed information about the symptoms, causes, and treatments of Food Allergy -- fruit is available below.'),
(27, 'Salicytes', ' A salicylate allergy is an adverse reaction by the body''s immune system to a food additive called salicylate which is used in a number of foods. Salicylates also occur naturally in a wide range of plant foods especially fruits. The specific symptoms that can result can vary considerably amongst patients and may range from mild to severe. More detailed information about the symptoms, causes, and treatments of Food Additive Allergy -- salicytes is available below.'),
(28, 'Allergenic cross-reactivity', 'Studies have indicated that a significant number of people with certain allergies will also have allergic responses to other allergens which have a similar protein. For example patients allergic to birch pollen will often have allergies to plant foods such as apples and peaches. Symptoms can range from mild response to severe allergic reactions. Cross-reactivity tends to have mainly oral allergy symptoms with breathing problems and anaphylactic reactions being extremely rare. Food allergies related to cross-reactivity tend to be less severe than those not related to cross-reactivity. More detailed information about the symptoms, causes, and treatments of Allergenic cross-reactivity is available below.'),
(29, 'Gluten allergy', 'Gluten allergy is an adverse reaction by the body''s immune system to gluten or foods containing gluten. The specific symptoms that can result can vary considerably amongst patients from a severe anaphylactic reaction to asthma, abdominal symptoms, eczema or headaches. Gluten allergy is similar to celiac disease - celiac disease only occurs in people with a genetic defect which predisposes them to the condition whereas gluten can occur in anyone but is more common in people who are also prone to other allergies. ');

-- --------------------------------------------------------

--
-- Table structure for table `dia-allergy_rec`
--

CREATE TABLE IF NOT EXISTS `dia-allergy_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pat_id` int(11) NOT NULL,
  `allergy_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- Dumping data for table `dia-allergy_rec`
--


-- --------------------------------------------------------

--
-- Table structure for table `dia-disease_rec`
--

CREATE TABLE IF NOT EXISTS `dia-disease_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pat_id` int(11) NOT NULL,
  `dis_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- Dumping data for table `dia-disease_rec`
--


-- --------------------------------------------------------

--
-- Table structure for table `disease`
--

CREATE TABLE IF NOT EXISTS `disease` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `disease`
--


-- --------------------------------------------------------

--
-- Table structure for table `log`
--

CREATE TABLE IF NOT EXISTS `log` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `date` varchar(20) NOT NULL,
  `user` varchar(20) NOT NULL,
  `content` varchar(50) NOT NULL,
  KEY `id` (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=69 ;

--
-- Dumping data for table `log`
--

INSERT INTO `log` (`id`, `date`, `user`, `content`) VALUES
(65, '2010/04/09 14:35:15', 'admin', 'Server starts'),
(66, '2010/04/09 14:38:49', 'admin', 'Server starts'),
(67, '2010/04/09 14:53:36', 'admin', 'Server starts'),
(68, '2010/04/09 15:22:01', 'admin', 'Server starts');

-- --------------------------------------------------------

--
-- Table structure for table `medicine`
--

CREATE TABLE IF NOT EXISTS `medicine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `medicine`
--


-- --------------------------------------------------------

--
-- Table structure for table `patient_personal`
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=11 ;

--
-- Dumping data for table `patient_personal`
--

INSERT INTO `patient_personal` (`pid`, `name`, `gender`, `address`, `contact_no`, `birthday`, `pic`, `description`) VALUES
(1, 'Tang Chi Chun Chris', 'M', 'Sai Ying Pun, Hong Kong', '95206712', '1987-05-15', 1, 'Chris Tang is studying in HKU CS'),
(2, 'Chung Man Ho Pizza', 'M', 'Hill Road, Hong Kong', '91230864', '1987-04-08', 1, 'Pizza loves eating pizza'),
(3, 'Wat Chun Pang Gilbert', 'M', 'N.T', '12339082', '1985-04-06', 1, 'Gilbert is one of the developer'),
(4, 'Yeung Siu Wai Wilson', 'M', 'Lee Shau Kee Hall, Hong Kong', '78902132', '1989-04-07', 1, 'Siu Wai is doing documents'),
(5, 'Ivy Yu', 'F', 'CausewayBay, Hong Kong', '96725632', '2004-04-15', 1, 'Ivy loves shopping'),
(6, 'CY Choi', 'M', 'HKU, HK', '12348972', '2009-04-15', 2, 'CY loves programming'),
(7, 'David Chan', 'M', 'Hong Kong', '12389402', '2010-03-08', 2, 'It is one of the tests'),
(8, 'Kaze Lee', 'F', 'Kowloon', '67849012', '2010-02-16', 2, 'It is one of the tests'),
(9, 'Philip', 'M', 'Hong Kong', '34568912', '1987-04-15', 1, 'It is one of the tests'),
(10, 'Larrine Lo', 'F', 'New York, USA', '67819023', '1987-01-04', 1, 'It is one of the tests');

-- --------------------------------------------------------

--
-- Table structure for table `privilege`
--

CREATE TABLE IF NOT EXISTS `privilege` (
  `Role` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `Read` tinyint(1) NOT NULL,
  `Write` tinyint(1) NOT NULL,
  `Add` tinyint(1) NOT NULL,
  PRIMARY KEY (`Role`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `privilege`
--

INSERT INTO `privilege` (`Role`, `Read`, `Write`, `Add`) VALUES
('doctor', 1, 1, 1),
('nurse', 1, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `swapped_rec`
--

CREATE TABLE IF NOT EXISTS `swapped_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `pub_key` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `mod` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `swapped_rec`
--


-- --------------------------------------------------------

--
-- Table structure for table `swapped_user`
--

CREATE TABLE IF NOT EXISTS `swapped_user` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `ori_pub_key` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ori_mod` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isValid` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `swapped_user`
--


-- --------------------------------------------------------

--
-- Table structure for table `t-m_rec`
--

CREATE TABLE IF NOT EXISTS `t-m_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- Dumping data for table `t-m_rec`
--


-- --------------------------------------------------------

--
-- Table structure for table `temp_card`
--

CREATE TABLE IF NOT EXISTS `temp_card` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `pub_key` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `mod` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `temp_card`
--


-- --------------------------------------------------------

--
-- Table structure for table `tmp_user`
--

CREATE TABLE IF NOT EXISTS `tmp_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pub_key` text COLLATE utf8_unicode_ci NOT NULL,
  `mod` text COLLATE utf8_unicode_ci NOT NULL,
  `isTaken` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- Dumping data for table `tmp_user`
--


-- --------------------------------------------------------

--
-- Table structure for table `treatment`
--

CREATE TABLE IF NOT EXISTS `treatment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `pic` int(11) NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `date_of_issue` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- Dumping data for table `treatment`
--


-- --------------------------------------------------------

--
-- Table structure for table `user`
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=4 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`uid`, `Role`, `pub_key`, `mod`, `pwd`, `RegDate`, `username`) VALUES
(3, 'nurse', '10001', 'acf092629501f68269dc268bd8a377b3dbc4f805122ca7a5e13639d260bf21af063e369c80592898a9cabbb6f4ea6fe6ff6f4171aba8f965a24491f1a9c36f83cfe665ba74bf4acec776a47bf3866be6656ad91af4c53fdb938db7f2eb0633b02b5b88c819bcc23234aadab6db8e2d814c6e51602c46c75e36f06266be34d821', '098f6bcd4621d373cade4e832627b4f6', '2010-04-09', 'test');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
