const express = require("express");

const router = express.Router();

const {
    obtenerTecnicos,
    obtenerTecnicosPorEspecialidad,
    actualizarDisponibilidad
} = require("../controladores/tecnicoControlador");


// Obtener todos los técnicos
router.get("/", obtenerTecnicos);


// Obtener técnicos por especialidad
router.get("/especialidad/:id", obtenerTecnicosPorEspecialidad);


// Actualizar disponibilidad del técnico
router.put("/:id/disponibilidad", actualizarDisponibilidad);


module.exports = router;