pipeline {
  agent any
  tools {
    maven 'Maven'
  }
  stages {
    stage('Compiler') {
      steps {
         sh 'ls -la && cd GoSecuriApp && mvn --version && mvn clean && mvn package && mvn install'
      }
    }

  }
}
