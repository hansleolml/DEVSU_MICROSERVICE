pipeline {
    agent {
        label 'built-in'
    }
    environment {
        REPOSITORY_DOCKER    = 'hansleolml/devsu'
        AZ_DOCKER_KEY_ID     = 'jenkins-user-for-docker-repository'
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
        stage('Push Docker') {
            steps {
                script {
                    docker.withRegistry('',AZ_DOCKER_KEY_ID) {
                        def now = new Date()
                        customImage.push('latest')
                        customImage.push(now.format("yyMMddHHmmss", TimeZone.getTimeZone('UTC')))
                        sh "docker rmi -f ${env.REPOSITORY_DOCKER}:${env.BUILD_ID} ${env.REPOSITORY_DOCKER}:latest"
                    }
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