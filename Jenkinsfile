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
                sh "docker-compose --version"
                sh "docker-compose up"
                sh "docker ps -a -q"
                sh 'mvn test'
                sh "docker-compose stop"
                sh "docker-compose down"
            }
        }
    }
}