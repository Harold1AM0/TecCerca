const prisma = require("../configuracion/prisma");


// Crear solicitud de servicio
const crearSolicitud = async (req, res) => {

    try {

        const {
            idCliente,
            idTecnico,
            descripcion
        } = req.body;


        // Evitar solicitudes duplicadas activas
        const solicitudActiva = await prisma.solicitudServicio.findFirst({

            where: {

                idCliente,

                idTecnico,

                estado: {
                    in: [
                        "PENDIENTE",
                        "ACEPTADA"
                    ]
                }

            }

        });


        if (solicitudActiva) {

            return res.status(400).json({

                mensaje: "Ya existe una solicitud activa para este técnico"

            });

        }



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




// Solicitudes del cliente
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


    } catch(error) {

        console.error(error);


        res.status(500).json({

            mensaje:"Error al obtener solicitudes"

        });

    }

};




// Solicitudes pendientes del técnico
const obtenerSolicitudesPendientesTecnico = async (req,res)=>{

    try {


        const idTecnico = Number(req.params.id);



        const solicitudes = await prisma.solicitudServicio.findMany({

            where: {

                idTecnico,

                estado:"PENDIENTE"

            },


            select:{

                idSolicitud:true,

                descripcion:true,

                estado:true,

                fecha:true,


                cliente:{

                    select:{

                        idCliente:true,


                        usuario:{

                            select:{

                                nombre:true,

                                telefono:true

                            }

                        }

                    }

                }

            }

        });


        res.json(solicitudes);



    }catch(error){

        console.error(error);


        res.status(500).json({

            mensaje:"Error al obtener solicitudes pendientes"

        });

    }

};





// Servicio actual del técnico
const obtenerServicioActualTecnico = async (req,res)=>{

    try {


        const idTecnico = Number(req.params.id);



        const solicitud = await prisma.solicitudServicio.findMany({

            where:{

                idTecnico,

                estado:"ACEPTADA"

            },


            select:{

                idSolicitud:true,

                descripcion:true,

                estado:true,

                fecha:true,


                cliente:{

                    select:{

                        idCliente:true,


                        usuario:{

                            select:{

                                nombre:true,

                                telefono:true

                            }

                        }

                    }

                }

            }

        });



        res.json(solicitud);



    }catch(error){

        console.error(error);


        res.status(500).json({

            mensaje:"Error al obtener servicio actual"

        });

    }

};




// Historial del técnico
const obtenerHistorialTecnico = async(req,res)=>{

    try{


        const idTecnico = Number(req.params.id);



        const solicitudes = await prisma.solicitudServicio.findMany({

            where:{

                idTecnico,

                estado:{

                    in:[

                        "FINALIZADA",

                        "RECHAZADA"

                    ]

                }

            },


            select:{

                idSolicitud:true,

                descripcion:true,

                estado:true,

                fecha:true,


                cliente:{

                    select:{

                        idCliente:true,


                        usuario:{

                            select:{

                                nombre:true,

                                telefono:true

                            }

                        }

                    }

                }

            }

        });



        res.json(solicitudes);



    }catch(error){

        console.error(error);


        res.status(500).json({

            mensaje:"Error al obtener historial"

        });

    }

};





// Actualizar estado
const actualizarEstadoSolicitud = async(req,res)=>{

    try{


        const idSolicitud = Number(req.params.id);


        const {estado}=req.body;



        const estadosPermitidos=[

            "PENDIENTE",

            "ACEPTADA",

            "RECHAZADA",

            "FINALIZADA"

        ];



        if(!estadosPermitidos.includes(estado)){

            return res.status(400).json({

                mensaje:"Estado no válido"

            });

        }



        const solicitud = await prisma.solicitudServicio.update({

            where:{

                idSolicitud

            },

            data:{

                estado

            }

        });




        if(solicitud.idTecnico){


            if(estado==="ACEPTADA"){

                await prisma.tecnico.update({

                    where:{

                        idTecnico:solicitud.idTecnico

                    },

                    data:{

                        disponible:false

                    }

                });

            }



            if(

                estado==="FINALIZADA" ||

                estado==="RECHAZADA"

            ){

                await prisma.tecnico.update({

                    where:{

                        idTecnico:solicitud.idTecnico

                    },

                    data:{

                        disponible:true

                    }

                });

            }


        }




        res.json({

            mensaje:"Estado de solicitud actualizado correctamente",

            solicitud

        });



    }catch(error){

        console.error(error);


        res.status(500).json({

            mensaje:"Error al actualizar estado"

        });

    }

};




module.exports={

    crearSolicitud,

    obtenerSolicitudesCliente,

    obtenerSolicitudesPendientesTecnico,

    obtenerServicioActualTecnico,

    obtenerHistorialTecnico,

    actualizarEstadoSolicitud

};