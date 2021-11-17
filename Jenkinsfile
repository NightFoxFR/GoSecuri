pipeline {
  agent any
  stages {
    stage('Compiler') {
      steps {
         sh 'mvn --version && ls -a && pwd && cd GoSecuriApp && mvn package && ls -a'
      }
    }

  }
}
