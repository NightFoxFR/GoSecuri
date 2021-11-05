pipeline {
  agent any
  stages {
    stage('Compiler') {
      steps {
        sh 'mvn --version && cd GoSecuriApp && mvn package && ls -a'
      }
    }

  }
}