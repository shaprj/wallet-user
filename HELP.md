# Getting Started

### Description

Wallet-User service main functions are:
* Add user operation
* Get all users
* Ban user

### Build Wallet-User service

Basically two ways for building Wallet-User service exist.

1. Use gradle builder from command line. This way is fast enough and could be used for local debug procedure:

* ```gradle build```

3. Use docker container builder. This way is good for deploying in container orchestration system, e.g. Kubernates. There are two gradle tasks in project:
* ```gradle docker_build``` task is used for building docker image.
* ```gradle docker_push``` task is used for pushing it to remote repo



### Run

There are two profiles in Wallet-User service project:

* Default. Is used for local development cycle. Provides default local settings 
* Prod. Is used for deploying to remote stand. following environment variables are used to control behaviour of service:

  * ENV_POSTGRESQL_HOST (Default value: 192.168.0.102)
  * ENV_POSTGRESQL_PORT (Default value: 5432)
  * ENV_POSTGRESQL_DATABASE (Default value: postgres)
  * ENV_POSTGRESQL_USERNAME (Default value: postgres)
  * ENV_POSTGRESQL_PASSWORD (Default value: 1q2w3e4r)
  * ENV_PRINT_STACK_TRACE (Default: local: true, stand: false)

Below is en example of running container:

```docker run -it --expose=8080 --env ENV_POSTGRESQL_HOST=192.168.0.102 -p 8080:8080 shaprj/wallet-user:latest```

Here only ENV_POSTGRESQL_HOST is redefined.

### Query examples

After Wallet-User service is successfully started we can access to it via swagger-ui web form:

```http://localhost:8080/swagger-ui```

Below are curl examples of REST querries:

1. Get all users query:
```
    curl -X 'GET' \
   'http://localhost:8080/user/user/all' \
   -H 'accept: */*'
```
2. Add user query:
```
   curl -X 'POST' \
   'http://localhost:8080/user/user/create' \
   -H 'accept: */*' \
   -H 'Content-Type: application/json' \
   -d '{
   "name": "Alex",
   "lastName": "Smith",
   "age": 25
   }'
```
3. Ban user query:
```
curl -X 'POST' \
  'http://localhost:8080/user/user/ban' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "baa741f8-63b8-43ce-ac16-96891108e3d0",
  "banReason": "Bad guy"
}'
```

### Integration

This API could be simply integrated via openapi. Scheme for classes auto generation could be loaded here:

```http://localhost:8080/v3/api-docs.yaml```

