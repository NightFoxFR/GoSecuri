pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'ls -la && cd GoSecuriApp && mvn --version && mvn clean && mvn package && mvn install'
      }
    }

    stage('Run') {
      steps {
        sh 'ls -la && cd GoSecuriApp && java -jar target/GoSecuriApp-1.0-SNAPSHOT.jar && ls -la'
        sh 'cd GoSecuriApp/src/main/java/com/epsi/gosecuri/ && ls -la'
        stash includes: 'GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/**/*', name: 'generatedFiles'
        stash includes: 'GoSecuriApp/src/main/java/com/epsi/gosecuri/ressourceFiles/**/*', name: 'ressourceFiles'
      }
    }

    stage('Deploy') {
      agent {
        docker {
          image 'gosecuri:latest'
        }
      }
      steps {
        sh 'cat /etc/nginx/conf.d/default.conf'
        //dir('/usr/share/nginx/html'){
        dir('/'){
          unstash 'generatedFiles'
          unstash 'ressourceFiles'
        }
        sh 'ls -la'
      }
    }

  }
  tools {
    maven 'Maven'
    nodejs 'NodeJS'
  }
}
