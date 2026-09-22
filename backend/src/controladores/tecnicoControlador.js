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
    // Actualizar ubicación del técnico
    const actualizarUbicacion = async (req, res) => {

        try {

            const idTecnico = Number(req.params.id);

            const {
                latitud,
                longitud
            } = req.body;



            const tecnico = await prisma.tecnico.update({

                where: {
                    idTecnico
                },

                data: {

                    latitud,

                    longitud

                },

                select: {

                    idTecnico: true,

                    latitud: true,

                    longitud: true

                }

            });



            res.json({

                mensaje: "Ubicación actualizada correctamente",

                tecnico

            });



        } catch (error) {

            console.error(error);


            res.status(500).json({

                mensaje: "Error al actualizar ubicación"

            });

        }

    };

    // Obtener técnicos cercanos
const obtenerTecnicosCercanos = async (req, res) => {

    try {

        const {
            latitud,
            longitud,
            idEspecialidad,
            radioKm = 5
        } = req.body;

        const tecnicos = await prisma.tecnico.findMany({
            where: {
                disponible: true,
                latitud: {
                    not: null
                },
                longitud: {
                    not: null
                },
                especialidades: {
                    some: {
                        idEspecialidad: Number(idEspecialidad)
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
        const tecnicosConDistancia = tecnicos.map(tecnico => {
        const distancia = calcularDistancia(
            Number(latitud),
            Number(longitud),
            Number(tecnico.latitud),
            Number(tecnico.longitud)
        );
        return {
            idTecnico: tecnico.idTecnico,
            nombre: tecnico.usuario.nombre,
            telefono: tecnico.usuario.telefono,
            experiencia: tecnico.experiencia,
            descripcion: tecnico.descripcion,
            puntajePromedio: tecnico.puntajePromedio,
            distanciaKm: Number(distancia.toFixed(2))
        };
    }).filter(tecnico => tecnico.distanciaKm <= Number(radioKm));
        tecnicosConDistancia.sort((a, b) => {
        if (a.distanciaKm !== b.distanciaKm) {
            return a.distanciaKm - b.distanciaKm;
        }
        return Number(b.puntajePromedio) - Number(a.puntajePromedio);
        });
        res.json(tecnicosConDistancia);

    } catch (error) {
        console.error(error);
        res.status(500).json({
            mensaje: "Error al buscar técnicos cercanos"
        });
    }
};

// Calcular distancia entre coordenadas
const calcularDistancia = (lat1, lon1, lat2, lon2) => {
    const R = 6371;
    const dLat = (lat2 - lat1) * Math.PI / 180;
    const dLon = (lon2 - lon1) * Math.PI / 180;

    const a = 
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(lat1 * Math.PI / 180) *
        Math.cos(lat2 * Math.PI / 180) *
        Math.sin(dLon / 2) *
        Math.sin(dLon / 2);

    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));


    return R * c;

    };
module.exports = {

    obtenerTecnicos,
    obtenerTecnicosPorEspecialidad,
    obtenerTecnicosDisponibles,
    obtenerTecnicosDisponiblesPorEspecialidad,
    obtenerTecnicosCercanos,
    actualizarDisponibilidad,
    actualizarUbicacion

};