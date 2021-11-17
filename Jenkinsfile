pipeline {
  agent any
  stages {
    stage('Compiler') {
      steps {
         sh 'ls -a && pwd && cd GoSecuriApp && mvn package && ls -a'
      }
    }

  }
}
