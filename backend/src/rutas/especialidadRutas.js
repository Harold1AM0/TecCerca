const express = require("express");

const router = express.Router();

const {
    obtenerEspecialidades
} = require("../controladores/especialidadControlador");


// Obtener todas las especialidades
router.get("/", obtenerEspecialidades);


module.exports = router;