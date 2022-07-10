pipeline {
    agent {
        label 'built-in'
    }
    environment {
        REPOSITORY_DOCKER    = 'hansleolml/devsu'
        AZ_DOCKER_KEY_ID     = 'jenkins-user-for-docker-repository'
        AZ_K8S_KEY_ID        = 'jenkins-user-for-k8s-azure'
    }
    stages {
        stage('Git Checkout'){
            steps {
                checkout scm
            }
        }
        stage('test - SonarQube analysis') {
            steps{
                sh("chmod u+x gradlew")
                withSonarQubeEnv('sonarqube-9.5') { 
                sh './gradlew sonarqube'
                }
            }
        }
        stage('build - Docker') {
            steps {
                script{
                    customImage = docker.build(REPOSITORY_DOCKER +":${env.BUILD_ID}")
                }
            }
        }
        stage('Push - Image Docker') {
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
        stage('deploy k8s') {
            steps {
                withCredentials([azureServicePrincipal(AZ_K8S_KEY_ID)]) {
                    sh 'az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID'
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