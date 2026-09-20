const express = require("express");

const router = express.Router();

const {
    obtenerTecnicosCercanos
} = require("../controladores/ubicacionControlador");


// Obtener técnicos cercanos
router.get("/cercanos", obtenerTecnicosCercanos);


module.exports = router;