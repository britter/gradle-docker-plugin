plugins {
    id("com.bmuschko.docker-remote-api") version "{project-version}"
}

import com.bmuschko.gradle.docker.tasks.container.DockerRemoveContainer
import com.bmuschko.gradle.docker.tasks.container.DockerLogsContainer

// tag::on-error[]
tasks.create("removeContainer", DockerRemoveContainer::class) {
    targetContainerId("container-that-does-not-exist")
    onError { exception ->
        // Ignore exception if container does not exist otherwise throw it
        if (!exception.getMessage().contains("No such container"))
            throw exception
    }
}
// end::on-error[]

// tag::on-next[]
tasks.create("logContainer", DockerLogsContainer::class) {
    targetContainerId("container-that-does-exist")
    follow.set(true)
    tailAll.set(true)
    onNext { message ->
        // Each log message from the container will be passed as it's made available
        logger.quiet(message.toString())
    }
}
// end::on-next[]

// tag::on-complete[]
tasks.create("removeContainer", DockerRemoveContainer::class) {
    targetContainerId("container-that-does-exist")
    onComplete {
        println("Executes first")
    }
    doLast {
        println("Executes second")
    }
}
// end::on-complete[]
// end::on-complete[]