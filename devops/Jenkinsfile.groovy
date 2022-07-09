pipeline {
    agent {
        label 'built-in'
    }
    environment {
        REPOSITORY_DOCKER    = 'hansleolml/devsu'
    }
    stages {
        stage('Git Checkout'){
            steps {
                checkout scm
            }
        }
        stage('Prueba') {
            steps {
                sh("docker -v")
                sh("az --version")
                sh("ls -la")
            }
        }
        stage('Docker Build') {
            steps {
                script{
                    customImage = docker.build(REPOSITORY_DOCKER +":${env.BUILD_ID}")
                }
            }
        }
    }
    post { 
        always { 
            cleanWs()
        }
    }
}