pipeline {
  agent any
  stages {
    stage('Get JWT Token') {
      steps {
        script{
          withCredentials(
            [usernamePassword(
              credentialsId: 'Portainer',
              usernameVariable: 'sk4m',
              passwordVariable: 'Yakuza89!Alaplage'
            )]
            {
              def json = """
                {"Username": "sk4m", "Password": "Yakuza89!Alaplage"}
              """
              def jwtResponse = httpRequest acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', validResponseCodes: '200', httpMode: 'POST', ignoreSslErrors: true, requestBody: json, url: "https://manage-portainer.sk4m.fr/api/auth"
              def jwtObject = new groovy.json.JsonSlurper().parseText(jwtResponse.getContent())
              env.JWTTOKEN = "Bearer ${jwtObject.jwt}"
            }
          )
        } 
      }
    }

  }
}
