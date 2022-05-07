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
                sh "/usr/local/bin/docker-compose up -d"
                sh 'mvn test'
                sh "/usr/local/bin/docker-compose down -v"
            }
        }
    }
}