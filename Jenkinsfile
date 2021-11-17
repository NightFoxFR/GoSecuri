pipeline {
    agent any
    tools {
        maven 'Maven'
        nodejs 'NodeJS'
    }
    stages {
        stage('Build') {
            steps {
                sh 'ls -la && cd GoSecuriApp && mvn --version && mvn clean && mvn package && mvn install'
            }
        }
        stage('Run') {
            steps {
                sh 'ls -la && cd GoSecuriApp && java -jar target/GoSecuriApp-1.0-SNAPSHOT.jar'
            }
        }
        stage('Deploy') {
            steps {
                sh 'pwd'
            }
        }    
    }
}
