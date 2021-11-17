pipeline {
  agent any
  tools {
    maven 'Maven'
  }
  stages {
    stage('Compiler') {
      steps {
         sh 'cd GoSecuriApp '
         sh 'mvn --version'
         sh 'mvn clean'
         sh 'mvn package'
         sh 'mvn install'
      }
    }

  }
}
