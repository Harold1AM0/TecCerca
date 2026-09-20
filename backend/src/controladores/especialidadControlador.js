const prisma = require("../configuracion/prisma");


const obtenerEspecialidades = async (req, res) => {
    try {

        const especialidades = await prisma.especialidad.findMany({
            select: {
                idEspecialidad: true,
                nombre: true,
                descripcion: true
            }
        });


        res.json(especialidades);


    } catch (error) {

        console.error(error);

        res.status(500).json({
            mensaje: "Error al obtener especialidades"
        });
    }
};


module.exports = {
    obtenerEspecialidades
};