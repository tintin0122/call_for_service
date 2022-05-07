pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'jdk8'
    }
    triggers {
        pollSCM '* * * * *'
    }
    stages {
//         stage('Build') {
//             steps {
//                 sh 'mvn clean install -DskipTests'
//             }
//         }
        stage('Test') {
            steps {
                sh "docker-compose up -d"
                sh 'mvn test'
                sh "docker-compose down -v"
            }
        }
    }
}