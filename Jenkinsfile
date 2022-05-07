pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'jdk8'
    }
    environment {
        PATH = "$PATH:/usr/local/bin"
        JAVA_HOME = "/Library/Java/JavaVirtualMachines/jdk-11.0.15.jdk/Contents/Home"
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