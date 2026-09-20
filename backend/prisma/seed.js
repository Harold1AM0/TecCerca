const { PrismaClient } = require("@prisma/client");

const prisma = new PrismaClient();

async function main() {

    // Crear roles
    const rolCliente = await prisma.rol.create({
        data: {
            nombre: "CLIENTE"
        }
    });

    const rolTecnico = await prisma.rol.create({
        data: {
            nombre: "TECNICO"
        }
    });


    // Crear especialidades
    const computadoras = await prisma.especialidad.create({
        data: {
            nombre: "Reparación de Computadoras",
            descripcion: "Mantenimiento, reparación y soporte técnico de equipos."
        }
    });

    const electricidad = await prisma.especialidad.create({
        data: {
            nombre: "Electricidad",
            descripcion: "Instalaciones y reparaciones eléctricas."
        }
    });


    // Crear usuario cliente
    const usuarioCliente = await prisma.usuario.create({
        data: {
            nombre: "Juan Pérez",
            correo: "juan@gmail.com",
            password: "123456",
            telefono: "999999999",
            idRol: rolCliente.idRol
        }
    });


    await prisma.cliente.create({
        data: {
            idUsuario: usuarioCliente.idUsuario
        }
    });


    // Crear usuario técnico
    const usuarioTecnico = await prisma.usuario.create({
        data: {
            nombre: "Carlos Ramirez",
            correo: "carlos@gmail.com",
            password: "123456",
            telefono: "988888888",
            idRol: rolTecnico.idRol
        }
    });


    const tecnico = await prisma.tecnico.create({
        data: {
            idUsuario: usuarioTecnico.idUsuario,
            experiencia: 5,
            descripcion: "Especialista en reparación de laptops y computadoras.",
            latitud: -12.046374,
            longitud: -77.042793,
            puntajePromedio: 4.8
        }
    });


    // Relacionar técnico con especialidad
    await prisma.tecnicoEspecialidad.create({
        data: {
            idTecnico: tecnico.idTecnico,
            idEspecialidad: computadoras.idEspecialidad
        }
    });


    console.log("Datos iniciales creados correctamente");
}


main()
    .then(async () => {
        await prisma.$disconnect();
    })
    .catch(async (error) => {
        console.error(error);
        await prisma.$disconnect();
        process.exit(1);
    });