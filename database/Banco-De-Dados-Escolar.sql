-- MySQL dump 10.13  Distrib 8.4.5, for Linux (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.4.5

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aluno`
--

DROP TABLE IF EXISTS `aluno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aluno` (
  `id_aluno` int NOT NULL,
  `nome_aluno` text NOT NULL,
  `cpf` varchar(20) NOT NULL,
  `rg` varchar(20) NOT NULL,
  `id_endereco` int NOT NULL,
  `telefone` varchar(15) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `status` varchar(15) DEFAULT NULL,
  `id_turma` int NOT NULL,
  PRIMARY KEY (`id_aluno`),
  UNIQUE KEY `id_aluno_UNIQUE` (`id_aluno`),
  UNIQUE KEY `rg_UNIQUE` (`rg`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`),
  KEY `fk_aluno_endereco_idx` (`id_endereco`),
  KEY `fk_aluno_turma1_idx` (`id_turma`),
  CONSTRAINT `fk_aluno_endereco` FOREIGN KEY (`id_endereco`) REFERENCES `endereco` (`id_endereco`),
  CONSTRAINT `fk_aluno_turma1` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id_turma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `aluno_responsavel`
--

DROP TABLE IF EXISTS `aluno_responsavel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aluno_responsavel` (
  `id_aluno_responsavel` int NOT NULL,
  `parentesco` varchar(15) DEFAULT NULL,
  `id_responsaveis` int NOT NULL,
  `id_aluno` int NOT NULL,
  PRIMARY KEY (`id_aluno_responsavel`,`id_aluno`),
  KEY `fk_aluno_responsavel_responsaveis1_idx` (`id_responsaveis`),
  KEY `fk_aluno_responsavel_aluno1_idx` (`id_aluno`),
  CONSTRAINT `fk_aluno_responsavel_aluno1` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id_aluno`),
  CONSTRAINT `fk_aluno_responsavel_responsaveis1` FOREIGN KEY (`id_responsaveis`) REFERENCES `responsaveis` (`id_responsaveis`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `avaliacoes`
--

DROP TABLE IF EXISTS `avaliacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avaliacoes` (
  `id_avaliacao` int NOT NULL AUTO_INCREMENT,
  `id_disciplina` int NOT NULL,
  `id_turma` int NOT NULL,
  `tipo_avaliacao` enum('prova','trabalho','participacao') NOT NULL,
  `descricao` varchar(100) DEFAULT NULL,
  `data_avaliacao` date NOT NULL,
  `valor_maximo` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id_avaliacao`),
  KEY `id_disciplina` (`id_disciplina`),
  KEY `id_turma` (`id_turma`),
  CONSTRAINT `avaliacoes_ibfk_1` FOREIGN KEY (`id_disciplina`) REFERENCES `disciplinas` (`id_disciplinas`),
  CONSTRAINT `avaliacoes_ibfk_2` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id_turma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `boletim`
--

DROP TABLE IF EXISTS `boletim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boletim` (
  `id_boletim` int NOT NULL,
  `observacoes` text,
  `status` varchar(15) DEFAULT NULL,
  `id_aluno` int NOT NULL,
  `id_disciplinas` int NOT NULL,
  `id_avaliacao` int NOT NULL,
  `nota` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id_aluno`,`id_disciplinas`,`id_avaliacao`),
  UNIQUE KEY `id_boletim_UNIQUE` (`id_boletim`),
  KEY `fk_boletim_aluno1_idx` (`id_aluno`),
  KEY `fk_boletim_disciplinas1_idx` (`id_disciplinas`),
  KEY `id_avaliacao` (`id_avaliacao`),
  CONSTRAINT `boletim_ibfk_1` FOREIGN KEY (`id_avaliacao`) REFERENCES `avaliacoes` (`id_avaliacao`),
  CONSTRAINT `fk_boletim_aluno1` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id_aluno`),
  CONSTRAINT `fk_boletim_disciplinas1` FOREIGN KEY (`id_disciplinas`) REFERENCES `disciplinas` (`id_disciplinas`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `disciplinas`
--

DROP TABLE IF EXISTS `disciplinas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disciplinas` (
  `id_disciplinas` int NOT NULL,
  `nome_disciplina` text NOT NULL,
  PRIMARY KEY (`id_disciplinas`),
  UNIQUE KEY `id_disciplinas_UNIQUE` (`id_disciplinas`),
  UNIQUE KEY `nome_disciplina_UNIQUE` (`nome_disciplina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `endereco` (
  `id_endereco` int NOT NULL,
  `rua` text DEFAULT NULL,
  `bairro` text DEFAULT NULL,
  `cep` varchar(15) DEFAULT NULL,
  `povoado` text DEFAULT NULL,
  `complemento` text DEFAULT NULL,
  `tipo_entidade` enum('professor','aluno') NOT NULL,
  `id_entidade` int NOT NULL,
  PRIMARY KEY (`id_endereco`),
  UNIQUE KEY `id_endereco_UNIQUE` (`id_endereco`),
  UNIQUE KEY `complemento_UNIQUE` (`complemento`),
  KEY `idx_endereco_entidade` (`tipo_entidade`,`id_entidade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frequencia`
--

DROP TABLE IF EXISTS `frequencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `frequencia` (
  `id_frequencia` int NOT NULL AUTO_INCREMENT,
  `id_aluno` int NOT NULL,
  `id_disciplina` int NOT NULL,
  `id_turma` int NOT NULL,
  `presenca` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_frequencia`),
  UNIQUE KEY `idx_frequencia_unica` (`id_aluno`,`id_disciplina`,`id_turma`,`data_aula`),
  KEY `id_disciplina` (`id_disciplina`),
  KEY `id_turma` (`id_turma`),
  CONSTRAINT `frequencia_ibfk_1` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id_aluno`),
  CONSTRAINT `frequencia_ibfk_2` FOREIGN KEY (`id_disciplina`) REFERENCES `disciplinas` (`id_disciplinas`),
  CONSTRAINT `frequencia_ibfk_3` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id_turma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `idlogin` int NOT NULL,
  `tipo_usuario` enum('admin','professor','aluno','funcionario') NOT NULL,
  `nome_usuario` varchar(45) NOT NULL,
  `id_usuario` int NOT NULL,
  `senha` varchar(255) NOT NULL,
  `data_criacao` datetime DEFAULT CURRENT_TIMESTAMP,
  `ultimo_login` datetime DEFAULT NULL,
  PRIMARY KEY (`idlogin`),
  UNIQUE KEY `idlogin_UNIQUE` (`idlogin`),
  UNIQUE KEY `nome_usuario_UNIQUE` (`nome_usuario`),
  KEY `idx_login_usuario` (`tipo_usuario`,`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `professor`
--

DROP TABLE IF EXISTS `professor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professor` (
  `id_professor` int NOT NULL,
  `nome_professor` text NOT NULL,
  `cpf` varchar(20) NOT NULL,
  `rg` varchar(20) NOT NULL,
  `telefone` varchar(25) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `status` text DEFAULT NULL,
  `disciplinas_id_disciplinas` int NOT NULL,
  PRIMARY KEY (`id_professor`,`disciplinas_id_disciplinas`),
  UNIQUE KEY `id_professor_UNIQUE` (`id_professor`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`),
  UNIQUE KEY `rg_UNIQUE` (`rg`),
  KEY `fk_professor_disciplinas1_idx` (`disciplinas_id_disciplinas`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `responsaveis`
--

DROP TABLE IF EXISTS `responsaveis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `responsaveis` (
  `id_responsaveis` int NOT NULL,
  `nome_responsavel` text NOT NULL,
  `cpf` varchar(20) NOT NULL,
  `telefone` varchar(25) NOT NULL,
  `rg` varchar(20) NOT NULL,
  PRIMARY KEY (`id_responsaveis`),
  UNIQUE KEY `id_responsaveis_UNIQUE` (`id_responsaveis`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`),
  UNIQUE KEY `rg_UNIQUE` (`rg`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `turma`
--

DROP TABLE IF EXISTS `turma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turma` (
  `id_turma` int NOT NULL,
  `nome_turma` text DEFAULT NULL,
  `turno` text DEFAULT NULL,
  PRIMARY KEY (`id_turma`),
  UNIQUE KEY `id_turma_UNIQUE` (`id_turma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `turma_disciplina_professor`
--

DROP TABLE IF EXISTS `turma_disciplina_professor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turma_disciplina_professor` (
  `id_turma` int NOT NULL,
  `id_disciplinas` int NOT NULL,
  `id_professor` int NOT NULL,
  KEY `fk_turma_disciplina_professor_turma1_idx` (`id_turma`),
  KEY `fk_turma_disciplina_professor_disciplinas1_idx` (`id_disciplinas`),
  KEY `fk_turma_disciplina_professor_professor1_idx` (`id_professor`),
  CONSTRAINT `fk_turma_disciplina_professor_disciplinas1` FOREIGN KEY (`id_disciplinas`) REFERENCES `disciplinas` (`id_disciplinas`),
  CONSTRAINT `fk_turma_disciplina_professor_professor1` FOREIGN KEY (`id_professor`) REFERENCES `professor` (`id_professor`),
  CONSTRAINT `fk_turma_disciplina_professor_turma1` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id_turma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

DROP TABLE IF EXISTS `eventos`;

CREATE TABLE `eventos` (
  `id_evento` int NOT NULL,
  `nome_evento` text NOT NULL,
  `tema` text DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  PRIMARY KEY (`id_evento`),
  PRIMARY KEY `id_evento_UNIQUE` (`id_evento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-25 18:02:27
