pipeline {
  agent any
  stages {
    stage('Compiler') {
      steps {
        sh 'cd GoSecuriApp && mvn package && ls -a'
      }
    }

  }
}