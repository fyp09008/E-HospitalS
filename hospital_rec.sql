-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- ??: localhost
-- ????: Apr 08, 2010, 09:01 AM
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
CREATE TABLE `allergy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- ?????: `dia-allergy_rec`
--

DROP TABLE IF EXISTS `dia-allergy_rec`;
CREATE TABLE `dia-allergy_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pat_id` int(11) NOT NULL,
  `allergy_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- ?????: `dia-disease_rec`
--

DROP TABLE IF EXISTS `dia-disease_rec`;
CREATE TABLE `dia-disease_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pat_id` int(11) NOT NULL,
  `dis_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- ?????: `disease`
--

DROP TABLE IF EXISTS `disease`;
CREATE TABLE `disease` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- ?????: `log`
--

DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `date` varchar(20) NOT NULL,
  `user` varchar(20) NOT NULL,
  `content` varchar(50) NOT NULL,
  KEY `id` (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=65 ;

-- --------------------------------------------------------

--
-- ?????: `medicine`
--

DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- ?????: `patient_personal`
--

DROP TABLE IF EXISTS `patient_personal`;
CREATE TABLE `patient_personal` (
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

-- --------------------------------------------------------

--
-- ?????: `privilege`
--

DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege` (
  `Role` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `Read` tinyint(1) NOT NULL,
  `Write` tinyint(1) NOT NULL,
  `Add` tinyint(1) NOT NULL,
  PRIMARY KEY (`Role`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- ?????: `swapped_user`
--

DROP TABLE IF EXISTS `swapped_user`;
CREATE TABLE `swapped_user` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `ori_pub_key` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ori_mod` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isValid` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

-- --------------------------------------------------------

--
-- ?????: `t-m_rec`
--

DROP TABLE IF EXISTS `t-m_rec`;
CREATE TABLE `t-m_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- ?????: `tmp_user`
--

DROP TABLE IF EXISTS `tmp_user`;
CREATE TABLE `tmp_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pub_key` text COLLATE utf8_unicode_ci NOT NULL,
  `mod` text COLLATE utf8_unicode_ci NOT NULL,
  `isTaken` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- ?????: `treatment`
--

DROP TABLE IF EXISTS `treatment`;
CREATE TABLE `treatment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `pic` int(11) NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `date_of_issue` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- ?????: `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `Role` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `pub_key` text COLLATE utf8_unicode_ci NOT NULL,
  `mod` text COLLATE utf8_unicode_ci NOT NULL,
  `pwd` text COLLATE utf8_unicode_ci NOT NULL,
  `RegDate` date NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;
