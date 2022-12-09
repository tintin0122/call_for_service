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
    // hello
    stages {
//         stage('Build') {
//             steps {
//                 sh 'mvn clean install -DskipTests'
//             }
//         }

        stage('Test') {
            steps {
                sh "docker-compose up -d"
                sh 'mvn clean install'
                sh "docker-compose down -v"
            }
        }

        stage('sonar-scanner') {
            steps{
                script {
                    def sonarqubeScannerHome = tool name: 'sonar', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                    withCredentials([string(credentialsId: 'sonar', variable: 'sonarLogin')]) {
                        sh "${sonarqubeScannerHome}/bin/sonar-scanner -e -Dsonar.host.url=http://localhost:8084 -Dsonar.login=${sonarLogin} -Dsonar.projectName=call_for_service -Dsonar.projectVersion=1 -Dsonar.projectKey=GS -Dsonar.coverage.jacoco.xmlReportPaths=coverage/target/coverage-report/coverage-report.xml -Dsonar.language=java -Dsonar.java.binaries=."
                    }
                }
            }
        }
    }
}