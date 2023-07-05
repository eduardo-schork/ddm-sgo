const express = require('express');
const app = express();

// Middleware para processar corpos de requisição JSON
app.use(express.json());

// Dados iniciais
let teams = [];
let appointments = [];
let projects = [];

// Rota para '/team' - Método POST
app.post('/team', (req, res) => {
  const team = req.body;
  teams.push(team);
  console.log({teams})
    res.status(201).json({ 
    ...team,
    synchronizedAt: new Date().getMilliseconds()
   });
});

// Rota para '/team' - Método GET
app.get('/team', (req, res) => {
  res.status(200).json(teams);
});

// Rota para '/appointment' - Método POST
app.post('/appointment', (req, res) => {
  const appointment = req.body;
  appointments.push(appointment);
  console.log({appointments})
    res.status(201).json({ 
    ...appointment,
    synchronizedAt: new Date().getMilliseconds()
   });
});

// Rota para '/appointment' - Método GET
app.get('/appointment', (req, res) => {
  res.status(200).json(appointments);
});

// Rota para '/project' - Método POST
app.post('/project', (req, res) => {
  const project = req.body;
  projects.push(project);
  console.log({projects})
    res.status(201).json({ 
    ...project,
    synchronizedAt: new Date().getMilliseconds()
   });
});

// Rota para '/project' - Método GET
app.get('/project', (req, res) => {
  res.status(200).json(projects);
});

// Inicia o servidor na porta 3000
app.listen(3000, () => {
  console.log('Servidor iniciado na porta 3000');
});
