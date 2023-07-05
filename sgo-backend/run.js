const express = require("express");
const app = express();

app.use(express.json());

let teams = [];
let appointments = [];
let projects = [];

// Rota para '/team' - Método POST
app.post("/team", (req, res) => {
    const team = req.body;
    teams.push(team);

    res.status(201).json({
        ...team,
        synchronizedAt: new Date().getMilliseconds(),
    });
});

// Rota para '/team' - Método GET
app.get("/team", (req, res) => {
    res.status(200).json(teams);
});

// Rota para '/appointment' - Método POST
app.post("/appointment", (req, res) => {
    const appointment = req.body;
    appointments.push(appointment);

    res.status(201).json({
        ...appointment,
        synchronizedAt: new Date().getMilliseconds(),
    });
});

// Rota para '/appointment' - Método GET
app.get("/appointment", (req, res) => {
    res.status(200).json(appointments);
});

// Rota para '/project' - Método POST
app.post("/project", (req, res) => {
    const project = req.body;
    projects.push(project);

    res.status(201).json({
        ...project,
        synchronizedAt: new Date().getMilliseconds(),
    });
});

// Rota para '/project' - Método GET
app.get("/project", (req, res) => {
    res.status(200).json(projects);
});

app.listen(3000, () => {
    console.log("Servidor iniciado na porta 3000");
});
