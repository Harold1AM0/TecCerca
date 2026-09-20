const express = require("express");

const router = express.Router();

const {
    crearValoracion
} = require("../controladores/valoracionControlador");


// Crear valoración
router.post("/", crearValoracion);


module.exports = router;