const express = require("express");

const router = express.Router();

const {
    obtenerTecnicos,
    obtenerTecnicosPorEspecialidad,
    obtenerTecnicosDisponibles,
    obtenerTecnicosDisponiblesPorEspecialidad,
    actualizarDisponibilidad
} = require("../controladores/tecnicoControlador");


// Obtener todos los técnicos
router.get("/", obtenerTecnicos);


// Obtener técnicos disponibles
router.get("/disponibles", obtenerTecnicosDisponibles);


// Obtener técnicos disponibles por especialidad
router.get("/disponibles/especialidad/:id", obtenerTecnicosDisponiblesPorEspecialidad);


// Obtener técnicos por especialidad
router.get("/especialidad/:id", obtenerTecnicosPorEspecialidad);


// Actualizar disponibilidad del técnico
router.put("/:id/disponibilidad", actualizarDisponibilidad);


module.exports = router;