-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 17, 2010 at 02:26 PM
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
  `name` text NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `allergy`
--

INSERT INTO `allergy` (`id`, `name`, `description`) VALUES
(1, 'allergy A', NULL),
(2, 'allergy B', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `dia-allergy_rec`
--

CREATE TABLE IF NOT EXISTS `dia-allergy_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pat_id` int(11) NOT NULL,
  `allergy_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `dia-disease_rec`
--


-- --------------------------------------------------------

--
-- Table structure for table `disease`
--

CREATE TABLE IF NOT EXISTS `disease` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `disease`
--

INSERT INTO `disease` (`id`, `name`, `description`) VALUES
(1, 'disease A', NULL),
(2, 'disease B', NULL);

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
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `log`
--


-- --------------------------------------------------------

--
-- Table structure for table `medicine`
--

CREATE TABLE IF NOT EXISTS `medicine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `medicine`
--

INSERT INTO `medicine` (`id`, `name`, `description`) VALUES
(1, 'medicine A', NULL),
(2, 'medicine B', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `patient_personal`
--

CREATE TABLE IF NOT EXISTS `patient_personal` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `address` text NOT NULL,
  `contact_no` varchar(8) NOT NULL,
  `birthday` date NOT NULL,
  `pic` int(11) NOT NULL,
  `last_diagnosis` datetime NOT NULL,
  `description` text,
  PRIMARY KEY (`pid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `patient_personal`
--


-- --------------------------------------------------------

--
-- Table structure for table `privilege`
--

CREATE TABLE IF NOT EXISTS `privilege` (
  `Role` varchar(25) NOT NULL,
  `Read` tinyint(1) NOT NULL,
  PRIMARY KEY (`Role`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `privilege`
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
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `t-m_rec`
--


-- --------------------------------------------------------

--
-- Table structure for table `treatment`
--

CREATE TABLE IF NOT EXISTS `treatment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `pic` int(11) NOT NULL,
  `description` text,
  `date_of_issue` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `treatment`
--


-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `Role` varchar(25) NOT NULL,
  `pub_key` text NOT NULL,
  `pwd` text NOT NULL,
  `RegDate` varchar(20) NOT NULL,
  `username` varchar(30) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `user`
--


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
