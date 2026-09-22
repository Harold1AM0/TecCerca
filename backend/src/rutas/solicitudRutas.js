const express = require("express");

const router = express.Router();


const {

    crearSolicitud,

    obtenerSolicitudesCliente,

    obtenerSolicitudesPendientesTecnico,

    obtenerServicioActualTecnico,

    obtenerHistorialTecnico,

    actualizarEstadoSolicitud


} = require("../controladores/solicitudControlador");



// Crear solicitud

router.post("/", crearSolicitud);



// Solicitudes del cliente

router.get("/cliente/:id", obtenerSolicitudesCliente);



// Solicitudes pendientes del técnico

router.get(
    "/tecnico/:id/pendientes",
    obtenerSolicitudesPendientesTecnico
);



// Servicio actual del técnico

router.get(
    "/tecnico/:id/actual",
    obtenerServicioActualTecnico
);



// Historial del técnico

router.get(
    "/tecnico/:id/historial",
    obtenerHistorialTecnico
);



// Cambiar estado

router.put(
    "/:id/estado",
    actualizarEstadoSolicitud
);



module.exports = router;