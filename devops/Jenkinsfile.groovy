pipeline {
    agent any
    environment {
        AZ_DOCKER_KEY_ID     = 'jenkins-user-for-docker-repository'
        REPOSITORY_DOCKER    = 'hansleolml/reto_devsu'
        REPOSITORY_GIT       = 'https://github.com/hansleolml/DEVSU_MICROSERVICE.git'
        AZ_K8S_KEY_ID        = 'jenkins-user-for-k8s-azure'
        AZ_GIT_KEY_ID        = 'jenkins-user-for-git-repository'
    }
    stages {
        stage('Git Clone'){
            steps {
                git credentialsId: AZ_GIT_KEY_ID, url: REPOSITORY_GIT
            }
        }
        stage('Prueba') {
            steps {
                sh("hostname")
                sh("az --version")
                sh("ls -la")
            }
        }
    }
    post { 
        always { 
            cleanWs()
        }
    }
}