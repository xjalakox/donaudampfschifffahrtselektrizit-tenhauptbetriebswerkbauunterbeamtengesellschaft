-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 30. Aug 2016 um 20:19
-- Server Version: 5.5.50-0+deb8u1
-- PHP-Version: 5.6.24-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `terra`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `blocks`
--

CREATE TABLE IF NOT EXISTS `blocks` (
`Id` int(11) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `TileID` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `inventorys`
--

CREATE TABLE IF NOT EXISTS `inventorys` (
`id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `slot1` text,
  `slot2` text,
  `slot3` text,
  `slot4` text,
  `slot5` text,
  `slot6` text,
  `slot7` text,
  `slot8` text,
  `slot9` text,
  `slot10` text,
  `slot11` text,
  `slot12` text,
  `slot13` text,
  `slot14` text,
  `slot15` text,
  `slot16` text,
  `slot17` text,
  `slot18` text,
  `slot19` text,
  `slot20` text,
  `slot21` text,
  `slot22` text,
  `slot23` text,
  `slot24` text,
  `slot25` text,
  `slot26` text,
  `slot27` text,
  `slot28` text,
  `slot29` text,
  `slot30` text,
  `slot31` text,
  `slot32` text,
  `slot33` text,
  `slot34` text,
  `slot35` text,
  `slot36` text,
  `slot37` text,
  `slot38` text,
  `slot39` text,
  `slot40` text
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `inventorys`
--

INSERT INTO `inventorys` (`id`, `user_id`, `slot1`, `slot2`, `slot3`, `slot4`, `slot5`, `slot6`, `slot7`, `slot8`, `slot9`, `slot10`, `slot11`, `slot12`, `slot13`, `slot14`, `slot15`, `slot16`, `slot17`, `slot18`, `slot19`, `slot20`, `slot21`, `slot22`, `slot23`, `slot24`, `slot25`, `slot26`, `slot27`, `slot28`, `slot29`, `slot30`, `slot31`, `slot32`, `slot33`, `slot34`, `slot35`, `slot36`, `slot37`, `slot38`, `slot39`, `slot40`) VALUES
(1, 1, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
(2, 2, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
(3, 3, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
(4, 4, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
(5, 5, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
(6, 6, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
(7, 7, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE IF NOT EXISTS `users` (
`Id` int(11) NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `isAdmin` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `users`
--

INSERT INTO `users` (`Id`, `username`, `password`, `x`, `y`, `isAdmin`) VALUES
(1, 'jalako', 'test123', 0, 0, 0),
(2, '1', '1', 0, 0, 0),
(3, '2', '2', 0, 0, 0),
(4, '3', '3', 0, 0, 0),
(5, '4', '4', 0, 0, 0),
(6, '5', '5', 0, 0, 0),
(7, 'd', 'd', 0, 0, 0);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `blocks`
--
ALTER TABLE `blocks`
 ADD PRIMARY KEY (`Id`);

--
-- Indizes für die Tabelle `inventorys`
--
ALTER TABLE `inventorys`
 ADD PRIMARY KEY (`id`), ADD KEY `user_id` (`user_id`);

--
-- Indizes für die Tabelle `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `blocks`
--
ALTER TABLE `blocks`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `inventorys`
--
ALTER TABLE `inventorys`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT für Tabelle `users`
--
ALTER TABLE `users`
MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `inventorys`
--
ALTER TABLE `inventorys`
ADD CONSTRAINT `inventorys_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`Id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
