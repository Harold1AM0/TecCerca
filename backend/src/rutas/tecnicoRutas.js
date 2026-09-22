const express = require("express");

const router = express.Router();

const {
    obtenerTecnicos,
    obtenerTecnicosPorEspecialidad,
    obtenerTecnicosDisponibles,
    actualizarDisponibilidad,
    actualizarUbicacion,
    obtenerTecnicosCercanos
} = require("../controladores/tecnicoControlador");


// Obtener todos los técnicos
router.get("/", obtenerTecnicos);


// Obtener técnicos disponibles
router.get("/disponibles", obtenerTecnicosDisponibles);


// Obtener técnicos por especialidad
router.get("/especialidad/:id", obtenerTecnicosPorEspecialidad);


// Actualizar disponibilidad del técnico
router.put("/:id/disponibilidad", actualizarDisponibilidad);


// Actualizar ubicación del técnico
router.put("/:id/ubicacion", actualizarUbicacion);

//Mostrar tecnicos cercanos xd
router.post("/cercanos", obtenerTecnicosCercanos);

module.exports = router;