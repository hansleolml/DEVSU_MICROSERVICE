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
        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([azureServicePrincipal(AZ_K8S_KEY_ID)]) {
                    sh 'az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID'
                    sh 'az aks get-credentials --resource-group rg-devsu-dev-centralUs-001 --name aks-devsu-dev-centralUs-001 --subscription ae272f53-0ce5-4e22-9041-f236c379f851'
                    sh 'kubectl apply -f ./kubernetes/api_deployment.yml -n nsdevsu-qa'
                    sh 'kubectl rollout restart deployment/devsuprueba-qa -n nsdevsu-qa'
                    sh 'sleep 10'
                }                   
            }
        }
        stage('Deploy to APIM') {
            steps {
                script {
                    sh 'az apim api import -g rg-devsu-dev-centralUs-001 --service-name apim-devsu-dev-centralUs-001 --subscription-key-header-name X-Parse-REST-API-Key --subscription-key-query-param-name subscription-key --api-id openapi-definition-qa --api-version qa --api-version-set-id 62ca714fb19f79aae4479f96 --path / --specification-url http://20.96.236.30/v3/api-docs.yaml --specification-format OpenApiJson'
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