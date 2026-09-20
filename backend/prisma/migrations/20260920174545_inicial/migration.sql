-- CreateTable
CREATE TABLE `roles` (
    `idRol` INTEGER NOT NULL AUTO_INCREMENT,
    `nombre` VARCHAR(191) NOT NULL,

    UNIQUE INDEX `roles_nombre_key`(`nombre`),
    PRIMARY KEY (`idRol`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `usuarios` (
    `idUsuario` INTEGER NOT NULL AUTO_INCREMENT,
    `nombre` VARCHAR(191) NOT NULL,
    `correo` VARCHAR(191) NOT NULL,
    `password` VARCHAR(191) NOT NULL,
    `telefono` VARCHAR(191) NULL,
    `idRol` INTEGER NOT NULL,

    UNIQUE INDEX `usuarios_correo_key`(`correo`),
    PRIMARY KEY (`idUsuario`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `clientes` (
    `idCliente` INTEGER NOT NULL AUTO_INCREMENT,
    `idUsuario` INTEGER NOT NULL,

    UNIQUE INDEX `clientes_idUsuario_key`(`idUsuario`),
    PRIMARY KEY (`idCliente`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `tecnicos` (
    `idTecnico` INTEGER NOT NULL AUTO_INCREMENT,
    `idUsuario` INTEGER NOT NULL,
    `experiencia` INTEGER NULL,
    `descripcion` VARCHAR(191) NULL,
    `latitud` DECIMAL(65, 30) NULL,
    `longitud` DECIMAL(65, 30) NULL,
    `puntajePromedio` DECIMAL(65, 30) NOT NULL DEFAULT 0,

    UNIQUE INDEX `tecnicos_idUsuario_key`(`idUsuario`),
    PRIMARY KEY (`idTecnico`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `especialidades` (
    `idEspecialidad` INTEGER NOT NULL AUTO_INCREMENT,
    `nombre` VARCHAR(191) NOT NULL,
    `descripcion` VARCHAR(191) NULL,

    UNIQUE INDEX `especialidades_nombre_key`(`nombre`),
    PRIMARY KEY (`idEspecialidad`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `tecnico_especialidad` (
    `idTecnico` INTEGER NOT NULL,
    `idEspecialidad` INTEGER NOT NULL,

    PRIMARY KEY (`idTecnico`, `idEspecialidad`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `solicitudes_servicio` (
    `idSolicitud` INTEGER NOT NULL AUTO_INCREMENT,
    `idCliente` INTEGER NOT NULL,
    `idTecnico` INTEGER NULL,
    `descripcion` VARCHAR(191) NOT NULL,
    `estado` VARCHAR(191) NOT NULL DEFAULT 'PENDIENTE',
    `fecha` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),

    PRIMARY KEY (`idSolicitud`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `valoraciones` (
    `idValoracion` INTEGER NOT NULL AUTO_INCREMENT,
    `idSolicitud` INTEGER NOT NULL,
    `idTecnico` INTEGER NOT NULL,
    `puntuacion` INTEGER NOT NULL,
    `comentario` VARCHAR(191) NULL,

    UNIQUE INDEX `valoraciones_idSolicitud_key`(`idSolicitud`),
    PRIMARY KEY (`idValoracion`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- AddForeignKey
ALTER TABLE `usuarios` ADD CONSTRAINT `usuarios_idRol_fkey` FOREIGN KEY (`idRol`) REFERENCES `roles`(`idRol`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `clientes` ADD CONSTRAINT `clientes_idUsuario_fkey` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios`(`idUsuario`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `tecnicos` ADD CONSTRAINT `tecnicos_idUsuario_fkey` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios`(`idUsuario`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `tecnico_especialidad` ADD CONSTRAINT `tecnico_especialidad_idTecnico_fkey` FOREIGN KEY (`idTecnico`) REFERENCES `tecnicos`(`idTecnico`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `tecnico_especialidad` ADD CONSTRAINT `tecnico_especialidad_idEspecialidad_fkey` FOREIGN KEY (`idEspecialidad`) REFERENCES `especialidades`(`idEspecialidad`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `solicitudes_servicio` ADD CONSTRAINT `solicitudes_servicio_idCliente_fkey` FOREIGN KEY (`idCliente`) REFERENCES `clientes`(`idCliente`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `solicitudes_servicio` ADD CONSTRAINT `solicitudes_servicio_idTecnico_fkey` FOREIGN KEY (`idTecnico`) REFERENCES `tecnicos`(`idTecnico`) ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `valoraciones` ADD CONSTRAINT `valoraciones_idSolicitud_fkey` FOREIGN KEY (`idSolicitud`) REFERENCES `solicitudes_servicio`(`idSolicitud`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `valoraciones` ADD CONSTRAINT `valoraciones_idTecnico_fkey` FOREIGN KEY (`idTecnico`) REFERENCES `tecnicos`(`idTecnico`) ON DELETE RESTRICT ON UPDATE CASCADE;
