<p align="center">
  <img src="docs/ostock-orbit.svg" alt="Services orbiting Eureka" width="920"/>
</p>

<p align="center">
  <img src="https://readme-typing-svg.demolab.com?font=JetBrains+Mono&weight=600&size=26&pause=1200&color=7C5CFF&center=true&vCenter=true&width=720&lines=Not+a+monolith.;A+small+city+of+JVMs.;They+find+each+other+in+the+dark." alt="typing headline"/>
</p>

<p align="center">
  <img alt="Java" src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk"/>
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring_Boot-4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img alt="Docker" src="https://img.shields.io/badge/Docker_Compose-alive-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
  <img alt="Eureka" src="https://img.shields.io/badge/Eureka-phonebook-black?style=for-the-badge"/>
</p>

---

## What is this place?

A lab from the **Ostock / Spring Cloud** trail: licenses live in one building, organizations in another. They do **not** share a database. They share a rumor mill called **Eureka**.

| Citizen | Port | Job |
|---|---|---|
| `licensing-service` | `8080` | sells keys, then asks “who is this org?” |
| `organization` | `8081` | keeps names, emails, phones |
| `config-server` | `8071` | whispers properties |
| `eureka-server` | `8070` | the phonebook |
| `postgres` | `5432` | two catalogs: `licenses` + `organizationdb` |

`license.organization_id` is just a string that *happens* to match `organizations.organization_id`. Postgres never joins them. The join happens over HTTP after Eureka points at a living instance.

```mermaid
flowchart LR
  You((you)) -->|GET license + clientType| L[licensing :8080]
  L -->|who is organization-service?| E[(eureka :8070)]
  E -->|host:port| L
  L -->|GET /v1/organization/id| O[organization :8081]
  L --- P1[(db licenses)]
  O --- P2[(db organizationdb)]
  C[config :8071] -.-> L
  C -.-> O
  C -.-> E
```

<details>
<summary>📼 Open the terminal tape</summary>

```text
 $  docker compose up -d
 >  postgres        ... UP
 >  config-server   ... 8071
 >  eureka-server   ... 8070
 >  organization    ... 8081
 >  licensing       ... 8080
 $  curl localhost:8070          # dashboard
 $  curl localhost:8081/v1/organization/optima
```

</details>

---

## Wake the city

From the repo root (JAR first, then images):

```bash
./gradlew bootJar :config-server:bootJar :organization:bootJar
./eureka-server/mvnw -f eureka-server/pom.xml package
docker compose up -d --build
```

Do **not** also hit Run in the IDE on the same ports. One process per socket.

| Check | URL |
|---|---|
| Eureka UI | http://localhost:8070 |
| Registry XML/JSON | http://localhost:8070/eureka/apps |
| Config | http://localhost:8071/licensing-service/dev |
| License API | http://localhost:8080/v1/organization/{orgId}/license |
| Org API | http://localhost:8081/v1/organization/{orgId} |

---

## A tiny ritual (data is not magic)

1. Create an **organization** (PUT keeps the id you choose):

```http
PUT http://localhost:8081/v1/organization/optima
Content-Type: application/json

{"name":"Optima","contactName":"Ivan","contactEmail":"ivan@optima.test","contactPhone":"+7-000"}
```

2. Create a **license** (id becomes a UUID):

```http
POST http://localhost:8080/v1/organization/optima/license
Content-Type: application/json

{"description":"Software product","organizationId":"optima","productName":"Ostock","licenseType":"full"}
```

3. Ask licensing to fetch the org through discovery (when clients are wired):

```http
GET http://localhost:8080/v1/organization/optima/license/{uuid}/discovery
GET http://localhost:8080/v1/organization/optima/license/{uuid}/rest
GET http://localhost:8080/v1/organization/optima/license/{uuid}/feign
```

Three doors, one hallway: **DiscoveryClient**, **@LoadBalanced RestTemplate**, **OpenFeign**.

---

## Layout

```text
microservice/                 licensing-service (Gradle)
  config-server/              native Spring Cloud Config
  organization/               organization-service
  eureka-server/              Netflix Eureka (Maven wrapper)
  docker-compose.yml          the city wall
  docs/ostock-orbit.svg       this README's heartbeat
```

Gradle owns the root. `organization` used to be a Maven guest; the living build is `organization/build.gradle`. Eureka still packs with `mvnw`.

---

<p align="center">
  <sub>built for learning how services find each other · if a container has a pirate name, it is probably a ghost — delete it</sub>
</p>
