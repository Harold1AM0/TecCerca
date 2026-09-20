const express = require("express");

const router = express.Router();

const {
    crearSolicitud,
    obtenerSolicitudesCliente,
    obtenerSolicitudesTecnico,
    actualizarEstadoSolicitud
} = require("../controladores/solicitudControlador");


// Crear solicitud
router.post("/", crearSolicitud);


// Obtener solicitudes de un cliente
router.get("/cliente/:id", obtenerSolicitudesCliente);


// Obtener solicitudes de un técnico
router.get("/tecnico/:id", obtenerSolicitudesTecnico);


// Actualizar estado de solicitud
router.put("/:id/estado", actualizarEstadoSolicitud);


module.exports = router;