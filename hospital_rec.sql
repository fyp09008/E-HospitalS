-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- ??: localhost
-- ????: Jan 03, 2010, 10:19 AM
-- ????: Jan 07, 2010, 03:20 PM
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- ??????????: `allergy`
--


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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- ??????????: `disease`
--


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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- ??????????: `medicine`
--


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
  `Write` tinyint(1) NOT NULL,
  `Add` tinyint(1) NOT NULL,
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
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- ??????????: `user`
--

