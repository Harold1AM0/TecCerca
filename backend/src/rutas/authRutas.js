const express = require("express");

const router = express.Router();

const {
    registrarUsuario,
    iniciarSesion
} = require("../controladores/authControlador");


// Registrar usuario
router.post("/registro", registrarUsuario);


// Iniciar sesión
router.post("/login", iniciarSesion);


module.exports = router;