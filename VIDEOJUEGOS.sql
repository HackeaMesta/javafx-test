-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 09-01-2016 a las 22:49:10
-- Versión del servidor: 5.5.46-0+deb8u1
-- Versión de PHP: 5.6.14-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `VIDEOJUEGOS`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `codigos`
--

CREATE TABLE IF NOT EXISTS `codigos` (
  `codigo` varchar(6) NOT NULL,
  `cantidad` decimal(9,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `codigos`
--

INSERT INTO `codigos` (`codigo`, `cantidad`) VALUES
('abcdef', 1000.00),
('123456', 500.00),
('654321', 750.00),
('qwerty', 500.00),
('987654', 1000.00),
('456789', 750.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_ventas`
--

CREATE TABLE IF NOT EXISTS `detalle_ventas` (
  `id_venta` int(11) NOT NULL,
  `id_videojuego` int(11) NOT NULL,
  `precio` decimal(7,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `detalle_ventas`
--

INSERT INTO `detalle_ventas` (`id_venta`, `id_videojuego`, `precio`) VALUES
(1, 1, 899.00),
(2, 2, 1199.00),
(3, 2, 1199.00),
(3, 4, 179.99),
(3, 5, 179.99);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `USUARIOS`
--

CREATE TABLE IF NOT EXISTS `USUARIOS` (
  `NICKNAME` varchar(30) NOT NULL,
  `CORREO` varchar(30) NOT NULL,
  `CONTRASENA` varchar(32) NOT NULL,
  `NOMBRE` varchar(30) NOT NULL,
  `A_PARTERNO` varchar(30) NOT NULL,
  `A_MATERNO` varchar(30) NOT NULL,
  `FOTO` varchar(50) DEFAULT NULL,
  `TIPO_USER` tinyint(1) NOT NULL,
  `saldo` decimal(11,2) NOT NULL DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `USUARIOS`
--

INSERT INTO `USUARIOS` (`NICKNAME`, `CORREO`, `CONTRASENA`, `NOMBRE`, `A_PARTERNO`, `A_MATERNO`, `FOTO`, `TIPO_USER`, `saldo`) VALUES
('hackeamesta', 'rk521@hotmail.com', 'toor', 'Ricardo', 'Osorio', '', NULL, 0, 996342.00),
('test', 'test@test.com', 'test', 'Tester', 'Null', '', 'null', 2, 0.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Ventas`
--

CREATE TABLE IF NOT EXISTS `Ventas` (
  `id_venta` int(11) NOT NULL,
  `nickname_usuario` varchar(30) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Ventas`
--

INSERT INTO `Ventas` (`id_venta`, `nickname_usuario`, `total`, `fecha`) VALUES
(1, 'hackeamesta', 899.00, '2016-01-01'),
(2, 'hackeamesta', 1199.00, '2016-01-07'),
(3, 'hackeamesta', 1558.98, '2016-01-09');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `VIDEOJUEGOS`
--

CREATE TABLE IF NOT EXISTS `VIDEOJUEGOS` (
  `ID_VIDEOJUEGO` int(11) NOT NULL,
  `TITULO` varchar(50) NOT NULL,
  `DESCRIPCION` text NOT NULL,
  `DESCARGAS` int(11) NOT NULL,
  `COSTO` decimal(7,2) NOT NULL,
  `ID_DESARROLLADOR` varchar(30) NOT NULL,
  `CATEGORIA` varchar(30) NOT NULL,
  `FECHA` date NOT NULL,
  `STATUS` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `VIDEOJUEGOS`
--

INSERT INTO `VIDEOJUEGOS` (`ID_VIDEOJUEGO`, `TITULO`, `DESCRIPCION`, `DESCARGAS`, `COSTO`, `ID_DESARROLLADOR`, `CATEGORIA`, `FECHA`, `STATUS`) VALUES
(1, 'Batman™: Arkham Knight', 'Batman™: Arkham Knight es la épica conclusión de la galardonada trilogía de Arkham, creada por Rocksteady Studios. El título, desarrollado en exclusiva para plataformas de nueva generación, presenta la espectacular versión del batmóvil imaginada por Rocksteady. La incorporación del legendario vehículo, unida al aclamado sistema de juego de la serie Arkham, ofrece a los jugadores una recreación definitiva del universo de Batman en la que pueden recorrer las calles y sobrevolar los tejados de la totalidad de Gotham City. En este explosivo desenlace, Batman se enfrenta a la mayor amenaza para la ciudad que ha jurado proteger, cuando el Espantapájaros reaparece para unir a todos los supervillanos de Gotham y jura destruir al murciélago de una vez para siempre.', 39, 899.00, 'Rocksteady Studios', 'Acción', '2015-12-30', 0),
(2, 'Call of Duty®: Black Ops III', 'Call of Duty: Black Ops 3 lleva a los jugadores a un futuro en el que la biotecnología ha permitido la creación de una nueva raza de soldados Black Ops. Ahora, los jugadores estarán siempre online y conectados a la red de inteligencia y a sus compañeros durante la batalla. En un mundo más dividido que nunca, este escuadrón de élite estará formado por hombres y mujeres que han mejorado sus habilidades de combate para luchar más rápido, más fuerte y de forma más inteligente. Cada soldado tiene que tomar decisiones difíciles y visitar las tinieblas en esta descarnada e interesante historia.\r\n', 22, 1199.00, 'Activision', 'Acción', '2015-12-30', 0),
(3, 'Left 4 Dead 2', 'Ambientado en el apocalipsis zombi, Left 4 Dead 2 (L4D2) es la secuela largamente esperada del galardonado Left 4 Dead, el juego co-op número 1 de 2008. \r\n\r\nEste FPS cooperativo de acción y horror os llevará a ti y a tus amigos por las ciudades, pantanos y cementerios del Sur Profundo, desde Savannah hasta Nueva Orleans a lo largo de cinco extensas campañas. \r\n\r\nJugarás como uno de los cuatro nuevos supervivientes, armado con un enorme y devastador arsenal de armas clásicas y mejoradas. Además de las armas de fuego, tendrás la oportunidad de atacar a los infectados con diversas armas de combate cuerpo a cuerpo, desde motosierras hasta hachas, e incluso una mortífera sartén. ', 19, 799.99, 'Valve', 'Acción', '2015-12-22', 0),
(4, 'Age of Empires II HD', 'In Age of Empires II: HD Edition, fans of the original game and new players alike will fall in love with the classic Age of Empires II experience. Explore all the original single player campaigns from both Age of Kings and The Conquerors expansion, choose from 18 civilizations spanning over a thousand years of history, and head online to challenge other Steam players in your quest for world domination throughout the ages. Originally developed by Ensemble Studios and re-imagined in high definition by Hidden Path Entertainment, and Skybox Labs, Microsoft Studios is proud to bring Age of Empires II: HD ', 21, 179.99, 'Microsoft Studios', 'Estrategia', '2015-12-20', 0),
(5, 'Metro: Last Light Redux', 'Bajo las ruinas del Moscú postapocalíptico, en los túneles del metro, lo poco que queda de la humanidad sufre el asedio de horribles amenazas externas... e internas. \r\n\r\nLos mutantes recorren los cielos venenosos y las catacumbas bajo la superficie desolada. \r\n\r\nPero, en vez de unirse, las distintas estaciones libran una lucha por hacerse con el poder definitivo obteniendo un mecanismo letal surgido de las cámaras militares de D6: una guerra civil que podría borrar a la humanidad de la faz de la Tierra para siempre. \r\n\r\nEres Artyom, un hombre lastrado por la culpa pero guiado por la esperanza que guarda la llave de nuestra supervivencia, la última luz en el momento más oscuro...', 2, 179.99, '4A Games', 'Acción', '2015-12-02', 0),
(6, 'Injustice: Gods Among Us Ultimate Edition', 'Injustice: Gods Among Us Ultimate Edition es la versión mejorada de la atrevida franquicia del género de videojuegos de combate creada por NetherRealm Studios. Con seis nuevos personajes de juego, mas de 30 nuevos skins y 60 nuevas misiones de los Laboratorios S.T.A.R., esta edición viene pegando duro. Además de íconos de DC Comics tales como Batman, The Joker, Green Lantern, The Flash, Superman y Wonder Woman, este último título del galardonado estudio presenta una historia profunda y original. Heroes y villanos se enfrentarán en épicas batallas a una enorme escala en un mundo donde la línea entre el bien y el mal ha sido borrada.', 15, 179.99, 'Warner Bros', 'Acción', '2016-01-05', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `videojuego_meta`
--

CREATE TABLE IF NOT EXISTS `videojuego_meta` (
  `id_videojuego` int(11) NOT NULL,
  `archivo` varchar(75) NOT NULL,
  `tipo` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `videojuego_meta`
--

INSERT INTO `videojuego_meta` (`id_videojuego`, `archivo`, `tipo`) VALUES
(1, '/home/rk521/NetBeansProjects/Game Store/img/bat_header.jpg', 0),
(1, '/home/rk521/NetBeansProjects/Game Store/img/bat_1.jpg', 0),
(1, '/home/rk521/NetBeansProjects/Game Store/img/bat_2.jpg', 0),
(1, '/home/rk521/NetBeansProjects/Game Store/img/bat_3.jpg', 0),
(2, '/home/rk521/NetBeansProjects/Game Store/img/cod_header.jpg', 0),
(2, '/home/rk521/NetBeansProjects/Game Store/img/cod_1.jpg', 0),
(2, '/home/rk521/NetBeansProjects/Game Store/img/cod_2.jpg', 0),
(2, '/home/rk521/NetBeansProjects/Game Store/img/cod_3.jpg', 0),
(3, '/home/rk521/NetBeansProjects/Game Store/img/ld2_header.jpg', 0),
(3, '/home/rk521/NetBeansProjects/Game Store/img/ld2_1.jpg', 0),
(3, '/home/rk521/NetBeansProjects/Game Store/img/ld2_2.jpg', 0),
(3, '/home/rk521/NetBeansProjects/Game Store/img/ld2_3.jpg', 0),
(4, '/home/rk521/NetBeansProjects/Game Store/img/aoe_header.jpg', 0),
(4, '/home/rk521/NetBeansProjects/Game Store/img/aoe_1.jpg', 0),
(4, '/home/rk521/NetBeansProjects/Game Store/img/aoe_2.jpg', 0),
(4, '/home/rk521/NetBeansProjects/Game Store/img/aoe_3.jpg', 0),
(5, '/home/rk521/NetBeansProjects/Game Store/img/mll_header.jpg', 0),
(5, '/home/rk521/NetBeansProjects/Game Store/img/mll_1.jpg', 0),
(5, '/home/rk521/NetBeansProjects/Game Store/img/mll_2.jpg', 0),
(5, '/home/rk521/NetBeansProjects/Game Store/img/mll_3.jpg', 0),
(6, '/home/rk521/NetBeansProjects/Game Store/img/inj_header.jpg', 0),
(6, '/home/rk521/NetBeansProjects/Game Store/img/inj_1.jpg', 0),
(6, '/home/rk521/NetBeansProjects/Game Store/img/inj_2.jpg', 0),
(6, '/home/rk521/NetBeansProjects/Game Store/img/inj_3.jpg', 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `USUARIOS`
--
ALTER TABLE `USUARIOS`
 ADD PRIMARY KEY (`NICKNAME`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
