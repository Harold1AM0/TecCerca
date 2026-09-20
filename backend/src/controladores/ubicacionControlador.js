const prisma = require("../configuracion/prisma");


// Obtener técnicos cercanos
const obtenerTecnicosCercanos = async (req, res) => {

    try {

        const {
            latitud,
            longitud
        } = req.query;


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


        const tecnicosOrdenados = tecnicos
            .map(tecnico => {

                const distancia = calcularDistancia(
                    Number(latitud),
                    Number(longitud),
                    Number(tecnico.latitud),
                    Number(tecnico.longitud)
                );


                return {
                    ...tecnico,
                    distancia: distancia.toFixed(2) + " km"
                };

            })
            .sort((a, b) => {
                return parseFloat(a.distancia) - parseFloat(b.distancia);
            });


        res.json(tecnicosOrdenados);


    } catch (error) {

        console.error(error);

        res.status(500).json({
            mensaje: "Error al buscar técnicos cercanos"
        });
    }
};



// Fórmula Haversine para calcular distancia
function calcularDistancia(lat1, lon1, lat2, lon2) {

    const R = 6371;

    const dLat = (lat2 - lat1) * Math.PI / 180;
    const dLon = (lon2 - lon1) * Math.PI / 180;


    const a =
        Math.sin(dLat / 2) *
        Math.sin(dLat / 2) +
        Math.cos(lat1 * Math.PI / 180) *
        Math.cos(lat2 * Math.PI / 180) *
        Math.sin(dLon / 2) *
        Math.sin(dLon / 2);


    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));


    return R * c;
}



module.exports = {
    obtenerTecnicosCercanos
};