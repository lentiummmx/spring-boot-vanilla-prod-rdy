[![YugabyteDB profile image](https://res.cloudinary.com/practicaldev/image/fetch/s--foNaeRLu--/c_fill,f_auto,fl_progressive,h_50,q_auto,w_50/https://dev-to-uploads.s3.amazonaws.com/uploads/organization/profile_image/4200/874c3da5-f3e8-4f6c-8b10-e36708099da3.png)](https://dev.to/yugabyte) [![Franck Pachot](https://res.cloudinary.com/practicaldev/image/fetch/s--dpV1rnnw--/c_fill,f_auto,fl_progressive,h_50,q_auto,w_50/https://dev-to-uploads.s3.amazonaws.com/uploads/user/profile_image/114176/c569c0db-9431-4319-ae0b-cb5aa7c7d0e3.png)](https://dev.to/franckpachot)

[Franck Pachot](https://dev.to/franckpachot) for [YugabyteDB](https://dev.to/yugabyte)

Posted on May 4 • Updated on Jun 27

# docker-compose.yaml to start YugabyteDB with POSTGRES\_USER POSTGRES\_PASSWORD POSTGRES\_DB env

[#yugabytedb](https://dev.to/t/yugabytedb) [#postgres](https://dev.to/t/postgres) [#docker](https://dev.to/t/docker) [#compose](https://dev.to/t/compose)

The Docker image provided by Yugabyte for YugabyteDB doesn't create and start a database because it can be used for different purposes: start one `yb-master` or `yb-tserver`, or both with `yugabyted`. An operational database is composed of multiple nodes. However, for development or automated tests, you just want to start one container with a defined database, user and password.

YugabyteDB is PostgreSQL compatible, which means that you can replace PostgreSQL by YugabyteDB without changing the application. The PostgreSQL image allows to create a database and set user and password with the following environment variables: POSTGRES\_USER POSTGRES\_PASSWORD POSTGRES\_DB and you probably want the same for YugabyteDB. And, why not, with the same environment variables.

Here is an example based on _knex.js_ test suite [https://github.com/knex/knex/blob/master/scripts/docker-compose.yml](https://github.com/knex/knex/blob/master/scripts/docker-compose.yml)

This `docker-compose.yml` starts PostgreSQL with a database named `knex_test` and user/password `testuser`/`knextest` and starts another container to check when this connection is available.

Here is how I add YugabyteDB in the same way:

```yaml
version: '3'
services:

  yugabytedb:
    image: docker.io/yugabytedb/yugabyte:latest
    command: |
     bash -c "
     # create database and user as soon as database is up
     until [ -z "POSTGRES_USER" ] || PGPASSWORD=yugabyte bin/ysqlsh -v ON_ERROR_STOP=1 \\
      -c \"create database $${POSTGRES_DB:-$${POSTGRES_USER}} \" \\
      -c \"create user $${POSTGRES_USER} password '$${POSTGRES_PASSWORD}' \" \\
      2>/dev/null
      do
       echo \"Waiting for YugabyteDB to be up for creating user $${POSTGRES_USER}.\" ; sleep 5
      done &
     # start YugabyteDB
     bin/yugabyted start --daemon=false --tserver_flags='ysql_enable_auth=true'
     "
    environment:
      - POSTGRES_USER=testuser
      - POSTGRES_PASSWORD=knextest
      - POSTGRES_DB=knex_test
    ports:
      - 25433:5433
      - 27000:7000

  waityugabyte:
    image: postgres:13-alpine
    links:
      - yugabytedb
    depends_on:
      - yugabytedb
    entrypoint:
      - bash
      - -c
      - 'until PGPASSWORD=knextest /usr/local/bin/psql postgres://testuser@yugabytedb:5433/knex_test -c "SELECT 1"; do sleep 5; done'

```

Enter fullscreen mode Exit fullscreen mode

The YugabyteDB database is started with `bin/yugabyted start --daemon=false --tserver_flags='ysql_enable_auth=true'` and a background loop tries to connect to create the database and the user according to the environment variables.

The default port for YugabyteBD is 5433 which makes it possible to start it along with PostgreSQL on its default port. The port 7000 is the graphical console.

If you want a more complex docker-compose.yaml with full control on yb-master/yb-tserver cluster topology, here is how I generate it for my labs: [https://github.com/FranckPachot/ybdemo/tree/main/docker/yb-lab](https://github.com/FranckPachot/ybdemo/tree/main/docker/yb-lab)

## [](https://dev.to/yugabyte/docker-composeyaml-to-start-yugabytedb-with-postgresuser-postgrespassword-postgresdb-env-4do3#update-on-june-2022)Update on June 2022

The following command is probably better, using the `--initial_scripts_dir`:

```yaml
    command: |
     bash -c '
     mkdir -p /var/ybinit
     echo "create database $${POSTGRES_DB:-$${POSTGRES_USER}}             " > /var/ybinit/01-db.sql
     echo "create user $${POSTGRES_USER} password '$${POSTGRES_PASSWORD}' " > /var/ybinit/02-usr.sql
     # start YugabyteDB
     bin/yugabyted start --daemon=false --initial_scripts_dir=/var/ybinit --tserver_flags=ysql_enable_auth=true
     '
```

Enter fullscreen mode Exit fullscreen mode
