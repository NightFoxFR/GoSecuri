pipeline {
  agent any
  tools {
    maven 'Maven'
  }
  stages {
    stage('Compiler') {
      steps {
         sh 'ls -la && cd GoSecuriApp && mvn --version && mvn clean && mvn package && mvn install && java -jar target/GoSecuriApp-1.0-SNAPSHOT.jar'
      }
    }
    stage('Run') {
      steps {
         sh 'ls -la && cd GoSecuriApp && java -jar target/GoSecuriApp-1.0-SNAPSHOT.jar'
      }
    }

  }
}
