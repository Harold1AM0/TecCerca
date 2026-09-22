const prisma = require("../configuracion/prisma");
const bcrypt = require("bcrypt");



// Registrar usuario
const registrarUsuario = async (req, res) => {

    try {

        const {
            nombre,
            correo,
            password,
            telefono,
            idRol,
            experiencia,
            descripcion,
            especialidades
        } = req.body;



        const passwordEncriptada = await bcrypt.hash(password, 10);



        const resultado = await prisma.$transaction(async (tx) => {



            // Crear usuario

            const usuario = await tx.usuario.create({

                data: {

                    nombre,

                    correo,

                    password: passwordEncriptada,

                    telefono,

                    idRol

                }

            });



            // Crear cliente

            if (idRol === 1) {


                await tx.cliente.create({

                    data: {

                        idUsuario: usuario.idUsuario

                    }

                });


            }



            // Crear técnico

            if (idRol === 2) {


                const tecnico = await tx.tecnico.create({

                    data: {

                        idUsuario: usuario.idUsuario,

                        experiencia,

                        descripcion,

                        disponible: false

                    }

                });



                // Crear especialidades del técnico

                if (especialidades && especialidades.length > 0) {


                    await tx.tecnicoEspecialidad.createMany({

                        data: especialidades.map(idEspecialidad => ({

                            idTecnico: tecnico.idTecnico,

                            idEspecialidad

                        }))

                    });


                }


            }



            return usuario;


        });




        res.status(201).json({

        mensaje: "Usuario registrado correctamente",

        usuario: {
        idUsuario: resultado.idUsuario,
        nombre: resultado.nombre,
        correo: resultado.correo,
        telefono: resultado.telefono,
        idRol: resultado.idRol
            }

        });



    } catch (error) {
        console.error(error);

        res.status(500).json({
            mensaje: "Error al registrar usuario",
             error: error.message
        });

    }

};


// Iniciar sesión
const iniciarSesion = async (req, res) => {


    try {


        const {
            correo,
            password
        } = req.body;



        const usuario = await prisma.usuario.findUnique({

            where: {

                correo

            }

        });



        if (!usuario) {


            return res.status(401).json({

                mensaje: "Correo o contraseña incorrectos"

            });

        }




        const passwordCorrecta = await bcrypt.compare(

            password,

            usuario.password

        );



        if (!passwordCorrecta) {


            return res.status(401).json({

                mensaje: "Correo o contraseña incorrectos"

            });

        }

        res.json({

            mensaje: "Inicio de sesión correcto",

            usuario: {

                idUsuario: usuario.idUsuario,

                nombre: usuario.nombre,

                correo: usuario.correo,

                idRol: usuario.idRol

            }

        });



    } catch (error) {
        console.error(error);
        res.status(500).json({

            mensaje: "Error al iniciar sesión"

        });

    }

};





module.exports = {

    registrarUsuario,

    iniciarSesion

};