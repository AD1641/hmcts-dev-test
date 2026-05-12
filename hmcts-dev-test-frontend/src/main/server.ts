import { app } from './app';

const PORT = process.env.PORT || 3100;

app.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}`);
});
