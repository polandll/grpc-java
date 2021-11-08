# grpc-java

In this demo you will work with the GRPC interface in Stargate

## 1. Prerequisites

#### 📦 Docker
- Use the [reference documentation](https://www.docker.com/products/docker-desktop) to install **Docker Desktop**
- Validate your installation with

```bash
docker -v
docker run hello-world
```

#### 📦 Java Development Kit (JDK) 8+
- Use the [reference documentation](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html) to install a **Java Development Kit**
- Validate your installation with

```bash
java --version
```

#### 📦 Apache Maven
- Use the [reference documentation](https://maven.apache.org/install.html) to install **Apache Maven**
- Validate your installation with

```bash
mvn -version
```

## 2. Start Stargate

> The element below have been extracted from [Stargate documentation](https://stargate.io/docs/stargate/1.0/quickstart/quick_start-rest.html
)

#### ✅ Step 2a: Pull the docker image

Download [Stargate docker image](https://hub.docker.com/r/stargateio/stargate-3_11/tags) : 
![Docker Image Version (tag latest semver)](https://img.shields.io/docker/v/stargateio/stargate-3_11/v1.0.40)

```bash
docker pull stargateio/stargate-3_11:v1.0.40
```

#### ✅ Step 2b: Start stargate container in *development* mode.

```bash
docker run --name stargate \
  -p 8080:8080 \
  -p 8081:8081 \
  -p 8082:8082 \
  -p 8090:8090 \
  -p 127.0.0.1:9042:9042 \
  -d \
  -e CLUSTER_NAME=stargate \
  -e CLUSTER_VERSION=3.11 \
  -e DEVELOPER_MODE=true \
  stargateio/stargate-3_11:v1.0.40
```

With Development mode Stargate also the role of a data node, you do not need an extra Cassandra container.

All apis are enabled, here is the port list:
- `8080` is the graphql port
- `8081` is the authentication port
- `8082` is the rest port
- `8090` is the grpc port

After 30 seconds you should be able to following URLs:
- [URL for health: http://localhost:8082/health](http://localhost:8082/health)
- [URL for Open Api specification: http://localhost:8082/swagger-ui/#/](http://localhost:8082/swagger-ui/#/)
- [URL for graphql Playground: http://localhost:8080/playground](http://localhost:8080/playground)



## 3. Execute the main class for Stargate

You can now run the example

```
mvn exec:java -Dexec.mainClass="com.datastax.tutorial.ConnectStargate"
```

**Expected output**

```
[INFO] 
[INFO] --- exec-maven-plugin:3.0.0:java (default-cli) @ grpc-java ---
Keyspace 'test' has been created.
Table 'users' has been created.
2 rows have been inserted in table users.
FirstName=Doug, lastname=Wettlaufer
FirstName=Lorina, lastname=Poland
Everything worked!
```

## 4. Execute the main class for Astra

You will need an [Astra Database](https://docs.datastax.com/en/astra/docs/creating-your-astra-database.html) with an [Astra Token](https://docs.datastax.com/en/astra/docs/manage-application-tokens.html)

In class `ConnectAstra` edit the following properties

```java
private static final String ASTRA_DB_ID      = "<id>";
private static final String ASTRA_DB_REGION  = "<region>";
private static final String ASTRA_TOKEN      = "<token>";
private static final String ASTRA_KEYSPACE   = "<keyspace>";
```

Then execute the class 
    
```
mvn exec:java -Dexec.mainClass="com.datastax.tutorial.ConnectAstra"
```

