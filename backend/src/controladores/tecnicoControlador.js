const prisma = require("../configuracion/prisma");


// Obtener todos los técnicos
const obtenerTecnicos = async (req, res) => {

    try {

        const tecnicos = await prisma.tecnico.findMany({

            select: {
                idTecnico: true,
                experiencia: true,
                descripcion: true,
                latitud: true,
                longitud: true,
                puntajePromedio: true,
                disponible: true,

                usuario: {
                    select: {
                        nombre: true,
                        telefono: true
                    }
                }
            }
        });


        res.json(tecnicos);


    } catch (error) {

        console.error(error);

        res.status(500).json({
            mensaje: "Error al obtener técnicos"
        });
    }
};



// Obtener técnicos por especialidad
const obtenerTecnicosPorEspecialidad = async (req, res) => {

    try {

        const idEspecialidad = Number(req.params.id);


        const tecnicos = await prisma.tecnico.findMany({

            where: {
                especialidades: {
                    some: {
                        idEspecialidad
                    }
                }
            },

            select: {
                idTecnico: true,
                experiencia: true,
                descripcion: true,
                latitud: true,
                longitud: true,
                puntajePromedio: true,
                disponible: true,

                usuario: {
                    select: {
                        nombre: true,
                        telefono: true
                    }
                }
            }
        });


        res.json(tecnicos);


    } catch (error) {

        console.error(error);

        res.status(500).json({
            mensaje: "Error al buscar técnicos por especialidad"
        });
    }
};



// Obtener técnicos disponibles
const obtenerTecnicosDisponibles = async (req, res) => {

    try {

        const tecnicos = await prisma.tecnico.findMany({

            where: {
                disponible: true
            },

            select: {
                idTecnico: true,
                experiencia: true,
                descripcion: true,
                latitud: true,
                longitud: true,
                puntajePromedio: true,
                disponible: true,

                usuario: {
                    select: {
                        nombre: true,
                        telefono: true
                    }
                }
            }
        });


        res.json(tecnicos);


    } catch (error) {

        console.error(error);

        res.status(500).json({
            mensaje: "Error al obtener técnicos disponibles"
        });
    }
};



// Obtener técnicos disponibles por especialidad
const obtenerTecnicosDisponiblesPorEspecialidad = async (req, res) => {

    try {

        const idEspecialidad = Number(req.params.id);


        const tecnicos = await prisma.tecnico.findMany({

            where: {

                disponible: true,

                especialidades: {
                    some: {
                        idEspecialidad
                    }
                }

            },

            select: {
                idTecnico: true,
                experiencia: true,
                descripcion: true,
                latitud: true,
                longitud: true,
                puntajePromedio: true,
                disponible: true,

                usuario: {
                    select: {
                        nombre: true,
                        telefono: true
                    }
                }
            }
        });


        res.json(tecnicos);


    } catch (error) {

        console.error(error);

        res.status(500).json({
            mensaje: "Error al obtener técnicos disponibles por especialidad"
        });
    }
};



// Actualizar disponibilidad del técnico
const actualizarDisponibilidad = async (req, res) => {

    try {

        const idTecnico = Number(req.params.id);

        const { disponible } = req.body;


        const tecnico = await prisma.tecnico.update({

            where: {
                idTecnico
            },

            data: {
                disponible
            },

            select: {
                idTecnico: true,
                disponible: true
            }
        });


        res.json({
            mensaje: "Disponibilidad actualizada correctamente",
            tecnico
        });


    } catch (error) {

        console.error(error);

        res.status(500).json({
            mensaje: "Error al actualizar disponibilidad"
        });
    }
};



module.exports = {
    obtenerTecnicos,
    obtenerTecnicosPorEspecialidad,
    obtenerTecnicosDisponibles,
    obtenerTecnicosDisponiblesPorEspecialidad,
    actualizarDisponibilidad
};