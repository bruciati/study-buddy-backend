#!/usr/bin/env python3
from concurrent.futures import ThreadPoolExecutor, as_completed
import multiprocessing
import os
from subprocess import  Popen, PIPE

# --------------------
# CONSTANTS
MAVEN_CMD = "./mvnw"
SERVICES = [
    ("eureka",   8761, False),
    ("gateway",  8080, False),
    ("auth",     8081, False),
    ("users",    8082, True ),
    ("groups",   8083, True ),
    ("meetings", 8084, True )
] # (service_name, service_port, needs_db)

DOCKER_CMD = "docker"
DOCKER_TMPLT = """
FROM openjdk:17-alpine
COPY target/{service}-0.0.1.jar /app.jar
EXPOSE {port:d}
RUN adduser -S web && \\
    chown web /app.jar && \\
    chmod +x /app.jar
USER web
ENTRYPOINT ["java", "-jar", "/app.jar"]
"""


# --------------------
# FUNCTIONS
def build_microservice(service):
    service_cwd = "{}/{}".format(os.getcwd(), service)
    with Popen([MAVEN_CMD, "clean", "package", "-Dmaven.test.skip"], cwd=service_cwd, shell=False) as process:
        print(f" ---->> Building '{service}' jar...")
        process.wait()


def build_dockerimage(service, port):
    service_cwd = "{}/{}".format(os.getcwd(), service)
    input_string = DOCKER_TMPLT.format(service = service, port = port)
    with Popen([DOCKER_CMD, "build", ".", "-f", "-", "-t", f"{service}_img:latest"], cwd=service_cwd, stdin=PIPE, shell=False) as process:
        print(f" ---->> Building '{service}' docker image...")
        process.communicate(str.encode(input_string))
        process.wait()


def build_task(service, port):
    build_microservice(service)
    build_dockerimage(service, port)


# --------------------
# MAIN
cores = multiprocessing.cpu_count()
with ThreadPoolExecutor(max_workers = cores) as executor:
    futures = { executor.submit(build_task, s, p): (s, p, d) for (s, p, d) in SERVICES }
    as_completed(futures)
