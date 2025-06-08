CREATE DATABASE Escola;
USE Escola;

DROP TABLE IF EXISTS `disciplinas`;
CREATE TABLE `disciplinas` (
  `id_disciplinas` bigint AUTO_INCREMENT NOT NULL,
  `nome_disciplina` varchar(100) NOT NULL,
  PRIMARY KEY (`id_disciplinas`),
  UNIQUE KEY `id_disciplinas_UNIQUE` (`id_disciplinas`),
  UNIQUE KEY `nome_disciplina_UNIQUE` (`nome_disciplina`)
);

DROP TABLE IF EXISTS `turma`;
CREATE TABLE `turma` (
  `id_turma` bigint AUTO_INCREMENT NOT NULL,
  `nome_turma` varchar(100) DEFAULT NULL,
  `turno` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_turma`),
  UNIQUE KEY `id_turma_UNIQUE` (`id_turma`)
);

DROP TABLE IF EXISTS `endereco`;
CREATE TABLE `endereco` (
  `id_endereco` bigint AUTO_INCREMENT NOT NULL,
  `nome_rua` varchar(100) DEFAULT NULL,
  `nome_bairro` varchar(100) DEFAULT NULL,
  `cep` varchar(15) DEFAULT NULL,
  `complemento` varchar(100) DEFAULT NULL,
  `tipo_entidade` enum('professor','aluno') NOT NULL,
  `id_entidade` bigint NOT NULL,
  PRIMARY KEY (`id_endereco`),
  UNIQUE KEY `id_endereco_UNIQUE` (`id_endereco`),
  UNIQUE KEY `complemento_UNIQUE` (`complemento`),
  KEY `idx_endereco_entidade` (`tipo_entidade`,`id_entidade`)
);

DROP TABLE IF EXISTS `responsaveis`;
CREATE TABLE `responsaveis` (
  `id_responsaveis` bigint AUTO_INCREMENT NOT NULL,
  `nome_responsavel` varchar(100) NOT NULL,
  `cpf` varchar(20) NOT NULL,
  `telefone` varchar(25) NOT NULL,
  `rg` varchar(20) NOT NULL,
  PRIMARY KEY (`id_responsaveis`),
  UNIQUE KEY `id_responsaveis_UNIQUE` (`id_responsaveis`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`),
  UNIQUE KEY `rg_UNIQUE` (`rg`)
);

DROP TABLE IF EXISTS `aluno`;
CREATE TABLE `aluno` (
  `id_aluno` bigint AUTO_INCREMENT NOT NULL,
  `nome_aluno` varchar(100) NOT NULL,
  `cpf` varchar(20) NOT NULL,
  `rg` varchar(20) NOT NULL,
  `id_endereco` bigint NOT NULL,
  `telefone` varchar(15) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `id_turma` bigint NOT NULL,
  PRIMARY KEY (`id_aluno`),
  UNIQUE KEY `id_aluno_UNIQUE` (`id_aluno`),
  UNIQUE KEY `rg_UNIQUE` (`rg`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`),
  KEY `fk_aluno_endereco_idx` (`id_endereco`),
  KEY `fk_aluno_turma1_idx` (`id_turma`),
  CONSTRAINT `fk_aluno_endereco` FOREIGN KEY (`id_endereco`) REFERENCES `endereco` (`id_endereco`),
  CONSTRAINT `fk_aluno_turma1` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id_turma`)
);

DROP TABLE IF EXISTS `professor`;
CREATE TABLE `professor` (
  `id_professor` bigint AUTO_INCREMENT NOT NULL,
  `nome_professor` varchar(100) NOT NULL,
  `cpf` varchar(20) NOT NULL,
  `rg` varchar(20) NOT NULL,
  `telefone` varchar(25) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `disciplinas_id_disciplinas` bigint NULL,
  PRIMARY KEY (`id_professor`),
  UNIQUE KEY `id_professor_UNIQUE` (`id_professor`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`),
  UNIQUE KEY `rg_UNIQUE` (`rg`),
  KEY `fk_professor_disciplinas1_idx` (`disciplinas_id_disciplinas`),
  CONSTRAINT `fk_professor_disciplinas1` FOREIGN KEY (`disciplinas_id_disciplinas`) REFERENCES `disciplinas` (`id_disciplinas`)
);

DROP TABLE IF EXISTS `aluno_responsavel`;
CREATE TABLE `aluno_responsavel` (
  `id_aluno_responsavel` bigint NOT NULL,
  `parentesco` varchar(15) DEFAULT NULL,
  `id_responsaveis` bigint NOT NULL,
  `id_aluno` bigint NOT NULL,
  PRIMARY KEY (`id_aluno_responsavel`,`id_aluno`),
  KEY `fk_aluno_responsavel_responsaveis1_idx` (`id_responsaveis`),
  KEY `fk_aluno_responsavel_aluno1_idx` (`id_aluno`),
  CONSTRAINT `fk_aluno_responsavel_aluno1` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id_aluno`),
  CONSTRAINT `fk_aluno_responsavel_responsaveis1` FOREIGN KEY (`id_responsaveis`) REFERENCES `responsaveis` (`id_responsaveis`)
);

DROP TABLE IF EXISTS `avaliacoes`;
CREATE TABLE `avaliacoes` (
  `id_avaliacao` bigint NOT NULL AUTO_INCREMENT,
  `id_disciplina` bigint NOT NULL,
  `id_turma` bigint NOT NULL,
  `tipo_avaliacao` enum('prova','trabalho','participacao') NOT NULL,
  `descricao` varchar(100) DEFAULT NULL,
  `data_avaliacao` date NOT NULL,
  `valor_maximo` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id_avaliacao`),
  KEY `id_disciplina` (`id_disciplina`),
  KEY `id_turma` (`id_turma`),
  CONSTRAINT `avaliacoes_ibfk_1` FOREIGN KEY (`id_disciplina`) REFERENCES `disciplinas` (`id_disciplinas`),
  CONSTRAINT `avaliacoes_ibfk_2` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id_turma`)
);

DROP TABLE IF EXISTS `boletim`;
CREATE TABLE `boletim` (
  `id_boletim` bigint NOT NULL,
  `observacoes` text,
  `status` varchar(15) DEFAULT NULL,
  `id_aluno` bigint NOT NULL,
  `id_disciplinas` bigint NOT NULL,
  `id_avaliacao` bigint NOT NULL,
  `nota` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id_aluno`,`id_disciplinas`,`id_avaliacao`),
  UNIQUE KEY `id_boletim_UNIQUE` (`id_boletim`),
  KEY `fk_boletim_aluno1_idx` (`id_aluno`),
  KEY `fk_boletim_disciplinas1_idx` (`id_disciplinas`),
  KEY `id_avaliacao` (`id_avaliacao`),
  CONSTRAINT `boletim_ibfk_1` FOREIGN KEY (`id_avaliacao`) REFERENCES `avaliacoes` (`id_avaliacao`),
  CONSTRAINT `fk_boletim_aluno1` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id_aluno`),
  CONSTRAINT `fk_boletim_disciplinas1` FOREIGN KEY (`id_disciplinas`) REFERENCES `disciplinas` (`id_disciplinas`)
);

DROP TABLE IF EXISTS `frequencia`;
CREATE TABLE `frequencia` (
  `id_frequencia` bigint NOT NULL AUTO_INCREMENT,
  `id_aluno` bigint NOT NULL,
  `id_disciplina` bigint NOT NULL,
  `id_turma` bigint NOT NULL,
  `presenca` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_frequencia`),
  UNIQUE KEY `idx_frequencia_unica` (`id_aluno`,`id_disciplina`,`id_turma`),
  KEY `id_disciplina` (`id_disciplina`),
  KEY `id_turma` (`id_turma`),
  CONSTRAINT `frequencia_ibfk_1` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id_aluno`),
  CONSTRAINT `frequencia_ibfk_2` FOREIGN KEY (`id_disciplina`) REFERENCES `disciplinas` (`id_disciplinas`),
  CONSTRAINT `frequencia_ibfk_3` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id_turma`)
);

DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `id_login` bigint AUTO_INCREMENT NOT NULL,
  `tipo_usuario` enum('admin','professor','aluno','responsavel') NOT NULL,
  `nome_usuario` varchar(45) NOT NULL,
  `id_usuario` bigint NOT NULL,
  `senha` varchar(255) NOT NULL,
  `data_criacao` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_login`),
  UNIQUE KEY `id_login_UNIQUE` (`id_login`),
  UNIQUE KEY `nome_usuario_UNIQUE` (`nome_usuario`),
  KEY `idx_login_usuario` (`tipo_usuario`,`id_usuario`)
);

DROP TABLE IF EXISTS `turma_disciplina_professor`;
CREATE TABLE `turma_disciplina_professor` (
  `id_turma` bigint AUTO_INCREMENT NOT NULL,
  `id_disciplinas` bigint NOT NULL,
  `id_professor` bigint NOT NULL,
  KEY `fk_turma_disciplina_professor_turma1_idx` (`id_turma`),
  KEY `fk_turma_disciplina_professor_disciplinas1_idx` (`id_disciplinas`),
  KEY `fk_turma_disciplina_professor_professor1_idx` (`id_professor`),
  CONSTRAINT `fk_turma_disciplina_professor_disciplinas1` FOREIGN KEY (`id_disciplinas`) REFERENCES `disciplinas` (`id_disciplinas`),
  CONSTRAINT `fk_turma_disciplina_professor_professor1` FOREIGN KEY (`id_professor`) REFERENCES `professor` (`id_professor`),
  CONSTRAINT `fk_turma_disciplina_professor_turma1` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id_turma`)
);

DROP TABLE IF EXISTS `eventos`;
CREATE TABLE `eventos` (
  `id_evento` bigint AUTO_INCREMENT NOT NULL,
  `nome_evento` text NOT NULL,
  `tema` text DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  PRIMARY KEY (`id_evento`),
  UNIQUE KEY `id_evento_UNIQUE` (`id_evento`)
);

