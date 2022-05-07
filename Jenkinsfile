pipeline {
    agent any
    tools {
        maven 'maven'
    }
    environment {
        JAVA_HOME = "/Library/Java/JavaVirtualMachines/jdk-11.0.15.jdk/Contents/Home/"
        PATH = "$PATH:/usr/local/bin:/Library/Java/JavaVirtualMachines/jdk-11.0.15.jdk/Contents/Home/bin"
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