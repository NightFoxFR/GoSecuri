pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'cd GoSecuriApp && mvn --version && mvn clean && mvn package && mvn install'
      }
    }

    stage('Run') {
      steps {
        sh 'cd GoSecuriApp && java -jar target/GoSecuriApp-1.0-SNAPSHOT.jar && ls -la'
        sh 'docker ps'
        sh 'cp GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/.htpasswd   $HOME/gosecuri/'
        sh 'cp GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/*  $HOME/gosecuri/'
        sh 'cp -r GoSecuriApp/src/main/java/com/epsi/gosecuri/ressourceFiles/ $HOME/gosecuri/'
        stash(includes: 'GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/*', name: 'generatedFiles')
        stash(includes: 'GoSecuriApp/src/main/java/com/epsi/gosecuri/ressourceFiles/*', name: 'ressourceFiles')
      }
    }

    stage('Deploy') {
      steps {
        //sh 'pwd && cat /etc/nginx/conf.d/default.conf'
        //dir('/usr/share/nginx/html'){
        dir(path: 'html') {
          unstash 'generatedFiles'
          unstash 'ressourceFiles'
        }
        //}
        sh 'pwd && ls -a'
        sh 'ls -a GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/'

        sh 'cp GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/.htpasswd  /var/wwww/html/'
        sh 'cp GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/*  /var/wwww/html/'
        sh 'cp -r GoSecuriApp/src/main/java/com/epsi/gosecuri/ressourceFiles/ /var/wwww/html/'
        
         //sh 'cp html/GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/.htpasswd  /var/wwww/html/'
        //sh 'cp html/GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/*  /var/wwww/html/'
        //sh 'cp -r html/GoSecuriApp/src/main/java/com/epsi/gosecuri/ressourceFiles/ /var/wwww/html/'
        }
      }
    

  }
  tools {
    maven 'Maven'
    nodejs 'NodeJS'
  }
}
