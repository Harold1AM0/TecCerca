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


        const usuario = await prisma.usuario.create({
            data: {
                nombre,
                correo,
                password: passwordEncriptada,
                telefono,
                idRol
            }
        });


        res.status(201).json({
            mensaje: "Usuario registrado correctamente",
            usuario: {
                idUsuario: usuario.idUsuario,
                nombre: usuario.nombre,
                correo: usuario.correo,
                telefono: usuario.telefono,
                idRol: usuario.idRol
            }
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


        if (!usuario || !(await bcrypt.compare(password, usuario.password))) {

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