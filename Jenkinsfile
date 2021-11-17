pipeline {
  agent any
  tools {
    maven 'Maven'
  }
  stages {
    stage('Compiler') {
      steps {
         sh 'ls -la && cd GoSecuriApp '
         sh 'ls -la && mvn --version'
         sh 'ls -la && mvn clean'
         sh 'ls -la && mvn package'
         sh 'ls -la && mvn install'
      }
    }

  }
}
