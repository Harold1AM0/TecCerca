const express = require("express");
const cors = require("cors");
require("dotenv").config();


const tecnicoRutas = require("./src/rutas/tecnicoRutas");
const especialidadRutas = require("./src/rutas/especialidadRutas");
const authRutas = require("./src/rutas/authRutas");


const app = express();


// Middlewares
app.use(cors());
app.use(express.json());


// Rutas API
app.use("/api/tecnicos", tecnicoRutas);
app.use("/api/especialidades", especialidadRutas);
app.use("/api/auth", authRutas);


// Ruta de prueba
app.get("/", (req, res) => {
    res.json({
        proyecto: "TecCerca",
        mensaje: "Backend funcionando correctamente 🚀"
    });
});


// Puerto
const PUERTO = process.env.PUERTO || 3000;


app.listen(PUERTO, () => {
    console.log(`Servidor TecCerca ejecutándose en puerto ${PUERTO}`);
});