const express = require("express");

const router = express.Router();

const {
    obtenerTecnicos,
    obtenerTecnicosPorEspecialidad
} = require("../controladores/tecnicoControlador");


// Obtener todos los técnicos
router.get("/", obtenerTecnicos);


// Obtener técnicos por especialidad
router.get("/especialidad/:id", obtenerTecnicosPorEspecialidad);


module.exports = router;