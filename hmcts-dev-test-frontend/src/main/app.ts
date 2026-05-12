import * as path from 'path';
import axios from 'axios';
import bodyParser from 'body-parser';
import cookieParser from 'cookie-parser';
import express from 'express';

import { Nunjucks } from './modules/nunjucks';

const env = process.env.NODE_ENV || 'development';
const app = express();
app.locals.ENV = env;

// Backend base URL
const BACKEND_URL = 'http://localhost:4000';

// Nunjucks template setup
new Nunjucks(env === 'development').enableFor(app);

// Middleware

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

// Disable caching for dev
app.use((req, res, next) => {
  res.setHeader('Cache-Control', 'no-cache, max-age=0, must-revalidate, no-store');
  next();
});


const statusMap: Record<string, string> = {
  Pending: 'PENDING',
  'In Progress': 'IN_PROGRESS',
  Completed: 'COMPLETED',
};


// HOME – Get all tasks
app.get('/', async (req, res) => {
  try {
    const response = await axios.get(`${BACKEND_URL}/tasks`);
    res.render('home', { tasks: response.data || [] });
  } catch (err: unknown) {
    console.error('Error fetching tasks:', (err as Error).message);
    res.render('home', { tasks: [], error: 'Unable to load tasks.' });
  }
});


// CREATE TASK
app.post('/tasks', async (req, res) => {
  try {
    const dueDate = req.body.dueDate || null;
    const status = statusMap[req.body.status] || 'PENDING';

    await axios.post(`${BACKEND_URL}/tasks`, {
      title: req.body.title,
      description: req.body.description || null,
      status,
      dueDate,
    });

    res.redirect('/');
  } catch (err: unknown) {
    console.error('Error creating task:', (err as Error).message);
    res.redirect('/');
  }
});


// UPDATE STATUS
app.post('/tasks/:id/status', async (req, res) => {
  try {
    // Map the form value to backend enum
    const humanStatus = req.body.status;
    const statusMap: Record<string, string> = {
      Pending: 'PENDING',
      'In Progress': 'IN_PROGRESS',
      Completed: 'COMPLETED',
    };
    const status = statusMap[humanStatus] || "PENDING";

    await axios.patch(`${BACKEND_URL}/tasks/${req.params.id}/status`, { status });

    res.redirect('/');
  } catch (err: unknown) {
    console.error('Error updating status:', (err as Error).message);
    res.redirect('/');
  }
});


// DELETE TASK
app.post('/tasks/:id/delete', async (req, res) => {
  try {
    await axios.delete(`${BACKEND_URL}/tasks/${req.params.id}`);
    res.redirect('/');
  } catch (err: unknown) {
    console.error('Error deleting task:', (err as Error).message);
    res.redirect('/');
  }
});


// Error handler
app.use((err: any, req: express.Request, res: express.Response, next: express.NextFunction) => {
  console.error(err);
  res.status(500);
  res.render('error');
});

export { app };
