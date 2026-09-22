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
            idRol
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



            // Si es cliente

            if (idRol === 1) {


                await tx.cliente.create({

                    data: {

                        idUsuario: usuario.idUsuario

                    }

                });


            }



            // Si es técnico

            if (idRol === 2) {


                await tx.tecnico.create({

                    data: {

                        idUsuario: usuario.idUsuario,

                        disponible: false

                    }

                });


            }



            return usuario;


        });



        res.status(201).json({

            mensaje: "Usuario registrado correctamente",

            usuario: resultado

        });



    } catch (error) {


        console.error(error);


        res.status(500).json({

            mensaje: "Error al registrar usuario"

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