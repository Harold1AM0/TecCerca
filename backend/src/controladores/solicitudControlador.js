const prisma = require("../configuracion/prisma");


// Crear solicitud de servicio
const crearSolicitud = async (req, res) => {

    try {

        const {
            idCliente,
            idTecnico,
            descripcion
        } = req.body;


        const solicitud = await prisma.solicitudServicio.create({
            data: {
                idCliente,
                idTecnico,
                descripcion,
                estado: "PENDIENTE"
            }
        });


        res.status(201).json({
            mensaje: "Solicitud creada correctamente",
            solicitud
        });


    } catch (error) {

        console.error(error);

        res.status(500).json({
            mensaje: "Error al crear solicitud"
        });
    }
};



// Obtener solicitudes de un cliente
const obtenerSolicitudesCliente = async (req, res) => {

    try {

        const idCliente = Number(req.params.id);


        const solicitudes = await prisma.solicitudServicio.findMany({

            where: {
                idCliente
            },

            select: {

                idSolicitud: true,
                descripcion: true,
                estado: true,
                fecha: true,

                tecnico: {
                    select: {

                        idTecnico: true,
                        experiencia: true,
                        descripcion: true,
                        puntajePromedio: true,

                        usuario: {
                            select: {
                                nombre: true,
                                telefono: true
                            }
                        }
                    }
                }
            }
        });


        res.json(solicitudes);


    } catch (error) {

        console.error(error);

        res.status(500).json({
            mensaje: "Error al obtener solicitudes"
        });
    }
};



// Obtener solicitudes de un técnico
const obtenerSolicitudesTecnico = async (req, res) => {

    try {

        const idTecnico = Number(req.params.id);


        const solicitudes = await prisma.solicitudServicio.findMany({

            where: {
                idTecnico
            },

            select: {

                idSolicitud: true,
                descripcion: true,
                estado: true,
                fecha: true,

                cliente: {
                    select: {

                        idCliente: true,

                        usuario: {
                            select: {
                                nombre: true,
                                telefono: true
                            }
                        }
                    }
                }
            }
        });


        res.json(solicitudes);


    } catch (error) {

        console.error(error);

        res.status(500).json({
            mensaje: "Error al obtener solicitudes"
        });
    }
};



// Actualizar estado de solicitud
const actualizarEstadoSolicitud = async (req, res) => {

    try {

        const idSolicitud = Number(req.params.id);

        const { estado } = req.body;


        const estadosPermitidos = [
            "PENDIENTE",
            "ACEPTADA",
            "RECHAZADA",
            "FINALIZADA"
        ];


        if (!estadosPermitidos.includes(estado)) {

            return res.status(400).json({
                mensaje: "Estado no válido"
            });

        }


        const solicitudExiste = await prisma.solicitudServicio.findUnique({

            where: {
                idSolicitud
            }

        });


        if (!solicitudExiste) {

            return res.status(404).json({
                mensaje: "Solicitud no encontrada"
            });

        }


        const solicitud = await prisma.solicitudServicio.update({

            where: {
                idSolicitud
            },

            data: {
                estado
            }
        });



        // Actualizar disponibilidad del técnico

        if (solicitud.idTecnico) {


            if (estado === "ACEPTADA") {

                await prisma.tecnico.update({

                    where: {
                        idTecnico: solicitud.idTecnico
                    },

                    data: {
                        disponible: false
                    }

                });

            }



            if (
                estado === "FINALIZADA" ||
                estado === "RECHAZADA"
            ) {

                await prisma.tecnico.update({

                    where: {
                        idTecnico: solicitud.idTecnico
                    },

                    data: {
                        disponible: true
                    }

                });

            }

        }



        res.json({

            mensaje: "Estado de solicitud actualizado correctamente",

            solicitud

        });


    } catch (error) {

        console.error(error);

        res.status(500).json({
            mensaje: "Error al actualizar estado de solicitud"
        });
    }
};



module.exports = {
    crearSolicitud,
    obtenerSolicitudesCliente,
    obtenerSolicitudesTecnico,
    actualizarEstadoSolicitud
};