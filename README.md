Event Sourcing DDD API Example

Commands
Start docker services
make up
or

docker-compose up -d
This will start the Node and PostgreSQL docker services.

If have error be sure that your local services (like postgreSQL) are stopped.

Down docker services
make down
or

docker-compose down
Run project
make run-dev
or

docker-compose exec api npm run dev
This will run nodemon with typescript in dev environment.

Run project compile
make run-start
or

docker-compose exec api npm run start
This will compile typescript in ./dist folder and run nodemon with js transpiled.

Build project
make run-build
or

docker-compose exec api npm run build
This will only compile the typscript

Install dependencies
make install
or

docker-compose exec api npm install
Run eslint
make eslint-check
make eslint-fix
or

docker-compose exec api npm run eslint:check
docker-compose exec api npm run eslint:fix
Rules for eslint are in .eslintrc.json file on root path.

Run unit test
make run-test-unit
or

docker-compose exec api npm run test:unit
Run coverage for unit test
make run-test-unit-coverage
or

docker-compose exec api npm run test:unit:coverage
