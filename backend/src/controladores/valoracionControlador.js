const prisma = require("../configuracion/prisma");


// Crear valoración
const crearValoracion = async (req, res) => {

    try {

        const {
            idSolicitud,
            idTecnico,
            puntuacion,
            comentario
        } = req.body;


        const valoracion = await prisma.valoracion.create({

            data: {
                idSolicitud,
                idTecnico,
                puntuacion,
                comentario
            }

        });


        // Actualizar promedio del técnico

        const valoraciones = await prisma.valoracion.findMany({

            where: {
                idTecnico
            }

        });


        const promedio =
            valoraciones.reduce(
                (total, item) => total + item.puntuacion,
                0
            ) / valoraciones.length;


        await prisma.tecnico.update({

            where: {
                idTecnico
            },

            data: {
                puntajePromedio: promedio
            }

        });


        res.status(201).json({

            mensaje: "Valoración registrada correctamente",
            valoracion

        });


    } catch (error) {

        console.error(error);

        res.status(500).json({

            mensaje: "Error al registrar valoración"

        });
    }
};



module.exports = {
    crearValoracion
};