/*
MySQL Backup
Database: piibienes
Backup Time: 2024-04-25 11:25:20
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `piibienes`.`bienes`;
DROP TABLE IF EXISTS `piibienes`.`entidades`;
DROP TABLE IF EXISTS `piibienes`.`faltantes`;
DROP TABLE IF EXISTS `piibienes`.`movimientos`;
DROP TABLE IF EXISTS `piibienes`.`sectores`;
DROP TABLE IF EXISTS `piibienes`.`servicios`;
DROP TABLE IF EXISTS `piibienes`.`trabajadores`;
DROP TABLE IF EXISTS `piibienes`.`unidades`;
DROP TABLE IF EXISTS `piibienes`.`usuarios`;
CREATE TABLE `bienes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clasificacion` varchar(25) NOT NULL,
  `nbien` varchar(25) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `estado` varchar(80) NOT NULL,
  `status` varchar(80) NOT NULL,
  `idtrabajador_asig` int(11) DEFAULT NULL,
  `ubicacion_asig` varchar(80) DEFAULT NULL,
  `monto_bs` varchar(80) NOT NULL,
  `monto_usd` varchar(80) NOT NULL,
  `idEntidad` int(11) DEFAULT NULL,
  `idSector` int(11) DEFAULT NULL,
  `idUnidad` int(11) DEFAULT NULL,
  `idServicio` int(11) DEFAULT NULL,
  `fecha_inventariado` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `entidades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) NOT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `municipio` varchar(25) DEFAULT NULL,
  `parroquia` varchar(50) DEFAULT NULL,
  `ubicacion` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `faltantes` (
  `id` int(11) NOT NULL,
  `factor` int(11) NOT NULL,
  `clasificacion` varchar(25) NOT NULL,
  `nbien` varchar(25) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `valor_unit` varchar(80) NOT NULL,
  `cantidad_diferencia` int(11) NOT NULL,
  `valor_totalbs` varchar(80) NOT NULL,
  `valor_refusd` varchar(80) NOT NULL,
  `idtrabajador_determinador` int(11) NOT NULL,
  `fecha_determinado` varchar(25) NOT NULL,
  `idusuario` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `movimientos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clasificacion` varchar(25) NOT NULL,
  `nbien` varchar(25) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `concepto` varchar(25) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `monto_bs` varchar(80) NOT NULL,
  `monto_usd` varchar(80) NOT NULL,
  `nfactura` varchar(40) DEFAULT NULL,
  `ordencompra` varchar(40) DEFAULT NULL,
  `actadesincorp` varchar(40) DEFAULT NULL,
  `idusuario` int(11) NOT NULL,
  `identidad` int(11) DEFAULT NULL,
  `idsector` int(11) DEFAULT NULL,
  `idunidad` int(11) DEFAULT NULL,
  `idservicio` int(11) DEFAULT NULL,
  `fecha_mov` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `sectores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) NOT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `municipio` varchar(25) DEFAULT NULL,
  `parroquia` varchar(50) DEFAULT NULL,
  `ubicacion` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `servicios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) NOT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `municipio` varchar(25) DEFAULT NULL,
  `parroquia` varchar(50) DEFAULT NULL,
  `ubicacion` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `trabajadores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `cedula` varchar(25) NOT NULL,
  `cargo` varchar(35) DEFAULT NULL,
  `iddependencia` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `unidades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) NOT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `municipio` varchar(25) DEFAULT NULL,
  `parroquia` varchar(50) DEFAULT NULL,
  `ubicacion` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(25) NOT NULL,
  `pwd` varchar(25) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
BEGIN;
LOCK TABLES `piibienes`.`bienes` WRITE;
DELETE FROM `piibienes`.`bienes`;
INSERT INTO `piibienes`.`bienes` (`id`,`clasificacion`,`nbien`,`descripcion`,`estado`,`status`,`idtrabajador_asig`,`ubicacion_asig`,`monto_bs`,`monto_usd`,`idEntidad`,`idSector`,`idUnidad`,`idServicio`,`fecha_inventariado`) VALUES (1, '2-13-0', '40588', 'Aire acomdiciomado 12 BTU Marca: Lakes S7N ', 'UBICADO', 'EXCELENTE', 1, 'NO ASIGNADO', '3550', '100', 1, 1, 1, 1, '23/4/2024'),(3, '2-13-0', '153741', 'Monitor 17� SVGA CRT Marca Siragon, Serial: LD67334D2871354', 'EXCELENTE', 'FALTANTE', 1, 'NO APLICA', '366,07', '9,2', 1, 1, 1, 5, '11/1/2024'),(4, '2-13-0', '153756', 'Combo Case ATX 450W (Teclado, Mouse y cornetas) S/S', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '171', '4,3', 1, 1, 1, 1, '11/1/2024'),(5, '2-13-0', '153756-1', 'Tarjeta Madre P4M900 Socket 775V, Marca Biostar, Serial: K081514227', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '280,5', '7', 1, 1, 1, 6, '11/1/2024'),(6, '2-13-0', '153756-2', 'Procesador Intel Pentium Dual Core 1.8 Ghz 775, Serial: 3A0941', 'EXCELENTE', 'A DESINCORPORAR', 1, 'NO APLICA', '510', '12,8', 1, 1, 1, 1, '11/1/2024'),(7, '2-13-0', '153756-3', 'Memoria 512 MB DDR2, Marca AMV, S/S', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '155,7', '3,9', 1, 1, 1, 1, '11/1/2024'),(8, '2-13-0', '153756-4', 'Disco Duro 160 GB 7200 rpm S/Sata, Marca Western Digital, Serial: WMAP9H654626', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '230,19', '5,8', 1, 1, 1, 1, '11/1/2024'),(9, '2-13-0', '153756-5', 'Unidad de CD-R 52X, Marca BenQ, Serial: KCL4609470SC0', 'EXCELENTE', 'A DESINCORPORAR', 1, 'NO APLICA', '110', '2,8', 1, 1, 1, 1, '11/1/2024'),(10, '2-13-0', '153756-6', 'Unidad Floppy 3 � HD 1.44 MB, Marca Samsung, Serial: FBTAS1AT7032978', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '24,94', '0,6', 1, 1, 1, 1, '11/1/2024'),(11, '2-13-0', '153757', 'Combo Case ATX 450W (Teclado, Mouse y cornetas) S/S', 'EXCELENTE', 'FALTANTE', 1, 'NO APLICA', '171', '4,3', 1, 1, 1, 6, '11/1/2024'),(12, '2-13-0', '153757-1', 'Tarjeta Madre P4M900 Socket 775V, Marca Biostar, Serial: K081513532', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '280,5', '7', 1, 1, 1, 1, '11/1/2024'),(13, '2-13-0', '153757-2', 'Procesador Intel Pentium Dual Core 1.8 Ghz 775, Serial: 3A0332', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '510', '12,8', 1, 2, 2, 2, '11/1/2024'),(14, '2-13-0', '153757-3', 'Memoria 512 MB DDR2, Marca AMV, S/S', 'EXCELENTE', 'FALTANTE', 1, 'NO APLICA', '155,7', '3,9', 1, 2, 2, 2, '11/1/2024'),(15, '2-13-0', '153757-4', 'Disco Duro 160 GB 7200 rpm S/Sata, Marca Western Digital, Serial: WMAP9H635905', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '230,19', '5,8', 1, 2, 2, 2, '11/1/2024'),(16, '2-13-0', '153757-5', 'Unidad de CD-R 52X, Marca BenQ, Serial: KCV4605711SC0', 'EXCELENTE', 'A DESINCORPORAR', 1, 'NO APLICA', '110', '2,8', 1, 2, 2, 2, '11/1/2024'),(17, '2-13-0', '153757-6', 'Unidad Floppy 3 � HD 1.44 MB, Marca Samsung, Serial: FBTAS1AT7032980', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '24,94', '0,7', 1, 2, 2, 2, '11/1/2024'),(18, '2-13-0', '153758', 'Combo Case ATX 450W (Teclado, Mouse y cornetas) S/S', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '171', '4,3', 1, 2, 2, 2, '11/1/2024'),(19, '2-13-0', '153758-1', 'Tarjeta Madre P4M900 Socket 775V, Marca Biostar, Serial: K081513530', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '280,5', '7', 1, 2, 2, 2, '11/1/2024'),(20, '2-13-0', '153758-2', 'Procesador Intel Pentium Dual Core 1.8 Ghz 775, Serial: 3A2674', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '510', '12,8', 1, 2, 2, 2, '11/1/2024'),(21, '2-13-0', '153758-3', 'Memoria 512 MB DDR2, Marca AMV, S/S', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '155,7', '3,9', 1, 2, 2, 2, '11/1/2024'),(22, '2-13-0', '153758-4', 'Disco Duro 160 GB 7200 rpm S/Sata, Marca Western Digital, Serial: WMAP9H667910', 'EXCELENTE', 'A DESINCORPORAR', 1, 'NO APLICA', '230,19', '5,75', 1, 2, 2, 2, '11/1/2024'),(23, '2-13-0', '153758-5', 'Unidad de CD-R 52X, Marca BenQ, Serial: KCV4605712SC0', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '110', '2,75', 1, 2, 2, 2, '11/1/2024'),(24, '2-13-0', '153758-6', 'Unidad Floppy 3 � HD 1.44 MB, Marca Samsung, Serial: FBTAS1AT7032981', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '24,94', '0,6', 1, 2, 2, 2, '11/1/2024'),(25, '2-13-0', '153759', 'Combo Case ATX 450W (Teclado, Mouse y cornetas) S/S', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '171', '4,3', 1, 3, 3, 3, '11/1/2024'),(26, '2-13-0', '153759-1', 'Tarjeta Madre P4M900 Socket 775V, Marca Biostar, Serial: K081513531', 'REGULAR', 'A DESINCORPORAR', 1, 'NO APLICA', '280,5', '7', 1, 3, 3, 4, '11/1/2024'),(27, '2-13-0', '153759-2', 'Procesador Intel Pentium Dual Core 1.8 Ghz 775, Serial: 3A1743', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '510', '12,75', 1, 3, 3, 3, '11/1/2024'),(28, '2-13-0', '153759-3', 'Memoria 512 MB DDR2, Marca AMV, S/S', 'EXCELENTE', 'FALTANTE', 1, 'NO APLICA', '155,7', '3,9', 1, 3, 3, 3, '11/1/2024'),(29, '2-13-0', '153759-4', 'Disco Duro 160 GB 7200 rpm S/Sata, Marca Western Digital, Serial: WMAP9H657564', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '230,19', '5,8', 1, 3, 3, 3, '11/1/2024'),(30, '2-13-0', '153759-5', 'Unidad de CD-R 52X, Marca BenQ, Serial: KCV4605713SC0', 'EXCELENTE', 'A DESINCORPORAR', 1, 'NO APLICA', '110', '2,75', 1, 3, 3, 3, '11/1/2024'),(31, '2-13-0', '153759-6', 'Unidad Floppy 3 � HD 1.44 MB, Marca Samsung, Serial: FBTAS1AT7032982', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '24,94', '0,65', 1, 3, 3, 4, '11/1/2024'),(32, '2-13-0', '153760', 'Combo Case ATX 450W (Teclado, Mouse y cornetas) S/S', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '171', '4,3', 1, 3, 3, 4, '11/1/2024'),(33, '2-13-0', '153760-1', 'Tarjeta Madre P4M900 Socket 775V, Marca Biostar, Serial: K081514226', 'EXCELENTE', 'UBICADO', 1, 'NO APLICA', '280,5', '7', 1, 3, 3, 4, '11/1/2024'),(34, '2-13-0', '153760-2', 'Procesador Intel Pentium Dual Core 1.8 Ghz 775, Serial: 3A2097', 'EXCELENTE', 'A DESINCORPORAR', 1, 'NO APLICA', '510', '12,75', 1, 3, 3, 4, '11/1/2024')
;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `piibienes`.`entidades` WRITE;
DELETE FROM `piibienes`.`entidades`;
INSERT INTO `piibienes`.`entidades` (`id`,`nombre`,`estado`,`municipio`,`parroquia`,`ubicacion`) VALUES (1, 'GOBERNACION DEL ESTADO BARINAS', 'BARINAS', 'BARINAS', 'CENTRO', 'INFORMACION DE EJEMPLO')
;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `piibienes`.`faltantes` WRITE;
DELETE FROM `piibienes`.`faltantes`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `piibienes`.`movimientos` WRITE;
DELETE FROM `piibienes`.`movimientos`;
INSERT INTO `piibienes`.`movimientos` (`id`,`clasificacion`,`nbien`,`cantidad`,`concepto`,`descripcion`,`monto_bs`,`monto_usd`,`nfactura`,`ordencompra`,`actadesincorp`,`idusuario`,`identidad`,`idsector`,`idunidad`,`idservicio`,`fecha_mov`) VALUES (1, '2-13-0', '40588', 1, '02', 'Aire acomdiciomado 12 BTU Marca: Lakes S7N ', '3550', '100', '000000255878', '15628', NULL, 2, 1, 1, 1, 1, '23/4/2024')
;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `piibienes`.`sectores` WRITE;
DELETE FROM `piibienes`.`sectores`;
INSERT INTO `piibienes`.`sectores` (`id`,`nombre`,`estado`,`municipio`,`parroquia`,`ubicacion`) VALUES (1, 'SECRETARIA EJECUTIVA DE EDUCACION BARINAS', 'BARINAS', 'BARINAS', 'CENTRO', 'AV CARABOBO, ETC, ETC'),(2, 'SECRETARIA EJECUTIVA PARA LA SALUD BARINAS', 'BARINAS', 'BARINAS', 'CENTRO', 'INFO EJEMPLO'),(3, 'INSTITUTO AUTONOMO DE CULTURA DEL ESTADO BARINAS', 'BARINAS', 'BARINAS', 'CENTRO', 'INFO EJEMPLO')
;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `piibienes`.`servicios` WRITE;
DELETE FROM `piibienes`.`servicios`;
INSERT INTO `piibienes`.`servicios` (`id`,`nombre`,`estado`,`municipio`,`parroquia`,`ubicacion`) VALUES (1, 'INFORMATICA INTEGRAL', 'BARINAS', 'BARINAS', 'CENTRO', 'INFORMACION DE EJEMPLO'),(2, 'FUNSALUD ALTO BARINAS', 'BARINAS', 'BARINAS', 'ALTO BARINAS', 'INFO EJEMPLO'),(3, 'OFICINA DE CULTURA DE LA GOBERNACION DEL ESTADO BARINAS', 'BARINAS', 'BARINAS', 'CENTRO', 'INF EJEM'),(4, 'ESCUELA DE MUSICA JOSE ANGEL LAMAS BARINAS', 'BARINAS', 'BARINAS', 'CENTRO', 'INFOR EJEMPLO'),(5, 'I. E. E. ESCUELA DOÑA MENCA DE LEONI', 'BARINAS', 'BARINAS', 'CORAZON DE JESUS', 'INFO EJEMPLO'),(6, 'I. E. E. LICEO EJEMPLO', 'BARINAS', 'ZAMORA', 'VACIO', 'INFO EJEMPLO')
;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `piibienes`.`trabajadores` WRITE;
DELETE FROM `piibienes`.`trabajadores`;
INSERT INTO `piibienes`.`trabajadores` (`id`,`nombre`,`cedula`,`cargo`,`iddependencia`) VALUES (1, 'NO APLICA / NO ASIGNADO', 'NO', 'NO APLICA', 0)
;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `piibienes`.`unidades` WRITE;
DELETE FROM `piibienes`.`unidades`;
INSERT INTO `piibienes`.`unidades` (`id`,`nombre`,`estado`,`municipio`,`parroquia`,`ubicacion`) VALUES (1, 'INFORMATICA INTEGRAL', 'BARINAS', 'BARINAS', 'CENTRO', 'INFORMACION DE EJEMPLO'),(2, 'FUNSALUD BARINAS', 'BARINAS', 'BARINAS', 'CENTRO', 'INFORMACION DE EJEMPLO'),(3, 'OFICINA DE CULTURA DE LA GOBERNACION DEL ESTADO BARINAS', 'BARINAS', 'BARINAS', 'CENTRO', 'INFO EJEMPLO')
;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `piibienes`.`usuarios` WRITE;
DELETE FROM `piibienes`.`usuarios`;
INSERT INTO `piibienes`.`usuarios` (`id`,`user`,`pwd`,`nombres`) VALUES (2, 'usuario', '31407532', 'JOSE MENDOZA CARDOZO DE LA SANTISIMA TRINIDAD PALACIOS Y BLANCO'),(3, 'pmendoza', '12345678', 'PEPE MENDOZA')
;
UNLOCK TABLES;
COMMIT;
