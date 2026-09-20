const prisma = require("../configuracion/prisma");

const obtenerTecnicos = async (req, res) => {
    try {

        const tecnicos = await prisma.tecnico.findMany({
            include: {
                usuario: true
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


module.exports = {
    obtenerTecnicos
};