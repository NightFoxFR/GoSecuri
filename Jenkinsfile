pipeline {
  agent any
  stages {
    stage('Compiler') {
      steps {
        sh 'cd GoSecuritiApp && mvn package && ls -a'
      }
    }

  }
}