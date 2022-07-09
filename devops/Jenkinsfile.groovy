pipeline {
    agent {
        label 'built-in'
    }
    stages {
        stage('Git Checkout'){
            steps {
                checkout scm
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