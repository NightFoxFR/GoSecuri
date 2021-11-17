pipeline {
  agent any
  tools {
    maven 'Maven'
  }
  stages {
    stage('Compiler') {
      steps {
         sh 'cd GoSecuriApp && mvn --version && ls -a && pwd &&  mvn package && ls -a'
      }
    }

  }
}
