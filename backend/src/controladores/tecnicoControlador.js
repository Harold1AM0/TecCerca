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
                        idEspecialidad: idEspecialidad
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



module.exports = {
    obtenerTecnicos,
    obtenerTecnicosPorEspecialidad
};