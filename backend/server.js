const express = require("express");
const cors = require("cors");
require("dotenv").config();

const app = express();


// Middlewares
app.use(cors());
app.use(express.json());


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