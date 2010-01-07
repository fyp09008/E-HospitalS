-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- ??: localhost
-- ????: Jan 03, 2010, 10:19 AM
-- ?????: 5.1.37
-- PHP ??: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- ???: `hospital_rec`
--

-- --------------------------------------------------------

--
-- ?????: `allergy`
--

DROP TABLE IF EXISTS `allergy`;
CREATE TABLE IF NOT EXISTS `allergy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- ??????????: `allergy`
--

INSERT INTO `allergy` (`id`, `name`, `description`) VALUES
(1, 'allergy A', NULL),
(2, 'allergy B', NULL);

-- --------------------------------------------------------

--
-- ?????: `dia-allergy_rec`
--

DROP TABLE IF EXISTS `dia-allergy_rec`;
CREATE TABLE IF NOT EXISTS `dia-allergy_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pat_id` int(11) NOT NULL,
  `allergy_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- ??????????: `dia-allergy_rec`
--


-- --------------------------------------------------------

--
-- ?????: `dia-disease_rec`
--

DROP TABLE IF EXISTS `dia-disease_rec`;
CREATE TABLE IF NOT EXISTS `dia-disease_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pat_id` int(11) NOT NULL,
  `dis_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- ??????????: `dia-disease_rec`
--


-- --------------------------------------------------------

--
-- ?????: `disease`
--

DROP TABLE IF EXISTS `disease`;
CREATE TABLE IF NOT EXISTS `disease` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- ??????????: `disease`
--

INSERT INTO `disease` (`id`, `name`, `description`) VALUES
(1, 'disease A', NULL),
(2, 'disease B', NULL);

-- --------------------------------------------------------

--
-- ?????: `medicine`
--

DROP TABLE IF EXISTS `medicine`;
CREATE TABLE IF NOT EXISTS `medicine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- ??????????: `medicine`
--

INSERT INTO `medicine` (`id`, `name`, `description`) VALUES
(1, 'medicine A', NULL),
(2, 'medicine B', NULL);

-- --------------------------------------------------------

--
-- ?????: `patient_personal`
--

DROP TABLE IF EXISTS `patient_personal`;
CREATE TABLE IF NOT EXISTS `patient_personal` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `gender` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `address` text COLLATE utf8_unicode_ci NOT NULL,
  `contact_no` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `birthday` date NOT NULL,
  `pic` int(11) NOT NULL,
  `last_diagnosis` datetime NOT NULL,
  `description` text CHARACTER SET latin1,
  PRIMARY KEY (`pid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- ??????????: `patient_personal`
--


-- --------------------------------------------------------

--
-- ?????: `privilege`
--

DROP TABLE IF EXISTS `privilege`;
CREATE TABLE IF NOT EXISTS `privilege` (
  `Role` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `Read` tinyint(1) NOT NULL,
  PRIMARY KEY (`Role`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- ??????????: `privilege`
--


-- --------------------------------------------------------

--
-- ?????: `t-m_rec`
--

DROP TABLE IF EXISTS `t-m_rec`;
CREATE TABLE IF NOT EXISTS `t-m_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- ??????????: `t-m_rec`
--


-- --------------------------------------------------------

--
-- ?????: `treatment`
--

DROP TABLE IF EXISTS `treatment`;
CREATE TABLE IF NOT EXISTS `treatment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `pic` int(11) NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `date_of_issue` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- ??????????: `treatment`
--


-- --------------------------------------------------------

--
-- ?????: `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `Role` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `pub_key` text COLLATE utf8_unicode_ci NOT NULL,
  `mod` text COLLATE utf8_unicode_ci,
  `pwd` text COLLATE utf8_unicode_ci NOT NULL,
  `RegDate` date NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=10 ;

--
-- ??????????: `user`
--

INSERT INTO `user` (`uid`, `Role`, `pub_key`, `mod`, `pwd`, `RegDate`) VALUES
(1, 'doc', '10001', NULL, 'G5XuytlF', '2010-01-02'),
(2, 'hi', '10001', NULL, 'CAhbMZgt', '2010-01-02'),
(5, '1', '10001', '9ec1d3c7fead0cc7562d2fbc845146a10f09fc06f78aba3345e4d5888af680ea30859233e82980caf9ef9da127433f4d3bf80feda93dae04293e8e4263abbfdd6ccf1a7a4cd3a6442f952e174ae5eabcca378dc6b96373c6450adcb8eb27dc75082eb480fbf269092935bc71cf48bf1f88eacbe73df3e979b867e833c8665541', '1234', '2010-01-03'),
(6, '1', '10001', 'b18bf734801f83038c0246f8fac2c888e5e20f7f204bd38de35af6d7f0cefaafb3279a5753c2656830a8b681a3c06596a7e2080313a5b9c959eec2c63eee8c7c87feb5a9160f47a70267c4d9d623d335581dfdb36ebc6be31b9921e151e957cee7279ff9cfdfb21cf71ccacd65e5d35e44617657165a6d7b0c693ab78985cc33', 'vQ3D1RhF', '2010-01-03'),
(7, '1', '10001', 'b18bf734801f83038c0246f8fac2c888e5e20f7f204bd38de35af6d7f0cefaafb3279a5753c2656830a8b681a3c06596a7e2080313a5b9c959eec2c63eee8c7c87feb5a9160f47a70267c4d9d623d335581dfdb36ebc6be31b9921e151e957cee7279ff9cfdfb21cf71ccacd65e5d35e44617657165a6d7b0c693ab78985cc33', 'vQ3D1RhF', '2010-01-03'),
(8, '1', '10001', 'b18bf734801f83038c0246f8fac2c888e5e20f7f204bd38de35af6d7f0cefaafb3279a5753c2656830a8b681a3c06596a7e2080313a5b9c959eec2c63eee8c7c87feb5a9160f47a70267c4d9d623d335581dfdb36ebc6be31b9921e151e957cee7279ff9cfdfb21cf71ccacd65e5d35e44617657165a6d7b0c693ab78985cc33', 'vQ3D1RhF', '2010-01-03'),
(9, '1', '10001', 'b729cd7593d9474d12e75a125cddc059edfd6c39a6a9864f9e186b4992499e8f7ed9dbb6dbda2c9e7b111596cc58c5969226ff713f19907acb40667e0737c60f08f9fedf6a4a22f56b804b6d83ee8d03f0dc75a8f87013b2c278dcad650c2f2800c1610e7ca749d7a0dcceb54456355cac2bf23acfc471fe4749772898e27f0d', '8783bax8', '2010-01-03');
