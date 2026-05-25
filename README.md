This is the brand new HMCTS case management system.

To run this project, go into the backend folder, build and run, then do the same for the frontend.

Start backend:
```bash
cd hmcts-dev-test-backend
./gradlew clean build
./gradlew clean bootrun
```
When running the backend, visit `http://localhost:4000/swagger-ui/index.html` for API docs.

Start Frontend:
```bash
cd hmcts-dev-test-frontend
yarn install
yarn webpack
yarn start:dev
```

then go to `http://localhost:3100/` to try it out!

